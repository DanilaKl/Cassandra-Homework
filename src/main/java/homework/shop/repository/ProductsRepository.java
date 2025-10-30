package homework.shop.repository;

import homework.shop.model.Product;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductsRepository extends CassandraRepository<Product, UUID> {
}
