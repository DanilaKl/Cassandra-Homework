package homework.shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@Table(value = "offers", keyspace = "shop")
public class Offer {

    @PrimaryKey
    private OfferPrimaryKey id;

    @Column("price")
    private BigDecimal price;

    @Data
    @AllArgsConstructor
    @PrimaryKeyClass
    public static class OfferPrimaryKey {
        @PrimaryKeyColumn(
                value = "product_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED
        )
        private UUID productId;

        @PrimaryKeyColumn(
                value = "offer_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED
        )
        private UUID offerId;
    }
}
