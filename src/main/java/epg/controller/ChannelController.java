package epg.controller;

import epg.model.Channel;
import epg.service.channel.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static epg.controller.RestEndpoint.BASE_URL;

@RestController
@RequestMapping(BASE_URL + "/channels")
public class ChannelController extends AbstractController {

    @Autowired
    private ChannelService channelService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Channel createChannel(@Valid @RequestBody Channel channel) {
        return channelService.createChannel(channel);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Channel> getAllChannels() {
        return channelService.getAllChannels();
    }
}