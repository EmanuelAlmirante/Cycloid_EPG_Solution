package epg.service.channel;

import epg.exception.BusinessException;
import epg.model.Channel;
import epg.repository.ChannelRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChannelServiceImplTests {

    @Mock
    private ChannelRepository channelRepository;

    @InjectMocks
    private ChannelServiceImpl channelServiceImpl;

    @Test
    public void createChannelSuccessfully() {
        // Arrange
        String name = "Channel 1";
        Integer position = 1;
        String category = "Sports";

        Channel channelToBeCreated =
                Channel.Builder.channelWith().withName(name).withPosition(position).withCategory(category).build();

        // Act
        when(channelRepository.getChannelByName(name)).thenReturn(null);
        when(channelRepository.getChannelByPosition(position)).thenReturn(null);
        when(channelRepository.save(channelToBeCreated)).thenReturn(channelToBeCreated);

        Channel channelCreated = channelServiceImpl.createChannel(channelToBeCreated);

        // Assert
        assertNotNull(channelCreated);
        assertEquals(name, channelCreated.getName());
        assertEquals(position, channelCreated.getPosition());
        assertEquals(category, channelCreated.getCategory());
    }

    @Test(expected = BusinessException.class)
    public void createChannelWithoutNameFails() {
        // Arrange
        Integer position = 1;
        String category = "Sports";

        Channel channelToBeCreated =
                Channel.Builder.channelWith().withPosition(position).withCategory(category).build();

        // Act
        try {
            channelServiceImpl.createChannel(channelToBeCreated);
        } catch (BusinessException be) {
            String exceptionMessage = "Channel needs to have a name!";
            assertEquals(exceptionMessage, be.getMessage());
            throw be;
        }

        fail("Business exception of channel not having a name was not thrown!");
    }

    @Test(expected = BusinessException.class)
    public void createChannelWithoutPositionFails() {
        // Arrange
        String name = "Channel 1";
        String category = "Sports";

        Channel channelToBeCreated =
                Channel.Builder.channelWith().withName(name).withCategory(category).build();

        // Act
        try {
            channelServiceImpl.createChannel(channelToBeCreated);
        } catch (BusinessException be) {
            String exceptionMessage = "Channel needs to have a position!";
            assertEquals(exceptionMessage, be.getMessage());
            throw be;
        }

        fail("Business exception of channel not having a position was not thrown!");
    }

    @Test(expected = BusinessException.class)
    public void createChannelWithInvalidPositionFails() {
        // Arrange
        String name = "Channel 1";
        Integer position = -1;
        String category = "Sports";

        Channel channelToBeCreated =
                Channel.Builder.channelWith().withName(name).withPosition(position).withCategory(category).build();

        // Act
        try {
            channelServiceImpl.createChannel(channelToBeCreated);
        } catch (BusinessException be) {
            String exceptionMessage = "Channel needs to have a position bigger than 0!";
            assertEquals(exceptionMessage, be.getMessage());
            throw be;
        }

        fail("Business exception of channel not having a valid position was not thrown!");
    }

    @Test(expected = BusinessException.class)
    public void createChannelWithoutCategoryFails() {
        // Arrange
        String name = "Channel 1";
        Integer position = 1;

        Channel channelToBeCreated =
                Channel.Builder.channelWith().withName(name).withPosition(position).build();

        // Act
        try {
            channelServiceImpl.createChannel(channelToBeCreated);
        } catch (BusinessException be) {
            String exceptionMessage = "Channel needs to have a category!";
            assertEquals(exceptionMessage, be.getMessage());
            throw be;
        }

        fail("Business exception of channel not having a category was not thrown!");
    }

    @Test(expected = BusinessException.class)
    public void createChannelWithExistingNameFails() {
        // Act
        String nameChannelOriginalChannelOne = "Channel 1";
        Integer positionChannelOriginalChannelOne = 1;
        String categoryChannelOriginalChannelOne = "Sports";

        Channel channelOriginalChannelOne =
                Channel.Builder.channelWith().withName(nameChannelOriginalChannelOne)
                               .withPosition(positionChannelOriginalChannelOne)
                               .withCategory(categoryChannelOriginalChannelOne).build();

        channelServiceImpl.createChannel(channelOriginalChannelOne);

        String nameChannelToBeCreated = "Channel 1";
        Integer positionChannelToBeCreated = 2;
        String categoryChannelToBeCreated = "Sports";

        Channel channelToBeCreated =
                Channel.Builder.channelWith().withName(nameChannelToBeCreated).withPosition(positionChannelToBeCreated)
                               .withCategory(categoryChannelToBeCreated).build();

        // Arrange
        when(channelRepository.getChannelByName(channelOriginalChannelOne.getName()))
               .thenReturn(channelOriginalChannelOne);

        try {
            channelServiceImpl.createChannel(channelToBeCreated);
        } catch (BusinessException be) {
            String exceptionMessage =
                    "A channel with the name " + nameChannelOriginalChannelOne + " already exists!";
            assertEquals(exceptionMessage, be.getMessage());
            throw be;
        }

        fail("Business exception of channel having an existing name was not thrown!");
    }

    @Test(expected = BusinessException.class)
    public void createChannelInPositionAlreadyOccupiedFails() {
        // Act
        String nameChannelOriginalChannelOne = "Channel 1";
        Integer positionChannelOriginalChannelOne = 1;
        String categoryChannelOriginalChannelOne = "Sports";

        Channel channelOriginalChannelOne =
                Channel.Builder.channelWith().withName(nameChannelOriginalChannelOne)
                               .withPosition(positionChannelOriginalChannelOne)
                               .withCategory(categoryChannelOriginalChannelOne).build();

        channelServiceImpl.createChannel(channelOriginalChannelOne);

        String nameChannelToBeCreated = "Channel 1";
        Integer positionChannelToBeCreated = 1;
        String categoryChannelToBeCreated = "Sports";

        Channel channelToBeCreated =
                Channel.Builder.channelWith().withName(nameChannelToBeCreated).withPosition(positionChannelToBeCreated)
                               .withCategory(categoryChannelToBeCreated).build();

        // Arrange
        when(channelRepository.getChannelByPosition(channelOriginalChannelOne.getPosition()))
               .thenReturn(channelOriginalChannelOne);

        try {
            channelServiceImpl.createChannel(channelToBeCreated);
        } catch (BusinessException be) {
            String exceptionMessage =
                    "A channel in the position " + positionChannelOriginalChannelOne + " already exists!";
            assertEquals(exceptionMessage, be.getMessage());
            throw be;
        }

        fail("Business exception of channel having a position already occupied was not thrown!");
    }

    @Test
    public void getAllChannelsReturnsPopulatedList() {
        // Arrange
        String nameChannelOne = "Channel 1";
        Integer positionChannelOne = 1;
        String categoryChannelOne = "Sports";

        Channel channelOne =
                Channel.Builder.channelWith().withName(nameChannelOne).withPosition(positionChannelOne)
                               .withCategory(categoryChannelOne).build();

        String nameChannelTwo = "Channel 2";
        Integer positionChannelTwo = 2;
        String categoryChannelTwo = "Sports";

        Channel channelTwo = Channel.Builder.channelWith().withName(nameChannelTwo).withPosition(positionChannelTwo)
                                            .withCategory(categoryChannelTwo).build();

        List<Channel> allChannelsList = new ArrayList<>(Arrays.asList(channelOne, channelTwo));

        // Act
        when(channelRepository.findAll()).thenReturn(allChannelsList);

        List<Channel> retrievedAllChannelsList = channelServiceImpl.getAllChannels();

        // Assert
        assertNotNull(retrievedAllChannelsList);
        assertEquals(2, retrievedAllChannelsList.size());
        assertTrue(retrievedAllChannelsList.stream().anyMatch(channelOne::equals));
        assertTrue(retrievedAllChannelsList.stream().anyMatch(channelTwo::equals));
    }

    @Test
    public void getAllChannelsReturnsEmptyList() {
        // Act
        when(channelRepository.findAll()).thenReturn(new ArrayList<>());

        List<Channel> retrievedAllChannelsList = channelServiceImpl.getAllChannels();

        // Assert
        assertNotNull(retrievedAllChannelsList);
        assertEquals(0, retrievedAllChannelsList.size());
    }
}
