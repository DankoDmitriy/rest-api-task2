package com.epam.esm.service.converter;

import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.model.impl.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DtoToGiftCertificateConverter implements Converter<GiftCertificateDto, GiftCertificate> {
    private final DtoToTagConverter dtoToTagConverter;

    @Autowired
    public DtoToGiftCertificateConverter(DtoToTagConverter dtoToTagConverter) {
        this.dtoToTagConverter = dtoToTagConverter;
    }

    @Override
    public GiftCertificate convert(GiftCertificateDto source) {
        return GiftCertificate.builder()
                .id(source.getId())
                .name(source.getName())
                .description(source.getDescription())
                .duration(source.getDuration())
                .price(source.getPrice())
                .createDate(source.getCreateDate())
                .lastUpdateDate(source.getLastUpdateDate())
                .tags(source.getTagDtoList() == null ? null :
                        source.getTagDtoList().stream().map(dtoToTagConverter::convert).collect(Collectors.toList()))
                .build();
    }
}
