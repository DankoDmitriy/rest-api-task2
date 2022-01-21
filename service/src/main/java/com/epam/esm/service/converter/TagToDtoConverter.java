package com.epam.esm.service.converter;

import com.epam.esm.service.dto.TagDto;
import com.epam.esm.model.impl.Tag;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TagToDtoConverter implements Converter<Tag, TagDto> {
    @Override
    public TagDto convert(Tag source) {
        return TagDto.builder()
                .id(source.getId())
                .name(source.getName())
                .build();
    }
}
