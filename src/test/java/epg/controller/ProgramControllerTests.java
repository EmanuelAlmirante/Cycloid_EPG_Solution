package epg.controller;

import com.jayway.jsonpath.JsonPath;
import epg.model.Channel;
import epg.model.Program;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProgramControllerTests extends AbstractTest {

    private String channelIdRetrievedChannelOne;
    private String channelIdRetrievedChannelTwo;

    @Override
    @Before
    public void setUp() {
        super.setUp();

        try {
            setUpDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUpDatabase() throws Exception {
        String uriChannels = "/epg/api/channels/create";

        String nameChannelOne = "Channel 1";
        Integer positionChannelOne = 1;
        String categoryChannelOne = "Sports";

        Channel channelToBeCreatedChannelOne =
                Channel.Builder.channelWith().withName(nameChannelOne).withPosition(positionChannelOne)
                               .withCategory(categoryChannelOne).build();

        String inputJsonChannelOne = super.mapToJson(channelToBeCreatedChannelOne);

        MvcResult mvcResultChannelOne =
                mvc.perform(post(uriChannels).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJsonChannelOne)
                                             .accept(MediaType.APPLICATION_JSON))
                   .andExpect(status().isCreated())
                   .andReturn();

        String jsonResponseChannelChannelOne = mvcResultChannelOne.getResponse().getContentAsString();

        channelIdRetrievedChannelOne = JsonPath.parse(jsonResponseChannelChannelOne).read("$.id");

        String nameChannelTwo = "Channel 2";
        Integer positionChannelTwo = 2;
        String categoryChannelTwo = "Sports";

        Channel channelToBeCreatedChannelTwo =
                Channel.Builder.channelWith().withName(nameChannelTwo).withPosition(positionChannelTwo)
                               .withCategory(categoryChannelTwo).build();

        String inputJsonChannelTwo = super.mapToJson(channelToBeCreatedChannelTwo);

        MvcResult mvcResultChannelTwo =
                mvc.perform(post(uriChannels).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJsonChannelTwo)
                                             .accept(MediaType.APPLICATION_JSON))
                   .andExpect(status().isCreated())
                   .andReturn();

        String jsonResponseChannelChannelTwo = mvcResultChannelTwo.getResponse().getContentAsString();

        channelIdRetrievedChannelTwo = JsonPath.parse(jsonResponseChannelChannelTwo).read("$.id");
    }

    @Test
    public void createProgramSuccessfully() throws Exception {
        // Arrange
        String uriPrograms = "/epg/api/programs/create";

        String channelId = channelIdRetrievedChannelOne;
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programToBeCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        String inputJsonProgram = super.mapToJson(programToBeCreated);

        // Act
        MvcResult mvcResultProgram = mvc.perform(
                post(uriPrograms).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJsonProgram)
                                 .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();

        String jsonResponseProgram = mvcResultProgram.getResponse().getContentAsString();
        String id = JsonPath.parse(jsonResponseProgram).read("$.id");

        String expectedJsonResponse =
                "{\"id\":" + "\"" + id + "\"" + ",\"channelId\":" + "\"" + channelId + "\"" + ",\"imageUrl\":" + "\"" +
                        imageUrl + "\"" + ",\"title\":" + "\"" + title + "\"" + ",\"description\":" + "\"" +
                        description + "\"" + ",\"startTime\":" + "\"" + startTime + "\"" + ",\"endTime\":" + "\"" +
                        endTime + "\"" + "}";

        // Assert
        assertEquals(expectedJsonResponse, jsonResponseProgram);
    }

    @Test
    public void createProgramWithoutChannelIdFails() throws Exception {
        // Arrange
        String uriPrograms = "/epg/api/programs/create";

        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programToBeCreated =
                Program.Builder.programWith().withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        String inputJsonProgram = super.mapToJson(programToBeCreated);

        // Act
        MvcResult mvcResultProgram = mvc.perform(
                post(uriPrograms).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJsonProgram)
                                 .accept(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError())
                                        .andReturn();

        String jsonResponseProgram = mvcResultProgram.getResponse().getContentAsString();

        String expectedJsonResponse =
                "{\"BusinessError\":{\"messageKey\":\"Program needs to have a channel id!\",\"arguments\":[]}}";

        // Assert
        assertEquals(expectedJsonResponse, jsonResponseProgram);
    }

    @Test
    public void createProgramWithoutImageUrlFails() throws Exception {
        // Arrange
        String uriPrograms = "/epg/api/programs/create";

        String channelId = channelIdRetrievedChannelOne;
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programToBeCreated =
                Program.Builder.programWith().withChannelId(channelId).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        String inputJsonProgram = super.mapToJson(programToBeCreated);

        // Act
        MvcResult mvcResultProgram = mvc.perform(
                post(uriPrograms).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJsonProgram)
                                 .accept(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError())
                                        .andReturn();

        String jsonResponseProgram = mvcResultProgram.getResponse().getContentAsString();

        String expectedJsonResponse =
                "{\"BusinessError\":{\"messageKey\":\"Program needs to have a image URL!\",\"arguments\":[]}}";

        // Assert
        assertEquals(expectedJsonResponse, jsonResponseProgram);
    }

    @Test
    public void createProgramWithoutTitleFails() throws Exception {
        // Arrange
        String uriPrograms = "/epg/api/programs/create";

        String channelId = channelIdRetrievedChannelOne;
        String imageUrl = "http://cycloid.com/channel1-image/";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programToBeCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        String inputJsonProgram = super.mapToJson(programToBeCreated);

        // Act
        MvcResult mvcResultProgram = mvc.perform(
                post(uriPrograms).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJsonProgram)
                                 .accept(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError())
                                        .andReturn();

        String jsonResponseProgram = mvcResultProgram.getResponse().getContentAsString();

        String expectedJsonResponse =
                "{\"BusinessError\":{\"messageKey\":\"Program needs to have a title!\",\"arguments\":[]}}";

        // Assert
        assertEquals(expectedJsonResponse, jsonResponseProgram);
    }

    @Test
    public void createProgramWithoutDescriptionFails() throws Exception {
        // Arrange
        String uriPrograms = "/epg/api/programs/create";

        String channelId = channelIdRetrievedChannelOne;
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programToBeCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withStartTime(startTime).withEndTime(endTime).build();

        String inputJsonProgram = super.mapToJson(programToBeCreated);

        // Act
        MvcResult mvcResultProgram = mvc.perform(
                post(uriPrograms).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJsonProgram)
                                 .accept(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError())
                                        .andReturn();

        String jsonResponseProgram = mvcResultProgram.getResponse().getContentAsString();

        String expectedJsonResponse =
                "{\"BusinessError\":{\"messageKey\":\"Program needs to have a description!\",\"arguments\":[]}}";

        // Assert
        assertEquals(expectedJsonResponse, jsonResponseProgram);
    }

    @Test
    public void createProgramWithoutStartTimeFails() throws Exception {
        // Arrange
        String uriPrograms = "/epg/api/programs/create";

        String channelId = channelIdRetrievedChannelOne;
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programToBeCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withEndTime(endTime).build();

        String inputJsonProgram = super.mapToJson(programToBeCreated);

        // Act
        MvcResult mvcResultProgram = mvc.perform(
                post(uriPrograms).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJsonProgram)
                                 .accept(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError())
                                        .andReturn();

        String jsonResponseProgram = mvcResultProgram.getResponse().getContentAsString();

        String expectedJsonResponse =
                "{\"BusinessError\":{\"messageKey\":\"Program needs to have a start time!\",\"arguments\":[]}}";

        // Assert
        assertEquals(expectedJsonResponse, jsonResponseProgram);
    }

    @Test
    public void createProgramWithoutEndTimeFails() throws Exception {
        // Arrange
        String uriPrograms = "/epg/api/programs/create";

        String channelId = channelIdRetrievedChannelOne;
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);

        Program programToBeCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).build();

        String inputJsonProgram = super.mapToJson(programToBeCreated);

        // Act
        MvcResult mvcResultProgram = mvc.perform(
                post(uriPrograms).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJsonProgram)
                                 .accept(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError())
                                        .andReturn();

        String jsonResponseProgram = mvcResultProgram.getResponse().getContentAsString();

        String expectedJsonResponse =
                "{\"BusinessError\":{\"messageKey\":\"Program needs to have a end time!\",\"arguments\":[]}}";

        // Assert
        assertEquals(expectedJsonResponse, jsonResponseProgram);
    }

    @Test
    public void createProgramWithStartTimeAfterEndTimeFails() throws Exception {
        // Arrange
        String uriPrograms = "/epg/api/programs/create";

        String channelId = channelIdRetrievedChannelOne;
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 13, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programToBeCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        String inputJsonProgram = super.mapToJson(programToBeCreated);

        // Act
        MvcResult mvcResultProgram = mvc.perform(
                post(uriPrograms).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJsonProgram)
                                 .accept(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError())
                                        .andReturn();

        String jsonResponseProgram = mvcResultProgram.getResponse().getContentAsString();

        String expectedJsonResponse =
                "{\"BusinessError\":{\"messageKey\":\"The start time must be before the end time!\"" +
                        ",\"arguments\":[\"Start time: " + startTime + "; End time: " + endTime + "\"]}}";

        // Assert
        assertEquals(expectedJsonResponse, jsonResponseProgram);
    }

    @Test
    public void createProgramWithStartTimeEqualToEndTimeFails() throws Exception {
        // Arrange
        String uriPrograms = "/epg/api/programs/create";

        String channelId = channelIdRetrievedChannelOne;
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);

        Program programToBeCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        String inputJsonProgram = super.mapToJson(programToBeCreated);

        // Act
        MvcResult mvcResultProgram = mvc.perform(
                post(uriPrograms).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJsonProgram)
                                 .accept(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError())
                                        .andReturn();

        String jsonResponseProgram = mvcResultProgram.getResponse().getContentAsString();

        String expectedJsonResponse =
                "{\"BusinessError\":{\"messageKey\":\"The start time and the end time are the same!\"" +
                        ",\"arguments\":[\"Start time: " + startTime + "; End time: " + endTime + "\"]}}";

        // Assert
        assertEquals(expectedJsonResponse, jsonResponseProgram);
    }

    @Test
    public void createProgramWithNonExistingChannelIdFails() throws Exception {
        // Arrange
        String uriPrograms = "/epg/api/programs/create";

        String channelId = "06d6232c-dca4-493d-86fb-f82f057dc5d5";
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programToBeCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        String inputJsonProgram = super.mapToJson(programToBeCreated);

        // Act
        MvcResult mvcResultProgram = mvc.perform(
                post(uriPrograms).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJsonProgram)
                                 .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andReturn();

        String jsonResponseProgram = mvcResultProgram.getResponse().getContentAsString();

        String expectedJsonResponse = "{\"ResourceNotFoundError\":{\"messageKey\":\"Channel with id " + channelId +
                " not found!\",\"arguments\":[\"Id: " + channelId + "\"]}}";

        // Assert
        assertEquals(expectedJsonResponse, jsonResponseProgram);
    }

    @Test
    public void createProgramThatOverlapsExistingProgramFromDifferentChannelSuccessfully() throws Exception {
        // Arrange
        String uriPrograms = "/epg/api/programs/create";

        String channelIdChannelOne = channelIdRetrievedChannelOne;
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

        String inputJsonProgramChannelOne = super.mapToJson(programToBeCreatedChannelOne);

        mvc.perform(
                post(uriPrograms).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJsonProgramChannelOne)
                                 .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();

        String channelIdChannelTwo = channelIdRetrievedChannelTwo;
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

        String inputJsonProgramChannelTwo = super.mapToJson(programToBeCreatedChannelTwo);

        // Act
        MvcResult mvcResultProgramChannelTwo = mvc.perform(
                post(uriPrograms).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJsonProgramChannelTwo)
                                 .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                                                  .andReturn();

        String jsonResponseProgramChannelTwo = mvcResultProgramChannelTwo.getResponse().getContentAsString();
        String idProgramChannelTwo = JsonPath.parse(jsonResponseProgramChannelTwo).read("$.id");

        String expectedJsonResponse =
                "{\"id\":" + "\"" + idProgramChannelTwo + "\"" + ",\"channelId\":" + "\"" + channelIdChannelTwo + "\"" +
                        ",\"imageUrl\":" + "\"" +
                        imageUrlProgramChannelTwo + "\"" + ",\"title\":" + "\"" + titleProgramChannelTwo + "\"" +
                        ",\"description\":" + "\"" +
                        descriptionProgramChannelTwo + "\"" + ",\"startTime\":" + "\"" + startTimeProgramChannelTwo +
                        "\"" + ",\"endTime\":" + "\"" +
                        endTimeProgramChannelTwo + "\"" + "}";

        // Assert
        assertEquals(expectedJsonResponse, jsonResponseProgramChannelTwo);
    }

    @Test
    public void createMultipleProgramsSameChannelAtDifferentTimesSuccessfully() throws Exception {
        // Arrange
        String uriPrograms = "/epg/api/programs/create";

        String channelIdChannelOne = channelIdRetrievedChannelOne;
        String imageUrlProgramOneChannelOne = "http://cycloid.com/channel1-image1/";
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

        String inputJsonProgramOneChannelOne = super.mapToJson(programOneToBeCreatedChannelOne);

        mvc.perform(
                post(uriPrograms).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJsonProgramOneChannelOne)
                                 .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();

        String imageUrlProgramTwoChannelOne = "http://cycloid.com/channel1-image2/";
        String titleProgramTwoChannelOne = "Best Bundesliga Goals";
        String descriptionProgramTwoChannelOne = "Review the amazing goals scored in the last Bundesliga season!";
        LocalDateTime startTimeProgramTwoChannelOne = LocalDateTime.of(2020, Month.JULY, 18, 12, 46, 50);
        LocalDateTime endTimeProgramTwoChannelOne = LocalDateTime.of(2020, Month.JULY, 18, 13, 46, 50);

        Program programTwoToBeCreatedChannelOne =
                Program.Builder.programWith().withChannelId(channelIdChannelOne)
                               .withImageUrl(imageUrlProgramTwoChannelOne)
                               .withTitle(titleProgramTwoChannelOne)
                               .withDescription(descriptionProgramTwoChannelOne)
                               .withStartTime(startTimeProgramTwoChannelOne)
                               .withEndTime(endTimeProgramTwoChannelOne).build();

        String inputJsonProgramTwoChannelOne = super.mapToJson(programTwoToBeCreatedChannelOne);

        // Act
        MvcResult mvcResultProgramTwoChannelOne = mvc.perform(
                post(uriPrograms).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJsonProgramTwoChannelOne)
                                 .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                                                     .andReturn();

        String jsonResponseProgramOneChannelOne = mvcResultProgramTwoChannelOne.getResponse().getContentAsString();
        String idProgramOneChannelOne = JsonPath.parse(jsonResponseProgramOneChannelOne).read("$.id");

        String expectedJsonResponse =
                "{\"id\":" + "\"" + idProgramOneChannelOne + "\"" + ",\"channelId\":" + "\"" + channelIdChannelOne +
                        "\"" +
                        ",\"imageUrl\":" + "\"" +
                        imageUrlProgramTwoChannelOne + "\"" + ",\"title\":" + "\"" + titleProgramTwoChannelOne + "\"" +
                        ",\"description\":" + "\"" +
                        descriptionProgramTwoChannelOne + "\"" + ",\"startTime\":" + "\"" +
                        startTimeProgramTwoChannelOne +
                        "\"" + ",\"endTime\":" + "\"" +
                        endTimeProgramTwoChannelOne + "\"" + "}";

        // Assert
        assertEquals(expectedJsonResponse, jsonResponseProgramOneChannelOne);
    }

    @Test
    public void createProgramThatOverlapsExistingProgramFromSameChannelFails() throws Exception {
        // Arrange
        String uriPrograms = "/epg/api/programs/create";

        String channelIdChannelOne = channelIdRetrievedChannelOne;
        String imageUrlProgramOneChannelOne = "http://cycloid.com/channel1-image1/";
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

        String inputJsonProgramOneChannelOne = super.mapToJson(programOneToBeCreatedChannelOne);

        mvc.perform(
                post(uriPrograms).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJsonProgramOneChannelOne)
                                 .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();

        String imageUrlProgramTwoChannelOne = "http://cycloid.com/channel1-image2/";
        String titleProgramTwoChannelOne = "Best Bundesliga Goals";
        String descriptionProgramTwoChannelOne = "Review the amazing goals scored in the last Bundesliga season!";
        LocalDateTime startTimeProgramTwoChannelOne = LocalDateTime.of(2020, Month.JULY, 18, 12, 26, 50);
        LocalDateTime endTimeProgramTwoChannelOne = LocalDateTime.of(2020, Month.JULY, 18, 13, 46, 50);

        Program programTwoToBeCreatedChannelOne =
                Program.Builder.programWith().withChannelId(channelIdChannelOne)
                               .withImageUrl(imageUrlProgramTwoChannelOne)
                               .withTitle(titleProgramTwoChannelOne)
                               .withDescription(descriptionProgramTwoChannelOne)
                               .withStartTime(startTimeProgramTwoChannelOne)
                               .withEndTime(endTimeProgramTwoChannelOne).build();

        String inputJsonProgramTwoChannelOne = super.mapToJson(programTwoToBeCreatedChannelOne);

        // Act
        MvcResult mvcResultProgramTwoChannelOne = mvc.perform(
                post(uriPrograms).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJsonProgramTwoChannelOne)
                                 .accept(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError())
                                                     .andReturn();

        String jsonResponseProgramOneChannelOne = mvcResultProgramTwoChannelOne.getResponse().getContentAsString();

        String expectedJsonResponse =
                "{\"BusinessError\":{\"messageKey\":\"There is already a program starting at " +
                        startTimeProgramOneChannelOne + " and ending at " + endTimeProgramOneChannelOne +
                        "\",\"arguments\":[\"Start time: " + startTimeProgramOneChannelOne + "; End time: " +
                        endTimeProgramOneChannelOne + "\"]}}";

        // Assert
        assertEquals(expectedJsonResponse, jsonResponseProgramOneChannelOne);
    }

    @Test
    public void getAllProgramsByChannelIdReturnsPopulatedList() throws Exception {
        // Arrange
        String uriProgramsCreate = "/epg/api/programs/create";
        String uriProgramsGetAllProgramsByChannelId = "/epg/api/programs/channelId/" + channelIdRetrievedChannelOne;

        String channelId = channelIdRetrievedChannelOne;
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programToBeCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        String inputJsonProgram = super.mapToJson(programToBeCreated);

        MvcResult mvcResultProgramCreated = mvc.perform(
                post(uriProgramsCreate).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJsonProgram)
                                       .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();

        String jsonResponseProgramCreated = mvcResultProgramCreated.getResponse().getContentAsString();
        String id = JsonPath.parse(jsonResponseProgramCreated).read("$.id");

        // Act
        MvcResult mvcResultProgramRetrieved = mvc.perform(
                get(uriProgramsGetAllProgramsByChannelId).contentType(MediaType.APPLICATION_JSON_VALUE)
                                                         .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                                                 .andReturn();

        String jsonResponseProgramRetrieved = mvcResultProgramRetrieved.getResponse().getContentAsString();

        String expectedJsonResponse =
                "[{\"id\":" + "\"" + id + "\"" + ",\"channelId\":" + "\"" + channelId + "\"" + ",\"imageUrl\":" + "\"" +
                        imageUrl + "\"" + ",\"title\":" + "\"" + title + "\"" + ",\"description\":" + "\"" +
                        description + "\"" + ",\"startTime\":" + "\"" + startTime + "\"" + ",\"endTime\":" + "\"" +
                        endTime + "\"" + "}]";

        // Assert
        assertEquals(expectedJsonResponse, jsonResponseProgramRetrieved);
    }

    @Test
    public void getAllProgramsByChannelIdReturnsEmpty() throws Exception {
        // Arrange
        String uriProgramsGetAllProgramsByChannelId = "/epg/api/programs/programId" + channelIdRetrievedChannelOne;

        // Act
        MvcResult mvcResultProgramRetrieved = mvc.perform(
                get(uriProgramsGetAllProgramsByChannelId).contentType(MediaType.APPLICATION_JSON_VALUE)
                                                         .accept(MediaType.APPLICATION_JSON))
                                                 .andExpect(status().isNotFound())
                                                 .andReturn();

        String jsonResponseProgramRetrieved = mvcResultProgramRetrieved.getResponse().getContentAsString();

        String expectedJsonResponse = "";

        // Assert
        assertEquals(expectedJsonResponse, jsonResponseProgramRetrieved);
    }

    @Test
    public void getProgramByIdSuccessfully() throws Exception {
        // Arrange
        String uriProgramsCreate = "/epg/api/programs/create";

        String channelId = channelIdRetrievedChannelOne;
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programToBeCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        String inputJsonProgram = super.mapToJson(programToBeCreated);

        MvcResult mvcResultProgramCreated = mvc.perform(
                post(uriProgramsCreate).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJsonProgram)
                                       .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();

        String jsonResponseProgramCreated = mvcResultProgramCreated.getResponse().getContentAsString();
        String id = JsonPath.parse(jsonResponseProgramCreated).read("$.id");

        String uriProgramsGetProgramById = "/epg/api/programs/programId/" + id;

        // Act
        MvcResult mvcResultProgramRetrieved = mvc.perform(
                get(uriProgramsGetProgramById).contentType(MediaType.APPLICATION_JSON_VALUE)
                                              .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                                                 .andReturn();

        String jsonResponseProgramRetrieved = mvcResultProgramRetrieved.getResponse().getContentAsString();

        String expectedJsonResponse =
                "{\"id\":" + "\"" + id + "\"" + ",\"channelId\":" + "\"" + channelId + "\"" + ",\"imageUrl\":" + "\"" +
                        imageUrl + "\"" + ",\"title\":" + "\"" + title + "\"" + ",\"description\":" + "\"" +
                        description + "\"" + ",\"startTime\":" + "\"" + startTime + "\"" + ",\"endTime\":" + "\"" +
                        endTime + "\"" + "}";

        // Assert
        assertEquals(expectedJsonResponse, jsonResponseProgramRetrieved);
    }

    @Test
    public void deleteProgramByIdSuccessfully() throws Exception {
        // Arrange
        String uriProgramsCreate = "/epg/api/programs/create";

        String channelId = channelIdRetrievedChannelOne;
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programToBeCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        String inputJsonProgram = super.mapToJson(programToBeCreated);

        MvcResult mvcResultProgramCreated = mvc.perform(
                post(uriProgramsCreate).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJsonProgram)
                                       .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();

        String jsonResponseProgramCreated = mvcResultProgramCreated.getResponse().getContentAsString();
        String id = JsonPath.parse(jsonResponseProgramCreated).read("$.id");

        String uriProgramsDeleteProgramById = "/epg/api/programs/programId/" + id;

        // Act
        MvcResult mvcResultProgramRetrieved = mvc.perform(
                delete(uriProgramsDeleteProgramById).contentType(MediaType.APPLICATION_JSON_VALUE)
                                                    .accept(MediaType.APPLICATION_JSON))
                                                 .andExpect(status().isNoContent())
                                                 .andReturn();

        String jsonResponseProgramRetrieved = mvcResultProgramRetrieved.getResponse().getContentAsString();

        String expectedJsonResponse = "";

        // Assert
        assertEquals(expectedJsonResponse, jsonResponseProgramRetrieved);
    }

    @Test
    public void deleteProgramByIdNonExistingProgramFails() throws Exception {
        // Arrange
        String id = "c0a4a28f-dccd-4c65-9b80-247377b0c3d9";
        String uriProgramsDeleteProgramById = "/epg/api/programs/programId/" + id;

        // Act
        MvcResult mvcResultProgramRetrieved = mvc.perform(
                delete(uriProgramsDeleteProgramById).contentType(MediaType.APPLICATION_JSON_VALUE)
                                                    .accept(MediaType.APPLICATION_JSON))
                                                 .andExpect(status().isInternalServerError())
                                                 .andReturn();

        String jsonResponseProgramRetrieved = mvcResultProgramRetrieved.getResponse().getContentAsString();

        String expectedJsonResponse =
                "{\"TechnicalError\":{\"message\":\"No class epg.model.Program entity with id " + id + " exists!\"}}";

        // Assert
        assertEquals(expectedJsonResponse, jsonResponseProgramRetrieved);
    }

    @Test
    public void updateProgramByIdSuccessfully() throws Exception {
        // Arrange
        String uriProgramsCreate = "/epg/api/programs/create";

        String channelId = channelIdRetrievedChannelOne;
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programToBeCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        String inputJsonProgram = super.mapToJson(programToBeCreated);

        MvcResult mvcResultProgramCreated = mvc.perform(
                post(uriProgramsCreate).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJsonProgram)
                                       .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();


        String jsonResponseProgramCreated = mvcResultProgramCreated.getResponse().getContentAsString();
        String id = JsonPath.parse(jsonResponseProgramCreated).read("$.id");

        String uriProgramsUpdateProgramById = "/epg/api/programs/programId/" + id;

        String channelIdUpdate = channelIdRetrievedChannelTwo;
        Program programUpdate = Program.Builder.programWith().withChannelId(channelIdUpdate).build();
        String inputJsonProgramUpdate = super.mapToJson(programUpdate);

        //Act
        MvcResult mvcResultProgramUpdated = mvc.perform(
                put(uriProgramsUpdateProgramById).contentType(MediaType.APPLICATION_JSON_VALUE)
                                                 .content(inputJsonProgramUpdate)
                                                 .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                                               .andReturn();

        String jsonResponseProgramUpdated = mvcResultProgramUpdated.getResponse().getContentAsString();

        String expectedJsonResponse =
                "{\"id\":" + "\"" + id + "\"" + ",\"channelId\":" + "\"" + channelIdUpdate + "\"" + ",\"imageUrl\":" +
                        "\"" +
                        imageUrl + "\"" + ",\"title\":" + "\"" + title + "\"" + ",\"description\":" + "\"" +
                        description + "\"" + ",\"startTime\":" + "\"" + startTime + "\"" + ",\"endTime\":" + "\"" +
                        endTime + "\"" + "}";

        // Assert
        assertEquals(expectedJsonResponse, jsonResponseProgramUpdated);
    }

    @Test
    public void updateProgramByIdFails() throws Exception {
        // Arrange
        String id = "c0a4a28f-dccd-4c65-9b80-247377b0c3d9";
        String uriProgramsUpdateProgramById = "/epg/api/programs/programId/" + id;

        String channelId = channelIdRetrievedChannelOne;
        String imageUrl = "http://cycloid.com/channel1-image/";
        String title = "Best EPL Goals";
        String description = "Review the amazing goals scored in the last English Premier League season!";
        LocalDateTime startTime = LocalDateTime.of(2020, Month.JULY, 18, 11, 45, 47);
        LocalDateTime endTime = LocalDateTime.of(2020, Month.JULY, 18, 12, 45, 47);

        Program programToBeCreated =
                Program.Builder.programWith().withChannelId(channelId).withImageUrl(imageUrl).withTitle(title)
                               .withDescription(description).withStartTime(startTime).withEndTime(endTime).build();

        String inputJsonProgram = super.mapToJson(programToBeCreated);

        // Act
        MvcResult mvcResultProgramRetrieved = mvc.perform(
                put(uriProgramsUpdateProgramById).contentType(MediaType.APPLICATION_JSON_VALUE)
                                                 .content(inputJsonProgram)
                                                 .accept(MediaType.APPLICATION_JSON))
                                                 .andExpect(status().isNotFound())
                                                 .andReturn();

        String jsonResponseProgramRetrieved = mvcResultProgramRetrieved.getResponse().getContentAsString();

        String expectedJsonResponse =
                "{\"ResourceNotFoundError\":{\"messageKey\":\"Program with id " + id +
                        " not found!\",\"arguments\":[\"Id: " + id + "\"]}}";

        // Assert
        assertEquals(expectedJsonResponse, jsonResponseProgramRetrieved);
    }
}
