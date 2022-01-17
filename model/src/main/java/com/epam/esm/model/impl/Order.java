package com.epam.esm.model.impl;

import com.epam.esm.model.AbstractEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@EntityListeners(AuditListener.class)
@Table(name = "orders")
@DynamicUpdate
@Data
@Builder
@NoArgsConstructor
public class Order extends RepresentationModel<Order> implements AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "order_has_certificate"
            , joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "gift_certificate_id", referencedColumnName = "id")
    )
    private List<GiftCertificate> certificates;
}
