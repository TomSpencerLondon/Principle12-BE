package com.codurance.principle12.acceptance;

import com.codurance.principle12.Retropolis;
import com.codurance.principle12.config.Environment;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", strict = true)
@CucumberContextConfiguration
@ActiveProfiles(Environment.TEST)
@SpringBootTest(classes = Retropolis.class, webEnvironment = WebEnvironment.DEFINED_PORT)
public class CucumberIntegrationTest {

}