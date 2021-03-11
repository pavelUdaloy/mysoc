package by.soc.mysoc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    @NotNull
    @Min(value = 6000000)
    @Max(value = 6999999)
    private int tagID = generateRandomID();
    @NotNull
    @Length(min = 3)
    private String name;

    private int generateRandomID() {
        return (int) (Math.random() * (999999)) + 6000000;
    }

    public void generateNewId() {
        this.tagID = generateRandomID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return tagID == tag.tagID &&
                Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagID, name);
    }

    public int getTagID() {
        return tagID;
    }
}
