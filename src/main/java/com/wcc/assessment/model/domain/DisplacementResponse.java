package com.wcc.assessment.model.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class DisplacementResponse {

    private String error;
    private RequestStatus requestStatus;
    @JsonProperty("targetLocations")
    private LocationResponse locationResponse;
    private double distance;

}
