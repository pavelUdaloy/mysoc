package by.soc.mysoc.service;

import by.soc.mysoc.entity.Group;
import by.soc.mysoc.entity.Post;
import by.soc.mysoc.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public boolean add(Group group) {
        return groupRepository.add(group);
    }

    @Override
    public boolean remove(Group group) {
        return groupRepository.delete(group);
    }

    @Override
    public boolean removeAll() {
        return groupRepository.deleteAll();
    }

    @Override
    public boolean contains(Group group) {
        return groupRepository.contains(group);
    }

    @Override
    public boolean contains(int groupID) {
        return groupRepository.contains(groupID);
    }

    @Override
    public Group get(int groupID) {
        return groupRepository.get(groupID);
    }

    @Override
    public List<Group> getAll() {
        return groupRepository.getAll();
    }

    @Override
    public boolean set(Group group) {
        return groupRepository.edit(group);
    }
}
