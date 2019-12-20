package by.soc.mysoc.controller;

import by.soc.mysoc.entity.Chat;
import by.soc.mysoc.entity.Message;
import by.soc.mysoc.entity.Token;
import by.soc.mysoc.entity.User;
import by.soc.mysoc.service.ChatService;
import by.soc.mysoc.service.TokenService;
import by.soc.mysoc.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping(path = "/chats")
public class ChatController {

    private final ChatService chatService;
    private final UserService userService;
    private final TokenService tokenService;

    public ChatController(UserService userService, ChatService chatService, TokenService tokenService) {
        this.userService = userService;
        this.chatService = chatService;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<List<Chat>> chats(@Valid @RequestBody Token token, BindingResult bindingResult) {
        if (generalCheck(token, bindingResult, 0)) {
            User user = tokenService.getUser(token.getToken());
            List<Chat> chats = new ArrayList<>(getChatsByUser(user));
            return ResponseEntity.ok(chats);
        } else return ResponseEntity.badRequest().build();
    }

    @PostMapping(path = "/add")
    public boolean addChat(@Valid @RequestBody TokenAndChat tokenAndChat, BindingResult bindingResult) {
        if (generalCheck(tokenAndChat.getToken(), bindingResult, 0)) {
            for (Chat chat : chatService.getAll()) {
                if (chat.getParticipants().equals(tokenAndChat.getChat().getParticipants())) return false;
            }
            return chatService.add(tokenAndChat.getChat());
        } else return false;
    }

    @PostMapping(path = "/{chatID}")
    public ResponseEntity<Chat> chat(@Valid @RequestBody Token token,
                                     BindingResult bindingResult, @PathVariable Integer chatID) {
        Chat chatById = getChatById(chatID);
        if (generalCheck(token, bindingResult, 0) && chatById != null) {
            chatById.getMessages().sort(new Message.MessageComparator());
            return ResponseEntity.ok(chatById);
        } else return ResponseEntity.badRequest().build();
    }

    @PostMapping(path = "/{chatID}/send")
    public ResponseEntity<Chat> sendMessage(@Valid @RequestBody TokenAndMessageText tokenAndMessageText,
                                            BindingResult bindingResult, @PathVariable Integer chatID) {
        Chat chatById = getChatById(chatID);
        if (generalCheck(tokenAndMessageText.getToken(), bindingResult, 0) && chatById != null) {
            User user = userService.get(tokenService.getUser(tokenAndMessageText.getToken().getToken()).getEmail());
            Message message = new Message(user, tokenAndMessageText.getText());
            chatById.getMessages().add(message);
            chatById.getMessages().sort(new Message.MessageComparator());
            return ResponseEntity.ok(chatById);
        } else return ResponseEntity.badRequest().build();
    }

    private Chat getChatById(int chatId) {
        for (Chat chat : chatService.getAll()) {
            if (chat.getChatID() == chatId) return chat;
        }
        return null;
    }

    private boolean generalCheck(Token token, BindingResult bindingResult, int permissibleCountOfErrors) {
        return tokenRelevanceCheck(token.getToken()) && validationCheck(bindingResult, 0);
    }

    private List<Chat> getChatsByUser(User user) {
        List<Chat> chatList = new LinkedList<>();
        for (Chat chat : chatService.getAll()) {
            if (chat.getParticipants().contains(user)) chatList.add(chat);
        }
        chatList.sort(new Chat.ChatComparator());
        return chatList;
    }

    private boolean tokenRelevanceCheck(Integer token) {
        return tokenService.getUser(token) != null;
    }

    private boolean validationCheck(BindingResult bindingResult, int permissibleCountOfErrors) {
        if (bindingResult.getErrorCount() == permissibleCountOfErrors) {
            return true;
        }
        System.out.println(bindingResult.getErrorCount());
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            System.out.println(fieldError.getField());
        }
        return false;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class TokenAndMessageText {
        @NotNull
        @Length(min = 3)
        String text;
        Token token;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class TokenAndChat {
        Chat chat;
        Token token;
    }
}
