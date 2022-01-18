package com.epam.esm.service.impl;

import com.epam.esm.data_provider.OrderDtoProvider;
import com.epam.esm.data_provider.OrderProvider;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.model.impl.Order;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.service.DtoToEntityConverterService;
import com.epam.esm.service.EntityToDtoConverterService;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.CustomPageDto;
import com.epam.esm.service.dto.OrderDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @InjectMocks
    OrderServiceImpl service;

    @Mock
    private GiftCertificateService certificateService;

    @Mock
    private UserService userService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private DtoToEntityConverterService dtoToEntityConverterService;

    @Mock
    private EntityToDtoConverterService entityToDtoConverterService;

    private final OrderProvider orderProvider = new OrderProvider();
    private final OrderDtoProvider orderDtoProvider = new OrderDtoProvider();

    @Test
    void findByIdPositiveTest() {
        OrderDto expected = orderDtoProvider.getOrder();
        Order order = orderProvider.getOrder();

        Mockito.when(orderRepository.findById(expected.getId())).thenReturn(Optional.of(order));
        Mockito.when(entityToDtoConverterService.convert(Mockito.any(Order.class))).thenReturn(expected);

        OrderDto actual = service.findById(expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    void findByIdNegativeTest() {
        Optional<Order> expected = Optional.empty();
        OrderDto orderDto = orderDtoProvider.getOrder();

        Mockito.when(orderRepository.findById(orderDto.getId())).thenReturn(expected);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.findById(orderDto.getId()));

        assertEquals(orderDto.getId(), exception.getId());
    }

    @Test
    public void findAllPositiveTest() {
        List<Order> orders = orderProvider.getOrderList();
        List<OrderDto> orderDtos = orderDtoProvider.getOrderList();
        Page<Order> page = new PageImpl<>(orders);
        Pageable pageable = PageRequest.of(0, 10);
        CustomPageDto<OrderDto> expected = new CustomPageDto(10, 1L, 1, 0, orderDtos);

        Mockito.when(orderRepository.findAll(pageable)).thenReturn(page);
        for (int i = 0; i < orders.size(); i++) {
            Mockito.when(entityToDtoConverterService.convert(orders.get(i))).thenReturn(orderDtos.get(i));
        }

        CustomPageDto actual = service.findAll(pageable);

        assertEquals(expected, actual);
    }

    @Test
    public void savePositiveTest() {
        Order orderWithOutId = orderProvider.getOrderWithOutId();
        Order order = orderProvider.getOrder();
        OrderDto orderDtoTagWithOutId = orderDtoProvider.getOrderWithOutId();
        OrderDto expected = orderDtoProvider.getOrder();

        Mockito.when(orderRepository.save(orderWithOutId)).thenReturn(order);
        Mockito.when(dtoToEntityConverterService.convert(orderDtoTagWithOutId)).thenReturn(orderWithOutId);
        Mockito.when(entityToDtoConverterService.convert(Mockito.any(Order.class))).thenReturn(expected);

        for (int i = 0; i < orderDtoTagWithOutId.getGiftCertificateDtoList().size(); i++) {
            Mockito.when(certificateService.findById(orderDtoTagWithOutId.getGiftCertificateDtoList().get(i).getId()))
                    .thenReturn(expected.getGiftCertificateDtoList().get(i));
        }

        Mockito.when(userService.findById(orderDtoTagWithOutId.getUserDto().getId())).thenReturn(expected.getUserDto());

        OrderDto actual = service.save(orderDtoTagWithOutId);
        assertEquals(expected, actual);
    }
}
