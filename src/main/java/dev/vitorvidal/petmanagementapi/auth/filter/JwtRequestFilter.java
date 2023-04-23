package dev.vitorvidal.petmanagementapi.auth.filter;

import dev.vitorvidal.petmanagementapi.auth.JwtToken;
import dev.vitorvidal.petmanagementapi.domain.service.impl.UserServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final UserServiceImpl userServiceImpl;
    private final JwtToken jwtToken;

    public JwtRequestFilter(@Lazy UserServiceImpl userServiceImpl, JwtToken jwtToken) {
        this.userServiceImpl = userServiceImpl;
        this.jwtToken = jwtToken;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");

        String contextPath = request.getRequestURI();
        if (Objects.equals(contextPath, "/rest/v1/user/signup") || Objects.equals(contextPath, "/rest/v1/user/login")) {
            filterChain.doFilter(request, response);
        }

        String username;
        String jwtToken;

        if (Objects.nonNull(requestTokenHeader) && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtToken.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                log.error("Unable to get JWT token");
                return;
            } catch (ExpiredJwtException e) {
                log.error("JWT token has expired");
                return;
            }
        } else {
            log.warn("JWT token does not begin with Bearer String");
            return;
        }

        if (Objects.nonNull(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            UserDetails userDetails = null;

            if (jwtToken.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
