package com.wcc.assessment.service;

import com.wcc.assessment.model.domain.DisplacementResponse;
import com.wcc.assessment.model.domain.PostCodeRequest;
import com.wcc.assessment.model.domain.PostCodeResponse;



public interface LocationService {

    public DisplacementResponse calculateDisplacement(String startingPostcode, String endingPostcode) ;

    public PostCodeResponse getPostcode(String postcode);

    public PostCodeResponse getCodeByCoords(Double latitude, Double longitude);

    PostCodeResponse updatePostCode(PostCodeRequest postCodeRequest);
}
