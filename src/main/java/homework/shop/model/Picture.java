package homework.shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data
@AllArgsConstructor
@Table(value = "pictures", keyspace = "shop")
public class Picture {
    
    @PrimaryKey
    private PicturePrimaryKey id;

    @Data
    @AllArgsConstructor
    @PrimaryKeyClass
    public static class PicturePrimaryKey {
        @PrimaryKeyColumn(
                value = "owner_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED
        )
        private UUID ownerId;

        @PrimaryKeyColumn(
                value = "source", ordinal = 1, type = PrimaryKeyType.PARTITIONED
        )
        private Sources source;

        @PrimaryKeyColumn(
                value = "picture_url", ordinal = 2, type = PrimaryKeyType.CLUSTERED
        )
        private String pictureUrl;
    }

    public enum Sources {
        PRODUCTS,
        REVIEWS
    }
}
