package com.epam.esm.repository;

import com.epam.esm.model.impl.Tag;

/**
 * The interface Tag dao.
 */
public interface TagDao extends BaseDao<Tag> {
    boolean isTagUsedInGiftCertificate(Long id);
}
