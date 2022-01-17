package com.epam.esm.service.converter;

import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.model.impl.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GiftCertificateToDtoConverter implements Converter<GiftCertificate, GiftCertificateDto> {
    private final TagToDtoConverter tagToDtoConverter;

    @Autowired
    public GiftCertificateToDtoConverter(TagToDtoConverter tagToDtoConverter) {
        this.tagToDtoConverter = tagToDtoConverter;
    }

    @Override
    public GiftCertificateDto convert(GiftCertificate source) {
        return GiftCertificateDto.builder()
                .id(source.getId())
                .name(source.getName())
                .description(source.getDescription())
                .duration(source.getDuration())
                .price(source.getPrice())
                .createDate(source.getCreateDate())
                .lastUpdateDate(source.getLastUpdateDate())
                .tagDtoList(source.getTags() == null ? null :
                        source.getTags().stream().map(tagToDtoConverter::convert).collect(Collectors.toList()))
                .build();
    }
}
