package dev.zykov.micronaut.service;

import dev.zykov.micronaut.dto.Right;
import dev.zykov.micronaut.entity.RightEntity;
import dev.zykov.micronaut.entity.RoleRightEntity;
import dev.zykov.micronaut.entity.RoleRightId;
import dev.zykov.micronaut.repository.RightRepository;
import dev.zykov.micronaut.repository.RoleRightRepository;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
@RequiredArgsConstructor
public class RightService {

    private final RoleRightRepository roleRightRepository;
    private final RightRepository rightRepository;

    public List<Right> getRightByRoleId(Integer roleId) {
        var roleRights = roleRightRepository.findAllByRoleRightIdRoleId(roleId)
                .stream()
                .map(RoleRightEntity::getRoleRightId)
                .map(RoleRightId::getRightId)
                .collect(Collectors.toSet());

        return rightRepository.findAllByIdIn(roleRights)
                .stream()
                .map(RightEntity::toDto)
                .collect(Collectors.toList());
    }
}
