package com.epam.esm.impl;

import com.epam.esm.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse implements AbstractEntity {
    private String errorMessage;
    private List<String> incorrectParameters;
    private String errorTime;
    private String errorCode;
}
