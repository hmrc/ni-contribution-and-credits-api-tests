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

import play.api.libs.json.Json
import uk.gov.hmrc.api.models.Request

class ExampleSpec extends BaseSpec {

  Feature("Example of using the Contributions and Credits API") {

    Scenario("Retrieve Class 1 and Class 2 data for given nationalInsuranceNumber") {

      Given("The NICC API is up and running")
      //val authBearerToken: String    = authHelper.getAuthBearerToken
     // val individualsMatchId: String = testDataHelper.createAnIndividual(authBearerToken, ninoUser)

      When("A request for NINC is sent")
      val response =
        niccService.makeRequest("testBearerToken", Request("1960-04-05"), "A123456C", "2019", "2021")

      val request = Json.parse(response.body).as[Seq[Request]]

      Then("Class 1 and Class 2 details are returned")
      response.status shouldBe 200
      response.header("correlationId") shouldBe "e470d658-99f7-4292-a4a1-ed12c72f1337"
      response.body.contains("niContribution") shouldBe true
      response.body.contains("niCredit") shouldBe true
      request.nonEmpty shouldBe true

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

    Scenario("Passing date of birth is exact 16 years old") {
      val response =
        niccService.makeRequest("testBearerToken", Request("2007-11-05"), "A123456B", "2019", "2021")
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

    Scenario("NICC details are not returned when not passing dateOfBirth") {
      val response =
        niccService.makeRequest("testBearerToken", Request("1960-04-05"), "", "2022", "2023")
      response.status shouldBe 404
    }

    Scenario("Internal Server Error") {
      val response =
        niccService.makeRequest("testBearerToken", Request("1960-04-05"), "A123456C", "2022", "2023")
      response.status shouldBe 500
    }

  }

}
