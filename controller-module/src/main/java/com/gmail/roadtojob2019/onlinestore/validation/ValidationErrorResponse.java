package com.gmail.roadtojob2019.onlinestore.validation;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationErrorResponse {

    private final List<Violation> violations = new ArrayList<>();

}
