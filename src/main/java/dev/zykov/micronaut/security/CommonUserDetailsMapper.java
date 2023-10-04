package dev.zykov.micronaut.security;

import dev.zykov.micronaut.dto.Right;
import dev.zykov.micronaut.service.UserService;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.stream.Collectors;

@Singleton
@RequiredArgsConstructor
public class CommonUserDetailsMapper {

    private final UserService userService;

    public AuthenticationResponse getAuthentication(String externalId, String nickName) {
        var user = userService.getUserByExternalId(externalId, nickName);

        return AuthenticationResponse.success(
                user.userName(),
                user.rights()
                        .stream().map(Right::name).collect(Collectors.toList()),
                Map.of(
                        "role", user.role().name(),
                        "id", user.id()
                )
        );
    }
}
