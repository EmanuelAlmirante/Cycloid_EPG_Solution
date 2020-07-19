package epg.repository;

import epg.model.Channel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ChannelRepositoryTests {

    @Autowired
    private ChannelRepository channelRepository;

    @Test
    public void saveChannelSuccessfully() {
        // Arrange
        String name = "Channel 1";
        Integer position = 1;
        String category = "Sports";

        Channel channelToBeCreated =
                Channel.Builder.channelWith().withName(name).withPosition(position).withCategory(category).build();

        // Act
        Channel channelCreated = channelRepository.save(channelToBeCreated);

        // Assert
        assertNotNull(channelCreated);
        assertFalse(channelCreated.getId().isEmpty());
        assertEquals(name, channelCreated.getName());
        assertEquals(position, channelCreated.getPosition());
        assertEquals(category, channelCreated.getCategory());
    }

    @Test
    public void findAllChannelsReturnsPopulatedList() {
        // Arrange
        String nameChannelOne = "Channel 1";
        Integer positionChannelOne = 1;
        String categoryChannelOne = "Sports";

        Channel channelOne =
                Channel.Builder.channelWith().withName(nameChannelOne).withPosition(positionChannelOne)
                               .withCategory(categoryChannelOne).build();

        channelRepository.save(channelOne);

        String nameChannelTwo = "Channel 2";
        Integer positionChannelTwo = 2;
        String categoryChannelTwo = "Sports";

        Channel channelTwo =
                Channel.Builder.channelWith().withName(nameChannelTwo).withPosition(positionChannelTwo)
                               .withCategory(categoryChannelTwo).build();

        channelRepository.save(channelTwo);

        // Act
        List<Channel> allChannelsList = channelRepository.findAll();

        // Assert
        assertNotNull(allChannelsList);
        assertEquals(2, allChannelsList.size());
        assertTrue(allChannelsList.stream().anyMatch(channelOne::equals));
        assertTrue(allChannelsList.stream().anyMatch(channelTwo::equals));
    }

    @Test
    public void findAllChannelsReturnsEmptyList() {
        // Act
        List<Channel> allChannelsList = channelRepository.findAll();

        // Assert
        assertNotNull(allChannelsList);
        assertEquals(0, allChannelsList.size());
    }

    @Test
    public void getChannelByNameSuccessfully() {
        // Arrange
        String name = "Channel 1";
        Integer position = 1;
        String category = "Sports";

        Channel channelToBeCreated =
                Channel.Builder.channelWith().withName(name).withPosition(position).withCategory(category).build();

        channelRepository.save(channelToBeCreated);

        // Act
        Channel channelRetrieved = channelRepository.getChannelByName(name);

        // Assert
        assertNotNull(channelRetrieved);
        assertFalse(channelRetrieved.getId().isEmpty());
        assertEquals(name, channelRetrieved.getName());
        assertEquals(position, channelRetrieved.getPosition());
        assertEquals(category, channelRetrieved.getCategory());
    }

    @Test
    public void getChannelByNameFails() {
        // Arrange
        String name = "Channel 1";

        // Act
        Channel channelRetrieved = channelRepository.getChannelByName(name);

        // Assert
        assertNull(channelRetrieved);
    }

    @Test
    public void getChannelByPositionSuccessfully() {
        // Arrange
        String name = "Channel 1";
        Integer position = 1;
        String category = "Sports";

        Channel channelToBeCreated =
                Channel.Builder.channelWith().withName(name).withPosition(position).withCategory(category).build();

        channelRepository.save(channelToBeCreated);

        // Act
        Channel channelRetrieved = channelRepository.getChannelByPosition(position);

        // Assert
        assertNotNull(channelRetrieved);
        assertFalse(channelRetrieved.getId().isEmpty());
        assertEquals(name, channelRetrieved.getName());
        assertEquals(position, channelRetrieved.getPosition());
        assertEquals(category, channelRetrieved.getCategory());
    }

    @Test
    public void getChannelByPositionFails() {
        // Arrange
        Integer position = 1;

        // Act
        Channel channelRetrieved = channelRepository.getChannelByPosition(position);

        // Assert
        assertNull(channelRetrieved);
    }

    @Test
    public void findByIdSuccessfully() {
        String name = "Channel 1";
        Integer position = 1;
        String category = "Sports";

        Channel channelToBeCreated =
                Channel.Builder.channelWith().withName(name).withPosition(position).withCategory(category).build();

        Channel channelCreated = channelRepository.save(channelToBeCreated);

        // Act
        Channel channelRetrieved = channelRepository.findById(channelCreated.getId()).orElse(null);

        // Assert
        assertNotNull(channelRetrieved);
        assertFalse(channelRetrieved.getId().isEmpty());
        assertEquals(name, channelRetrieved.getName());
        assertEquals(position, channelRetrieved.getPosition());
        assertEquals(category, channelRetrieved.getCategory());
    }

    @Test
    public void findByIdFails() {
        // Arrange
        String id = "0d8d1a97-bec1-4d23-91b6-e164f6c635c6";

        // Act
        Channel channelRetrieved = channelRepository.findById(id).orElse(null);

        // Assert
        assertNull(channelRetrieved);
    }
}
