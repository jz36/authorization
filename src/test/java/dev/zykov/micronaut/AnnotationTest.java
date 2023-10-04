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
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@MicronautTest
public class AnnotationTest {

    @Inject
    JwtTokenGenerator jwtTokenGenerator;

    @Inject
    @Client("/annotation")
    HttpClient client;


    @Test
    public void shouldReturn200_whenGoingToAnyone_givenNothing() {
        var request = HttpRequest.GET("/anyone")
                .accept(MediaType.TEXT_PLAIN_TYPE);
        var response = client.toBlocking().exchange(request, String.class);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Allowed to everyone", response.body());
    }

    @Test
    public void shouldReturnUnauthorized_whenGoingToOnlyAuthenticated_givenNoAuthentication() {
        HttpClientResponseException exception = assertThrows(
                HttpClientResponseException.class,
                () -> client.toBlocking().exchange(HttpRequest.GET("/only-authenticated"))
        );

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
    }

    @Test
    public void shouldReturn200_whenGoingToOnlyAuthenticated_givenAuthentication() {
        var authentication = Authentication.build("testUser", List.of("ROLE_SOME"));
        var token = jwtTokenGenerator.generateToken(authentication, 1000);

        var request = HttpRequest.GET("/only-authenticated")
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .cookie(Cookie.of("JWT", token.get()));
        var response = client.toBlocking().exchange(request, String.class);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Allowed only to authenticated users", response.body());
    }

    @Test
    public void shouldReturn200_whenGoingToOnlyForAdmin_givenAuthentication() {
        var authentication = Authentication.build("testUser", List.of("ROLE_ADMIN"));
        var token = jwtTokenGenerator.generateToken(authentication, 1000);

        var request = HttpRequest.GET("/only-admins")
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .cookie(Cookie.of("JWT", token.get()));
        var response = client.toBlocking().exchange(request, String.class);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Allowed only to admins", response.body());
    }

    @Test
    public void shouldReturn200_whenGoingToSecuredWithExpression_givenAuthentication() {
        var authentication = Authentication.build(
                "testUser",
                List.of("ROLE_RANDOM"),
                Map.of("monthOfBirth", "September")
                );
        var token = jwtTokenGenerator.generateToken(authentication, 1000);

        var request = HttpRequest.GET("/secured-with-expression")
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .cookie(Cookie.of("JWT", token.get()));
        var response = client.toBlocking().exchange(request, String.class);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("secured with expression", response.body());
    }
}
