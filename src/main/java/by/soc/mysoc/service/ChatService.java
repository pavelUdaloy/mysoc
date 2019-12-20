package by.soc.mysoc.service;

import by.soc.mysoc.entity.Chat;
import by.soc.mysoc.entity.Message;

import java.util.List;

public interface ChatService {
    boolean add(Chat chat);
    boolean remove(Chat chat);
    boolean removeAll();
    boolean contains(Chat chat);
    boolean contains(int chatID);
    Chat get(int chatID);
    List<Chat> getAll();
    boolean set(Chat chat);
}
