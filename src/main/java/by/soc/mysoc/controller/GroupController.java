package by.soc.mysoc.controller;

import by.soc.mysoc.entity.*;
import by.soc.mysoc.service.GroupService;
import by.soc.mysoc.service.TagService;
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
@RequestMapping(path = "/groups")
public class GroupController {
    private final UserService userService;
    private final GroupService groupService;
    private final TokenService tokenService;
    private final TagService tagService;

    public GroupController(UserService userService, GroupService groupService,
                           TokenService tokenService, TagService tagService) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.groupService = groupService;
        this.tagService=tagService;
    }

    @PostMapping
    public ResponseEntity<List<Group>> groups(@Valid @RequestBody Token token, BindingResult bindingResult) {
        if (generalCheck(token, bindingResult, 0)) {
            User user = tokenService.getUser(token.getToken());
            List<Group> groups = new ArrayList<>(getGroupsByUser(user));
            return ResponseEntity.ok(groups);
        } else return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<Group>> allGroups() {
        return ResponseEntity.ok(groupService.getAll());
    }

    @PostMapping(path = "/add")
    public boolean addGroup(@Valid @RequestBody TokenAndGroup tokenAndGroup, BindingResult bindingResult) {
        User user1 = tokenService.getUser(tokenAndGroup.getToken().getToken());
        User user = userService.get(user1.getEmail());
        if (generalCheck(tokenAndGroup.getToken(), bindingResult, 0) &&
                tokenAndGroup.getGroup().getAdmin().equals(user) && user != null) {
            for (Group group : groupService.getAll()) {
                if (group.equals(tokenAndGroup.getGroup())) return false;
            }
            groupService.add(tokenAndGroup.getGroup());
            return true;
        } else return false;
    }

    @GetMapping(path = "/{groupID}")
    public ResponseEntity<Group> group(@PathVariable Integer groupID) {
        Group groupById = getGroupById(groupID);
        if (groupById != null) {
            groupById.getPosts().sort(new Post.PostComparator());
            return ResponseEntity.ok(groupById);
        } else return ResponseEntity.badRequest().build();
    }

    @PostMapping(path = "/{groupID}/delete")
    public boolean removeGroup(@Valid @RequestBody Token token,
                               BindingResult bindingResult, @PathVariable Integer groupID) {
        Group groupById = getGroupById(groupID);
        User user = tokenService.getUser(token.getToken());
        if (generalCheck(token, bindingResult, 0) &&
                groupById != null && user.equals(groupById.getAdmin())) {
            return groupService.remove(groupById);
        } else return false;
    }

    @PostMapping(path = "/{groupID}/edit")
    public boolean editGroup(@Valid @RequestBody TokenAndGroupInfoForEdit tokenAndGroupInfoForEdit,
                             BindingResult bindingResult, @PathVariable Integer groupID) {
        Group groupById = getGroupById(groupID);
        User user = tokenService.getUser(tokenAndGroupInfoForEdit.getToken().getToken());
        if (generalCheck(tokenAndGroupInfoForEdit.getToken(), bindingResult, 0) &&
                groupById != null && user.equals(groupById.getAdmin())) {
            groupById.setName(tokenAndGroupInfoForEdit.getName());
            return true;
        } else return false;
    }

    @GetMapping(path = "/{groupID}/posts")
    public ResponseEntity<List<Post>> posts(@PathVariable Integer groupID) {
        Group groupById = getGroupById(groupID);
        if (groupById != null) {
            groupById.getPosts().sort(new Post.PostComparator());
            return ResponseEntity.ok(groupById.getPosts());
        } else return ResponseEntity.badRequest().build();
    }

    @PostMapping(path = "/{groupID}/add")
    public boolean addPost(@Valid @RequestBody TokenAndPost tokenAndPost,
                           BindingResult bindingResult, @PathVariable Integer groupID) {
        Group groupById = getGroupById(groupID);
        User user = tokenService.getUser(tokenAndPost.getToken().getToken());
        if (generalCheck(tokenAndPost.getToken(), bindingResult, 0) &&
                groupById != null && user.equals(groupById.getAdmin())) {
            return groupById.getPosts().add(tokenAndPost.getPost());
        } else return false;
    }

    @GetMapping(path = "/{groupID}/{postID}")
    public ResponseEntity<Post> post(@PathVariable Integer groupID, @PathVariable Integer postID) {
        Group groupById = getGroupById(groupID);
        Post post = getPostById(groupById, postID);
        if (post != null) return ResponseEntity.ok(post);
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(path = "/{groupID}/{postID}/delete")
    public boolean removePost(@Valid @RequestBody Token token, BindingResult bindingResult,
                              @PathVariable Integer groupID, @PathVariable Integer postID) {
        Group groupById = getGroupById(groupID);
        User user = tokenService.getUser(token.getToken());
        if (generalCheck(token, bindingResult, 0) &&
                groupById != null && user.equals(groupById.getAdmin())) {
            return groupById.getPosts().remove(getPostById(groupById, postID));
        } else return false;
    }

    @PostMapping(path = "/{groupID}/{postID}/edit")
    public boolean editPost(@Valid @RequestBody TokenAndPostInfoForEdit tokenAndPostInfoForEdit,
                            BindingResult bindingResult,
                            @PathVariable Integer groupID, @PathVariable Integer postID) {
        Group groupById = getGroupById(groupID);
        User user = tokenService.getUser(tokenAndPostInfoForEdit.getToken().getToken());
        if (generalCheck(tokenAndPostInfoForEdit.getToken(), bindingResult, 0) &&
                groupById != null && user.equals(groupById.getAdmin())) {
            Post postById = getPostById(groupById, postID);
            postById.setText(tokenAndPostInfoForEdit.getText());
            postById.setTitle(tokenAndPostInfoForEdit.getTitle());
            return true;
        } else return false;
    }

    @PostMapping(path = "/{groupID}/{postID}/add")
    public ResponseEntity<List<Comment>> addPost(@Valid @RequestBody TokenAndComment tokenAndComment, @PathVariable Integer postID,
                                                 BindingResult bindingResult, @PathVariable Integer groupID) {
        Group groupById = getGroupById(groupID);
        Post postById = getPostById(groupById, postID);
        User user = userService.get(tokenService.getUser(tokenAndComment.getToken().getToken()).getEmail());
        if (generalCheck(tokenAndComment.getToken(), bindingResult, 0) &&
                postById != null && user != null && tokenAndComment.getComment().getAdmin().equals(user)) {
            postById.getComments().add(tokenAndComment.getComment());
            postById.getComments().sort(new Comment.CommentComparator());
            return ResponseEntity.ok(postById.getComments());
        } else return ResponseEntity.badRequest().build();
    }

    @GetMapping(path = "/{groupID}/{postID}/comments")
    public ResponseEntity<List<Comment>> comments(@PathVariable Integer groupID, @PathVariable Integer postID) {
        Group groupById = getGroupById(groupID);
        Post post = getPostById(groupById, postID);
        if (post != null) {
            post.getComments().sort(new Comment.CommentComparator());
            return ResponseEntity.ok(post.getComments());
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(path = "/{groupID}/{postID}/{commentID}")
    public ResponseEntity<Comment> post(@PathVariable Integer groupID, @PathVariable Integer postID,
                                        @PathVariable Integer commentID) {
        Group groupById = getGroupById(groupID);
        Post post = getPostById(groupById, postID);
        Comment commentById = getCommentById(post, commentID);
        if (commentById != null) {
            return ResponseEntity.ok(commentById);
        } else return ResponseEntity.badRequest().build();
    }

    @PostMapping(path = "/{groupID}/{postID}/{commentID}/delete")
    public boolean removeComment(@Valid @RequestBody Token token,
                                 BindingResult bindingResult, @PathVariable Integer commentID,
                                 @PathVariable Integer groupID, @PathVariable Integer postID) {
        Group groupById = getGroupById(groupID);
        Post postById = getPostById(groupById, postID);
        Comment commentById = getCommentById(postById, commentID);
        User user = tokenService.getUser(token.getToken());
        if (generalCheck(token, bindingResult, 0) &&
                commentById != null && user.equals(commentById.getAdmin())) {
            return postById.getComments().remove(commentById);
        } else return false;
    }

    @PostMapping(path = "/{groupID}/{postID}/{commentID}/edit")
    public boolean editComment(@Valid @RequestBody TokenAndCommentInfoForEdit tokenAndCommentInfoForEdit,
                               BindingResult bindingResult, @PathVariable Integer commentID,
                               @PathVariable Integer groupID, @PathVariable Integer postID) {
        Group groupById = getGroupById(groupID);
        Post postById = getPostById(groupById, postID);
        Comment commentById = getCommentById(postById, commentID);
        User user = tokenService.getUser(tokenAndCommentInfoForEdit.getToken().getToken());
        if (generalCheck(tokenAndCommentInfoForEdit.getToken(), bindingResult, 0) &&
                commentById != null && user.equals(commentById.getAdmin())) {
            commentById.setText(tokenAndCommentInfoForEdit.getText());
            return true;
        } else return false;
    }

    @PostMapping(path = "/{groupID}/subscribe")
    public boolean subscribe(@Valid @RequestBody Token token,
                             BindingResult bindingResult, @PathVariable Integer groupID) {
        Group groupById = getGroupById(groupID);
        User user = tokenService.getUser(token.getToken());
        if (generalCheck(token, bindingResult, 0) &&
                groupById != null && !user.equals(groupById.getAdmin())) {
            return groupById.getSubscribers().add(user);
        } else return false;
    }

    @PostMapping(path = "/{groupID}/unsubscribe")
    public boolean unsubscribe(@Valid @RequestBody Token token,
                               BindingResult bindingResult, @PathVariable Integer groupID) {
        Group groupById = getGroupById(groupID);
        User user = tokenService.getUser(token.getToken());
        if (generalCheck(token, bindingResult, 0) &&
                groupById != null && !user.equals(groupById.getAdmin())) {
            return groupById.getSubscribers().remove(user);
        } else return false;
    }

    @PostMapping(path = "/{groupID}/addTag")
    public boolean addTagToGroup(@Valid @RequestBody TokenAndTag tokenAndTag,
                             BindingResult bindingResult, @PathVariable Integer groupID) {
        Group groupById = getGroupById(groupID);
        User user = tokenService.getUser(tokenAndTag.getToken().getToken());
        if (generalCheck(tokenAndTag.getToken(), bindingResult, 0) &&
                groupById != null && user.equals(groupById.getAdmin())) {
            return tagService.put(tokenAndTag.getTag(),groupById);
        } else return false;
    }

    @PostMapping(path = "/{groupID}/{postID}/addTag")
    public boolean addTagToPost(@Valid @RequestBody TokenAndTag tokenAndTag, BindingResult bindingResult,
                                 @PathVariable Integer groupID,@PathVariable Integer postID) {
        Group groupById = getGroupById(groupID);
        User user = tokenService.getUser(tokenAndTag.getToken().getToken());
        Post postById = getPostById(groupById, postID);
        if (generalCheck(tokenAndTag.getToken(), bindingResult, 0) &&
                postById != null && user.equals(groupById.getAdmin())) {
            return tagService.put(tokenAndTag.getTag(),postById);
        } else return false;
    }

    @PostMapping(path = "/{groupID}/{postID}/{commentID}/addTag")
    public boolean addTagToComment(@Valid @RequestBody TokenAndTag tokenAndTag, BindingResult bindingResult,
             @PathVariable Integer groupID,@PathVariable Integer postID,@PathVariable Integer commentID) {
        Group groupById = getGroupById(groupID);
        User user = tokenService.getUser(tokenAndTag.getToken().getToken());
        Post postById = getPostById(groupById, postID);
        Comment commentById = getCommentById(postById, commentID);
        if (generalCheck(tokenAndTag.getToken(), bindingResult, 0) &&
                commentById != null && user.equals(groupById.getAdmin())) {
            return tagService.put(tokenAndTag.getTag(),commentById);
        } else return false;
    }

    @PostMapping(path = "/{groupID}/deleteTag")
    public boolean deleteGroupTag(@Valid @RequestBody TokenAndTag tokenAndTag,
                                 BindingResult bindingResult, @PathVariable Integer groupID) {
        Group groupById = getGroupById(groupID);
        User user = tokenService.getUser(tokenAndTag.getToken().getToken());
        if (generalCheck(tokenAndTag.getToken(), bindingResult, 0) &&
                groupById != null && user.equals(groupById.getAdmin())) {
            return tagService.removeTag(groupById,tokenAndTag.getTag());
        } else return false;
    }

    @PostMapping(path = "/{groupID}/{postID}/deleteTag")
    public boolean deletePostTag(@Valid @RequestBody TokenAndTag tokenAndTag, BindingResult bindingResult,
                                @PathVariable Integer groupID,@PathVariable Integer postID) {
        User user = tokenService.getUser(tokenAndTag.getToken().getToken());
        Group groupById = getGroupById(groupID);
        Post postById = getPostById(groupById, postID);
        if (generalCheck(tokenAndTag.getToken(), bindingResult, 0) &&
                postById != null && user.equals(groupById.getAdmin())) {
            return tagService.removeTag(postById,tokenAndTag.getTag());
        } else return false;
    }

    @PostMapping(path = "/{groupID}/{postID}/{commentID}/deleteTag")
    public boolean deleteCommentTag(@Valid @RequestBody TokenAndTag tokenAndTag, BindingResult bindingResult,
                                   @PathVariable Integer groupID,@PathVariable Integer postID,@PathVariable Integer commentID) {
        Group groupById = getGroupById(groupID);
        User user = tokenService.getUser(tokenAndTag.getToken().getToken());
        Post postById = getPostById(groupById, postID);
        Comment commentById = getCommentById(postById, commentID);
        if (generalCheck(tokenAndTag.getToken(), bindingResult, 0) &&
                commentById != null && user.equals(groupById.getAdmin())) {
            return tagService.removeTag(commentById,tokenAndTag.getTag());
        } else return false;
    }

    private Group getGroupById(int groupId) {
        for (Group group : groupService.getAll()) {
            if (group.getGroupID() == groupId) return group;
        }
        return null;
    }

    private Post getPostById(Group group, int postId) {
        for (Post post : group.getPosts()) {
            if (post.getPostID() == postId) return post;
        }
        return null;
    }

    private Comment getCommentById(Post post, int commentId) {
        for (Comment comment : post.getComments()) {
            if (comment.getCommentID() == commentId) return comment;
        }
        return null;
    }

    private List<Group> getGroupsByUser(User user) {
        List<Group> groupList = new LinkedList<>();
        for (Group group : groupService.getAll()) {
            if (group.getSubscribers().contains(user)) groupList.add(group);
        }
        groupList.sort(new Group.GroupComparator());
        return groupList;
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
    @AllArgsConstructor
    @NoArgsConstructor
    private static class TokenAndTag {
        Tag tag;
        Token token;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class TokenAndGroup {
        Group group;
        Token token;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class TokenAndPost {
        Post post;
        Token token;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class TokenAndComment {
        Comment comment;
        Token token;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class TokenAndGroupInfoForEdit {
        @NotNull
        @Length(min = 3)
        String name;
        Token token;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class TokenAndPostInfoForEdit {
        Token token;
        @NotNull
        @Length(min = 3)
        private String title;
        @NotNull
        @Length(min = 3)
        private String text;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class TokenAndCommentInfoForEdit {
        Token token;
        @NotNull
        @Length(min = 3)
        private String text;
    }
}
