package by.soc.mysoc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @NotNull
    @Min(value = 5000000)
    @Max(value = 5999999)
    private int postID = generateRandomID();
    @NotNull
    @Length(min = 3)
    private String title;
    @NotNull
    @Length(min = 3)
    private String text;
    private Date publicationDate = new Date();
    @UniqueElements
    private List<Comment> comments = new LinkedList<>();

    private int generateRandomID() {
        return (int) (Math.random() * (999999)) + 5000000;
    }

    public void generateNewId() {
        this.postID = generateRandomID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return postID == post.postID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(postID);
    }

    public static class PostComparator implements Comparator<Post> {
        @Override
        public int compare(Post o1, Post o2) {
            return o2.getPublicationDate().compareTo(o1.getPublicationDate());
        }
    }
}
