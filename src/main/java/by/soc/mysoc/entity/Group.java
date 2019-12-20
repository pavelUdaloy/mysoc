package by.soc.mysoc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    @UniqueElements
    List<User> subscribers = new LinkedList<>();
    @NotNull
    @Min(value = 4000000)
    @Max(value = 4999999)
    private int groupID = generateRandomID();
    @NotNull
    @Length(min = 3)
    private String name;
    @NotNull
    private User admin;
    @UniqueElements
    private List<Post> posts = new LinkedList<>();

    private int generateRandomID() {
        return (int) (Math.random() * (999999)) + 4000000;
    }

    public void generateNewId() {
        this.groupID = generateRandomID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return groupID == group.groupID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupID);
    }

    public static class GroupComparator implements Comparator<Group> {
        @Override
        public int compare(Group o1, Group o2) {
            return Integer.compare(o2.getSubscribers().size(), o1.getSubscribers().size());
        }
    }
}
