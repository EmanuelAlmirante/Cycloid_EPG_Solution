package epg.controller;

import com.jayway.jsonpath.JsonPath;
import epg.model.Channel;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ChannelControllerTests extends AbstractTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void createChannelSuccessfully() throws Exception {
        // Arrange
        String uri = "/epg/api/channels/create";

        String name = "Channel 1";
        Integer position = 1;
        String category = "Sports";

        Channel channelToBeCreated =
                Channel.Builder.channelWith().withName(name).withPosition(position).withCategory(category).build();

        String inputJson = super.mapToJson(channelToBeCreated);

        // Act
        MvcResult mvcResult = mvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)
                                                   .accept(MediaType.APPLICATION_JSON))
                                 .andExpect(status().isCreated())
                                 .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        String id = JsonPath.parse(jsonResponse).read("$.id");
        String expectedJsonResponse =
                "{\"id\":" + "\"" + id + "\"" + ",\"name\":" + "\"" + name + "\"" + ",\"position\":" + position +
                        ",\"category\":" + "\"" + category + "\"" + "}";

        // Assert
        assertEquals(expectedJsonResponse, jsonResponse);
    }

    @Test
    public void createChannelWithoutNameFails() throws Exception {
        // Arrange
        String uri = "/epg/api/channels/create";

        Integer position = 1;
        String category = "Sports";

        Channel channelToBeCreated =
                Channel.Builder.channelWith().withPosition(position).withCategory(category).build();

        String inputJson = super.mapToJson(channelToBeCreated);

        // Act
        MvcResult mvcResult = mvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)
                                                   .accept(MediaType.APPLICATION_JSON))
                                 .andExpect(status().isInternalServerError())
                                 .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse =
                "{\"BusinessError\":{\"messageKey\":\"Channel needs to have a name!\",\"arguments\":[]}}";

        // Assert
        assertEquals(expectedJsonResponse, jsonResponse);
    }

    @Test
    public void createChannelWithExistingNameFails() throws Exception {
        // Arrange
        String uri = "/epg/api/channels/create";

        String nameChannelOne = "Channel 1";
        Integer positionChannelOne = 1;
        String categoryChannelOne = "Sports";

        Channel channelOne =
                Channel.Builder.channelWith().withName(nameChannelOne).withPosition(positionChannelOne)
                               .withCategory(categoryChannelOne).build();

        String inputJsonChannelOne = super.mapToJson(channelOne);

        mvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJsonChannelOne)
                             .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isCreated())
           .andReturn();

        String nameChannelTwo = "Channel 1";
        Integer positionChannelTwo = 2;
        String categoryChannelTwo = "Sports";

        Channel channelTwo =
                Channel.Builder.channelWith().withName(nameChannelTwo).withPosition(positionChannelTwo)
                               .withCategory(categoryChannelTwo).build();

        String inputJsonChannelTwo = super.mapToJson(channelTwo);

        // Act
        MvcResult mvcResult =
                mvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJsonChannelTwo)
                                     .accept(MediaType.APPLICATION_JSON))
                   .andExpect(status().isInternalServerError())
                   .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse =
                "{\"BusinessError\":{\"messageKey\":\"A channel with the name " + nameChannelOne +
                        " already exists!\",\"arguments\":[\"Name: Channel 1\"]}}";

        // Assert
        assertEquals(expectedJsonResponse, jsonResponse);
    }

    @Test
    public void createChannelWithoutPositionFails() throws Exception {
        // Arrange
        String uri = "/epg/api/channels/create";

        String name = "Channel 1";
        String category = "Sports";

        Channel channelToBeCreated =
                Channel.Builder.channelWith().withName(name).withCategory(category).build();

        String inputJson = super.mapToJson(channelToBeCreated);

        // Act
        MvcResult mvcResult = mvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)
                                                   .accept(MediaType.APPLICATION_JSON))
                                 .andExpect(status().isInternalServerError())
                                 .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse =
                "{\"BusinessError\":{\"messageKey\":\"Channel needs to have a position!\",\"arguments\":[]}}";

        // Assert
        assertEquals(expectedJsonResponse, jsonResponse);
    }

    @Test
    public void createChannelWithExistingPositionFails() throws Exception {
        // Arrange
        String uri = "/epg/api/channels/create";

        String nameChannelOne = "Channel 1";
        Integer positionChannelOne = 1;
        String categoryChannelOne = "Sports";

        Channel channelOne =
                Channel.Builder.channelWith().withName(nameChannelOne).withPosition(positionChannelOne)
                               .withCategory(categoryChannelOne).build();

        String inputJsonChannelOne = super.mapToJson(channelOne);

        mvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJsonChannelOne)
                             .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isCreated())
           .andReturn();

        String nameChannelTwo = "Channel 2";
        Integer positionChannelTwo = 1;
        String categoryChannelTwo = "Sports";

        Channel channelTwo =
                Channel.Builder.channelWith().withName(nameChannelTwo).withPosition(positionChannelTwo)
                               .withCategory(categoryChannelTwo).build();

        String inputJsonChannelTwo = super.mapToJson(channelTwo);

        // Act
        MvcResult mvcResult =
                mvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJsonChannelTwo)
                                     .accept(MediaType.APPLICATION_JSON))
                   .andExpect(status().isInternalServerError())
                   .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse =
                "{\"BusinessError\":{\"messageKey\":\"A channel in the position " + positionChannelOne +
                        " already exists!\",\"arguments\":[\"Position: 1\"]}}";

        // Assert
        assertEquals(expectedJsonResponse, jsonResponse);
    }

    @Test
    public void createChannelWithInvalidPositionFails() throws Exception {
        // Arrange
        String uri = "/epg/api/channels/create";

        String name = "Channel 1";
        Integer position = -1;
        String category = "Sports";

        Channel channelToBeCreated =
                Channel.Builder.channelWith().withName(name).withPosition(position).withCategory(category).build();

        String inputJson = super.mapToJson(channelToBeCreated);

        // Act
        MvcResult mvcResult = mvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)
                                                   .accept(MediaType.APPLICATION_JSON))
                                 .andExpect(status().isInternalServerError())
                                 .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse =
                "{\"BusinessError\":{\"messageKey\":\"Channel needs to have a position bigger than 0!\",\"arguments\":[]}}";

        // Assert
        assertEquals(expectedJsonResponse, jsonResponse);
    }

    @Test
    public void createChannelWithoutCategoryFails() throws Exception {
        // Arrange
        String uri = "/epg/api/channels/create";

        String name = "Channel 1";
        Integer position = 1;

        Channel channelToBeCreated =
                Channel.Builder.channelWith().withName(name).withPosition(position).build();

        String inputJson = super.mapToJson(channelToBeCreated);

        // Act
        MvcResult mvcResult = mvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)
                                                   .accept(MediaType.APPLICATION_JSON))
                                 .andExpect(status().isInternalServerError())
                                 .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse =
                "{\"BusinessError\":{\"messageKey\":\"Channel needs to have a category!\",\"arguments\":[]}}";

        // Assert
        assertEquals(expectedJsonResponse, jsonResponse);
    }

    @Test
    public void getAllChannelsReturnsPopulatedList() throws Exception {
        // Arrange
        String createUri = "/epg/api/channels/create";
        String getUri = "/epg/api/channels";

        String name = "Channel 1";
        Integer position = 1;
        String category = "Sports";

        Channel channelToBeCreated =
                Channel.Builder.channelWith().withName(name).withPosition(position).withCategory(category).build();

        String inputJson = super.mapToJson(channelToBeCreated);

        mvc.perform(post(createUri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)
                                   .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isCreated())
           .andReturn();

        // Act
        MvcResult mvcResult = mvc.perform(get(getUri).contentType(MediaType.APPLICATION_JSON_VALUE)
                                                     .accept(MediaType.APPLICATION_JSON))
                                 .andExpect(status().isOk())
                                 .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        String id = JsonPath.parse(jsonResponse).read("$[0].id");
        String expectedJsonResponse =
                "[{\"id\":" + "\"" + id + "\"" + ",\"name\":" + "\"" + name + "\"" + ",\"position\":" + position +
                        ",\"category\":" + "\"" + category + "\"" +
                        "}]";

        // Assert
        assertEquals(expectedJsonResponse, jsonResponse);
    }

    @Test
    public void getAllChannelsReturnsEmptyList() throws Exception {
        // Arrange
        String uri = "/epg/api/channels";

        // Act
        MvcResult mvcResult = mvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE)
                                                  .accept(MediaType.APPLICATION_JSON))
                                 .andExpect(status().isOk())
                                 .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = "[]";

        // Assert
        assertEquals(expectedJsonResponse, jsonResponse);
    }
}
