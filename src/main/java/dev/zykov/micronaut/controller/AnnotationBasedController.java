package dev.zykov.micronaut.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/annotation")
public class AnnotationBasedController {

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Produces(MediaType.TEXT_PLAIN)
    @Get("/anyone")
    public String allowedAnyone() {
        return "Allowed to everyone";
    }

    @Produces(MediaType.TEXT_PLAIN)
    @Get("/only-authenticated")
    public String onlyAuthenticated() {
        return "Allowed only to authenticated users";
    }

    @Secured({"ROLE_ADMIN"})
    @Produces(MediaType.TEXT_PLAIN)
    @Get("/only-admins")
    public String onlyAdmins() {
        return "Allowed only to admins";
    }

    @Secured("#{ user?.attributes?.get('monthOfBirth') == 'September' }")
    @Produces(MediaType.TEXT_PLAIN)
    @Get("/secured-with-expression")
    public String securedWithExpression() {
        return "secured with expression";
    }
}

