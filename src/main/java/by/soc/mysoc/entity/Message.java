package by.soc.mysoc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message {
    @NotNull
    private User admin;
    @NotNull
    @Length(min = 1)
    private String text;
    private Date publicationDate = new Date();

    public Message(@NotNull User admin, @NotNull @Length(min = 1) String text) {
        this.admin = admin;
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(admin, message.admin) &&
                Objects.equals(text, message.text) &&
                Objects.equals(publicationDate, message.publicationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(admin, text, publicationDate);
    }

    public static class MessageComparator implements Comparator<Message> {
        @Override
        public int compare(Message o1, Message o2) {
            return o2.getPublicationDate().compareTo(o1.getPublicationDate());
        }
    }
}
