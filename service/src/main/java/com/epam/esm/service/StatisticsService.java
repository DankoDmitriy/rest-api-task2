package com.epam.esm.service;

import com.epam.esm.service.dto.TagDto;

/**
 * The interface Statistics service.
 */
public interface StatisticsService {
    /**
     * Popular tag tag.
     *
     * @return the TagDto
     */
    TagDto popularTag();
}
