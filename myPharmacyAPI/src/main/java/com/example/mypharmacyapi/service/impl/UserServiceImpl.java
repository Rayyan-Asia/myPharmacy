package com.example.mypharmacyapi.service.impl;

import com.example.mypharmacyapi.dto.UserDto;
import com.example.mypharmacyapi.dto.UserDto;
import com.example.mypharmacyapi.dto.UserInsertDto;
import com.example.mypharmacyapi.entity.User;
import com.example.mypharmacyapi.repository.UserRepository;
import com.example.mypharmacyapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Override
    public UserDto insertUser(UserInsertDto userInsertDto) {
        User user = modelMapper.map(userInsertDto, User.class);
        User newUser = userRepository.save(user);
        return modelMapper.map(newUser, UserDto.class);
    }

    @Override
    public UserDto getUser(String id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return modelMapper.map(user.get(), UserDto.class);
        } else {
            return null;
        }
    }
}
