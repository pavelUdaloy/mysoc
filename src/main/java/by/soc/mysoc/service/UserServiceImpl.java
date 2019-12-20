package by.soc.mysoc.service;

import by.soc.mysoc.entity.User;
import by.soc.mysoc.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean add(User user) {
        return userRepository.add(user);
    }

    @Override
    public boolean remove(User user) {
        return userRepository.delete(user);
    }

    @Override
    public boolean removeAll() {
        return userRepository.deleteAll();
    }

    @Override
    public boolean contains(User user) {
        return userRepository.contains(user);
    }

    @Override
    public boolean contains(String email) {
        return userRepository.contains(email);
    }

    @Override
    public User get(String email) {
        return userRepository.get(email);
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public boolean set(User user) {
        return userRepository.edit(user);
    }
}
