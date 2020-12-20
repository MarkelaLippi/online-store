package com.gmail.roadtojob2019.onlinestore.service.mapper;

import com.gmail.roadtojob2019.onlinestore.repository.entity.Review;
import com.gmail.roadtojob2019.onlinestore.repository.entity.User;
import com.gmail.roadtojob2019.onlinestore.service.dto.ReviewDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mappings({
            @Mapping(target="displayed", source="isDisplayed")
    })
    ReviewDto fromReviewToDto(Review review);

    @Mappings({
            @Mapping(target="isDisplayed", source="displayed")
    })
    Review fromDtoToReview(ReviewDto reviewDto);
}
