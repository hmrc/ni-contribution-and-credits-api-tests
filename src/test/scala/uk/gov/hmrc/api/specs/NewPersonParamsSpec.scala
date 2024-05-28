/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.specs

import org.scalatest.prop.TableDrivenPropertyChecks
import uk.gov.hmrc.test.api.client.ServiceResponse
import uk.gov.hmrc.test.api.helpers.FeatureHelper
import uk.gov.hmrc.test.api.models.{IndUseCase, UseCaseResponse}
import uk.gov.hmrc.test.api.tags.individualsAPIs

class NewPersonParamsSpec extends BaseSpec with FeatureHelper with TableDrivenPropertyChecks {

  Feature("Individuals Employments API Steps") {
      Scenario(
         "journey through Auth, Individuals Matching and Individuals Employments",
        NewPersonParamsAPI
      ) {
        Given(s"an authorised user generates a bearer token")
        //val authResponse = authService.getLocalBearer(indUseCase)
        authResponse.status shouldBe created

// Individuals Matching

        When("they make a GET request to Individuals Matching using that valid MatchId")
        val actualIndividualsMatchGETResponse: ServiceResponse =
          getNiInfo(authToken,niInfo,correlationId)

        Then("they should receive a record of the same individual in response")
        val expectedIndividualsMatchGETResponse =
          getExpectedForNewPersonApiEndpoint(IndividualsMatching, MatchLinks, indMatchId, indUseCase)
        verifyCall(actualIndividualsMatchGETResponse)(expectedIndividualsMatchGETResponse)

      }
    }
  }
}
