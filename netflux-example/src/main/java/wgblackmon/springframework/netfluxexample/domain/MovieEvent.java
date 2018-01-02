package wgblackmon.springframework.netfluxexample.domain;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@Data
@NoArgsConstructor
public class MovieEvent {

    @NonNull
    private String movieId;

    private Date date;
}
