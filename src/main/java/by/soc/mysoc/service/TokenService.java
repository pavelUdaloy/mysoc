package by.soc.mysoc.service;


import by.soc.mysoc.entity.User;

public interface TokenService {
    String getTokenInString(User user);
    int getToken(User user);
    int generateNewToken(User user);
    User getUser(int token);
    boolean contains(int token);
    boolean contains(User user);
    boolean logout(int token);
}
