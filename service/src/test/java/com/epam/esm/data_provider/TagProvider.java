package com.epam.esm.data_provider;

import com.epam.esm.model.impl.Tag;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TagProvider {

    public Tag getTag() {
        Tag tag = getTagWithOutId();
        tag.setId(1L);
        return tag;
    }

    public Tag getTagWithOutId() {
        Tag tag = new Tag();
        tag.setName("Tag 1");
        return tag;
    }

    public List<Tag> getTagList() {
        return Collections.singletonList(getTag());
    }

}
