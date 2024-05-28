package uk.gov.hmrc.api.runner

import io.cucumber.junit.CucumberOptions
import io.cucumber.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(classOf[Cucumber])
@CucumberOptions(
  features = Array("src/test/resources/features"),
  glue = Array("uk.gov.hmrc.api.specs"),
  plugin = Array("pretty", "html:target/cucumber/index.html", "json:target/cucumber.json")
)
class Runner {}