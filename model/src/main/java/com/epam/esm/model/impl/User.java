package com.epam.esm.model.impl;

import com.epam.esm.model.AbstractEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLInsert;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@EntityListeners(AuditListener.class)
@Table(name = "users")
@DynamicUpdate
@Data
@Builder
@SQLInsert(sql = "INSERT INTO Users (name) VALUES(?) ON DUPLICATE KEY UPDATE id=LAST_INSERT_ID(id)")
@NoArgsConstructor
public class User extends RepresentationModel<User> implements AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
}
