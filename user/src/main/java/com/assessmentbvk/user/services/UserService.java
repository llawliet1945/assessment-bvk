package com.assessmentbvk.user.services;

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
        ResponseDetailUser response = modelMapper.map(user.get(), ResponseDetailUser.class);
        return GenerateResponse.success("Get data success", response);
    }
}
