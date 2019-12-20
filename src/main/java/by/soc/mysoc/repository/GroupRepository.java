package by.soc.mysoc.repository;

import by.soc.mysoc.entity.Group;

import java.util.List;

public interface GroupRepository {
    boolean add(Group group);
    boolean delete(Group group);
    boolean deleteAll();
    boolean contains(Group group);
    boolean contains(int groupID);
    Group get(int groupID);
    List<Group> getAll();
    boolean edit(Group group);
}
