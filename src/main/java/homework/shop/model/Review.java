package homework.shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data
@AllArgsConstructor
@Table(value = "reviews", keyspace = "shop")
public class Review {

    @PrimaryKey
    private CommentPrimaryKey id;

    @Column("comment")
    private String comment;

    @Data
    @AllArgsConstructor
    @PrimaryKeyClass
    public static class CommentPrimaryKey {
        @PrimaryKeyColumn(
                value = "product_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED
        )
        private UUID productId;

        @PrimaryKeyColumn(
                value = "comment_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED
        )
        private UUID reviewId;
    }
}
