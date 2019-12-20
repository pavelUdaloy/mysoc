package by.soc.mysoc.repository;

import by.soc.mysoc.entity.Tag;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public interface TagRepository {
    boolean add(Tag tag, Object object);
    boolean deleteTag(Object object,Tag tag);
    boolean deleteAllTags(Object object);
    boolean deleteAll();
    boolean contains(Tag tag);
    List<Tag> getTags(Object object);
    HashMap<Tag, LinkedList<Object>> getAll();
    List<Tag> getTags();
    List<Object> get(Tag tag);
    Tag get(String tagName);
}
