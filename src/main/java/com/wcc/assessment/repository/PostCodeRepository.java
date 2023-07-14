package com.wcc.assessment.repository;

import com.wcc.assessment.model.PostCode;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PostCodeRepository extends CrudRepository<PostCode, Long> {

    Optional<PostCode> findFirstByPostcode(String postcode);

    Optional<PostCode> findByPostcode(String postcode);

    Optional<PostCode> findByLongitudeAndLatitude(Double longitude,Double latitude);


}
