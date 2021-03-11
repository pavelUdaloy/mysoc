package by.soc.mysoc.controller;

import by.soc.mysoc.entity.Group;
import by.soc.mysoc.entity.Post;
import by.soc.mysoc.entity.Tag;
import by.soc.mysoc.entity.User;
import by.soc.mysoc.service.GroupService;
import by.soc.mysoc.service.TagService;
import by.soc.mysoc.service.TokenService;
import by.soc.mysoc.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping(path = "/tags")
public class TagController {
    private final UserService userService;
    private final GroupService groupService;
    private final TokenService tokenService;
    private final TagService tagService;

    public TagController(UserService userService, GroupService groupService,
                         TokenService tokenService, TagService tagService) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.groupService = groupService;
        this.tagService=tagService;
    }

    @GetMapping
    public ResponseEntity<List<Tag>> tags() {
        return ResponseEntity.ok(tagService.getTags());
    }

    @GetMapping(path = "/{tagID}")
    public ResponseEntity<List<Object>> tag(@PathVariable Integer tagID) {
        Tag tagById = getTagById(tagID);
        if (tagById != null) {
            return ResponseEntity.ok(tagService.get(tagById));
        } else return ResponseEntity.badRequest().build();
    }

    @GetMapping(path="/find")
    public ResponseEntity<List<Object>> findObjWithTag(@PathParam("tagName") String tagName,@PathParam("range") String range) {
        Tag tag = tagService.get(tagName);
        if (tag==null) return ResponseEntity.badRequest().build();
        switch (range){
            case "all":
                return ResponseEntity.ok(tagService.get(tag));
            case "posts":
                List<Object> posts=new LinkedList<>();
                for (Object o : tagService.get(tag)) {
                    try{
                        Post post= ((Post) o);
                        posts.add(post);
                    }catch (ClassCastException e){
                        continue;
                    }
                }
                return ResponseEntity.ok(posts);
            case "groups":
                List<Object> groups=new LinkedList<>();
                for (Object o : tagService.get(tag)) {
                    try{
                        Group group= ((Group) o);
                        groups.add(group);
                    }catch (ClassCastException e){
                        continue;
                    }
                }
                return ResponseEntity.ok(groups);
            case "users":
                List<Object> users=new LinkedList<>();
                for (Object o : tagService.get(tag)) {
                    try{
                        User user= ((User) o);
                        users.add(user);
                    }catch (ClassCastException e){
                        continue;
                    }
                }
                return ResponseEntity.ok(users);
        }
        return ResponseEntity.badRequest().build();
    }

//    public static void main(String[] args) {
//        Post post=new Post();
//        Object post1 = post;
//        Post post11 = (Post) post1;
//        Tag post12 = (Tag) post1;
//        Group post13 = (Group) post1;
//        System.out.println();
//        ClassCastException
//    }
    private Tag getTagById(int tagID) {
        for (Tag tag : tagService.getTags()) {
            if (tag.getTagID() == tagID) return tag;
        }
        return null;
    }
}
