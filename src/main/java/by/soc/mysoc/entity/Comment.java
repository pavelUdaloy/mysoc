package by.soc.mysoc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @NotNull
    @Min(value = 2000000)
    @Max(value = 2999999)
    private int commentID = generateRandomID();
    private Date publicationDate = new Date();
    @NotNull
    @Length(min = 3)
    private String text;
    @NotNull
    private User admin;

    private int generateRandomID() {
        return (int) (Math.random() * (999999)) + 2000000;
    }

    public void generateNewId() {
        this.commentID = generateRandomID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return commentID == comment.commentID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentID);
    }

    public static class CommentComparator implements Comparator<Comment> {
        @Override
        public int compare(Comment o1, Comment o2) {
            return o2.getPublicationDate().compareTo(o1.getPublicationDate());
        }
    }
}
