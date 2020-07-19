package epg.service.program;

import epg.model.Program;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProgramService {

    Program createProgram(Program program);

    List<Program> getAllProgramsByChannelId(String channelId);

    Program getProgramById(String id);

    void deleteProgramById(String id);

    Program updateProgramById(String id, Program program);
}
