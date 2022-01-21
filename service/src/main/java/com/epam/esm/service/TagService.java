package com.epam.esm.service;

import com.epam.esm.service.dto.CustomPageDto;
import com.epam.esm.service.dto.TagDto;
import org.springframework.data.domain.Pageable;

/**
 * The interface Tag service.
 */
public interface TagService extends BaseService<TagDto> {
    /**
     * Find all tags page custom page dto.
     *
     * @param pageable the pageable
     * @return the custom page dto
     */
    CustomPageDto findAllTagsPage(Pageable pageable);
}
