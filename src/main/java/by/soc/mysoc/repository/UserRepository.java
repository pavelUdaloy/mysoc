package by.soc.mysoc.repository;


import by.soc.mysoc.entity.User;

import java.util.List;

public interface UserRepository {
    boolean add(User user);
    boolean delete(User user);
    boolean deleteAll();
    boolean contains(User user);
    boolean contains(String email);
    User get(String email);
    List<User> getAll();
    boolean edit(User user);
}
