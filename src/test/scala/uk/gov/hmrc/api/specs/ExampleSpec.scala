/*
 * Copyright 2024 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.api.specs

import uk.gov.hmrc.api.models.Request

class ExampleSpec extends BaseSpec {

  Feature("Example of using the Contributions and Credits API") {

    Scenario("Retrieve Class 1 and Class 2 data for given nationalInsuranceNumber") {

      Given("There is an existing nationalInsuranceNumber and range of tax years")
      //val authBearerToken: String    = authHelper.getAuthBearerToken
     // val individualsMatchId: String = testDataHelper.createAnIndividual(authBearerToken, ninoUser)

      When("I use the date of birth to retrieve the Class 1 and Class2 details")
      val response =
        niccService.makeRequest("testBearerToken", Request("1960-04-05"), "A123456C", "2019", "2021")

      Then("I am returned the Class 1 and Class 2 details")

      response.header("correlationId") shouldBe "e470d658-99f7-4292-a4a1-ed12c72f1337"
      response.body.contains("niContribution") shouldBe true
      response.body.contains("niCredit") shouldBe true
      response.status shouldBe 200
    }
    Scenario("Verify Nino Endpoints happy path") {
     // val consignorToken = givenGetToken(ninoUser.nino)
     // val response       = newUser(token, nino)
     // thenValidateResponseCode(response, 200)
      // checkJsonValue(response, "tfc_account_status", "active")
    }

  }

  Feature("Example of using the NIContributions and NICredits API Negative Scenarios") {

    Scenario("Passing invalid nationalInsuranceNumber") {
      val response =
        niccService.makeRequest("testBearerToken", Request("1960-04-05"), "A123456", "2019", "2021")
        response.status shouldBe 400
    }

    Scenario("Passing date of birth year is above pension age") {
      val response =
        niccService.makeRequest("testBearerToken", Request("1950-04-05"), "A123456B", "2019", "2021")
      response.status shouldBe 400
    }

    Scenario("Passing Start Tax Year after end tax year") {
      val response =
        niccService.makeRequest("testBearerToken", Request("1960-04-05"), "A123456B", "2022", "2021")
      response.status shouldBe 400
    }

    Scenario("Passing Start Tax Year after CY-1") {
      val response =
        niccService.makeRequest("testBearerToken", Request("1960-04-05"), "A123456B", "2024", "2025")
      response.status shouldBe 400
    }

    Scenario("Passing Tax Year range over 6 years") {
      val response =
        niccService.makeRequest("testBearerToken", Request("1960-04-05"), "A123456B", "2016", "2023")
      response.status shouldBe 400
    }

      Scenario("Incorrect Access Token Type") {
        val response =
          niccService.makeRequest("testBearerToken", Request("1960-04-05"), "A123456C", "2019", "2021")
        response.status shouldBe 401
      }

    Scenario("Resource not found") {
      val response =
        niccService.makeRequest("testBearerToken", Request("1960-04-05"), "A123456C", "2022", "2023")
      response.status shouldBe 404
    }

    Scenario("Internal Server Error") {
      val response =
        niccService.makeRequest("testBearerToken", Request("1960-04-05"), "A123456C", "2022", "2023")
      response.status shouldBe 500
    }

  }

}
