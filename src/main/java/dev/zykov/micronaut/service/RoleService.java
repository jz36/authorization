package dev.zykov.micronaut.service;

import dev.zykov.micronaut.dto.Role;
import dev.zykov.micronaut.repository.RoleRepository;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.exceptions.HttpStatusException;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getUserRole(Integer roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new HttpStatusException(HttpStatus.NOT_FOUND, "Role doesn't exist"))
                .toDto();
    }

    public Role getRoleByRoleName(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new HttpStatusException(HttpStatus.NOT_FOUND, "Role doesn't exist"))
                .toDto();}
}
