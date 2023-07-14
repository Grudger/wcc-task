package com.wcc.assessment.model.domain;

import com.wcc.assessment.model.PostCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostCodeRequest {

    @NotBlank
    private String id;
    @NotEmpty
    private String postcode;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;

    public static PostCodeRequest from(PostCode postCode) {
        return
                PostCodeRequest.builder()
                        .id(postCode.getId().toString())
                        .postcode(postCode.getPostcode())
                        .latitude(postCode.getLatitude())
                        .longitude(postCode.getLongitude())
                        .build();
    }

}
