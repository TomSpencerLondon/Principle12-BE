package com.codurance.principle12.acceptance.cards;

import static com.codurance.principle12.utils.HttpWrapper.executePost;
import static com.codurance.principle12.utils.HttpWrapper.postResponse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.codurance.principle12.models.Card;
import com.codurance.principle12.requests.NewCardRequestObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;

public class CardStepDefsIntegrationTest {

  @When("the client posts to cards endpoint with column_id:{int}, text:{string} and userName:{string}")
  public void theClientPostsToCardsEndpointWithColumn_idAndText(int columnId, String text, String userName) {
    executePost("http://localhost:5000/cards", new HttpEntity<>(new NewCardRequestObject(text, columnId, userName)));
  }

  @Then("^the client receives a status code of (\\d+)$")
  public void theClientReceivesStatusCodeOf(int statusCode) {
    final HttpStatus currentStatusCode = postResponse.getTheResponse().getStatusCode();
    assertThat(currentStatusCode.value(), is(statusCode));
  }

  @And("the client receives the card with the column_id:{int}, text:{string} and userName:{string}")
  public void theClientReceivesTheCardWithTheColumn_idAndText(int columnId, String text, String userName) throws JsonProcessingException {
    Card card = new ObjectMapper().readValue(postResponse.getBody(), new TypeReference<>() {
    });

    assertThat(card.getColumnId(), is(columnId));
    assertThat(card.getText(), is(text));
    assertThat(card.getUserName(), is(userName));
  }
}
