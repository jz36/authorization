package dev.zykov.micronaut.dto;

import java.util.List;

public record User (
        Integer id,
        String userName,
        Role role,
        List<Right> rights
) {
}
