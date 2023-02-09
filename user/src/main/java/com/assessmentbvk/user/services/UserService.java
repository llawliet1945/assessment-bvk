package com.assessmentbvk.user.services;

import com.assessmentbvk.user.dto.RequestUpdateUser;
import com.assessmentbvk.user.dto.ResponseDetailUser;
import com.assessmentbvk.user.models.User;
import com.assessmentbvk.user.repositories.UserRepository;
import com.assessmentbvk.user.utility.GenerateResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<String> detailUser(Integer userId) throws JsonProcessingException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return GenerateResponse.notFound("User not found", null);
        }
        ResponseDetailUser response = modelMapper.map(user.get(), ResponseDetailUser.class);
        return GenerateResponse.success("Get data success", response);
    }

    public ResponseEntity<String> update(Integer userId, RequestUpdateUser request) throws JsonProcessingException {
        Optional<User> checkUser = userRepository.findById(userId);
        if (checkUser.isEmpty()) {
            return GenerateResponse.notFound("User not found", null);
        }
        User user = modelMapper.map(request, User.class);
        user.setUserId(checkUser.get().getUserId());
        user.setUserUuid(checkUser.get().getUserUuid());
        user.setUserEmail(checkUser.get().getUserEmail());
        user.setUserStatus(checkUser.get().getUserStatus());
        user.setCreatedBy(checkUser.get().getCreatedBy());
        user.setCreatedDate(new Date());
        user.setUpdatedBy(userId);
        user.setUpdatedDate(new Date());
        userRepository.save(user);
        return GenerateResponse.success("Update data success", null);
    }
}
