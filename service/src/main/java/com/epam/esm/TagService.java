package com.epam.esm;

import com.epam.esm.impl.Tag;

import java.util.List;

public interface TagService extends BaseService<Tag> {
    List<Tag> findAll();
}
