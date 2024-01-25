package com.nicmsaraiva.demoparkapi.web.dto.mapper;

import com.nicmsaraiva.demoparkapi.entity.User;
import com.nicmsaraiva.demoparkapi.web.dto.UserCreateDTO;
import com.nicmsaraiva.demoparkapi.web.dto.UserResponseDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    public static User toUser(UserCreateDTO userCreateDTO) {
        return new ModelMapper().map(userCreateDTO, User.class);
    }

    public static UserResponseDTO toDTO(User user) {
        String role = user.getRole().name().substring("ROLE_".length());
        PropertyMap<User, UserResponseDTO> props = new PropertyMap<User, UserResponseDTO>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };

        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(user, UserResponseDTO.class);
    }

    public static List<UserResponseDTO> listToDTO(List<User> users) {
        List<UserResponseDTO> dtos = new ArrayList<>();
        // or -> List<UserResponseDTO> usersDTO = users.stream().map(UserMapper::toDTO).toList();
        for(User user : users) {
            dtos.add(toDTO(user));
        }
        return dtos;
    }
}
