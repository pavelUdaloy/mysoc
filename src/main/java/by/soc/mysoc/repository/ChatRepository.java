package by.soc.mysoc.repository;

import by.soc.mysoc.entity.Chat;

import java.util.List;

public interface ChatRepository {
    boolean add(Chat chat);
    boolean delete(Chat chat);
    boolean deleteAll();
    boolean contains(Chat chat);
    boolean contains(int chatID);
    Chat get(int chatID);
    List<Chat> getAll();
    boolean edit(Chat chat);
}
