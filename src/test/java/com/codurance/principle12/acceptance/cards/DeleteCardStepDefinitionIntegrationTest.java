package com.codurance.principle12.acceptance.cards;

import static com.codurance.principle12.utils.HttpWrapper.executeDelete;
import static com.codurance.principle12.utils.HttpWrapper.responseResult;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.codurance.principle12.acceptance.BaseStepDefinition;
import com.codurance.principle12.responses.CardResponseObject;
import com.codurance.principle12.utils.HttpWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.http.HttpHeaders;

public class DeleteCardStepDefinitionIntegrationTest extends BaseStepDefinition {

  private final String USER_EMAIL = "john.doe@codurance.com";
  private final Long COLUMN_ID = 1L;
  private final String CARD_TEXT = "Hello";
  private final String TOKEN = "token";
  private HttpHeaders headers;


  public DeleteCardStepDefinitionIntegrationTest(DataSource dataSource) {
    super(dataSource);
  }

  @Before
  public void cleanUpDatabase() throws SQLException {
    cleanUp();
    headers = new HttpHeaders();
    headers.set(HttpHeaders.AUTHORIZATION, TOKEN);
  }

  @When("the client deletes to cards with this id passing it as path variable to endpoint")
  public void theClientDeletesToCardsEndpointWithPathVariable() throws JsonProcessingException {
    CardResponseObject cardResponseObject = new ObjectMapper()
        .readValue(responseResult.getBody(), new TypeReference<>() {
        });
    executeDelete(url + "/cards/" + cardResponseObject.getId());
  }

  @Then("the client receives a status code of {int} after card was deleted")
  public void theClientReceivesAStatusCodeOfAfterCardWasDeleted(int statusCode) {
    assertThat(HttpWrapper.responseResult.getResponseCode(), is(statusCode));
  }
}
