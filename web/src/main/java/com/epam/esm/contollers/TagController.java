package com.epam.esm.contollers;

import com.epam.esm.hateaos.HateoasBuilder;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.CustomPageDto;
import com.epam.esm.service.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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

import javax.validation.Valid;
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
    public ResponseEntity<CustomPageDto> getAllTags(Pageable pageable) {
        CustomPageDto customPageDto = tagService.findAllTagsPage(pageable);
        List<TagDto> tagDtos = customPageDto.getItems();
        hateoasBuilder.setLinksTags(tagDtos);
        return new ResponseEntity<>(customPageDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> getTagById(@PathVariable("id") long id) {
        TagDto tag = tagService.findById(id);
        hateoasBuilder.setLinks(tag);
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TagDto> addTag(@Valid @RequestBody TagDto tag) {
        TagDto tagFromDataBase = tagService.save(tag);
        hateoasBuilder.setLinks(tagFromDataBase);
        return new ResponseEntity<>(tagFromDataBase, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTagById(@PathVariable("id") long id) {
        tagService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
