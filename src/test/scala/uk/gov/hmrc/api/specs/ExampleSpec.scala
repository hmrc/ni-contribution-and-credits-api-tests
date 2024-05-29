/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.api.specs

import uk.gov.hmrc.api.models.User
import User._

class ExampleSpec extends BaseSpec {

  Feature("Example of using the Individuals Matching API") {

    Scenario("Get an individuals details by MatchId") {

      Given("There is an existing individual with a MatchId")
      val authBearerToken: String    = authHelper.getAuthBearerToken
      val individualsMatchId: String = testDataHelper.createAnIndividual(authBearerToken, ninoUser)

      When("I use that MatchId to retrieve the same individuals details")
      val actualUser: User =
        individualsMatchingHelper.getIndividualByMatchId(authBearerToken, individualsMatchId)

      Then("I am returned the individuals details")
      actualUser shouldBe ninoUser
    }
    Scenario("Verify Nino Endpoints happy path") {
      val consignorToken = givenGetToken(ninoUser.nino)
      val response       = newUser(token,nino)
      thenValidateResponseCode(response, 200)
      //checkJsonValue(response, "tfc_account_status", "active")
    }

  }

}
