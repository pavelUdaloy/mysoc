package by.soc.mysoc.service;

import by.soc.mysoc.entity.Tag;
import by.soc.mysoc.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public boolean put(Tag tag, Object object) {
        return tagRepository.add(tag, object);
    }

    @Override
    public boolean removeTag(Object object, Tag tag) {
        return tagRepository.deleteTag(object, tag);
    }

    @Override
    public boolean removeAllTags(Object object) {
        return tagRepository.deleteAllTags(object);
    }

    @Override
    public boolean removeAll() {
        return tagRepository.deleteAll();
    }

    @Override
    public boolean contains(Tag tag) {
        return tagRepository.contains(tag);
    }

    @Override
    public List<Tag> getTags(Object object) {
        return tagRepository.getTags(object);
    }

    @Override
    public HashMap<Tag, LinkedList<Object>> getAll() {
        return tagRepository.getAll();
    }

    @Override
    public List<Tag> getTags() {
        return tagRepository.getTags();
    }

    @Override
    public List<Object> get(Tag tag) {
        return tagRepository.get(tag);
    }

    @Override
    public Tag get(String tagName) {
        return tagRepository.get(tagName);
    }
}
