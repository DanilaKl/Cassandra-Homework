package homework.shop.service;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import homework.shop.dto.GetReviewDto;
import homework.shop.dto.PutReviewDto;
import homework.shop.model.Picture;
import homework.shop.model.Review;
import homework.shop.repository.ProductsRepository;
import homework.shop.repository.ReviewsRepository;
import homework.shop.repository.PicturesRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ReviewService {
    private final ProductsRepository productsRepository;
    private final ReviewsRepository reviewsRepository;
    private final PicturesRepository picturesRepository;

    public ReviewService(ProductsRepository productsRepository,
                         ReviewsRepository reviewsRepository,
                         PicturesRepository picturesRepository) {
        this.productsRepository = productsRepository;
        this.reviewsRepository = reviewsRepository;
        this.picturesRepository = picturesRepository;
    }

    public List<GetReviewDto> getAllReviews(UUID productId) {
        List<Review> reviews = reviewsRepository.findByIdProductId(productId);

        List<GetReviewDto> getReviewDtos = new ArrayList<>(reviews.size());
        for (Review review : reviews) {
            List<Picture> pictures = picturesRepository.findByIdOwnerIdAndIdSource(
                    review.getId().getReviewId(),
                    Picture.Sources.REVIEWS
            );

            GetReviewDto dto = mapToDto(review, pictures);
            getReviewDtos.add(dto);
        }

        return getReviewDtos;
    }

    public GetReviewDto getReview(UUID productId, UUID commentId) {
        Review review = reviewsRepository.findById(new Review.CommentPrimaryKey(productId, commentId))
                .orElseThrow(() -> new IllegalArgumentException(
                        "No comment with given id: " + productId + " " + commentId
                ));

        List<Picture> pictures = picturesRepository.findByIdOwnerIdAndIdSource(
                review.getId().getReviewId(), Picture.Sources.REVIEWS
        );

        return mapToDto(review, pictures);
    }

    public Review.CommentPrimaryKey putReview(PutReviewDto putReviewDto) {
        if (putReviewDto.getComment() == null || putReviewDto.getComment().isBlank()) {
            throw new IllegalArgumentException("Comments can't be empty!");
        }

        if (!productsRepository.existsById(putReviewDto.getProductId())) {
            throw new IllegalArgumentException("No product with given id");
        }

        Review review = new Review(
                new Review.CommentPrimaryKey(putReviewDto.getProductId(), Uuids.timeBased()),
                putReviewDto.getComment()
        );
        reviewsRepository.insert(review);

        if (putReviewDto.getPictureUrls() != null && !putReviewDto.getPictureUrls().isEmpty()) {
            picturesRepository.insert(createPictureEntities(
                    review.getId().getReviewId(), putReviewDto.getPictureUrls()
            ));
        }

        return review.getId();
    }

    private GetReviewDto mapToDto(Review review, List<Picture> pictures) {
        GetReviewDto dto = new GetReviewDto();
        dto.setProductId(review.getId().getProductId());
        dto.setReviewId(review.getId().getReviewId());
        dto.setComment(review.getComment());

        if (!pictures.isEmpty()) {
            dto.setPictureUrls(pictures.stream()
                    .map(picture -> picture.getId().getPictureUrl())
                    .toList());
        }

        return dto;
    }

    private List<Picture> createPictureEntities(UUID reviewId, List<String> pictureUrls) {
        return pictureUrls.stream()
                .filter(Objects::nonNull)
                .filter(url -> !url.isBlank())
                .map(url -> new Picture(new Picture.PicturePrimaryKey(
                        reviewId, Picture.Sources.REVIEWS, url
                )))
                .toList();
    }
}
