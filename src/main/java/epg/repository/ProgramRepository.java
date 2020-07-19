package epg.repository;

import epg.model.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<Program, String> {

    @Query("select p from Program p where p.startTime <= :endTime and p.endTime >= :startTime and p.channelId = :channelId")
    Program getProgramByChannelIdAndTime(String channelId, LocalDateTime startTime, LocalDateTime endTime);

    @Query("select p from Program p where p.channelId = :channelId")
    List<Program> getAllProgramsByChannelId(String channelId);
}
