package wgblackmon.springframework.netfluxexample.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

@Document
@Data
@NoArgsConstructor
public class Movie {

    private String id;

    @NonNull
    private String title;


}   // end class
