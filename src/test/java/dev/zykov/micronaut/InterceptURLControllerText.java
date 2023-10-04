package dev.zykov.micronaut;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.cookie.Cookie;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.token.jwt.generator.JwtTokenGenerator;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@MicronautTest
public class InterceptURLControllerText {


    @Inject
    JwtTokenGenerator jwtTokenGenerator;

    @Inject
    @Client("/intercept")
    HttpClient client;

    @Test
    public void shouldReturn200_whenGoingToAnyone_givenNothing() {
        var request = HttpRequest.GET("/allowed-to-everyone")
                .accept(MediaType.TEXT_PLAIN_TYPE);
        var response = client.toBlocking().exchange(request, String.class);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Allowed to everyone", response.body());
    }

    @Test
    public void shouldReturn401_whenGoingToAuthenticated_givenNothing() {
        var request = HttpRequest.GET("/allowed-to-authenticated")
                .accept(MediaType.TEXT_PLAIN_TYPE);
        var response = assertThrows(
                HttpClientResponseException.class,
                () -> client.toBlocking().exchange(request, String.class)
        );

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatus());
    }

    @Test
    public void shouldReturn200_whenGoingToAuthenticated_givenAuthenticated() {
        var authentication = Authentication.build("testUser", List.of("ROLE_SOME"));
        var token = jwtTokenGenerator.generateToken(authentication, 1000);
        var request = HttpRequest.GET("/allowed-to-authenticated")
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .cookie(Cookie.of("JWT", token.get()));
        var response = client.toBlocking().exchange(request, String.class);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Only authenticated", response.body());
    }

    @Test
    public void shouldReturn403_whenGoingToAdmins_givenNotAdmin() {
        var authentication = Authentication.build("testUser", List.of("ROLE_SOME"));
        var token = jwtTokenGenerator.generateToken(authentication, 1000);
        var request = HttpRequest.GET("/allowed-to-admin")
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .cookie(Cookie.of("JWT", token.get()));
        var response = assertThrows(
                HttpClientResponseException.class,
                () -> client.toBlocking().exchange(request, String.class)
        );

        assertEquals(HttpStatus.FORBIDDEN, response.getStatus());
    }

    @Test
    public void shouldReturn200_whenGoingToAdmins_givenAdmin() {
        var authentication = Authentication.build("testUser", List.of("ROLE_ADMIN"));
        var token = jwtTokenGenerator.generateToken(authentication, 1000);
        var request = HttpRequest.GET("/allowed-to-admin")
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .cookie(Cookie.of("JWT", token.get()));
        var response = client.toBlocking().exchange(request, String.class);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Only admin", response.body());
    }

}
