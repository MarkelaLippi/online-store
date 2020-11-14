package com.gmail.roadtojob2019.onlinestore.service.mapper;

import com.gmail.roadtojob2019.onlinestore.repository.entity.User;
import com.gmail.roadtojob2019.onlinestore.service.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto fromUserToDto(User user);

    User fromDtoToUser(UserDto userDto);
}
