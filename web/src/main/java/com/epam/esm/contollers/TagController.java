package com.epam.esm.contollers;

import com.epam.esm.TagService;
import com.epam.esm.impl.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tags", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {
    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<Tag> getAllTags() {
        List<Tag> tagItems = tagService.findAll();
        return tagItems;
    }

    @GetMapping("/{id}")
    public Tag getTagById(@PathVariable("id") long id) {
        return tagService.findById(id).orElse(null);
    }

    @PostMapping
    public Tag addTag(@RequestBody Tag tag) {
        tagService.save(tag);
        return tag;
    }

    @DeleteMapping("/{id}")
    public String deleteTagById(@PathVariable("id") long id) {
        tagService.delete(id);
        return "delete";
    }
}
