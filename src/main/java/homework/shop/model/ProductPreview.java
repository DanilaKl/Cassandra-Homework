package homework.shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@Table(value = "product_previews", keyspace = "shop")
public class ProductPreview {
    @PrimaryKeyColumn(
            value = "product_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED
    )
    @Id
    private UUID id;

    @Column(value = "name")
    private String name;

    @Column(value = "price")
    private BigDecimal price;
}
