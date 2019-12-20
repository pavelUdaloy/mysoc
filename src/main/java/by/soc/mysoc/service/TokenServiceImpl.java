package by.soc.mysoc.service;

import by.soc.mysoc.entity.User;
import by.soc.mysoc.repository.TokenRepository;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;

    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public int getToken(User user) {
        return tokenRepository.getToken(user);
    }

    @Override
    public String getTokenInString(User user) {
        return String.valueOf(tokenRepository.getToken(user));
    }

    @Override
    public int generateNewToken(User user) {
        return tokenRepository.generateNewToken(user);
    }

    @Override
    public User getUser(int token) {
        return tokenRepository.getUser(token);
    }

    @Override
    public boolean contains(int token) {
        return tokenRepository.contains(token);
    }

    @Override
    public boolean contains(User user) {
        return tokenRepository.contains(user);
    }

    @Override
    public boolean logout(int token) {
        return tokenRepository.logout(token);
    }
}
