package epg.repository;

import epg.model.Program;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProgramRepositoryTests {

    @Autowired
    private ProgramRepository programRepository;

    @Test
    public void saveProgramSuccessfully() {
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
        Program programCreated = programRepository.save(programToBeCreated);

        // Assert
        assertNotNull(programCreated);
        assertFalse(programCreated.getChannelId().isEmpty());
        assertEquals(channelId, programCreated.getChannelId());
        assertEquals(imageUrl, programCreated.getImageUrl());
        assertEquals(title, programCreated.getTitle());
        assertEquals(description, programCreated.getDescription());
        assertEquals(startTime, programCreated.getStartTime());
        assertEquals(endTime, programCreated.getEndTime());
    }

    @Test
    public void deleteProgramByIdSuccessfully() {
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

        Program programCreated = programRepository.save(programToBeCreated);

        // Act
        String id = programCreated.getId();

        programRepository.deleteById(id);

        Program programRetrieved = programRepository.findById(id).orElse(null);

        // Assert
        assertNull(programRetrieved);
    }

    @Test
    public void getProgramByChannelIdAndTimeSuccessfully() {
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

        programRepository.save(programToBeCreated);

        // Act
        Program programRetrieved = programRepository.getProgramByChannelIdAndTime(channelId, startTime, endTime);

        // Assert
        assertNotNull(programRetrieved);
        assertFalse(programRetrieved.getChannelId().isEmpty());
        assertEquals(channelId, programRetrieved.getChannelId());
        assertEquals(imageUrl, programRetrieved.getImageUrl());
        assertEquals(title, programRetrieved.getTitle());
        assertEquals(description, programRetrieved.getDescription());
        assertEquals(startTime, programRetrieved.getStartTime());
        assertEquals(endTime, programRetrieved.getEndTime());
    }

    @Test
    public void getProgramByChannelIdAndTimeFails() {
        // Arrange
        String channelId = "0d8d1a97-bec1-4d23-91b6-e164f6c635c6";

        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        // Act
        Program programRetrieved = programRepository.getProgramByChannelIdAndTime(channelId, startTime, endTime);

        // Assert
        assertNull(programRetrieved);
    }

    @Test
    public void getAllProgramsByChannelIdSuccessfully() {
        // Arrange
        String channelIdChannelOne = "0d8d1a97-bec1-4d23-91b6-e164f6c635c6";
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

        programRepository.save(programOneToBeCreatedChannelOne);

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

        programRepository.save(programTwoToBeCreatedChannelOne);

        // Act
        List<Program> allProgramsList = programRepository.getAllProgramsByChannelId(channelIdChannelOne);

        // Assert
        assertNotNull(allProgramsList);
        assertEquals(2, allProgramsList.size());
        assertTrue(allProgramsList.stream().anyMatch(programOneToBeCreatedChannelOne::equals));
        assertTrue(allProgramsList.stream().anyMatch(programTwoToBeCreatedChannelOne::equals));
    }

    @Test
    public void getAllProgramsByChannelIdFails() {
        // Arrange
        String channelIdChannelOne = "0d8d1a97-bec1-4d23-91b6-e164f6c635c6";

        // Act
        List<Program> allProgramsList = programRepository.getAllProgramsByChannelId(channelIdChannelOne);

        // Assert
        assertNotNull(allProgramsList);
        assertEquals(0, allProgramsList.size());
    }

    @Test
    public void findByIdSuccessfully() {
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

        Program programCreated = programRepository.save(programToBeCreated);

        // Act
        Program programRetrieved = programRepository.findById(programCreated.getId()).orElse(null);

        // Assert
        assertNotNull(programRetrieved);
        assertFalse(programRetrieved.getChannelId().isEmpty());
        assertEquals(channelId, programRetrieved.getChannelId());
        assertEquals(imageUrl, programRetrieved.getImageUrl());
        assertEquals(title, programRetrieved.getTitle());
        assertEquals(description, programRetrieved.getDescription());
        assertEquals(startTime, programRetrieved.getStartTime());
        assertEquals(endTime, programRetrieved.getEndTime());
    }

    @Test
    public void findByIdFails() {
        // Arrange
        String id = "0d8d1a97-bec1-4d23-91b6-e164f6c635c6";

        // Act
        Program programRetrieved = programRepository.findById(id).orElse(null);

        // Assert
        assertNull(programRetrieved);
    }

    @Test
    public void updateProgramByIdSuccessfully() {
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

        Program programCreated = programRepository.save(programToBeCreated);

        String channelIdUpdate = "0d8d1a97-bec1-4d23-91b6-e164f6c635c6";
        String imageUrlUpdate = "http://cycloid.com/channel2-image/";
        String titleUpdate = "Best Bundesliga Goals";
        String descriptionUpdate = "Review the amazing goals scored in the last Bundesliga season!";
        LocalDateTime startTimeUpdate = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTimeUpdate = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        programCreated.setChannelId(channelIdUpdate);
        programCreated.setImageUrl(imageUrlUpdate);
        programCreated.setTitle(titleUpdate);
        programCreated.setDescription(descriptionUpdate);
        programCreated.setStartTime(startTimeUpdate);
        programCreated.setEndTime(endTimeUpdate);

        // Act
        Program programUpdated = programRepository.save(programCreated);

        // Assert
        assertNotNull(programUpdated);
        assertEquals(programCreated.getId(), programUpdated.getId());
        assertEquals(channelIdUpdate, programUpdated.getChannelId());
        assertEquals(imageUrlUpdate, programUpdated.getImageUrl());
        assertEquals(titleUpdate, programUpdated.getTitle());
        assertEquals(descriptionUpdate, programUpdated.getDescription());
        assertEquals(startTimeUpdate, programUpdated.getStartTime());
        assertEquals(endTimeUpdate, programUpdated.getEndTime());
    }

    @Test
    public void updateProgramByIdUpdateOnlyChannelIdSuccessfully() {
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

        Program programCreated = programRepository.save(programToBeCreated);

        String channelIdUpdate = "0d8d1a97-bec1-4d23-91b6-e164f6c635c6";

        programCreated.setChannelId(channelIdUpdate);

        // Act
        Program programUpdated = programRepository.save(programCreated);

        // Assert
        assertNotNull(programUpdated);
        assertEquals(programCreated.getId(), programUpdated.getId());
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
        String channelId = "0d8d1a97-bec1-4d23-91b6-e164f6c635c6";
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programToBeCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        Program programCreated = programRepository.save(programToBeCreated);

        String imageUrlUpdate = "http://cycloid.com/channel2-image/";


        programCreated.setImageUrl(imageUrlUpdate);

        // Act
        Program programUpdated = programRepository.save(programCreated);

        // Assert
        assertNotNull(programUpdated);
        assertEquals(programCreated.getId(), programUpdated.getId());
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
        String channelId = "0d8d1a97-bec1-4d23-91b6-e164f6c635c6";
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programToBeCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        Program programCreated = programRepository.save(programToBeCreated);

        String titleUpdate = "Best Bundesliga Goals";

        programCreated.setTitle(titleUpdate);

        // Act
        Program programUpdated = programRepository.save(programCreated);

        // Assert
        assertNotNull(programUpdated);
        assertEquals(programCreated.getId(), programUpdated.getId());
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
        String channelId = "0d8d1a97-bec1-4d23-91b6-e164f6c635c6";
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programToBeCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        Program programCreated = programRepository.save(programToBeCreated);

        String descriptionUpdate = "Review the amazing goals scored in the last Bundesliga season!";

        programCreated.setDescription(descriptionUpdate);

        // Act
        Program programUpdated = programRepository.save(programCreated);

        // Assert
        assertNotNull(programUpdated);
        assertEquals(programCreated.getId(), programUpdated.getId());
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
        String channelId = "0d8d1a97-bec1-4d23-91b6-e164f6c635c6";
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programToBeCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        Program programCreated = programRepository.save(programToBeCreated);

        LocalDateTime startTimeUpdate = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);

        programCreated.setStartTime(startTimeUpdate);

        // Act
        Program programUpdated = programRepository.save(programCreated);

        // Assert
        assertNotNull(programUpdated);
        assertEquals(programCreated.getId(), programUpdated.getId());
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
        String channelId = "0d8d1a97-bec1-4d23-91b6-e164f6c635c6";
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programToBeCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        Program programCreated = programRepository.save(programToBeCreated);

        LocalDateTime endTimeUpdate = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        programCreated.setEndTime(endTimeUpdate);

        // Act
        Program programUpdated = programRepository.save(programCreated);

        // Assert
        assertNotNull(programUpdated);
        assertEquals(programCreated.getId(), programUpdated.getId());
        assertEquals(channelId, programUpdated.getChannelId());
        assertEquals(imageUrl, programUpdated.getImageUrl());
        assertEquals(title, programUpdated.getTitle());
        assertEquals(description, programUpdated.getDescription());
        assertEquals(startTime, programUpdated.getStartTime());
        assertEquals(endTimeUpdate, programUpdated.getEndTime());
    }
}
