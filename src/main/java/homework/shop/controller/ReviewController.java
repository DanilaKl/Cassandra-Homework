package homework.shop.controller;

import homework.shop.dto.GetReviewDto;
import homework.shop.dto.PutReviewDto;
import homework.shop.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/get/review")
    @ResponseBody
    public GetReviewDto getReview(@RequestParam("product_id") UUID productId,
                                  @RequestParam("comment_id") UUID commentId) {
        return reviewService.getReview(productId, commentId);
    }

    @GetMapping("/list/reviews")
    @ResponseBody
    public List<GetReviewDto> listReviews(@RequestParam("product_id") UUID productId) {
        return reviewService.getAllReviews(productId);
    }

    @PostMapping("/put/review")
    @ResponseBody
    public Map<String, Object> putReview(@RequestBody PutReviewDto putReviewDto) {
        var reviewId = reviewService.putReview(putReviewDto);
        return Map.of(
                "product_id", reviewId.getProductId(),
                "comment_id", reviewId.getReviewId()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error_message", ex.getMessage()));
    }
}
