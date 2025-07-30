package com.budgetPal.mapper;

import com.budgetPal.dto.UserModelDto;
import com.budgetPal.dto.UserRegisterDto;
import com.budgetPal.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", imports = {java.time.LocalDateTime.class, com.budgetPal.model.Role.class})
public interface UserModelMapper {


    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "role", expression = "java(Role.USER)")
    User toEntity(UserRegisterDto userRegisterDto);

    UserModelDto toDto(User user);

    List<UserModelDto> toDto(List<User> users);
}
