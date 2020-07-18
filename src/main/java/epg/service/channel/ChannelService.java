package epg.service.channel;

import epg.model.Channel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ChannelService {

    Channel createChannel(Channel channel);

    List<Channel> getAllChannels();
}
