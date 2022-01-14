package com.epam.esm.service.impl;

import com.epam.esm.data_provider.CustomPageProvider;
import com.epam.esm.data_provider.OrderProvider;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.InputPagesParametersIncorrect;
import com.epam.esm.service.dto.CustomPage;
import com.epam.esm.model.impl.GiftCertificate;
import com.epam.esm.model.impl.Order;
import com.epam.esm.repository.OrderDoa;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.PageSetup;
import com.epam.esm.util.PageCalculator;
import com.epam.esm.validator.PaginationVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @InjectMocks
    OrderServiceImpl service;

    @Mock
    private OrderDoa orderDoaMock;

    @Mock
    private GiftCertificateService certificateServiceMock;

    @Mock
    private UserService userServiceMock;

    @Mock
    private PageCalculator pageCalculatorMock;

    @Mock
    private PaginationVerifier paginationValidatorMock;

    private final OrderProvider provider = new OrderProvider();
    private final CustomPageProvider customPageProvider = new CustomPageProvider();

    @Test
    void findByIdPositiveTest() {
        Order expected = provider.getOrder();
        Mockito.when(orderDoaMock.findById(expected.getId())).thenReturn(Optional.of(expected));
        Order actual = service.findById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    void findByIdNegativeTest() {
        Order expected = provider.getOrder();
        Mockito.when(orderDoaMock.findById(expected.getId())).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.findById(expected.getId()));
        assertEquals(expected.getId(), exception.getId());
    }

    @Test
    public void findAllPositiveTest() {
        CustomPage<Order> expected = customPageProvider.getCustomPageOrder();
        Integer startPosition = expected.getItems().size();
        PageSetup setup = new PageSetup();
        setup.setPage(1);
        setup.setSize(10);

        Mockito.when(orderDoaMock.countRowsInTable()).thenReturn(Long.valueOf(expected.getItems().size()));
        Mockito.when(pageCalculatorMock.calculator(setup.getPage(), setup.getSize())).thenReturn(0);
//        Mockito.when(paginationValidatorMock.validate(Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
        Mockito.when(orderDoaMock.findAll(0, 10)).thenReturn(expected.getItems());

        CustomPage<Order> actual = service.findAll(setup);
        assertEquals(expected, actual);
    }

    @Test
    public void findAllNegativeTest() {
        List<Order> expected = provider.getOrderList();
        Integer startPosition = expected.size();
        PageSetup setup = new PageSetup();
        setup.setPage(-1);
        setup.setSize(-10);

        Mockito.when(paginationValidatorMock.verifyPagination(Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(false);

        assertThrows(InputPagesParametersIncorrect.class, () -> service.findAll(setup));
    }

    @Test
    public void savePositiveTest() {
        Order expected = provider.getOrder();
        GiftCertificate certificate = expected.getCertificates().get(0);
        Mockito.when(userServiceMock.findById(expected.getUser().getId())).thenReturn(expected.getUser());
        Mockito.when(certificateServiceMock.findById(certificate.getId())).thenReturn(certificate);
        Mockito.when(orderDoaMock.save(expected)).thenReturn(expected);
        Order actual = service.save(expected);
        assertEquals(expected, actual);
    }

    @Test
    public void saveNegativeTest() {
        Order expected = provider.getOrder();
        expected.setCertificates(null);
        assertThrows(InputPagesParametersIncorrect.class, () -> service.save(expected));
    }
}
