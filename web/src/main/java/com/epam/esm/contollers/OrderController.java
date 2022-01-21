package com.epam.esm.contollers;

import com.epam.esm.hateaos.HateoasBuilder;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.dto.CustomPageDto;
import com.epam.esm.service.dto.OrderDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
    @PreAuthorize("hasAuthority('SCOPE_order:read')")
    public ResponseEntity<CustomPageDto> getAllOrders(Pageable pageable) {
        CustomPageDto<OrderDto> customPage = orderService.findAll(pageable);
        List<OrderDto> orderList = customPage.getItems();
        hateoasBuilder.setLinksOrders(orderList);
        return new ResponseEntity<>(customPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_order:read')")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable("id") long id) {
        OrderDto order = orderService.findById(id);
        hateoasBuilder.setLinks(order);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/my/{id}")
    @PreAuthorize("authentication.name eq T(String).valueOf(#id) or hasAuthority('SCOPE_order:read') ")
    public ResponseEntity<CustomPageDto> getOrderByUserId(@PathVariable("id") long id, Pageable pageable) {
        CustomPageDto<OrderDto> customPage = orderService.findAllOrdersByUserId(id, pageable);
        List<OrderDto> orderList = customPage.getItems();
        hateoasBuilder.setLinksOrders(orderList);
        return new ResponseEntity<>(customPage, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_order:write')")
    public ResponseEntity<OrderDto> addOrder(@Valid @RequestBody OrderDto orderDto) {
        OrderDto orderFromDataBase = orderService.save(orderDto);
        hateoasBuilder.setLinks(orderFromDataBase);
        return new ResponseEntity<>(orderService.save(orderFromDataBase), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_order:read')")
    public ResponseEntity<Void> deleteOrderById(@PathVariable("id") long id) {
        orderService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
