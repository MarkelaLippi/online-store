package com.gmail.roadtojob2019.onlinestore.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Embeddable
public class OrderItem {
    private Item item;
    @AttributeOverrides({
            @AttributeOverride(name = "amount",
                    column = @Column(name = "product_quantity")),
            @AttributeOverride(name = "measure",
                    column = @Column(name = "measure_unit"))
    })
    private MeasureAmount measureAmount;
}
