package com.codurance.principle12.acceptance.cards;

import static com.codurance.principle12.utils.HttpWrapper.executePatch;
import static com.codurance.principle12.utils.HttpWrapper.responseResult;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.codurance.principle12.acceptance.BaseStepDefinition;
import com.codurance.principle12.requests.UpVoteRequestObject;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public class RemoveUpvoteStepDefinitionIntegrationTest extends BaseStepDefinition {

  private final String TOKEN = "token";
  private HttpHeaders headers;

  public RemoveUpvoteStepDefinitionIntegrationTest(DataSource dataSource) {
    super(dataSource);
  }

  @Before
  public void cleanUpDatabase() throws SQLException {
    cleanUp();
    headers = new HttpHeaders();
    headers.set(HttpHeaders.AUTHORIZATION, TOKEN);
  }

  @When("the client removes card vote with voter:{string}")
  public void theClientRemovesCardVoteWithVoter(String email)
      throws JsonProcessingException {
    CardResponseObject cardResponseObject = new ObjectMapper().readValue(responseResult.getBody(), new TypeReference<>() {
    });
    executePatch(url + "/cards/" + cardResponseObject.getId() + "/vote", new HttpEntity<>(new UpVoteRequestObject(email, false)));
  }

  @Then("the client receives the card without their vote")
  public void theClientReceivesTheCardWithoutTheirVote() throws JsonProcessingException {
    CardResponseObject cardResponseObject = new ObjectMapper().readValue(responseResult.getBody(), new TypeReference<>() {
    });

    assertThat(HttpWrapper.responseResult.getResponseCode(), is(HttpStatus.OK.value()));
    assertThat(cardResponseObject.getTotalVoters(), is(0));
    assertThat(cardResponseObject.getHaveVoted(), is(false));
  }
}
