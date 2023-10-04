package dev.zykov.micronaut.service;

import dev.zykov.micronaut.dto.User;
import dev.zykov.micronaut.entity.UserEntity;
import dev.zykov.micronaut.repository.UserRepository;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.exceptions.HttpStatusException;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final RightService rightService;

    public User getUserByExternalId(String externalId, String userName) {
        var userEntity = userRepository.findByExternalId(externalId);
        if (userEntity.isPresent()) {
            return getUserDto(userEntity.get());
        }
        return saveUser(externalId, userName);
    }

    public User getUserById(Integer id) {
        var userEntity = userRepository.findById(id)
                .orElseThrow(() -> new HttpStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return getUserDto(userEntity);
    }

    private User getUserDto(UserEntity userEntity) {
        var role = roleService.getUserRole(userEntity.getUserRoleId());
        var rights = rightService.getRightByRoleId(userEntity.getUserRoleId());

        return userEntity.toDto(role, rights);
    }

    public User saveUser(String externalId, String userName) {
        var defaultRole = roleService.getRoleByRoleName("DEFAULT_ROLE");
        var rights = rightService.getRightByRoleId(defaultRole.id());
        return userRepository.save(
                UserEntity.builder()
                        .externalId(externalId)
                        .userName(userName)
                        .userRoleId(defaultRole.id())
                        .build()
        ).toDto(defaultRole, rights);
    }
}
