package by.soc.mysoc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    @NotNull
    @Min(value = 1000000)
    @Max(value = 9999999)
    private int token;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token1 = (Token) o;
        return token == token1.token;
    }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }
}
