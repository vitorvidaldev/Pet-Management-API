package dev.vitorvidal.petmanagementapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/v1/login")
public class LoginController {
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
}
