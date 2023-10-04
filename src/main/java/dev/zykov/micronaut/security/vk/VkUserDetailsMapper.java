package dev.zykov.micronaut.security.vk;

import dev.zykov.micronaut.security.CommonUserDetailsMapper;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.oauth2.endpoint.authorization.state.State;
import io.micronaut.security.oauth2.endpoint.token.response.OauthAuthenticationMapper;
import io.micronaut.security.oauth2.endpoint.token.response.TokenResponse;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

@Named("vk") // Bean должен иметь данную аннотацию с тем же значением, которое было написано в файле конфигураций
@Singleton
@RequiredArgsConstructor
public class VkUserDetailsMapper implements OauthAuthenticationMapper {
    private final VkApiClient vkApiClient;
    private final CommonUserDetailsMapper commonUserDetailsMapper;

    @Override
    public Publisher<AuthenticationResponse> createAuthenticationResponse(
        TokenResponse tokenResponse, @Nullable State state) {
        return Flux.from(vkApiClient.getUser(tokenResponse.getAccessToken(), "5.89", "domain"))
            .map(response -> {
                var user = response.getResponse().get(0);
                return commonUserDetailsMapper.getAuthentication(user.getId(), user.getFirstName() + user.getLastName());
            });
    }
}
