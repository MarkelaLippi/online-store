package com.gmail.roadtojob2019.onlinestore.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Embeddable
public class Price {
    @NonNull
    private Currency currency;
    @NonNull
    private BigDecimal amount;
}
