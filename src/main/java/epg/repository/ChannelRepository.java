package epg.repository;

import epg.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, String> {

    @Query("select c from Channel c where c.name = :name")
    Channel getChannelByName(String name);

    @Query("select c from Channel c where c.position = :position")
    Channel getChannelByPosition(int position);
}
