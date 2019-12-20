package by.soc.mysoc.service;


import by.soc.mysoc.entity.User;

import java.util.List;

public interface UserService {
    boolean add(User user);

    boolean remove(User user);

    boolean removeAll();

    boolean contains(User user);

    boolean contains(String email);

    User get(String email);

    List<User> getAll();

    boolean set(User user);
}
