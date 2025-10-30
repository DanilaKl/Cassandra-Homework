package homework.shop.service;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import homework.shop.dto.GetCardDto;
import homework.shop.dto.GetCardPreviewDto;
import homework.shop.dto.GetReviewDto;
import homework.shop.dto.PutCardDto;
import homework.shop.model.Offer;
import homework.shop.model.Picture;
import homework.shop.model.Product;
import homework.shop.model.ProductPreview;
import homework.shop.repository.OffersRepository;
import homework.shop.repository.PicturesRepository;
import homework.shop.repository.ProductPreviewsRepository;
import homework.shop.repository.ProductsRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class CardService {

    private final ProductsRepository productsRepository;
    private final ProductPreviewsRepository previewsRepository;
    private final OffersRepository offersRepository;
    private final PicturesRepository picturesRepository;
    private final ReviewService reviewService;

    public CardService(ProductsRepository productsRepository,
                       ProductPreviewsRepository previewsRepository,
                       OffersRepository offersRepository,
                       PicturesRepository picturesRepository,
                       ReviewService reviewService) {
        this.productsRepository = productsRepository;
        this.previewsRepository = previewsRepository;
        this.offersRepository = offersRepository;
        this.picturesRepository = picturesRepository;
        this.reviewService = reviewService;
    }

    public List<GetCardPreviewDto> getAllCards() {
        List<ProductPreview> products = previewsRepository.findAll();

        return products.stream()
                .map(product -> new GetCardPreviewDto(
                        product.getId(), product.getName(), product.getPrice()
                ))
                .toList();
    }

    public GetCardDto getCard(UUID cardId) {
        Product product = productsRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("No card with given id: " + cardId));

        List<Offer> offers = offersRepository.findByIdProductId(cardId);

        List<Picture> pictures = picturesRepository.findByIdOwnerIdAndIdSource(cardId, Picture.Sources.PRODUCTS);

        List<GetReviewDto> reviews = reviewService.getAllReviews(cardId);

        return new GetCardDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                offers.stream().map(Offer::getPrice).toList(),
                pictures.stream().map(picture -> picture.getId().getPictureUrl()).toList(),
                reviews
        );
    }

    public UUID putCard(PutCardDto putCardDto) {
        Product product = new Product(Uuids.timeBased(), putCardDto.getName(), putCardDto.getDescription());
        productsRepository.save(product);

        UUID productId = product.getId();

        if (putCardDto.getPrices() != null && !putCardDto.getPrices().isEmpty()) {
            offersRepository.insert(createOfferEntities(productId, putCardDto.getPrices()));
        }

        BigDecimal minPrice = null;
        if (putCardDto.getPrices() != null) {
            minPrice = putCardDto.getPrices().stream().min(BigDecimal::compareTo).orElse(null);
        }

        ProductPreview productPreview = new ProductPreview(
                productId,
                putCardDto.getName(),
                minPrice
        );
        previewsRepository.insert(productPreview);

        if (putCardDto.getPictureUrls() != null &&  !putCardDto.getPictureUrls().isEmpty()) {
                picturesRepository.insert(createPictureEntities(productId, putCardDto.getPictureUrls()));
        }

        return productId;
    }

    private List<Offer> createOfferEntities(UUID productId, List<BigDecimal> prices) {
        return prices.stream()
                .map(price -> new Offer(
                        new Offer.OfferPrimaryKey(productId, Uuids.timeBased()), price
                ))
                .toList();
    }

    private List<Picture> createPictureEntities(UUID productId, List<String> pictureUrls) {
        return pictureUrls.stream()
                .filter(Objects::nonNull)
                .filter(url -> !url.isBlank())
                .map(url -> new Picture(new Picture.PicturePrimaryKey(
                        productId, Picture.Sources.PRODUCTS, url
                )))
                .toList();
    }

}
