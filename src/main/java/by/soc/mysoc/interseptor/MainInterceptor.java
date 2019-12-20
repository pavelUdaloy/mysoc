package by.soc.mysoc.interseptor;

import by.soc.mysoc.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainInterceptor implements HandlerInterceptor {
    @Autowired
    private final TokenService tokenService;

    public MainInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getServletPath());
        switch (request.getServletPath()) {
//            case "/":
//            case "/reg":
//            case "/{userID}":
//            case "/groups":
//            case "/groups/{groupID}":
//            case "/groups/{groupID}/posts":
//            case "/groups/{groupID}/posts/{postID}":
//            case "/groups/{groupID}/{postID}/comments":
//            case "/groups/{groupID}/{postID}/{commentID}":
//                return true;
            default:
                return true;
//                String token = request.getParameter("token");
//                try {
//                    if (checkToken(Integer.parseInt(token))) return true;
//                } catch (NumberFormatException e) {
//                    System.out.println("Неправильный формат токена");
//                    System.out.println(e.toString());
//                    return false;
//                }
//                System.out.println(token);
//                return false;
//        }
//    }
//    private boolean checkToken(int token) {
//        User user = tokenService.getUser(token);
//        return user != null;
//    }
        }
    }
}