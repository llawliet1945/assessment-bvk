package com.assesstment.bvk.auth.services;

import com.assesstment.bvk.auth.config.JwtUtil;
import com.assesstment.bvk.auth.dto.RequestLoginDTO;
import com.assesstment.bvk.auth.models.User;
import com.assesstment.bvk.auth.repositories.UserRepository;
import com.assesstment.bvk.auth.utility.GenerateResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    JwtUtil jwt;

    @Autowired
    private BCryptPasswordEncoder encoder;

        public ResponseEntity<String> login(RequestLoginDTO request) throws JsonProcessingException {
        Optional<User> checkUser = userRepository.findByUserEmail(request.getUsername());
        if (checkUser.isEmpty()) {
            return GenerateResponse.notFound("user not found", null);
        }
        if (!encoder.matches(request.getPassword(), checkUser.get().getUserPass())) {
            return GenerateResponse.badRequest("password not match", null);
        }
        String accessToken = jwt.generate(checkUser.get(), "ACCESS");
        return GenerateResponse.success("Login Success", accessToken);
    }
}
