package by.soc.mysoc.repository;

import by.soc.mysoc.entity.User;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @UniqueElements
    private List<User> users = new ArrayList<>();

    public UserRepositoryImpl() {
    }

    @Override
    public boolean add(User user) {
        if (contains(user)) return false;
        else {
            users.add(user);
            return true;
        }
    }

    @Override
    public boolean delete(User user) {
        if (!contains(user)) return false;
        else {
            users.remove(user);
            return true;
        }
    }

    @Override
    public boolean deleteAll() {
        if (users.isEmpty()) return false;
        else {
            users.removeAll(new ArrayList<>(users));
            return true;
        }
    }

    @Override
    public boolean contains(User user) {
        return users.contains(user);
    }

    @Override
    public boolean contains(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) return true;
        }
        return false;
    }

    @Override
    public User get(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) return user;
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users);
    }

    @Override
    public boolean edit(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).equals(user)) {
                user.setUserID(users.get(i).getUserID());
                users.set(i, user);
                return true;
            }
        }
        return false;
    }
}
