package com.epam.esm.data_provider;

import com.epam.esm.service.dto.TagDto;

import java.util.Arrays;
import java.util.List;

public class TagDtoProvider {
    public TagDto getTag() {
        TagDto tag = getTagWithOutId();
        tag.setId(1L);
        return tag;
    }

    public TagDto getTagWithOutId() {
        TagDto tag = new TagDto();
        tag.setName("Tag 1");
        return tag;
    }

    public List<TagDto> getTagList() {
        return Arrays.asList(getTag());
    }
}
