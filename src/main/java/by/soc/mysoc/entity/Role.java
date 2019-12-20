package by.soc.mysoc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private ROLE role = ROLE.USER;
    private int level = 1;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role1 = (Role) o;
        return level == role1.level &&
                role == role1.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, level);
    }

    public enum ROLE {
        ADMIN,
        USER,
    }
}
