package homework.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PutReviewDto {
    @JsonProperty("product_id")
    private UUID productId;
    private String comment;
    @JsonProperty("picture_url")
    private List<String> pictureUrls;
}
