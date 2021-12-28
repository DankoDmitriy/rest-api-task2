package com.epam.esm.repository;

import com.epam.esm.model.impl.Tag;

/**
 * The interface Tag dao.
 */
public interface TagDao extends BaseDao<Tag> {
    /**
     * Is tag used in gift certificate boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean isTagUsedInGiftCertificate(Long id);
}
