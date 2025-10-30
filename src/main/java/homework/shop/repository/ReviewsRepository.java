package homework.shop.repository;

import homework.shop.model.Review;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewsRepository extends CassandraRepository<Review, Review.CommentPrimaryKey> {

    List<Review> findByIdProductId(UUID productId);

}
