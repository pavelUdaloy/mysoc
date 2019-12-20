package by.soc.mysoc.controller;

import by.soc.mysoc.entity.Token;
import by.soc.mysoc.entity.User;
import by.soc.mysoc.service.TokenService;
import by.soc.mysoc.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

// post main/        RequestBody User user
// post main/reg        RequestBody User user
// get main/logout        RequestBody Integer token

@RestController
@RequestMapping
public class MainController {
    private final UserService userService;
    private final TokenService tokenService;

    public MainController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping(path = "/")
    public ResponseEntity<Integer> auth(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (checkTokenForNull(user) && validationCheck(bindingResult, 2)) {
            User user1 = userService.get(user.getEmail());
            if (user1.getPassword().equals(user.getPassword())) {
                return ResponseEntity.ok(tokenService.generateNewToken(user1));
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(path = "/reg")
    public boolean reg(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (checkTokenForNull(user) && validationCheck(bindingResult, 0) && (userService.get(user.getEmail()) == null)) {
            return userService.add(user);
        } else return false;
    }

    @PostMapping(path = "/logout")
    public boolean logout(@Valid @RequestBody Token token, BindingResult bindingResult) {
        if (tokenRelevanceCheck(token.getToken()) && validationCheck(bindingResult, 0)) {
            tokenService.logout(token.getToken());
            return true;
        } else return false;
    }

//    @GetMapping(path = "/forTesting")
//    public ResponseEntity<AccountController.UserFullInfo> page(@PathVariable int userID) {
//        User user = getUserById(userID);
//        if (user == null) return ResponseEntity.badRequest().build();
//
//        AccountController.UserFullInfo body = new AccountController.UserFullInfo(user, getGroupsByUser(user), getCommentsByUser(user), getChatsByUser(user));
//        return ResponseEntity.ok(body);
//    }


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

    private boolean checkTokenForNull(User user) {
        return tokenService.getToken(user) == 0;
    }

    private boolean tokenRelevanceCheck(Integer token) {
        return tokenService.getUser(token) != null;
    }

//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    private class MainUserData {
//        @NotNull
//        @Email
//        private String email;
//        @NotNull
//        @Length(min = 8, max = 8)
//        private String password;
//    }

}
