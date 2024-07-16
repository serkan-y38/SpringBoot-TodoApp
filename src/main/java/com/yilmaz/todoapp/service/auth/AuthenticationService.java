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
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static com.yilmaz.todoapp.utils.MyResponseMessages.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Async
    public Future<ResponseEntity<?>> register(RegisterRequest request) {

        if (isRegisterRequestInvalid(request))
            return CompletableFuture.completedFuture(
                    new ResponseEntity<>(INVALID_REGISTER_REQUEST_MESSAGE, HttpStatus.NOT_ACCEPTABLE)
            );

        if (isMailAddressExist(request.getEmail()))
            return CompletableFuture.completedFuture(new ResponseEntity<>(EMAIL_EXISTS_MESSAGE, HttpStatus.NOT_ACCEPTABLE));

        try {
            repository.save(buildUserFromRequest(request));
        } catch (Exception e) {
            return CompletableFuture.completedFuture(new ResponseEntity<>(ERROR_SAVING_USER_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR));
        }

        return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.OK).build());
    }

    @Async
    public Future<ResponseEntity<?>> login(LoginRequest request, HttpServletResponse response) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (BadCredentialsException e) {
            return CompletableFuture.completedFuture(new ResponseEntity<>(WRONG_CREDENTIALS_MESSAGE, HttpStatus.FORBIDDEN));
        }

        final Optional<User> user = repository.findByEmail(request.getEmail());

        if (user.isEmpty())
            return CompletableFuture.completedFuture(new ResponseEntity<>(USER_NOT_FOUND_MESSAGE, HttpStatus.FORBIDDEN));

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        final String jwt = jwtService.generateToken(new HashMap<>(), userDetails);

        try {
            response.addHeader("Authorization", "Bearer " + jwt);
            response.getWriter().write(
                    new JSONObject().put("id", user.get().getId())
                            .put("role", user.get().getRole())
                            .toString()
            );
        } catch (IOException | JSONException e) {
            CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        }

        return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.OK).build());
    }

    private boolean isRegisterRequestInvalid(RegisterRequest request) {
        return request.getFirstname().isEmpty() ||
                request.getLastname().isEmpty() ||
                request.getEmail().isEmpty() ||
                request.getPassword().isEmpty();
    }

    private User buildUserFromRequest(RegisterRequest request) {
        return User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
    }

    private boolean isMailAddressExist(String email) {
        return repository.findByEmail(email).isPresent();
    }

}
