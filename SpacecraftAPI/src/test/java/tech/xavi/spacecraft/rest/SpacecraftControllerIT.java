package tech.xavi.spacecraft.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import tech.xavi.spacecraft.configuration.EndPoints;
import tech.xavi.spacecraft.entity.Spacecraft;
import tech.xavi.spacecraft.exception.ApiError;
import tech.xavi.spacecraft.service.FakeDataGenerator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Log4j2
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@SpringBootTest
class SpacecraftControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private FakeDataGenerator fakeDataGenerator;

    @Test
    void createSpacecraft() throws Exception {
        // given
        Spacecraft requestSpacecraft = fakeDataGenerator.generateTestData(1).get(0);
        String jsonReqBody = new ObjectMapper().writeValueAsString(requestSpacecraft);
        log.info("TEST: createSpacecraft (POST) {}  - REQ. BODY: {}",
                EndPoints.EP_SPACECRAFT,
                jsonReqBody
        );
        // when
        var response = mockMvc.perform(
                post(EndPoints.EP_SPACECRAFT)
                        .content(jsonReqBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        String jsonResBody = response.getContentAsString();
        // then
        assertEquals(HttpStatus.CREATED.value(),response.getStatus(),"HttpStatus should be created");
        assertEquals(jsonReqBody,jsonResBody,"Json Response should be the same");
        log.info("RETRIEVED RESPONSE: {}",jsonResBody);
    }

    @Test
    void getSpacecraftsByNameContains() throws Exception {
        // given
        List<Spacecraft> spacecrafts  = fakeDataGenerator.generateTestData(1);
        Spacecraft firstSc = spacecrafts.get(0);
        String searchParamVal = firstSc.getName().substring(1,3);
        log.info("TEST: getSpacecraftsByNameContains (POST) {}  - Search Param Value: {}",
                EndPoints.EP_SPACECRAFT,
                searchParamVal
        );
        // when
        var response = mockMvc.perform(
                        get(EndPoints.EP_SC_NAME_CONTAINS)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("name",searchParamVal)
                )
                .andReturn()
                .getResponse();
        String jsonResBody = response.getContentAsString();
        // then
        assertEquals(HttpStatus.OK.value(),response.getStatus(),"HttpStatus should be OK");
        assertTrue(new ObjectMapper().readValue(jsonResBody, List.class).size() > 0,"Records found");
        log.info("RETRIEVED RESPONSE: {}",jsonResBody);
    }

    @Test
    void whenReqNegativeId_shouldReturnBadRequest() throws Exception {
        // given
        String negativeId = "-1337";
        log.info("TEST: whenReqNegativeId_shouldReturnBadRequest (GET) {}  - Requested Negative Id: {}",
                EndPoints.EP_SPACECRAFT,
                negativeId
        );
        // when
        var response = mockMvc.perform(get(EndPoints.EP_SC_BY_ID.replace("{id}",negativeId)))
                .andReturn()
                .getResponse();
        String jsonResBody = response.getContentAsString();
        // then
        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus(),"HttpStatus should be BAD_REQ.");
        assertTrue(jsonResBody.contains(ApiError.NEGATIVE_ID.getMessage()),"Negative ID message is present");
        log.info("RETRIEVED RESPONSE: {}",jsonResBody);
    }

}