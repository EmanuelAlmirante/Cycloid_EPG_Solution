package epg.service.program;

import epg.exception.BusinessException;
import epg.exception.ResourceNotFoundException;
import epg.exception.TechnicalException;
import epg.model.Channel;
import epg.model.Program;
import epg.repository.ChannelRepository;
import epg.repository.ProgramRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProgramServiceImplTests {

    @Mock
    private ChannelRepository channelRepository;

    @Mock
    private ProgramRepository programRepository;

    @InjectMocks
    private ProgramServiceImpl programServiceImpl;

    @Test
    public void createProgramSuccessfully() {
        // Arrange
        String channelId = "0d8d1a97-bec1-4d23-91b6-e164f6c635c6";
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programToBeCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        // Act
        when(channelRepository.findById(channelId)).thenReturn(Optional.of(new Channel()));
        when(programRepository.getProgramByChannelIdAndTime(channelId, startTime, endTime)).thenReturn(null);
        when(programRepository.save(programToBeCreated)).thenReturn(programToBeCreated);

        Program programCreated = programServiceImpl.createProgram(programToBeCreated);

        // Assert
        assertNotNull(programCreated);
        assertEquals(channelId, programCreated.getChannelId());
        assertEquals(imageUrl, programCreated.getImageUrl());
        assertEquals(title, programCreated.getTitle());
        assertEquals(description, programCreated.getDescription());
        assertEquals(startTime, programCreated.getStartTime());
        assertEquals(endTime, programCreated.getEndTime());
    }

    @Test(expected = BusinessException.class)
    public void createProgramWithoutChannelIdFails() {
        // Arrange
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programToBeCreated =
                Program.Builder.programWith().withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        // Act
        try {
            programServiceImpl.createProgram(programToBeCreated);
        } catch (BusinessException be) {
            String exceptionMessage = "Program needs to have a channel id!";
            assertEquals(exceptionMessage, be.getMessage());
            throw be;
        }

        fail("Business exception when creating program without channel id was not thrown!");
    }

    @Test(expected = BusinessException.class)
    public void createProgramWithoutImageUrlFails() {
        // Arrange
        String channelId = "0d8d1a97-bec1-4d23-91b6-e164f6c635c6";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programToBeCreated =
                Program.Builder.programWith().withChannelId(channelId).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        // Act
        try {
            programServiceImpl.createProgram(programToBeCreated);
        } catch (BusinessException be) {
            String exceptionMessage = "Program needs to have a image URL!";
            assertEquals(exceptionMessage, be.getMessage());
            throw be;
        }

        fail("Business exception when creating program without image URL was not thrown!");
    }

    @Test(expected = BusinessException.class)
    public void createProgramWithoutTitleFails() {
        // Arrange
        String channelId = "0d8d1a97-bec1-4d23-91b6-e164f6c635c6";
        String imageUrl = "http://cycloid.com/channel1-image/";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programToBeCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        // Act
        try {
            programServiceImpl.createProgram(programToBeCreated);
        } catch (BusinessException be) {
            String exceptionMessage = "Program needs to have a title!";
            assertEquals(exceptionMessage, be.getMessage());
            throw be;
        }

        fail("Business exception when creating program without title was not thrown!");
    }

    @Test(expected = BusinessException.class)
    public void createProgramWithoutDescriptionFails() {
        // Arrange
        String channelId = "0d8d1a97-bec1-4d23-91b6-e164f6c635c6";
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programToBeCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withStartTime(startTime).withEndTime(endTime).build();

        // Act
        try {
            programServiceImpl.createProgram(programToBeCreated);
        } catch (BusinessException be) {
            String exceptionMessage = "Program needs to have a description!";
            assertEquals(exceptionMessage, be.getMessage());
            throw be;
        }

        fail("Business exception when creating program without description was not thrown!");
    }

    @Test(expected = BusinessException.class)
    public void createProgramWithoutStartTimeFails() {
        // Arrange
        String channelId = "0d8d1a97-bec1-4d23-91b6-e164f6c635c6";
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programToBeCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withEndTime(endTime).build();

        // Act
        try {
            programServiceImpl.createProgram(programToBeCreated);
        } catch (BusinessException be) {
            String exceptionMessage = "Program needs to have a start time!";
            assertEquals(exceptionMessage, be.getMessage());
            throw be;
        }

        fail("Business exception when creating program without start time was not thrown!");
    }

    @Test(expected = BusinessException.class)
    public void createProgramWithoutEndTimeFails() {
        // Arrange
        String channelId = "0d8d1a97-bec1-4d23-91b6-e164f6c635c6";
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);

        Program programToBeCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).build();

        // Act
        try {
            programServiceImpl.createProgram(programToBeCreated);
        } catch (BusinessException be) {
            String exceptionMessage = "Program needs to have a end time!";
            assertEquals(exceptionMessage, be.getMessage());
            throw be;
        }

        fail("Business exception when creating program without end time was not thrown!");
    }

    @Test(expected = BusinessException.class)
    public void createProgramWithStartTimeAfterEndTimeFails() {
        // Arrange
        String channelId = "06d6232c-dca4-493d-86fb-f82f057dc5d5";
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 13, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programToBeCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        // Act
        try {
            programServiceImpl.createProgram(programToBeCreated);
        } catch (BusinessException be) {
            String exceptionMessage = "The start time must be before the end time!";
            assertEquals(exceptionMessage, be.getMessage());
            throw be;
        }

        fail("Business exception of start time being after end time was not thrown!");
    }

    @Test(expected = BusinessException.class)
    public void createProgramWithStartTimeEqualToEndTimeFails() {
        // Arrange
        String channelId = "06d6232c-dca4-493d-86fb-f82f057dc5d5";
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);

        Program programToBeCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        // Act
        try {
            programServiceImpl.createProgram(programToBeCreated);
        } catch (BusinessException be) {
            String exceptionMessage = "The start time and the end time are the same!";
            assertEquals(exceptionMessage, be.getMessage());
            throw be;
        }

        fail("Business exception of start time being equal to end time was not thrown!");
    }

    @Test(expected = ResourceNotFoundException.class)
    public void createProgramWithNonExistingChannelIdFails() {
        // Arrange
        String channelId = "06d6232c-dca4-493d-86fb-f82f057dc5d5";
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programToBeCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        // Act
        when(channelRepository.findById(channelId))
                .thenThrow(new ResourceNotFoundException("Channel with id " + channelId + " not found!",
                                                         "Id: " + channelId));

        try {
            programServiceImpl.createProgram(programToBeCreated);
        } catch (ResourceNotFoundException re) {
            String exceptionMessage = "Channel with id " + channelId + " not found!";
            assertEquals(exceptionMessage, re.getMessageKey());
            throw re;
        }

        fail("Resource not found exception when channel does not exist was not thrown!");
    }

    @Test
    public void createProgramThatOverlapsExistingProgramFromDifferentChannelSuccessfully() {
        // Arrange
        String channelIdChannelOne = "06d6232c-dca4-493d-86fb-f82f057dc5d5";
        String imageUrlProgramChannelOne = "http://cycloid.com/channel1-image/";
        String titleProgramChannelOne = "Best EPL Goals";
        String descriptionProgramChannelOne =
                "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTimeProgramChannelOne = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTimeProgramChannelOne = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programToBeCreatedChannelOne =
                Program.Builder.programWith().withChannelId(channelIdChannelOne).withImageUrl(imageUrlProgramChannelOne)
                               .withTitle(titleProgramChannelOne)
                               .withDescription(descriptionProgramChannelOne).withStartTime(startTimeProgramChannelOne)
                               .withEndTime(endTimeProgramChannelOne).build();

        when(channelRepository.findById(channelIdChannelOne)).thenReturn(Optional.of(new Channel()));
        when(programRepository
                     .getProgramByChannelIdAndTime(channelIdChannelOne, startTimeProgramChannelOne,
                                                   endTimeProgramChannelOne))
                .thenReturn(null);

        programServiceImpl.createProgram(programToBeCreatedChannelOne);

        String channelIdChannelTwo = "0d8d1a97-bec1-4d23-91b6-e164f6c635c6";
        String imageUrlProgramChannelTwo = "http://cycloid.com/channel2-image/";
        String titleProgramChannelTwo = "Best Bundesliga Goals";
        String descriptionProgramChannelTwo = "Review the amazing goals scored in the last Bundesliga season!";
        LocalDateTime startTimeProgramChannelTwo = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTimeProgramChannelTwo = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programToBeCreatedChannelTwo =
                Program.Builder.programWith().withChannelId(channelIdChannelTwo).withImageUrl(imageUrlProgramChannelTwo)
                               .withTitle(titleProgramChannelTwo)
                               .withDescription(descriptionProgramChannelTwo).withStartTime(startTimeProgramChannelTwo)
                               .withEndTime(endTimeProgramChannelTwo).build();

        // Act
        when(channelRepository.findById(channelIdChannelTwo)).thenReturn(Optional.of(new Channel()));
        when(programRepository
                     .getProgramByChannelIdAndTime(channelIdChannelTwo, startTimeProgramChannelTwo,
                                                   endTimeProgramChannelTwo))
                .thenReturn(null);
        when(programRepository.save(programToBeCreatedChannelTwo)).thenReturn(programToBeCreatedChannelTwo);

        Program programChannelTwoCreated = programServiceImpl.createProgram(programToBeCreatedChannelTwo);

        // Assert
        assertNotNull(programChannelTwoCreated);
        assertEquals(channelIdChannelTwo, programChannelTwoCreated.getChannelId());
        assertEquals(imageUrlProgramChannelTwo, programChannelTwoCreated.getImageUrl());
        assertEquals(titleProgramChannelTwo, programChannelTwoCreated.getTitle());
        assertEquals(descriptionProgramChannelTwo, programChannelTwoCreated.getDescription());
        assertEquals(startTimeProgramChannelTwo, programChannelTwoCreated.getStartTime());
        assertEquals(endTimeProgramChannelTwo, programChannelTwoCreated.getEndTime());
    }

    @Test
    public void createMultipleProgramsSameChannelAtDifferentTimesSuccessfully() {
        // Arrange
        String channelIdChannelOne = "06d6232c-dca4-493d-86fb-f82f057dc5d5";
        String imageUrlProgramOneChannelOne = "http://cycloid.com/channel1-image/";
        String titleProgramOneChannelOne = "Best EPL Goals";
        String descriptionProgramOneChannelOne =
                "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTimeProgramOneChannelOne = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTimeProgramOneChannelOne = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programOneToBeCreatedChannelOne =
                Program.Builder.programWith().withChannelId(channelIdChannelOne)
                               .withImageUrl(imageUrlProgramOneChannelOne)
                               .withTitle(titleProgramOneChannelOne)
                               .withDescription(descriptionProgramOneChannelOne)
                               .withStartTime(startTimeProgramOneChannelOne)
                               .withEndTime(endTimeProgramOneChannelOne).build();

        when(channelRepository.findById(channelIdChannelOne)).thenReturn(Optional.of(new Channel()));
        when(programRepository
                     .getProgramByChannelIdAndTime(channelIdChannelOne, startTimeProgramOneChannelOne,
                                                   endTimeProgramOneChannelOne))
                .thenReturn(null);
        when(programRepository.save(programOneToBeCreatedChannelOne)).thenReturn(programOneToBeCreatedChannelOne);

        programServiceImpl.createProgram(programOneToBeCreatedChannelOne);

        String imageUrlProgramTwoChannelOne = "http://cycloid.com/channel2-image/";
        String titleProgramTwoChannelOne = "Best Bundesliga Goals";
        String descriptionProgramTwoChannelOne = "Review the amazing goals scored in the last Bundesliga season!";
        LocalDateTime startTimeProgramTwoChannelOne = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 48);
        LocalDateTime endTimeProgramTwoChannelOne = LocalDateTime.of(2020, Month.JULY, 18, 13, 45, 47);

        Program programTwoToBeCreatedChannelOne =
                Program.Builder.programWith().withChannelId(channelIdChannelOne)
                               .withImageUrl(imageUrlProgramTwoChannelOne)
                               .withTitle(titleProgramTwoChannelOne)
                               .withDescription(descriptionProgramTwoChannelOne)
                               .withStartTime(startTimeProgramTwoChannelOne)
                               .withEndTime(endTimeProgramTwoChannelOne).build();

        // Act
        when(channelRepository.findById(channelIdChannelOne)).thenReturn(Optional.of(new Channel()));
        when(programRepository
                     .getProgramByChannelIdAndTime(channelIdChannelOne, startTimeProgramTwoChannelOne,
                                                   endTimeProgramTwoChannelOne))
                .thenReturn(null);
        when(programRepository.save(programTwoToBeCreatedChannelOne)).thenReturn(programTwoToBeCreatedChannelOne);

        Program programTwoChannelOneCreated = programServiceImpl.createProgram(programTwoToBeCreatedChannelOne);

        // Assert
        assertNotNull(programTwoChannelOneCreated);
        assertEquals(channelIdChannelOne, programTwoChannelOneCreated.getChannelId());
        assertEquals(imageUrlProgramTwoChannelOne, programTwoChannelOneCreated.getImageUrl());
        assertEquals(titleProgramTwoChannelOne, programTwoChannelOneCreated.getTitle());
        assertEquals(descriptionProgramTwoChannelOne, programTwoChannelOneCreated.getDescription());
        assertEquals(startTimeProgramTwoChannelOne, programTwoChannelOneCreated.getStartTime());
        assertEquals(endTimeProgramTwoChannelOne, programTwoChannelOneCreated.getEndTime());
    }

    @Test(expected = BusinessException.class)
    public void createProgramThatOverlapsExistingProgramFromSameChannelFails() {
        // Arrange
        String channelIdChannelOne = "06d6232c-dca4-493d-86fb-f82f057dc5d5";
        String imageUrlProgramOneChannelOne = "http://cycloid.com/channel1-image/";
        String titleProgramOneChannelOne = "Best EPL Goals";
        String descriptionProgramOneChannelOne =
                "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTimeProgramOneChannelOne = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTimeProgramOneChannelOne = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programOneToBeCreatedChannelOne =
                Program.Builder.programWith().withChannelId(channelIdChannelOne)
                               .withImageUrl(imageUrlProgramOneChannelOne)
                               .withTitle(titleProgramOneChannelOne)
                               .withDescription(descriptionProgramOneChannelOne)
                               .withStartTime(startTimeProgramOneChannelOne)
                               .withEndTime(endTimeProgramOneChannelOne).build();

        when(channelRepository.findById(channelIdChannelOne)).thenReturn(Optional.of(new Channel()));
        when(programRepository
                     .getProgramByChannelIdAndTime(channelIdChannelOne, startTimeProgramOneChannelOne,
                                                   endTimeProgramOneChannelOne))
                .thenReturn(null);
        when(programRepository.save(programOneToBeCreatedChannelOne)).thenReturn(programOneToBeCreatedChannelOne);

        Program programOneChannelOneCreated = programServiceImpl.createProgram(programOneToBeCreatedChannelOne);

        String imageUrlProgramTwoChannelOne = "http://cycloid.com/channel2-image/";
        String titleProgramTwoChannelOne = "Best Bundesliga Goals";
        String descriptionProgramTwoChannelOne = "Review the amazing goals scored in the last Bundesliga season!";
        LocalDateTime startTimeProgramTwoChannelOne = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTimeProgramTwoChannelOne = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programTwoToBeCreatedChannelOne =
                Program.Builder.programWith().withChannelId(channelIdChannelOne)
                               .withImageUrl(imageUrlProgramTwoChannelOne)
                               .withTitle(titleProgramTwoChannelOne)
                               .withDescription(descriptionProgramTwoChannelOne)
                               .withStartTime(startTimeProgramTwoChannelOne)
                               .withEndTime(endTimeProgramTwoChannelOne).build();

        // Act
        when(channelRepository.findById(channelIdChannelOne)).thenReturn(Optional.of(new Channel()));
        when(programRepository
                     .getProgramByChannelIdAndTime(channelIdChannelOne, startTimeProgramTwoChannelOne,
                                                   endTimeProgramTwoChannelOne))
                .thenReturn(programOneChannelOneCreated);

        try {
            programServiceImpl.createProgram(programTwoToBeCreatedChannelOne);
        } catch (BusinessException be) {
            String exceptionMessage =
                    "There is already a program starting at " + startTimeProgramOneChannelOne + " and ending at " +
                            endTimeProgramOneChannelOne;
            assertEquals(exceptionMessage, be.getMessage());
            throw be;
        }

        fail("Business exception for program overlapping existing program was not thrown!");
    }

    @Test
    public void getAllProgramsByChannelIdReturnsPopulatedList() {
        // Arrange
        String channelIdChannelOne = "06d6232c-dca4-493d-86fb-f82f057dc5d5";
        String imageUrlProgramOneChannelOne = "http://cycloid.com/channel1-image/";
        String titleProgramOneChannelOne = "Best EPL Goals";
        String descriptionProgramOneChannelOne =
                "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTimeProgramOneChannelOne = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTimeProgramOneChannelOne = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programOneToBeCreatedChannelOne =
                Program.Builder.programWith().withChannelId(channelIdChannelOne)
                               .withImageUrl(imageUrlProgramOneChannelOne)
                               .withTitle(titleProgramOneChannelOne)
                               .withDescription(descriptionProgramOneChannelOne)
                               .withStartTime(startTimeProgramOneChannelOne)
                               .withEndTime(endTimeProgramOneChannelOne).build();

        String imageUrlProgramTwoChannelOne = "http://cycloid.com/channel2-image/";
        String titleProgramTwoChannelOne = "Best Bundesliga Goals";
        String descriptionProgramTwoChannelOne = "Review the amazing goals scored in the last Bundesliga season!";
        LocalDateTime startTimeProgramTwoChannelOne = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 48);
        LocalDateTime endTimeProgramTwoChannelOne = LocalDateTime.of(2020, Month.JULY, 18, 13, 45, 47);

        Program programTwoToBeCreatedChannelOne =
                Program.Builder.programWith().withChannelId(channelIdChannelOne)
                               .withImageUrl(imageUrlProgramTwoChannelOne)
                               .withTitle(titleProgramTwoChannelOne)
                               .withDescription(descriptionProgramTwoChannelOne)
                               .withStartTime(startTimeProgramTwoChannelOne)
                               .withEndTime(endTimeProgramTwoChannelOne).build();

        List<Program> allProgramsFromChannelList =
                new ArrayList<>(Arrays.asList(programOneToBeCreatedChannelOne, programTwoToBeCreatedChannelOne));

        // Act
        when(programRepository.getAllProgramsByChannelId(anyString())).thenReturn(allProgramsFromChannelList);

        List<Program> retrievedAllProgramsList = programServiceImpl.getAllProgramsByChannelId(anyString());

        // Assert
        assertNotNull(retrievedAllProgramsList);
        assertEquals(2, retrievedAllProgramsList.size());
    }

    @Test
    public void getAllProgramsByChannelIdReturnsEmptyList() {
        // Act
        when(programRepository.getAllProgramsByChannelId(anyString())).thenReturn(new ArrayList<>());

        List<Program> retrievedAllProgramsList = programServiceImpl.getAllProgramsByChannelId(anyString());

        // Assert
        assertNotNull(retrievedAllProgramsList);
        assertEquals(0, retrievedAllProgramsList.size());
    }

    @Test
    public void getProgramByIdSuccessfully() {
        // Arrange
        String channelId = "06d6232c-dca4-493d-86fb-f82f057dc5d5";
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program program =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        // Act
        when(programRepository.findById(anyString())).thenReturn(Optional.ofNullable(program));

        Program retrievedProgram = programServiceImpl.getProgramById(anyString());

        // Assert
        assertNotNull(retrievedProgram);
        assertEquals(channelId, retrievedProgram.getChannelId());
        assertEquals(imageUrl, retrievedProgram.getImageUrl());
        assertEquals(title, retrievedProgram.getTitle());
        assertEquals(description, retrievedProgram.getDescription());
        assertEquals(startTime, retrievedProgram.getStartTime());
        assertEquals(endTime, retrievedProgram.getEndTime());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getProgramByIdFails() {
        // Arrange
        String id = "06d6232c-dca4-493d-86fb-f82f057dc5d5";

        when(programRepository.findById(anyString())).thenThrow(
                new ResourceNotFoundException("No program found for this id: " + id, "Id: " + id));

        // Act
        try {
            programServiceImpl.getProgramById(id);
        } catch (ResourceNotFoundException re) {
            String exceptionMessage = "No program found for this id: " + id;
            assertEquals(exceptionMessage, re.getMessageKey());
            throw re;
        }

        fail("Resource not found exception when program does not exist was not thrown!");
    }

    @Test
    public void deleteProgramByIdSuccessfully() {
        // Arrange
        String id = "06d6232c-dca4-493d-86fb-f82f057dc5d5";

        // Act
        programServiceImpl.deleteProgramById(id);

        // Assert
        verify(programRepository, times(1)).deleteById(id);
    }

    @Test(expected = TechnicalException.class)
    public void deleteProgramByIdNonExistingProgramFails() {
        // Arrange
        String id = "06d6232c-dca4-493d-86fb-f82f057dc5d5";
        final ProgramServiceImpl programServiceImpl = mock(ProgramServiceImpl.class);

        doThrow(new TechnicalException("No class epg.model.Program entity with id " + id + " exists!"))
                .when(programServiceImpl).deleteProgramById(id);

        // Act
        try {
            programServiceImpl.deleteProgramById(id);
        } catch (TechnicalException te) {
            String exceptionMessage = "No class epg.model.Program entity with id " + id + " exists!";
            assertEquals(exceptionMessage, te.getMessage());
            throw te;
        }

        fail("Technical exception for when there is no program to delete was not thrown!");
    }

    @Test
    public void updateProgramByIdSuccessfully() {
        // Arrange
        String channelId = "06d6232c-dca4-493d-86fb-f82f057dc5d5";
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        String channelIdUpdate = "0d8d1a97-bec1-4d23-91b6-e164f6c635c6";
        String imageUrlUpdate = "http://cycloid.com/channel2-image/";
        String titleUpdate = "Best Bundesliga Goals";
        String descriptionUpdate = "Review the amazing goals scored in the last Bundesliga season!";
        LocalDateTime startTimeUpdate = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTimeUpdate = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programUpdate =
                Program.Builder.programWith().withChannelId(channelIdUpdate).withImageUrl(imageUrlUpdate)
                               .withTitle(titleUpdate)
                               .withDescription(descriptionUpdate).withStartTime(startTimeUpdate)
                               .withEndTime(endTimeUpdate).build();

        // Act
        when(programRepository.findById(anyString())).thenReturn(Optional.ofNullable(programCreated));
        when(channelRepository.findById(anyString())).thenReturn(Optional.of(new Channel()));

        Program programUpdated = programServiceImpl.updateProgramById(anyString(), programUpdate);

        // Assert
        assertNotNull(programUpdated);
        assertEquals(channelIdUpdate, programUpdated.getChannelId());
        assertEquals(imageUrlUpdate, programUpdated.getImageUrl());
        assertEquals(titleUpdate, programUpdated.getTitle());
        assertEquals(descriptionUpdate, programUpdated.getDescription());
        assertEquals(startTimeUpdate, programUpdated.getStartTime());
        assertEquals(endTimeUpdate, programUpdated.getEndTime());
    }

    @Test
    public void updateProgramByIdWithNoChangesSuccessfully() {
        // Arrange
        String channelId = "06d6232c-dca4-493d-86fb-f82f057dc5d5";
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        // Act
        when(programRepository.findById(anyString())).thenReturn(Optional.ofNullable(programCreated));

        Program programUpdated = programServiceImpl.updateProgramById(anyString(), programCreated);

        // Assert
        assertNotNull(programUpdated);
        assertEquals(channelId, programUpdated.getChannelId());
        assertEquals(imageUrl, programUpdated.getImageUrl());
        assertEquals(title, programUpdated.getTitle());
        assertEquals(description, programUpdated.getDescription());
        assertEquals(startTime, programUpdated.getStartTime());
        assertEquals(endTime, programUpdated.getEndTime());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateProgramByIdNonExistingIdFails() {
        // Arrange
        String id = "0d8d1a97-bec1-4d23-91b6-e164f6c635c6";

        String channelId = "06d6232c-dca4-493d-86fb-f82f057dc5d5";
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        // Act
        try {
            programServiceImpl.updateProgramById(id, programCreated);
        } catch (ResourceNotFoundException re) {
            String exceptionMessage = "Program with id " + id + " not found!";
            throw re;
        }

        fail("Resource not found exception for non existing program when updating was not thrown!");
    }

    @Test
    public void updateProgramByIdUpdateOnlyChannelIdSuccessfully() {
        // Arrange
        String channelId = "06d6232c-dca4-493d-86fb-f82f057dc5d5";
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        String channelIdUpdate = "93ad1060-8b7a-444e-ba9e-59440b4a7764";

        Program programUpdate = Program.Builder.programWith().withChannelId(channelIdUpdate).build();

        // Act
        when(programRepository.findById(anyString())).thenReturn(Optional.ofNullable(programCreated));
        when(channelRepository.findById(anyString())).thenReturn(Optional.of(new Channel()));

        Program programUpdated = programServiceImpl.updateProgramById(anyString(), programUpdate);

        // Assert
        assertNotNull(programUpdated);
        assertEquals(channelIdUpdate, programUpdated.getChannelId());
        assertEquals(imageUrl, programUpdated.getImageUrl());
        assertEquals(title, programUpdated.getTitle());
        assertEquals(description, programUpdated.getDescription());
        assertEquals(startTime, programUpdated.getStartTime());
        assertEquals(endTime, programUpdated.getEndTime());
    }

    @Test
    public void updateProgramByIdUpdateOnlyImageUrlSuccessfully() {
        // Arrange
        String channelId = "06d6232c-dca4-493d-86fb-f82f057dc5d5";
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        String imageUrlUpdate = "http://cycloid.com/channel1-image-1/";

        Program programUpdate = Program.Builder.programWith().withImageUrl(imageUrlUpdate).build();

        // Act
        when(programRepository.findById(anyString())).thenReturn(Optional.ofNullable(programCreated));
        when(channelRepository.findById(anyString())).thenReturn(Optional.of(new Channel()));

        Program programUpdated = programServiceImpl.updateProgramById(anyString(), programUpdate);

        // Assert
        assertNotNull(programUpdated);
        assertEquals(channelId, programUpdated.getChannelId());
        assertEquals(imageUrlUpdate, programUpdated.getImageUrl());
        assertEquals(title, programUpdated.getTitle());
        assertEquals(description, programUpdated.getDescription());
        assertEquals(startTime, programUpdated.getStartTime());
        assertEquals(endTime, programUpdated.getEndTime());
    }

    @Test
    public void updateProgramByIdUpdateOnlyTitleSuccessfully() {
        // Arrange
        String channelId = "06d6232c-dca4-493d-86fb-f82f057dc5d5";
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        String titleUpdate = "Best Bundesliga Goals";

        Program programUpdate = Program.Builder.programWith().withTitle(titleUpdate).build();

        // Act
        when(programRepository.findById(anyString())).thenReturn(Optional.ofNullable(programCreated));
        when(channelRepository.findById(anyString())).thenReturn(Optional.of(new Channel()));

        Program programUpdated = programServiceImpl.updateProgramById(anyString(), programUpdate);

        // Assert
        assertNotNull(programUpdated);
        assertEquals(channelId, programUpdated.getChannelId());
        assertEquals(imageUrl, programUpdated.getImageUrl());
        assertEquals(titleUpdate, programUpdated.getTitle());
        assertEquals(description, programUpdated.getDescription());
        assertEquals(startTime, programUpdated.getStartTime());
        assertEquals(endTime, programUpdated.getEndTime());
    }

    @Test
    public void updateProgramByIdUpdateOnlyDescriptionSuccessfully() {
        // Arrange
        String channelId = "06d6232c-dca4-493d-86fb-f82f057dc5d5";
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        String descriptionUpdate = "Review the amazing goals scored in the 2018/2019 English Premier League season!";

        Program programUpdate = Program.Builder.programWith().withDescription(descriptionUpdate).build();

        // Act
        when(programRepository.findById(anyString())).thenReturn(Optional.ofNullable(programCreated));
        when(channelRepository.findById(anyString())).thenReturn(Optional.of(new Channel()));

        Program programUpdated = programServiceImpl.updateProgramById(anyString(), programUpdate);

        // Assert
        assertNotNull(programUpdated);
        assertEquals(channelId, programUpdated.getChannelId());
        assertEquals(imageUrl, programUpdated.getImageUrl());
        assertEquals(title, programUpdated.getTitle());
        assertEquals(descriptionUpdate, programUpdated.getDescription());
        assertEquals(startTime, programUpdated.getStartTime());
        assertEquals(endTime, programUpdated.getEndTime());
    }

    @Test
    public void updateProgramByIdUpdateOnlyStartTimeSuccessfully() {
        // Arrange
        String channelId = "06d6232c-dca4-493d-86fb-f82f057dc5d5";
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        LocalDateTime startTimeUpdate = LocalDateTime.of(2020, Month.JULY, 18, 11, 49, 47);

        Program programUpdate = Program.Builder.programWith().withStartTime(startTimeUpdate).build();

        // Act
        when(programRepository.findById(anyString())).thenReturn(Optional.ofNullable(programCreated));
        when(channelRepository.findById(anyString())).thenReturn(Optional.of(new Channel()));

        Program programUpdated = programServiceImpl.updateProgramById(anyString(), programUpdate);

        // Assert
        assertNotNull(programUpdated);
        assertEquals(channelId, programUpdated.getChannelId());
        assertEquals(imageUrl, programUpdated.getImageUrl());
        assertEquals(title, programUpdated.getTitle());
        assertEquals(description, programUpdated.getDescription());
        assertEquals(startTimeUpdate, programUpdated.getStartTime());
        assertEquals(endTime, programUpdated.getEndTime());
    }

    @Test
    public void updateProgramByIdUpdateOnlyEndTimeSuccessfully() {
        // Arrange
        String channelId = "06d6232c-dca4-493d-86fb-f82f057dc5d5";
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        LocalDateTime endTimeUpdate = LocalDateTime.of(2020, Month.JULY, 18, 12, 40, 47);

        Program programUpdate = Program.Builder.programWith().withEndTime(endTimeUpdate).build();

        // Act
        when(programRepository.findById(anyString())).thenReturn(Optional.ofNullable(programCreated));
        when(channelRepository.findById(anyString())).thenReturn(Optional.of(new Channel()));

        Program programUpdated = programServiceImpl.updateProgramById(anyString(), programUpdate);

        // Assert
        assertNotNull(programUpdated);
        assertEquals(channelId, programUpdated.getChannelId());
        assertEquals(imageUrl, programUpdated.getImageUrl());
        assertEquals(title, programUpdated.getTitle());
        assertEquals(description, programUpdated.getDescription());
        assertEquals(startTime, programUpdated.getStartTime());
        assertEquals(endTimeUpdate, programUpdated.getEndTime());
    }

    @Test(expected = BusinessException.class)
    public void updateProgramByIdStartTimeAfterEndTimeFails() {
        // Arrange
        String channelId = "06d6232c-dca4-493d-86fb-f82f057dc5d5";
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        LocalDateTime startTimeUpdate = LocalDateTime.of(2020, Month.JULY, 18, 13, 45, 47);

        Program programUpdate = Program.Builder.programWith().withStartTime(startTimeUpdate).build();

        // Act
        when(programRepository.findById(anyString())).thenReturn(Optional.ofNullable(programCreated));

        try {
            programServiceImpl.updateProgramById(anyString(), programUpdate);
        } catch (BusinessException be) {
            String exceptionMessage = "The start time must be before the end time!";
            assertEquals(exceptionMessage, be.getMessage());
            throw be;
        }

        fail("Business exception of start time being after end time was not thrown!");
    }

    @Test(expected = BusinessException.class)
    public void updateProgramByIdStartTimeEqualEndTimeFails() {
        // Arrange
        String channelId = "06d6232c-dca4-493d-86fb-f82f057dc5d5";
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        LocalDateTime startTimeUpdate = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programUpdate = Program.Builder.programWith().withStartTime(startTimeUpdate).build();

        // Act
        when(programRepository.findById(anyString())).thenReturn(Optional.ofNullable(programCreated));

        try {
            programServiceImpl.updateProgramById(anyString(), programUpdate);
        } catch (BusinessException be) {
            String exceptionMessage = "The start time and the end time are the same!";
            assertEquals(exceptionMessage, be.getMessage());
            throw be;
        }

        fail("Business exception of start time being after end time was not thrown!");
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateProgramByIdNonExistingChannelIdFails() {
        // Arrange
        String channelId = "06d6232c-dca4-493d-86fb-f82f057dc5d5";
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        String channelIdUpdate = "93ad1060-8b7a-444e-ba9e-59440b4a7764";

        Program programUpdate = Program.Builder.programWith().withChannelId(channelIdUpdate).build();

        // Act
        when(programRepository.findById(anyString())).thenReturn(Optional.ofNullable(programCreated));
        when(channelRepository.findById(anyString()))
                .thenThrow(new ResourceNotFoundException("Channel with id " + channelIdUpdate + " not found!",
                                                         "Id: " + channelIdUpdate));

        try {
            programServiceImpl.updateProgramById(anyString(), programUpdate);
        } catch (ResourceNotFoundException re) {
            String exceptionMessage = "Channel with id " + channelIdUpdate + " not found!";
            assertEquals(exceptionMessage, re.getMessageKey());
            throw re;
        }

        fail("Resource not found exception when channel does not exist was not thrown!");
    }

    @Test(expected = BusinessException.class)
    public void updateProgramByIdThatOverlapsExistingProgramFromSameChannelFails() {
        // Arrange
        String channelId = "06d6232c-dca4-493d-86fb-f82f057dc5d5";
        String imageUrlProgramOne = "http://cycloid.com/channel1-image/";
        String titleProgramOne = "Best EPL Goals";
        String descriptionProgramOne = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTimeProgramOne = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTimeProgramOne = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programOneCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrlProgramOne)
                               .withTitle(titleProgramOne)
                               .withDescription(descriptionProgramOne).withStartTime(startTimeProgramOne)
                               .withEndTime(endTimeProgramOne).build();

        String imageUrlProgramTwo = "http://cycloid.com/channel2-image/";
        String titleProgramTwo = "Best Bundesliga Goals";
        String descriptionProgramTwo = "Review the amazing goals scored in the last Bundesliga season!";
        LocalDateTime startTimeProgramTwo = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 48);
        LocalDateTime endTimeProgramTwo = LocalDateTime.of(2020, Month.JULY, 18, 13, 45, 48);

        Program programTwoCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrlProgramTwo)
                               .withTitle(titleProgramTwo)
                               .withDescription(descriptionProgramTwo).withStartTime(startTimeProgramTwo)
                               .withEndTime(endTimeProgramTwo).build();


        LocalDateTime endTimeProgramOneUpdate = LocalDateTime.of(2020, Month.JULY, 18, 13, 25, 47);

        Program programUpdate =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrlProgramOne)
                               .withTitle(titleProgramOne)
                               .withDescription(descriptionProgramOne).withStartTime(startTimeProgramOne)
                               .withEndTime(endTimeProgramOneUpdate)
                               .build();

        // Act
        when(programRepository.findById(anyString())).thenReturn(Optional.ofNullable(programOneCreated));
        when(channelRepository.findById(anyString())).thenReturn(Optional.of(new Channel()));
        when(programRepository.getProgramByChannelIdAndTime(channelId, startTimeProgramOne, endTimeProgramOneUpdate))
                .thenReturn(programTwoCreated);

        try {
            programServiceImpl.updateProgramById(anyString(), programUpdate);
        } catch (BusinessException be) {
            String exceptionMessage =
                    "There is already a program starting at " + startTimeProgramTwo + " and ending at " +
                            endTimeProgramTwo;
            assertEquals(exceptionMessage, be.getMessage());
            throw be;
        }

        fail("Business exception for program overlapping existing program was not thrown!");
    }
}
