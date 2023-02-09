package com.assesstment.bvk.auth.services;

import com.assesstment.bvk.auth.config.JwtUtil;
import com.assesstment.bvk.auth.dto.RequestLoginDTO;
import com.assesstment.bvk.auth.dto.RequestRegisterDTO;
import com.assesstment.bvk.auth.models.User;
import com.assesstment.bvk.auth.repositories.UserRepository;
import com.assesstment.bvk.auth.utility.GenerateResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    JwtUtil jwt;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private ModelMapper modelMapper;

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

    public ResponseEntity<String> register(RequestRegisterDTO request) throws JsonProcessingException {
        Optional<User> checkUser = userRepository.findByUserEmail(request.getUserEmail());
        if (checkUser.isPresent()) {
            return GenerateResponse.badRequest("Email was already taken", null);
        }
        if (!request.getUserPass().equals(request.getUserConfirmPass())) {
            return GenerateResponse.badRequest("Password and confirmation password not match", null);
        }
        try {
            User user = modelMapper.map(request, User.class);
            user.setUserPass(encoder.encode(request.getUserPass()));
            user.setUserUuid(UUID.randomUUID().toString());
            user.setCreatedBy(user.getUserId());
            user.setCreatedDate(new Date());
            userRepository.save(user);
            return GenerateResponse.success("Register success", null);
        } catch (Exception e) {
            e.printStackTrace();
            return GenerateResponse.error("Internal server error", null);
        }
    }
}
