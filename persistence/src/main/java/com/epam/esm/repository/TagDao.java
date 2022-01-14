package com.epam.esm.repository;

import com.epam.esm.model.impl.Tag;

/**
 * The interface Tag dao.
 */
public interface TagDao extends BaseDao<Tag> {
    /**
     * Is tag used in gift certificate boolean.
     *
     * @param id tag ID
     * @return return true if tag used in gift certificates else return false
     */
    boolean isTagUsedInGiftCertificate(Long id);
}
