package epg.service.program;

import epg.exception.BusinessException;
import epg.exception.ResourceNotFoundException;
import epg.model.Program;
import epg.repository.ChannelRepository;
import epg.repository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProgramServiceImpl implements ProgramService {

    private final ChannelRepository channelRepository;
    private final ProgramRepository programRepository;

    @Autowired
    public ProgramServiceImpl(ChannelRepository channelRepository, ProgramRepository programRepository) {
        this.channelRepository = channelRepository;
        this.programRepository = programRepository;
    }


    @Override
    public Program createProgram(Program program) {
        verifyProgramIsValid(program);
        verifyStartTimeBeforeEndTime(program);
        verifyChannelExists(program);
        verifyProgramTimeDoesNotOverlapExistingProgramTimeFromSameChannel(program);

        return programRepository.save(program);
    }

    @Override
    public List<Program> getAllProgramsByChannelId(String channelId) {
        return programRepository.getAllProgramsByChannelId(channelId);
    }

    @Override
    public Program getProgramById(String id) {
        return programRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No program found for this id: " + id, "Id: " + id));
    }

    @Override
    public void deleteProgramById(String id) {
        programRepository.deleteById(id);
    }

    @Override
    public Program updateProgramById(String id, Program program) {
        Program programToBeUpdated = programRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Program with id " + id + " not found!", "Id: " + id));

        boolean programUpdateHasNoChanges = assertThatProgramUpdateHasNoChanges(programToBeUpdated, program);

        if (programUpdateHasNoChanges) {
            return programToBeUpdated;
        }

        setProgramUpdate(programToBeUpdated, program);

        verifyStartTimeBeforeEndTime(programToBeUpdated);
        verifyChannelExists(programToBeUpdated);
        verifyProgramTimeDoesNotOverlapExistingProgramTimeFromSameChannel(programToBeUpdated);

        programRepository.save(programToBeUpdated);

        return programToBeUpdated;
    }

    private void verifyProgramIsValid(Program program) {
        if (program.getChannelId() == null) {
            throw new BusinessException("Program needs to have a channel id!");
        }

        if (program.getImageUrl() == null) {
            throw new BusinessException("Program needs to have a image URL!");
        }

        if (program.getTitle() == null) {
            throw new BusinessException("Program needs to have a title!");
        }

        if (program.getDescription() == null) {
            throw new BusinessException("Program needs to have a description!");
        }

        if (program.getStartTime() == null) {
            throw new BusinessException("Program needs to have a start time!");
        }

        if (program.getEndTime() == null) {
            throw new BusinessException("Program needs to have a end time!");
        }
    }

    private void verifyStartTimeBeforeEndTime(Program program) {
        LocalDateTime startTime = program.getStartTime();
        LocalDateTime endTime = program.getEndTime();

        if (startTime.isAfter(endTime)) {
            throw new BusinessException("The start time must be before the end time!",
                                        "Start time: " + startTime + "; End time: " +
                                                endTime);
        } else if (startTime.equals(endTime)) {
            throw new BusinessException("The start time and the end time are the same!",
                                        "Start time: " + startTime + "; End time: " +
                                                endTime);
        }
    }

    private void verifyChannelExists(Program program) {
        channelRepository.findById(program.getChannelId()).orElseThrow(
                () -> new ResourceNotFoundException("Channel with id " + program.getChannelId() + " not found!",
                                                    "Id: " + program.getChannelId()));
    }

    private void verifyProgramTimeDoesNotOverlapExistingProgramTimeFromSameChannel(Program program) {
        String channelId = program.getChannelId();
        LocalDateTime startTime = program.getStartTime();
        LocalDateTime endTime = program.getEndTime();

        Program overlappingProgram = programRepository.getProgramByChannelIdAndTime(channelId, startTime, endTime);

        if (overlappingProgram != null) {
            throw new BusinessException(
                    "There is already a program starting at " + overlappingProgram.getStartTime() + " and ending at " +
                            overlappingProgram.getEndTime(),
                    "Start time: " + overlappingProgram.getStartTime() + "; End time: " +
                            overlappingProgram.getEndTime());
        }
    }

    private boolean assertThatProgramUpdateHasNoChanges(Program programToBeUpdated, Program programUpdate) {
        return programToBeUpdated.getChannelId().equals(programUpdate.getChannelId()) &&
                programToBeUpdated.getImageUrl().equals(programUpdate.getImageUrl()) &&
                programToBeUpdated.getTitle().equals(programUpdate.getTitle()) &&
                programToBeUpdated.getDescription().equals(programUpdate.getDescription()) &&
                programToBeUpdated.getStartTime().equals(programUpdate.getStartTime()) &&
                programToBeUpdated.getEndTime().equals(programUpdate.getEndTime());
    }

    private void setProgramUpdate(Program programToBeUpdated, Program programUpdate) {
        programToBeUpdated.setChannelId(programUpdate.getChannelId() == null ?
                                        programToBeUpdated.getChannelId() :
                                        programUpdate.getChannelId());

        programToBeUpdated.setImageUrl(
                programUpdate.getImageUrl() == null ?
                programToBeUpdated.getImageUrl() :
                programUpdate.getImageUrl());

        programToBeUpdated
                .setTitle(programUpdate.getTitle() == null ?
                          programToBeUpdated.getTitle() :
                          programUpdate.getTitle());

        programToBeUpdated.setDescription(programUpdate.getDescription() == null ?
                                          programToBeUpdated.getDescription() :
                                          programUpdate.getDescription());

        programToBeUpdated.setStartTime(programUpdate.getStartTime() == null ?
                                        programToBeUpdated.getStartTime() :
                                        programUpdate.getStartTime());

        programToBeUpdated.setEndTime(
                programUpdate.getEndTime() == null ?
                programToBeUpdated.getEndTime() :
                programUpdate.getEndTime());
    }
}
