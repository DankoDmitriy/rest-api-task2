package com.epam.esm.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiftCertificateDto {

    private static final int ID_MIN_SIZE = 1;
    private static final String GIFT_CERTIFICATE_NAME_REGEXP = "^[a-zA-ZА-Яа-я0-9\\s]{2,255}$";
    private static final String GIFT_CERTIFICATE_DESCRIPTION_REGEXP = "^[a-zA-ZА-Яа-я0-9,.:;!?\\s]{2,255}$";
    private static final String PRICE_MIN = "0.01";
    private static final String PRICE_MAX = "1000.00";
    private static final int DURATION_MIN = 1;
    private static final int DURATION_MAX = 365;

    @Size(min = ID_MIN_SIZE, message = "{id.size}")
    private Long id;

    @Pattern(regexp = GIFT_CERTIFICATE_NAME_REGEXP, message = "{gift.name.properties}")
    private String name;

    @Pattern(regexp = GIFT_CERTIFICATE_DESCRIPTION_REGEXP, message = "{gift.description.properties}")
    private String description;

    @DecimalMin(value = PRICE_MIN, message = "{gift.price.properties}")
    @DecimalMax(value = PRICE_MAX, message = "{gift.price.properties}")
    private BigDecimal price;

    @Size(min = DURATION_MIN, max = DURATION_MAX, message = "{gift.duration.properties}")
    private Integer duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private List<TagDto> tagDtoList;
}
