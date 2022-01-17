package com.epam.esm.service.converter;

import com.epam.esm.service.dto.TagDto;
import com.epam.esm.model.impl.Tag;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToTagConverter implements Converter<TagDto, Tag> {
    @Override
    public Tag convert(TagDto source) {
        return Tag.builder()
                .id(source.getId())
                .name(source.getName())
                .build();
    }
}
