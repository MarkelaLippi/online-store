package com.gmail.roadtojob2019.onlinestore.validation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Violation {
    private final String fieldName;

    private final String message;
}
