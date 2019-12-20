package by.soc.mysoc.repository;

import by.soc.mysoc.entity.Group;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class GroupRepositoryImpl implements GroupRepository {
    @UniqueElements
    private List<Group> groups=new ArrayList<>();

    @Override
    public boolean add(Group group) {
        return groups.add(group);
    }

    @Override
    public boolean delete(Group group) {
        return groups.remove(group);
    }

    @Override
    public boolean deleteAll() {
        return groups.removeAll(new ArrayList<>(groups));
    }

    @Override
    public boolean contains(Group group) {
        return groups.contains(group);
    }

    @Override
    public boolean contains(int groupID) {
        return get(groupID)!=null;
    }

    @Override
    public Group get(int groupID) {
        for (Group group : groups) {
            if (group.getGroupID()==groupID) return group;
        }
        return null;
    }

    @Override
    public List<Group> getAll() {
        return groups;
    }

    @Override
    public boolean edit(Group group) {
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).equals(group)) {
                group.setGroupID(groups.get(i).getGroupID());
                groups.set(i, group);
                return true;
            }
        }
        return false;
    }
}
