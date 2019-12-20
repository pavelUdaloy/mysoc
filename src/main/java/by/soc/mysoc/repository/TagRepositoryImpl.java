package by.soc.mysoc.repository;

import by.soc.mysoc.entity.Tag;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TagRepositoryImpl implements TagRepository {
    @UniqueElements
    private Map<Tag, LinkedList<Object>> tagMap = new HashMap<>();

    private void checkList(Tag tag) {
        if (tagMap.get(tag) == null) tagMap.put(tag, new LinkedList<>());
    }

    @Override
    public boolean add(Tag tag, Object object) {
        checkList(tag);
        return tagMap.get(tag).add(object);
    }

    @Override
    public boolean deleteTag(Object object, Tag tag) {
        checkList(tag);
        return tagMap.get(tag).remove(object);
    }

    @Override
    public boolean deleteAllTags(Object object) {
        for (Tag tag : (Tag[]) tagMap.keySet().toArray()) {
            tagMap.get(tag).remove(object);
        }
        return true;
    }

    @Override
    public boolean deleteAll() {
        tagMap = new HashMap<>();
        return true;
    }

    @Override
    public boolean contains(Tag tag) {
        checkList(tag);
        return tagMap.get(tag).size() >= 1;
    }

    @Override
    public List<Tag> getTags(Object object) {
        List<Tag> tagList = new LinkedList<>();
        for (Tag tag : (Tag[]) tagMap.keySet().toArray()) {
            if (tagMap.get(tag).contains(object)) {
                tagList.add(tag);
            }
        }
        return tagList;
    }

    @Override
    public HashMap<Tag, LinkedList<Object>> getAll() {
        return (HashMap<Tag, LinkedList<Object>>) tagMap;
    }

    @Override
    public List<Tag> getTags() {
        if (tagMap.size() == 0) return new LinkedList<>();
        Set<Tag> tags1 = tagMap.keySet();
        ArrayList<Tag> tags = new ArrayList<>(tags1);
        return tags;
    }

    @Override
    public List<Object> get(Tag tag) {
        return tagMap.get(tag);
    }

    @Override
    public Tag get(String tagName) {
        if (tagMap.size() == 0) return null;
        for (Tag tag : tagMap.keySet()) {
            if (tag.getName().equals(tagName)) return tag;
        }
        return null;
    }
}
