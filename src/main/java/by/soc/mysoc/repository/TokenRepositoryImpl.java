package by.soc.mysoc.repository;

import by.soc.mysoc.entity.User;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TokenRepositoryImpl implements TokenRepository {
    @UniqueElements
    private Map<Integer, User> userMap = new HashMap<>();

    @Override
    public int getToken(User user) {
        for (Object o : userMap.keySet().toArray()) {
            if (userMap.get((Integer) o).equals(user)) {
                return (Integer) o;
            }
        }
        return 0;
    }

    @Override
    public int generateNewToken(User user) {
        int token = (int) (1000000 + Math.random() * 8999999);
        userMap.put(token, user);
        return token;
    }


    @Override
    public User getUser(int token) {
        return userMap.get(token);
    }

    @Override
    public boolean contains(int token) {
        return userMap.containsKey(token);
    }

    @Override
    public boolean contains(User user) {
        return userMap.containsValue(user);
    }

    @Override
    public boolean logout(int token) {
        return userMap.remove(token, userMap.get(token));
    }
}
