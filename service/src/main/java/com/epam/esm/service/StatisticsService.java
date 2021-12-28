package com.epam.esm.service;

import com.epam.esm.model.impl.Tag;

/**
 * The interface Statistics service.
 */
public interface StatisticsService {
    /**
     * Popular tag tag.
     *
     * @return the tag
     */
    Tag popularTag();
}
