package homework.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetCardDto {
    private UUID cardId;
    private String productName;
    private String productDescription;
    private List<BigDecimal> prices;
    private List<String> pictureUrls;
    private List<GetReviewDto> reviews;
}
