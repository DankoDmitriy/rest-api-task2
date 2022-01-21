package com.epam.esm.service.impl;

import com.epam.esm.constant.ErrorMessagesConstant;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.model.impl.Order;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.service.DtoToEntityConverterService;
import com.epam.esm.service.EntityToDtoConverterService;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.CustomPageDto;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final GiftCertificateService certificateService;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final DtoToEntityConverterService dtoToEntityConverterService;
    private final EntityToDtoConverterService entityToDtoConverterService;

    public OrderServiceImpl(GiftCertificateService certificateService,
                            UserService userService,
                            OrderRepository orderRepository,
                            DtoToEntityConverterService dtoToEntityConverterService,
                            EntityToDtoConverterService entityToDtoConverterService) {
        this.certificateService = certificateService;
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.dtoToEntityConverterService = dtoToEntityConverterService;
        this.entityToDtoConverterService = entityToDtoConverterService;
    }

    @Override
    public CustomPageDto findAll(Pageable pageable) {
        Page<Order> page = orderRepository.findAll(pageable);
        return CustomPageDto.builder()
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .number(page.getNumber())
                .size(pageable.getPageSize())
                .items(
                        page.getContent()
                                .stream()
                                .map(entityToDtoConverterService::convert)
                                .collect(Collectors.toList()))
                .build();
    }

    @Override
    public OrderDto findById(Long id) {
        return entityToDtoConverterService.convert(findOrderById(id));
    }

    @Override
    @Transactional
    public OrderDto save(OrderDto orderDto) {
        BigDecimal orderCost = BigDecimal.valueOf(0.00);
        List<GiftCertificateDto> certificatesFromBase = new ArrayList<>();

        for (int i = 0; i < orderDto.getGiftCertificateDtoList().size(); i++) {
            GiftCertificateDto certificate;
            certificate = certificateService.findById(orderDto.getGiftCertificateDtoList().get(i).getId());
            orderCost = orderCost.add(certificate.getPrice());
            certificatesFromBase.add(certificate);
        }

        orderDto.setUserDto(userService.findById(orderDto.getUserDto().getId()));
        orderDto.setGiftCertificateDtoList(certificatesFromBase);
        orderDto.setCost(orderCost);
        orderDto.setPurchaseDate(LocalDateTime.now());

        return entityToDtoConverterService.convert(
                orderRepository.save(
                        dtoToEntityConverterService.convert(orderDto)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Order Order = findOrderById(id);
        orderRepository.delete(Order);
    }

    private Order findOrderById(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (!optionalOrder.isPresent()) {
            throw new EntityNotFoundException(ErrorMessagesConstant.ORDER_NOT_FOUND_BY_ID, id);
        } else {
            return optionalOrder.get();
        }
    }
}
