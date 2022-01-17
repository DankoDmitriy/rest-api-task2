package com.epam.esm.model.impl;

import com.epam.esm.model.AbstractEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLInsert;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Builder
@EntityListeners(AuditListener.class)
@Table(name = "tags")
@SQLInsert(sql = "INSERT INTO Tags (name) VALUES(?) ON DUPLICATE KEY UPDATE id=LAST_INSERT_ID(id)")
@NoArgsConstructor
public class Tag extends RepresentationModel<Tag> implements AbstractEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;
}
