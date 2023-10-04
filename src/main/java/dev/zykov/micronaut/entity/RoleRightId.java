package dev.zykov.micronaut.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RoleRightId implements Serializable {

    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "right_id")
    private Integer rightId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleRightId that = (RoleRightId) o;

        if (!roleId.equals(that.roleId)) return false;
        return rightId.equals(that.rightId);
    }

    @Override
    public int hashCode() {
        int result = roleId.hashCode();
        result = 31 * result + rightId.hashCode();
        return result;
    }
}
