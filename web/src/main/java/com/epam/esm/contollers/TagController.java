package com.epam.esm.contollers;

import com.epam.esm.hateaos.HateoasBuilder;
import com.epam.esm.model.impl.CustomPage;
import com.epam.esm.model.impl.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.PageSetup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/tags", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {
    private final TagService tagService;
    private final HateoasBuilder hateoasBuilder;

    @Autowired
    public TagController(TagService tagService, HateoasBuilder hateaosBuilder) {
        this.tagService = tagService;
        this.hateoasBuilder = hateaosBuilder;
    }

    @GetMapping
    public ResponseEntity<CustomPage> getAllTags(PageSetup pageSetup) {
        CustomPage<Tag> customPage = tagService.findAll(pageSetup);
        List<Tag> tagList = customPage.getItems();
        hateoasBuilder.setLinksTags(tagList);
        return new ResponseEntity<>(customPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable("id") long id) {
        Tag tag = tagService.findById(id);
        hateoasBuilder.setLinks(tag);
        return new ResponseEntity<>(tagService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Tag> addTag(@RequestBody Tag tag) {
        Tag tagFromDataBase = tagService.save(tag);
        hateoasBuilder.setLinks(tagFromDataBase);
        return new ResponseEntity<>(tagFromDataBase, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTagById(@PathVariable("id") long id) {
        tagService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
