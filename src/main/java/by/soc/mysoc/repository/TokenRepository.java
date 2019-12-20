package by.soc.mysoc.repository;


import by.soc.mysoc.entity.User;

public interface TokenRepository {
    int getToken(User user);
    int generateNewToken(User user);

    User getUser(int token);
    boolean contains(int token);
    boolean contains(User user);
    boolean logout(int token);
}
