package by.soc.mysoc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Chat {
    @UniqueElements
    private List<Message> messages = new LinkedList<>();
    @UniqueElements
    @NotNull
    @Size(min = 2)
    private List<User> participants;
    @NotNull
    private String name;
    @NotNull
    @Min(value = 1000000)
    @Max(value = 1999999)
    private int chatID = generateRandomID();

    private int generateRandomID() {
        return (int) (Math.random() * (999999)) + 1000000;
    }

    public void generateNewId() {
        this.chatID = generateRandomID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return chatID == chat.chatID &&
                Objects.equals(participants, chat.participants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participants, chatID);
    }

    public static class ChatComparator implements Comparator<Chat> {
        @Override
        public int compare(Chat o1, Chat o2) {
            return Integer.compare(o2.getParticipants().size(), o1.getParticipants().size());
        }
    }
}
