package com.epam.esm.model.impl;

import com.epam.esm.model.AbstractEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Tag implements AbstractEntity {
    private Long id;
    private String name;
}
