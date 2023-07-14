package com.wcc.assessment.model.domain;

import com.wcc.assessment.model.PostCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Builder
@Getter
public class PostCodeResponse {

    private String id;
    private String postcode;
    private Double latitude;
    private Double longitude;

    public static PostCodeResponse from(PostCode postCode) {
        return
                PostCodeResponse.builder()
                        .id(postCode.getId().toString())
                        .postcode(postCode.getPostcode())
                        .latitude(postCode.getLatitude())
                        .longitude(postCode.getLongitude())
                        .build();
    }
}
