package dev.zykov.micronaut.entity;

import dev.zykov.micronaut.dto.Right;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "rights")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RightEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "right_name")
    private String name;

    @Column(name = "right_description")
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RightEntity that = (RightEntity) o;

        if (!id.equals(that.id)) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    public Right toDto() {
        return new Right(this.id, this.name, this.description);
    }
}
