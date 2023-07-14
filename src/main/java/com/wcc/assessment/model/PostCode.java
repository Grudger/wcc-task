package com.wcc.assessment.model;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "postcodes")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PostCode {

    @MongoId
    private ObjectId id;
    @Indexed(unique = true)
    private String postcode;
    private Double latitude;
    private Double longitude;

}
