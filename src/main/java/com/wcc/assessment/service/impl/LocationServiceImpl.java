package com.wcc.assessment.service.impl;

import com.wcc.assessment.exception.WccException;
import com.wcc.assessment.model.PostCode;
import com.wcc.assessment.model.domain.*;
import com.wcc.assessment.repository.PostCodeRepository;
import com.wcc.assessment.service.LocationService;
import com.wcc.assessment.util.DisplacementCalculator;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.wcc.assessment.util.Validator.isValidPostCode;

@Service

public class LocationServiceImpl implements LocationService {


    @Autowired
    private PostCodeRepository postCodeRepository;
    @Autowired
    private DisplacementCalculator displacementCalculator;

    @Override
    public DisplacementResponse calculateDisplacement(String startingPostcode, String endingPostcode) throws WccException {

        // check if given postcodes are the same
        if (startingPostcode.equals(endingPostcode)) {
            throw new WccException("The provided postcodes are the same!. Please input distinct postcodes", HttpStatus.BAD_REQUEST);
        }
        // check if the given postcodes are valid
        if (!(isValidPostCode(startingPostcode) && isValidPostCode(endingPostcode))) {
            throw new WccException("The provided postcodes are not valid. Please enter valid postcodes for UK !", HttpStatus.BAD_REQUEST);
        }

        PostCode postCode1 = postCodeRepository.findByPostcode(startingPostcode).orElseThrow(()
                -> new WccException("Postcode " + startingPostcode + " not present in system", HttpStatus.INTERNAL_SERVER_ERROR));
        PostCode postCode2 = postCodeRepository.findByPostcode(endingPostcode).orElseThrow(()
                -> new WccException("Postcode " + startingPostcode + " not present in system", HttpStatus.INTERNAL_SERVER_ERROR));

        double distance = displacementCalculator.calculateDistance(
                postCode1.getLatitude(), postCode1.getLongitude(), postCode2.getLatitude(), postCode2.getLongitude());

        return DisplacementResponse.builder()
                .requestStatus(RequestStatus.STATUS_SUCCESS)
                .locationResponse(new LocationResponse(postCode1, postCode2))
                .distance(distance)
                .error(null)
                .build();

    }

    @Override
    public PostCodeResponse getPostcode(String postcode) throws WccException {
        // check if the given postcodes are valid
        if (!isValidPostCode(postcode)) {
            throw new WccException("The provided postcode is not valid. Please enter valid postcode for UK !", HttpStatus.BAD_REQUEST);
        } else {
            PostCode postCode = postCodeRepository.findByPostcode(postcode).orElseThrow(() ->
                    new WccException("The provided postcode does not exist in the system", HttpStatus.NOT_FOUND));
            return PostCodeResponse.from(postCode);
        }
    }

    @Override
    public PostCodeResponse getCodeByCoords(Double latitude, Double longitude) throws WccException {

        PostCode postCode = postCodeRepository.findByLongitudeAndLatitude(latitude, longitude).orElseThrow(() ->
                new WccException("The provided coordinates do not exist in system", HttpStatus.NOT_FOUND));
        return PostCodeResponse.from(postCode);
    }

    @Override
    public PostCodeResponse updatePostCode(PostCodeRequest postCodeRequest) throws WccException {

        postCodeRepository.findByPostcode(postCodeRequest.getPostcode()).orElseThrow(() ->
                new WccException("The provided postcode is either not valid or does not exist in system", HttpStatus.BAD_REQUEST));

        PostCode postCodeUpdate = PostCode.builder()
                .id(new ObjectId(postCodeRequest.getId()))
                .postcode(postCodeRequest.getPostcode())
                .longitude(postCodeRequest.getLongitude())
                .latitude(postCodeRequest.getLatitude())
                .build();
        postCodeRepository.save(postCodeUpdate);

        return PostCodeResponse.from(postCodeUpdate);
    }

}
