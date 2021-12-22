package com.epam.esm.service.impl;

import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.InputPagesParametersIncorrect;
import com.epam.esm.model.impl.Order;
import com.epam.esm.repository.OrderDoa;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.PageSetup;
import com.epam.esm.util.PageCalculator;
import com.epam.esm.validator.PaginationValidator;
import com.epam.esm.validator.ValidationError;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderDoa orderDoa;
    private final GiftCertificateService certificateService;
    private final UserService userService;
    private final PageCalculator pageCalculator;
    private final PaginationValidator paginationValidator;

    @Autowired
    public OrderServiceImpl(OrderDoa orderDoa,
                            GiftCertificateService certificateService,
                            UserService userService,
                            PageCalculator pageCalculator,
                            PaginationValidator paginationValidator) {
        this.orderDoa = orderDoa;
        this.certificateService = certificateService;
        this.userService = userService;
        this.pageCalculator = pageCalculator;
        this.paginationValidator = paginationValidator;
    }

    @Override
    public List<Order> findAll(PageSetup pageSetup) {
        Long rowsInDataBase = orderDoa.rowsInTable();
        Integer startPosition = pageCalculator.calculator(pageSetup.getPage(), pageSetup.getSize());
        if (paginationValidator.validate(rowsInDataBase, pageSetup.getPage(), startPosition)) {
            return orderDoa.findAll(startPosition, pageSetup.getSize());
        } else {
            throw new InputPagesParametersIncorrect(ValidationError.PROBLEM_WITH_INPUT_PARAMETERS);
        }
    }

    @Override
    public Order findById(Long id) {
        Optional<Order> optionalTag = orderDoa.findById(id);
        if (!optionalTag.isPresent()) {
            throw new EntityNotFoundException(ValidationError.ORDER_NOT_FOUND_BY_ID, id);
        }
        return optionalTag.get();
    }

    @Override
    @Transactional
    public Order save(Order order) {
        order.setUser(userService.findById(order.getUser().getId()));
        BigDecimal orderCost = BigDecimal.valueOf(0.00);
        for (int i = 0; i < order.getCertificates().size(); i++) {
            var certificate = certificateService.findById(order.getCertificates().get(i).getId());
            orderCost = orderCost.add(certificate.getPrice());
            order.getCertificates().set(i, certificate);
        }
        order.setCost(orderCost);
        order.setPurchaseDate(LocalDateTime.now());
        return orderDoa.save(order);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Order order = findById(id);
        orderDoa.delete(order);
    }
}
