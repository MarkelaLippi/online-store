package com.gmail.roadtojob2019.onlinestore.service.impl;

import com.gmail.roadtojob2019.onlinestore.repository.ReviewRepository;
import com.gmail.roadtojob2019.onlinestore.repository.UserRepository;
import com.gmail.roadtojob2019.onlinestore.repository.entity.Role;
import com.gmail.roadtojob2019.onlinestore.repository.entity.User;
import com.gmail.roadtojob2019.onlinestore.service.EmailService;
import com.gmail.roadtojob2019.onlinestore.service.RandomPasswordGenerator;
import com.gmail.roadtojob2019.onlinestore.service.UserService;
import com.gmail.roadtojob2019.onlinestore.service.dto.UserDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.UsersPageDto;
import com.gmail.roadtojob2019.onlinestore.service.exception.OnlineMarketSuchUserNotFoundException;
import com.gmail.roadtojob2019.onlinestore.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final UserMapper userMapper;
    private final RandomPasswordGenerator randomPasswordGenerator;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UsersPageDto getPageOfUsersSortedByEmail(final int pageNumber, final int pageSize) {
        final String SORTING_PARAMETER = "email";
        final PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(SORTING_PARAMETER));
        final Page<User> pageOfUsers = userRepository.findAll(pageRequest);
        final int totalNumberOfUsers = pageOfUsers.getNumberOfElements();
        final int totalNumberOfPages = pageOfUsers.getTotalPages();
        final List<UserDto> usersOnPage = pageOfUsers.stream()
                .map(userMapper::fromUserToDto)
                .collect(Collectors.toList());
        final List<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toList());
        return UsersPageDto.builder()
                .totalNumberOfUsers(totalNumberOfUsers)
                .totalNumberOfPages(totalNumberOfPages)
                .users(usersOnPage)
                .roles(roles)
                .build();
    }

    @Override
    @Transactional
    public void deleteUsersByIds(final int[] usersIds) {
        final List<Long> usersIdsAsLong = convertIntIdsToLongIds(usersIds);
        reviewRepository.deleteReviewsByUsersIds(usersIdsAsLong);
        userRepository.deleteUsersByIds(usersIdsAsLong);
    }

    private List<Long> convertIntIdsToLongIds(final int[] usersIds) {
        return Arrays.stream(usersIds)
                .mapToLong(Long::valueOf)
                .boxed()
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean changeUserPasswordAndSendItToEmail(final Long userId) throws OnlineMarketSuchUserNotFoundException {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new OnlineMarketSuchUserNotFoundException("User with id = " + userId + " was not found"));
        final String randomPassword = randomPasswordGenerator.generateRandomPassword();
        final String encodedRandomPassword = passwordEncoder.encode(randomPassword);
        user.setPassword(encodedRandomPassword);
        final User userAfterChangingPassword = userRepository.saveAndFlush(user);
        sendNewUserPasswordToEmail(user, randomPassword);
        return userAfterChangingPassword.getPassword().equals(encodedRandomPassword);
    }

    private void sendNewUserPasswordToEmail(final User user, final String newUserPassword) {
        final String userEmail = user.getEmail();
        final String MAIL_SUBJECT = "Your password was changed";
        emailService.sendNewUserPasswordToEmail(userEmail, MAIL_SUBJECT, newUserPassword);
    }

    @Override
    @Transactional
    public boolean changeUserRole(final Long userId, final String userRole) throws OnlineMarketSuchUserNotFoundException {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new OnlineMarketSuchUserNotFoundException("User with id = " + userId + " was not found"));
        final Role newUserRole = Role.valueOf(userRole);
        user.setRole(newUserRole);
        final User userAfterChangingRole = userRepository.saveAndFlush(user);
        return userAfterChangingRole.getRole().equals(newUserRole);
    }

    @Override
    public Long addUser(final UserDto newUserDto) {
        final User newUser = userMapper.fromDtoToUser(newUserDto);
        final User addedNewUser = userRepository.saveAndFlush(newUser);
        return addedNewUser.getId();
    }
}
