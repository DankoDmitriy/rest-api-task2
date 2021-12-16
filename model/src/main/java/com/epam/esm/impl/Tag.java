package com.epam.esm.impl;

import com.epam.esm.AbstractEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Tag implements AbstractEntity {
    private Long id;
    private String name;
}
