package com.ness.bookmanagement.controller;

import com.ness.bookmanagement.config.JwtUtil;
import com.ness.bookmanagement.dto.AuthRequestDTO;
import com.ness.bookmanagement.exception.UserNotAuthenticatedException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Authorization Controller")
@Slf4j
@RestController
public class AuthorizationController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @ApiOperation(value = "Generate Authentication Token")
    @PostMapping("/authenticate")
    public ResponseEntity<String> generateToken(@RequestBody AuthRequestDTO authRequest) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    authRequest.getUserName(),
                    authRequest.getPassword()
            );

            authenticationManager.authenticate(authenticationToken);

        } catch (Exception ex) {
            log.error("Authentication Failed: message=" + ex.getMessage());
            throw new UserNotAuthenticatedException("User is not authenticated");
        }

        final String token = jwtUtil.generateToken(authRequest.getUserName());
        return ResponseEntity.status(HttpStatus.CREATED).body(token);
    }
}
