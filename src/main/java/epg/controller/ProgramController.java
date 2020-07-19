package epg.controller;

import epg.model.Program;
import epg.service.program.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static epg.controller.RestEndpoint.BASE_URL;

@RestController
@RequestMapping(BASE_URL + "/programs")
public class ProgramController extends AbstractController {

    @Autowired
    private ProgramService programService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Program createProgram(@Valid @RequestBody Program program) {
        return programService.createProgram(program);
    }

    @GetMapping("/channelId/{channelId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Program> getAllProgramsByChannelId(@PathVariable(name = "channelId") String channelId) {
        return programService.getAllProgramsByChannelId(channelId);
    }

    @GetMapping("/programId/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Program getProgramById(@PathVariable(name = "id") String id) {
        return programService.getProgramById(id);
    }

    @DeleteMapping("/programId/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteProgramById(@PathVariable(name = "id") String id) {
        boolean wasDeleted = programService.deleteProgramById(id);

        if(wasDeleted) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/programId/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Program updateProgramById(@PathVariable(name = "id") String id, @RequestBody Program program) {
        return programService.updateProgramById(id, program);
    }
}
