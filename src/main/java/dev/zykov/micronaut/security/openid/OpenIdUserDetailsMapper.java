package dev.zykov.micronaut.security.openid;

import dev.zykov.micronaut.security.CommonUserDetailsMapper;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.oauth2.endpoint.authorization.state.State;
import io.micronaut.security.oauth2.endpoint.token.response.DefaultOpenIdAuthenticationMapper;
import io.micronaut.security.oauth2.endpoint.token.response.OpenIdAuthenticationMapper;
import io.micronaut.security.oauth2.endpoint.token.response.OpenIdClaims;
import io.micronaut.security.oauth2.endpoint.token.response.OpenIdTokenResponse;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

@Named("google")
@Singleton
@Replaces(DefaultOpenIdAuthenticationMapper.class)
@RequiredArgsConstructor
public class OpenIdUserDetailsMapper implements OpenIdAuthenticationMapper {

    private final CommonUserDetailsMapper commonUserDetailsMapper;

    @Override
    public @NonNull Publisher<AuthenticationResponse> createAuthenticationResponse(
            String providerName,
            OpenIdTokenResponse tokenResponse,
            OpenIdClaims openIdClaims,
            @Nullable State state
    ) {

        return Flux.just(
                commonUserDetailsMapper
                        .getAuthentication(openIdClaims.getName(), openIdClaims.getNickname())
        );
    }
}

