package com.wcc.assessment.model.domain;

import com.wcc.assessment.model.PostCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LocationResponse {

    private PostCode startingPostcode;
    private PostCode endingPostCode;


}
