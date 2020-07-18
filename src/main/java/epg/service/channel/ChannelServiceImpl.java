package epg.service.channel;

import epg.exception.BusinessException;
import epg.model.Channel;
import epg.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;

    @Autowired
    public ChannelServiceImpl(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Override
    public Channel createChannel(Channel channel) {
        verifyChannelIsValid(channel);
        verifyNameIsValid(channel);
        verifyPositionIsAvailable(channel);

        return channelRepository.save(channel);
    }

    @Override
    public List<Channel> getAllChannels() {
        return channelRepository.findAll();
    }

    private void verifyChannelIsValid(Channel channel) {
        if (channel.getName() == null) {
            throw new BusinessException("Channel needs to have a name!");
        }

        if (channel.getPosition() == null) {
            throw new BusinessException("Channel needs to have a position!");
        }

        if (channel.getPosition() <= 0) {
            throw new BusinessException("Channel needs to have a position bigger than 0!");
        }

        if (channel.getCategory() == null) {
            throw new BusinessException("Channel needs to have a category!");
        }
    }

    private void verifyNameIsValid(Channel channel) {
        String name = channel.getName();
        Channel duplicatedChannelName = channelRepository.getChannelByName(name);

        if (duplicatedChannelName != null) {
            throw new BusinessException(
                    "A channel with the name " + name + " already exists!", "Name: " + name);
        }
    }

    private void verifyPositionIsAvailable(Channel channel) {
        int position = channel.getPosition();
        Channel duplicatedChannelPosition = channelRepository.getChannelByPosition(position);

        if (duplicatedChannelPosition != null) {
            throw new BusinessException(
                    "A channel in the position " + position + " already exists!", "Position: " + position);
        }
    }
}
