package com.gmail.roadtojob2019.onlinestore.service.mapper;

import com.gmail.roadtojob2019.onlinestore.repository.entity.User;
import com.gmail.roadtojob2019.onlinestore.service.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mappings({
            @Mapping(target="lastName", source="lastMiddleFirstName.lastName"),
            @Mapping(target="middleName", source="lastMiddleFirstName.middleName"),
            @Mapping(target="firstName", source="lastMiddleFirstName.firstName")
    })

    UserDto fromUserToDto(User user);
    @Mappings({
            @Mapping(target="lastMiddleFirstName.lastName", source="lastName"),
            @Mapping(target="lastMiddleFirstName.middleName", source="middleName"),
            @Mapping(target="lastMiddleFirstName.firstName", source="firstName")
    })
    User fromDtoToUser(UserDto userDto);
}
