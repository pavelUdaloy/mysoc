package by.soc.mysoc.service;

import by.soc.mysoc.entity.Chat;
import by.soc.mysoc.entity.Message;
import by.soc.mysoc.repository.ChatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    public ChatServiceImpl(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public boolean add(Chat chat) {
        return chatRepository.add(chat);
    }

    @Override
    public boolean remove(Chat chat) {
        return chatRepository.delete(chat);
    }

    @Override
    public boolean removeAll() {
        return chatRepository.deleteAll();
    }

    @Override
    public boolean contains(Chat chat) {
        return chatRepository.contains(chat);
    }

    @Override
    public boolean contains(int chatID) {
        return chatRepository.contains(chatID);
    }

    @Override
    public Chat get(int chatID) {
        return chatRepository.get(chatID);
    }

    @Override
    public List<Chat> getAll() {
        return chatRepository.getAll();
    }

    @Override
    public boolean set(Chat chat) {
        return chatRepository.edit(chat);
    }
}
