package dev.vitorvidal.petmanagementapi.auth;

import dev.vitorvidal.petmanagementapi.auth.filter.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class HttpSecurity {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    public HttpSecurity(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtRequestFilter jwtRequestFilter,
                        UserDetailsService userService) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtUserDetailsService = userService;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // Configure the authentication manager to use the user details service
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new AuthenticationTokenFilter();
    }

    @Bean
    public SecurityFilterChain configure(org.springframework.security.config.annotation.web.builders.HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.cors()
                .and().authorizeHttpRequests().requestMatchers("/rest/v1/user/**").permitAll()
                .anyRequest().authenticated().and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable();

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}

/*
* GitHub Copilot: To implement a logic using `doFilter` from Spring to verify user authentication in the login endpoint and generate a JWT token in the response, you can create a custom filter that intercepts requests to the login endpoint and performs the necessary authentication and token generation.

Here's an example of how you can implement this:

1. Create a new class that extends `OncePerRequestFilter` and overrides the `doFilterInternal` method:

```java
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Get the authorization header from the request
        final String header = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // Check if the authorization header is present and formatted correctly
        if (header != null && header.startsWith("Bearer ")) {
            // Extract the JWT token from the authorization header
            jwt = header.substring(7);
            try {
                // Extract the username from the JWT token
                username = jwtTokenUtil.getUsernameFromToken(jwt);
            } catch (IllegalArgumentException e) {
                logger.error("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                logger.error("JWT Token has expired");
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }

        // If the username is present and the user is not already authenticated
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Load the user details from the database
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // If the JWT token is valid, authenticate the user
            if (jwtTokenUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        // Continue the filter chain
        chain.doFilter(request, response);
    }
}
```

2. In the `doFilterInternal` method, extract the JWT token from the authorization header of the request, extract the username from the JWT token, and load the user details from the database.

3. If the JWT token is valid, authenticate the user by creating a new `UsernamePasswordAuthenticationToken` and setting it in the `SecurityContextHolder`.

4. Add the `JwtAuthenticationFilter` to the Spring Security filter chain by adding the following configuration to your `WebSecurityConfigurerAdapter`:

```java
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // Configure the authentication manager to use the user details service
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new AuthenticationTokenFilter();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // Add the JWT authentication filter to the filter chain
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // Configure the login endpoint to permit all requests
        httpSecurity.csrf().disable().authorizeRequests().antMatchers("/login").permitAll().anyRequest()
                .authenticated().and().exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
```

5. In the `configure` method of your `WebSecurityConfigurerAdapter`, add the `JwtAuthenticationFilter` to the filter chain using the `addFilterBefore` method. Also, configure the login endpoint to permit all requests and set the authentication entry point to the `JwtAuthenticationEntryPoint`.

6. In your login endpoint, generate a JWT token and add it to the response header:

```java
@PostMapping("/login")
public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

    // Authenticate the user
    authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

    // Generate the JWT token
    final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
    final String token = jwtTokenUtil.generateToken(userDetails);

    // Add the JWT token to the response header
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + token);

    // Return the JWT token in the response body
    return ResponseEntity.ok().headers(headers).body(new JwtResponse(token));
}
```

In this example, the `JwtTokenUtil` class is used to generate and validate JWT tokens, and the `UserDetailsService` is used to load user details from the database. You will need to implement these classes to fit your specific use case.

I hope this helps! Let me know if you have any further questions.
*
*
*  */