package com.codurance.principle12.acceptance.board;

import static com.codurance.principle12.utils.HttpWrapper.executePost;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import static com.codurance.principle12.utils.HttpWrapper.responseResult;
import com.codurance.principle12.acceptance.BaseStepDefinition;
import com.codurance.principle12.entities.Board;
import com.codurance.principle12.requests.NewBoardRequestObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.core.JsonProcessingException;

public class AddBoardStepDefsIntegrationTest extends BaseStepDefinition {

  private String userEmail;
  private final String TOKEN = "token";
  private HttpHeaders headers;


  public AddBoardStepDefsIntegrationTest(DataSource dataSource) {
    super(dataSource);
  }

  @Before
  public void cleanUpDatabase() throws SQLException {
    cleanUp();
    headers = new HttpHeaders();
    headers.set(HttpHeaders.AUTHORIZATION, TOKEN);
  }

  @Given("a user is logged in")
  public void aUserIsLoggedIn() {
    userEmail = "john.doe@codurance.com";
  }

  @When("the client sends the title of the board {string} and their email")
  public void theClientSendsTheNameOfTheBoardAndTheirEmail(String boardTitle) {
    executePost(url + "/boards", new HttpEntity<>(new NewBoardRequestObject(boardTitle, userEmail), headers));
  }

  @Then("the client receives the new board with title {string}")
  public void theClientReceivesTheNewBoard(String boardTitle) throws JsonProcessingException {
    Board board = new ObjectMapper().readValue(responseResult.getBody(), new TypeReference<>() {
    });

    assertThat(responseResult.getResponseCode(), is(HttpStatus.CREATED.value()));
    assertThat(board.getTitle(), is(boardTitle));
  }
}