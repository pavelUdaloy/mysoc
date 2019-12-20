package by.soc.mysoc.controller;

import by.soc.mysoc.entity.*;
import by.soc.mysoc.service.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping
public class AccountController {
    private final ChatService chatService;
    private final GroupService groupService;
    private final UserService userService;
    private final TokenService tokenService;
    private final TagService tagService;

    public AccountController(GroupService groupService, TokenService tokenService,
                             ChatService chatService, UserService userService, TagService tagService) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.chatService = chatService;
        this.groupService = groupService;
        this.tagService=tagService;
    }

    @GetMapping(path = "/{userID}")
    public ResponseEntity<UserFullInfo> page(@PathVariable int userID) {
        User user = getUserById(userID);
        if (user == null) return ResponseEntity.badRequest().build();

        UserFullInfo body = new UserFullInfo(user, getGroupsByUser(user), getCommentsByUser(user), getChatsByUser(user));
        return ResponseEntity.ok(body);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping(path = "/edit")
    public boolean edit(@Valid @RequestBody TokenAndUserInfoForEdit edit, BindingResult bindingResult) {
        if (tokenRelevanceCheck(edit.getToken().getToken()) && validationCheck(bindingResult, 0)) {
            User user1 = getUser(edit.getToken().getToken());
            User user = userService.get(user1.getEmail());
            user.setBirthDate(edit.getBirthDate());
            user.setFirstName(edit.getFirstName());
            user.setLastName(edit.getLastName());
            user.setPassword(edit.getPassword());
            return true;
        }
        return false;
    }

    @PostMapping(path = "/feed")
    public ResponseEntity<List<Post>> feed(@Valid @RequestBody Token token, BindingResult bindingResult) {
        if (tokenRelevanceCheck(token.getToken()) && validationCheck(bindingResult, 0)) {
            List<Post> posts = new LinkedList<>();
            User user = getUser(token.getToken());
            for (Group group : getGroupsByUser(user)) {
                posts.addAll(group.getPosts());
            }
            posts.sort(new Post.PostComparator());
            return ResponseEntity.ok(posts);
        } else return ResponseEntity.badRequest().build();
    }

    @PostMapping(path = "/{userID}/addTag")
    public boolean addTagToUser(@Valid @RequestBody TokenAndTag tokenAndTag,
                                 BindingResult bindingResult, @PathVariable Integer userID) {
        User userById = getUserById(userID);
        User user = tokenService.getUser(tokenAndTag.getToken().getToken());
        if (generalCheck(tokenAndTag.getToken(), bindingResult, 0) && user.equals(userById)) {
            return tagService.put(tokenAndTag.getTag(),userById);
        } else return false;
    }

    private User getUserById(int userId) {
        for (User user : userService.getAll()) {
            if (user.getUserID() == userId) return user;
        }
        return null;
    }

    private List<Comment> getCommentsByUser(User user) {
        List<Comment> commentList = new ArrayList<>();
        for (Group group : groupService.getAll()) {
            for (Post post : group.getPosts()) {
                for (Comment comment : post.getComments()) {
                    if (comment.getAdmin().equals(user)) commentList.add(comment);
                }
            }
        }
        commentList.sort(new Comment.CommentComparator());
        return commentList;
    }

    private List<Group> getGroupsByUser(User user) {
        List<Group> groupList = new ArrayList<>();
        for (Group group : groupService.getAll()) {
            if (group.getSubscribers().contains(user)) groupList.add(group);
        }
        groupList.sort(new Group.GroupComparator());
        return groupList;
    }

    private List<Chat> getChatsByUser(User user) {
        List<Chat> chatList = new ArrayList<>();
        for (Chat chat : chatService.getAll()) {
            if (chat.getParticipants().contains(user)) chatList.add(chat);
        }
        chatList.sort(new Chat.ChatComparator());
        return chatList;
    }


    private User getUser(Integer token) {
        return tokenService.getUser(token);
    }

    private boolean generalCheck(Token token, BindingResult bindingResult, int permissibleCountOfErrors) {
        return tokenRelevanceCheck(token.getToken()) && validationCheck(bindingResult, 0);
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
    @NoArgsConstructor
    @AllArgsConstructor
    private static class TokenAndUserInfoForEdit {
        @NotNull
        Token token;
        @NotNull
        @Length(min = 3)
        private String firstName;
        @NotNull
        @Length(min = 3)
        private String lastName;
        @NotNull
        private Date birthDate;
        @NotNull
        @Length(min = 8, max = 8)
        private String password;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class UserFullInfo {
        @NotNull
        @UniqueElements
        private User user;
        @NotNull
        @UniqueElements
        private List<Group> groupList;
        @NotNull
        @UniqueElements
        private List<Comment> commentList;
        @NotNull
        @UniqueElements
        private List<Chat> chatList;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class TokenAndTag {
        Tag tag;
        Token token;
    }
}
