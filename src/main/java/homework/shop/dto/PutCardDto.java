package homework.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PutCardDto {
    private String name;
    private String description;
    private List<BigDecimal> prices;
    @JsonProperty("picture_urls")
    private List<String> pictureUrls;
}
