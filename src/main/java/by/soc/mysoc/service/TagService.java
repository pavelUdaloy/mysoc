package by.soc.mysoc.service;

import by.soc.mysoc.entity.Tag;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public interface TagService {
    boolean put(Tag tag, Object object);
    boolean removeTag(Object object,Tag tag);
    boolean removeAllTags(Object object);
    boolean removeAll();
    boolean contains(Tag tag);
    List<Tag> getTags(Object object);
    HashMap<Tag, LinkedList<Object>> getAll();
    List<Tag> getTags();
    List<Object> get(Tag tag);
    Tag get(String tagName);
}
