package com.gmail.roadtojob2019.onlinestore.validation;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Violation {

    private final String fieldName;

    private final String message;

}
