package com.gmail.roadtojob2019.onlinestore.repository.entity;

import com.gmail.roadtojob2019.onlinestore.repository.converter.UnitConvertor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.Convert;
import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Embeddable
public class MeasureAmount {
    @NonNull
    private Double amount;
    @NonNull
    @Convert(converter = UnitConvertor.class)
    private Unit measure;
}
