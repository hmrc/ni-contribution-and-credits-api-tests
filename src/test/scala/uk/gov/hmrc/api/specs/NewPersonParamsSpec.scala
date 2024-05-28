/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.specs

import org.scalatest.prop.TableDrivenPropertyChecks
import uk.gov.hmrc.test.api.helpers.FeatureHelper
import uk.gov.hmrc.test.api.service.NewPersonParamsService

class NewPersonParamsSpec extends BaseSpec with FeatureHelper with TableDrivenPropertyChecks {

  val service = new NewPersonParamsService()
  Feature("Individuals Employments API Steps") {
      Scenario(
         "journey through Auth, Individuals Matching and Individuals Employments"
      ) {
        Given(s"an authorised user generates a bearer token")
        //val authResponse = authService.getLocalBearer(indUseCase)
//        authResponse.status shouldBe created


        When("they make a GET request to the nino service")
        val response = service.getNiInfo("authToken","Test NINO","Test CorrelationId")

        // TODO: Verify the body of the response

//        Then("they should receive a record of the same individual in response")
//        val expectedIndividualsMatchGETResponse =
//          getExpectedForNewPersonApiEndpoint(IndividualsMatching, MatchLinks, indMatchId, indUseCase)
//        verifyCall(actualIndividualsMatchGETResponse)(expectedIndividualsMatchGETResponse)

      }
    }
  }
}
