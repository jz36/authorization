package dev.zykov.micronaut.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "roles_rights")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RoleRightEntity {

    @EmbeddedId
    private RoleRightId roleRightId;
}
