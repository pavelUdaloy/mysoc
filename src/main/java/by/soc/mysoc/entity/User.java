package by.soc.mysoc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @NotNull
    @Length(min = 3)
    private String firstName;
    @NotNull
    @Length(min = 3)
    private String lastName;
    @NotNull
    @Min(value = 3000000)
    @Max(value = 3999999)
    private int userID = generateRandomID();
    private Date birthDate = new Date();
    private Role role = new Role(Role.ROLE.USER, 1);
    @NotNull
    @Email(regexp = "/^(([^<>()\\[\\]\\.,;:\\s@\\\"]+(\\.[^<>()\\[\\]\\.,;:\\s@\\\"]+)*)|(\\\".+\\\"))@(([^<>()[\\]\\.,;:\\s@\\\"]+\\.)+[^<>()[\\]\\.,;:\\s@\\\"]{2,})$/i")
    private String email;
    @NotNull
    @Length(min = 8, max = 8)
    private String password;

    private int generateRandomID() {
        return (int) (Math.random() * (999999)) + 3000000;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    public void generateNewId() {
        this.userID = generateRandomID();
    }
}
