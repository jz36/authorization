package dev.zykov.micronaut.entity;

import dev.zykov.micronaut.dto.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RoleEntity{

    @Id
    @Column(name = "id")
    private Integer roleId;

    @Column(name = "role_name")
    private String name;

    @Column(name = "role_description")
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleEntity that = (RoleEntity) o;

        if (!roleId.equals(that.roleId)) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = roleId.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    public Role toDto() {
        return new Role(this.roleId, this.name, this.description);
    }
}
