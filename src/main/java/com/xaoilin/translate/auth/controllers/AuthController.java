package com.xaoilin.translate.auth.controllers;

import com.xaoilin.translate.auth.jwt.JwtUtils;
import com.xaoilin.translate.auth.payload.request.LoginRequest;
import com.xaoilin.translate.auth.payload.request.SignupRequest;
import com.xaoilin.translate.auth.payload.response.JwtResponse;
import com.xaoilin.translate.auth.payload.response.MessageResponse;
import com.xaoilin.translate.auth.services.UserDetailsImpl;
import com.xaoilin.translate.auth.validation.RegisterValidation;
import com.xaoilin.translate.database.model.Status;
import com.xaoilin.translate.database.repository.RoleRepository;
import com.xaoilin.translate.database.repository.UserRepository;
import com.xaoilin.translate.database.model.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//        List<String> roles = userDetails.getAuthorities().stream()
//                .map(item -> item.getAuthority())
//                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getEmail()
                ));
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        if (!RegisterValidation.isEmailValid(signupRequest.getEmail()) || !RegisterValidation.isPasswordValid(signupRequest.getPassword())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Bad email / password"));
        }

        AuthUser user = new AuthUser(signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()),
                Status.PENDING_EMAIL_CONFIRMATION.name());

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


}
