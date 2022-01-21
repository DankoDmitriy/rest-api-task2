package com.epam.esm.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto extends RepresentationModel<OrderDto> implements AbstractDto {

    private static final int ID_MIN_SIZE = 1;

    private Long id;
    private BigDecimal cost;
    private LocalDateTime purchaseDate;

    @NotNull(message = "{order.user.properties}")
    private UserDto userDto;

    @NotNull(message = "{order.gift.properties}")
    private List<GiftCertificateDto> giftCertificateDtoList;
}
