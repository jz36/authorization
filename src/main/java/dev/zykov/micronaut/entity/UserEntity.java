package dev.zykov.micronaut.entity;

import dev.zykov.micronaut.dto.Right;
import dev.zykov.micronaut.dto.Role;
import dev.zykov.micronaut.dto.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class UserEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer userId;

    @Column(name = "external_id")
    private String externalId;

    @Column(name = "username")
    private String userName;

    @Column(name = "user_role")
    private Integer userRoleId;

    public User toDto(Role role, List<Right> rights) {
        return new User(
                this.userId,
                this.userName,
                role,
                rights
        );
    }
}
