package com.yilmaz.todoapp.service.auth;

import com.yilmaz.todoapp.dto.auth.LoginRequest;
import com.yilmaz.todoapp.dto.auth.RegisterRequest;
import com.yilmaz.todoapp.entity.User;
import com.yilmaz.todoapp.entity.enums.Role;
import com.yilmaz.todoapp.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public ResponseEntity<?> register(RegisterRequest request) {
        if (!isMailAddressExist(request.getEmail())) {
            repository.save(
                    User.builder()
                            .firstname(request.getFirstname())
                            .lastname(request.getLastname())
                            .email(request.getEmail())
                            .password(passwordEncoder.encode(request.getPassword()))
                            .role(Role.USER)
                            .build()
            );
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    public void authenticate(
            LoginRequest request,
            HttpServletResponse response
    ) throws IOException, JSONException {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        final String jwt = jwtService.generateToken(new HashMap<>(), userDetails);
        Optional<User> user = repository.findByEmail(request.getEmail());

        if (user.isPresent())
            response.getWriter().write(
                    new JSONObject().
                            put("id", user.get().getId()).
                            put("role", user.get().getRole()).toString()
            );

        response.addHeader("Authorization", "Bearer " + jwt);
    }

    private boolean isMailAddressExist(String email) {
        return repository.findByEmail(email).isPresent();
    }

}