package dev.zykov.micronaut.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

@Controller("/intercept")
public class InterceptURLMapController {

    @Produces(MediaType.TEXT_PLAIN)
    @Get("/allowed-to-everyone")
    public String allowedToEveryOne() {
        return "Allowed to everyone";
    }

    @Produces(MediaType.TEXT_PLAIN)
    @Get("/allowed-to-authenticated")
    public String onlyAuthenticated() {
        return "Only authenticated";
    }

    @Produces(MediaType.TEXT_PLAIN)
    @Get("/allowed-to-admin")
    public String onlyAdmin() {
        return "Only admin";
    }

}
