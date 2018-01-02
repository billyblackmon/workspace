package wgblackmon.springframework.netfluxexample.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import wgblackmon.springframework.netfluxexample.domain.Movie;

/**
 * Created by wgb on 1/1/2018
 */
public interface MovieRepository extends ReactiveMongoRepository<Movie, String> {

    


}   // end class
