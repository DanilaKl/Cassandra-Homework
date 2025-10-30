package homework.shop.repository;

import homework.shop.model.Picture;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PicturesRepository extends CassandraRepository<Picture, Picture.PicturePrimaryKey> {

    List<Picture> findByIdOwnerIdAndIdSource(UUID ownerId, Picture.Sources source);

}
