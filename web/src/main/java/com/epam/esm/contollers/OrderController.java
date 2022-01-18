package com.epam.esm.contollers;

import com.epam.esm.hateaos.HateoasBuilder;
import com.epam.esm.service.dto.CustomPage;
import com.epam.esm.model.impl.Order;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.dto.CustomPageDto;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.PageSetup;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {
    private final OrderService orderService;
    private final HateoasBuilder hateoasBuilder;

    public OrderController(OrderService orderService, HateoasBuilder hateoasBuilder) {
        this.orderService = orderService;
        this.hateoasBuilder = hateoasBuilder;
    }

    @GetMapping
    public ResponseEntity<CustomPageDto> getAllOrders(Pageable pageable) {
        CustomPageDto<OrderDto> customPage = orderService.findAll(pageable);
//        List<Order> orderList = customPage.getItems();
//        hateoasBuilder.setLinksOrders(orderList);
        return new ResponseEntity<>(customPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable("id") long id) {
        OrderDto order = orderService.findById(id);
//        hateoasBuilder.setLinks(order);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderDto> addOrder(@RequestBody OrderDto orderDto) {
        OrderDto orderFromDataBase = orderService.save(orderDto);
//        hateoasBuilder.setLinks(orderFromDataBase);
        return new ResponseEntity<>(orderService.save(orderFromDataBase), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable("id") long id) {
        orderService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
