package by.soc.mysoc.repository;

import by.soc.mysoc.entity.Chat;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ChatRepositoryImpl implements ChatRepository {
    @UniqueElements
    private List<Chat> chats = new ArrayList<>();

    @Override
    public boolean add(Chat chat) {
        return chats.add(chat);
    }

    @Override
    public boolean delete(Chat chat) {
        return chats.remove(chat);
    }

    @Override
    public boolean deleteAll() {
        return chats.removeAll(new ArrayList<>(chats));
    }

    @Override
    public boolean contains(Chat chat) {
        return chats.contains(chat);
    }

    @Override
    public boolean contains(int chatID) {
        return get(chatID) != null;
    }

    @Override
    public Chat get(int chatID) {
        for (Chat chat : chats) {
            if (chat.getChatID()==chatID) return chat;
        }
        return null;
    }

    @Override
    public List<Chat> getAll() {
        return chats;
    }

    @Override
    public boolean edit(Chat chat) {
        for (int i = 0; i < chats.size(); i++) {
            if (chats.get(i).equals(chat)) {
                chat.setChatID(chats.get(i).getChatID());
                chats.set(i, chat);
                return true;
            }
        }
        return false;
    }
}
