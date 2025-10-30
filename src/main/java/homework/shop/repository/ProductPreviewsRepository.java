package homework.shop.repository;

import homework.shop.model.ProductPreview;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductPreviewsRepository extends CassandraRepository<ProductPreview, UUID> {
}
