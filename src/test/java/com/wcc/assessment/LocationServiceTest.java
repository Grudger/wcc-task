package com.wcc.assessment;

import com.wcc.assessment.exception.WccException;
import com.wcc.assessment.model.PostCode;
import com.wcc.assessment.model.domain.DisplacementResponse;
import com.wcc.assessment.model.domain.RequestStatus;
import com.wcc.assessment.repository.PostCodeRepository;
import com.wcc.assessment.service.LocationService;
import com.wcc.assessment.util.DisplacementCalculator;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = LocationServiceTest.ContextConfiguration.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class LocationServiceTest {

    @MockBean
    private PostCodeRepository postCodeRepository;
    @MockBean
    private DisplacementCalculator displacementCalculator;
    @Autowired
    private LocationService locationService;

    @Test
    public void testCalculateDisplacementWithDistinctPostcodes() {

        PostCode startingPostCode = postcodeStub1();
        PostCode endingPostCode = postcodeStub2();
        String startingCode = startingPostCode.getPostcode();
        String endingCode = endingPostCode.getPostcode();
        Double lat1 = 12.345;
        Double lon1 = 67.890;
        Double lat2 = 98.765;
        Double lon2 = 43.210;

        try {
            when(postCodeRepository.findByPostcode(startingCode)).thenReturn(Optional.of(startingPostCode));
            when(postCodeRepository.findByPostcode(endingCode)).thenReturn(Optional.of(endingPostCode));
            DisplacementResponse response = locationService.calculateDisplacement(startingCode, endingCode);

            assertNotNull(response);
            assertEquals(RequestStatus.STATUS_SUCCESS, response.getRequestStatus());

            double expectedDistance = displacementCalculator.calculateDistance(12.345, 67.890, 98.765, 43.210);
            assertEquals(expectedDistance, response.getDistance(), 0.001);

        } catch (WccException e) {
            fail("Unexpected WccException occurred: " + e.getMessage());
        }
    }

    @Test
    public void testCalculateDisplacementWithSamePostcodes() {

        String samePostcode = "AB12 3CD";

        try {
            locationService.calculateDisplacement(samePostcode, samePostcode);
            fail("Expected WccException to be thrown for the same postcodes");
        } catch (WccException e) {
            assertEquals("The provided postcodes are the same!. Please input distinct postcodes", e.getMessage());
            assertEquals(HttpStatus.BAD_REQUEST, e.getErrorCode());
        }
    }

    @Test
    public void testCalculateDisplacementWithInvalidStartingPostcode() {

        String invalidStartingPostcode = "ABC123";
        String endingPostcode = "XY34 5ZW";

        try {

            locationService.calculateDisplacement(invalidStartingPostcode, endingPostcode);
            fail("Expected WccException to be thrown for an invalid starting postcode");
        } catch (WccException e) {
            assertEquals("The provided postcodes are not valid. Please enter valid postcodes for UK !", e.getMessage());
            assertEquals(HttpStatus.BAD_REQUEST, e.getErrorCode());
        }
    }

    @Test
    public void testCalculateDisplacementWithInvalidEndingPostcode() {

        String startingPostcode = "AB12 3CD";
        String invalidEndingPostcode = "XYZ123";

        try {
            locationService.calculateDisplacement(startingPostcode, invalidEndingPostcode);
            fail("Expected WccException to be thrown for an invalid ending postcode");
        } catch (WccException e) {
            assertEquals("The provided postcodes are not valid. Please enter valid postcodes for UK !", e.getMessage());
            assertEquals(HttpStatus.BAD_REQUEST, e.getErrorCode());
        }
    }

    private PostCode postcodeStub1() {
        return PostCode.builder()
                .postcode("AB10 7JB")
                .latitude(12.345d)
                .longitude(67.890d)
                .id(new ObjectId())
                .build();
    }

    private PostCode postcodeStub2() {
        return PostCode.builder()
                .postcode("AB23 8QP")
                .latitude(98.765d)
                .longitude(43.210d)
                .id(new ObjectId())
                .build();
    }

    @Configuration(proxyBeanMethods = false)
    @ComponentScan(
            useDefaultFilters = false,
            basePackageClasses = {LocationService.class},
            includeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = LocationService.class)}
    )
    public static class ContextConfiguration {
    }

}
