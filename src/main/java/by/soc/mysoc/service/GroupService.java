package by.soc.mysoc.service;

import by.soc.mysoc.entity.Group;
import by.soc.mysoc.entity.Post;
import by.soc.mysoc.entity.Tag;

import java.util.List;

public interface GroupService {
    boolean add(Group group);
    boolean remove(Group group);
    boolean removeAll();
    boolean contains(Group group);
    boolean contains(int groupID);
    Group get(int groupID);
    List<Group> getAll();
    boolean set(Group group);
}
