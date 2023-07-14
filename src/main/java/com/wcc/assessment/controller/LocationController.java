package com.wcc.assessment.controller;

import com.wcc.assessment.exception.WccException;
import com.wcc.assessment.model.domain.DisplacementResponse;
import com.wcc.assessment.model.domain.PostCodeRequest;
import com.wcc.assessment.model.domain.PostCodeResponse;
import com.wcc.assessment.service.LocationService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.wcc.assessment.util.Validator.isValidPostCode;

@RequestMapping(value = "/location")
@RestController
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping(value = "distance")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @Description("Get displacement between two postcodes")
    public DisplacementResponse getDisplacement(
            @RequestParam @NotBlank String startingPostcode,
            @RequestParam @NotBlank String endingPostcode) {

        return locationService.calculateDisplacement(startingPostcode, endingPostcode);
    }

    @GetMapping(value = "postcode/{postcode}")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @Description("Get details for a given postcode")
    public PostCodeResponse getPostcodeByCode(@PathVariable @NotBlank String postcode) {
        return locationService.getPostcode(postcode);
    }

    @GetMapping(value = "postcode")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @Description("Get a postcode by coordinates")
    public PostCodeResponse getPostcodeByCoords(@RequestParam @NotNull Double latitude, @RequestParam @NotNull Double longitude) {
        return locationService.getCodeByCoords(latitude, longitude);
    }

    @PutMapping(value = "update")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @Description("Update a postcode by ID")
    public PostCodeResponse updatePostCode(@RequestBody PostCodeRequest postCodeRequest){

        if (!isValidPostCode(postCodeRequest.getPostcode())) {
            throw new WccException("The provided postcode is not valid. Please enter valid postcode for UK !", HttpStatus.BAD_REQUEST);
        }

        return locationService.updatePostCode(postCodeRequest);
    }


}
