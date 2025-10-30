package homework.shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data
@AllArgsConstructor
@Table(value = "products", keyspace = "shop")
public class Product {

    @PrimaryKeyColumn(
            value = "product_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED
    )
    @Id
    private UUID id;


    @Column(value = "name")
    private String name;

    @Column(value = "description")
    private String description;
}
