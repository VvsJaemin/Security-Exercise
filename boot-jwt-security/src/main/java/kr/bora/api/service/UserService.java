package kr.bora.api.service;

import kr.bora.api.domain.Authority;
import kr.bora.api.domain.User;
import kr.bora.api.dto.UserRequestDto;
import kr.bora.api.dto.UserResponseDto;
import kr.bora.api.repository.UserRepository;
import kr.bora.api.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
public interface UserService {

    UserRequestDto modify(UserRequestDto userRequestDto);

    default User dtoEntity(UserRequestDto userRequestDto, PasswordEncoder passwordEncoder) {
        User entity = User.builder()
                .id(userRequestDto.getId())
                .email(userRequestDto.getEmail())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .authority(Authority.ROLE_USER)
                .build();
        return entity;
    }

    default UserRequestDto entityDto(User user, PasswordEncoder passwordEncoder) {
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .authority(Authority.ROLE_USER)
                .build();

        return userRequestDto;
    }

}
