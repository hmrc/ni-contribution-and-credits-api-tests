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

import play.api.libs.json.Format.GenericFormat
import play.api.libs.json._
import uk.gov.hmrc.api.models.{Request, Response}

class ExampleSpec extends BaseSpec {

  Feature("Example of using the NIContributions and NICredits API") {

    Scenario("Retrieve Class 1 and Class 2 data for given nationalInsuranceNumber") {

      Given("The NICC API is up and running")
      //val authBearerToken: String    = authHelper.getAuthBearerToken
      // val individualsMatchId: String = testDataHelper.createAnIndividual(authBearerToken, ninoUser)

      // niccService.makeRequest("testBearerToken", "H001", Request.........)
      When("A request for NINC is sent")

      val response =
        //niccService.makeRequest("testBearerToken", new Request("1960-04-05"), "SS000200", "2019", "2021")
        niccService.makeRequest(Request("1960-04-05"), "BB000200B", "2019", "2021")


      val responseBody: Response = Json.parse(response.body).as[Response] //json to case class

      Then("Class 1 and Class 2 details are returned")
      response.status shouldBe 200
      response.body.contains("niContribution") shouldBe true
      response.body.contains("niCredit") shouldBe true
      println("The Response Status Code is : " + response.status + " " + response.statusText)
      println("The Response Body is : " + response.body)
      responseBody.niContribution.head.class1ContributionStatus shouldBe "COMPLIANCE & YIELD INCOMPLETE"
      responseBody.niCredit.head.contributionCreditType shouldBe "CLASS 2 - NORMAL RATE"

    }

  }

  Feature("Example of using the NIContributions and NICredits API Negative Scenarios") {

    Scenario("Passing invalid nationalInsuranceNumber") {
      val response =
        niccService.makeRequest(Request("1960-04-05"), "BB000400B", "2019", "2021")
      response.status shouldBe 400
      println("The Response Status Code is : " + response.status + " " + response.statusText)
      println(response.body)
    }

    Scenario("Passing incorrect date of birth format at request") {
      val response =
        niccService.makeRequest(Request("04-05-1960"), "BB000400B", "2019", "2021")
      response.status shouldBe 400
      println("The Response Status Code is : " + response.status + " " + response.statusText)
      println(response.body)
    }

    Scenario("Passing date of birth year is above pension age") {
      val response =
        niccService.makeRequest(Request("1950-04-05"), "BB000200B", "2019", "2021")
      response.status shouldBe 200
      println("The Response Status Code is : " + response.status + " " + response.statusText)
      println(response.body)
    }

    Scenario("Passing date of birth is exact 16 years old") {
      val response =
        niccService.makeRequest(Request("2007-11-05"), "BB000200B", "2019", "2021")
      response.status shouldBe 200
      println("The Response Status Code is : " + response.status + " " + response.statusText)
      println(response.body)
    }

    Scenario("Passing Start Tax Year after end tax year") {
      val response =
        niccService.makeRequest(Request("1976-04-05"), "BB000422B", "2022", "2021")
      response.status shouldBe 422
      println("The Response Status Code is : " + response.status + " " + response.statusText)
      println(response.body)
    }

    Scenario("Passing Start Tax Year after CY-1") {
      val response =
        niccService.makeRequest(Request("1960-04-05"), "BB000422B", "2024", "2025")
      response.status shouldBe 422
      println("The Response Status Code is : " + response.status + " " + response.statusText)
      println(response.body)
    }

    Scenario("Passing End Tax Year after CY-1") {
      val response =
        niccService.makeRequest(Request("1960-04-05"), "BB000422B", "2022", "2023")
      response.status shouldBe 422
      println("The Response Status Code is : " + response.status + " " + response.statusText)
      println(response.body)
    }

    Scenario("Passing Start Tax Year before 1975") {
      val response =
        niccService.makeRequest(Request("1960-04-05"), "BB000422B", "1974", "2024")
      response.status shouldBe 422
      println("The Response Status Code is : " + response.status + " " + response.statusText)
      println(response.body)
    }

    Scenario("Passing Tax Year range over 6 years") {
      val response =
        niccService.makeRequest(Request("1960-04-05"), "BB000422B", "2016", "2023")
      response.status shouldBe 422
      println("The Response Status Code is : " + response.status + " " + response.statusText)
      println(response.body)
    }

    /* Scenario("Incorrect Access Token Type") {
        val response =
          niccService.makeRequest(Request("1960-04-05"), "BB000401B", "2019", "2021")
        response.status shouldBe 401
        println("The Response Status Code is : " + response.status + " " + response.statusText)
        println(response.body)
      }*/

    /* Scenario("No Access Token Type") {
        val response =
          niccService.makeRequest(Request("1960-04-05"), "BB000401B", "2019", "2021")
        response.status shouldBe 401
        println("The Response Status Code is : " + response.status + " " + response.statusText)
        println(response.body)
      }*/

    Scenario("NICC details are not returned when end point not found") {
      val response =
        niccService.makeRequest(Request("1960-04-05"), "", "2022", "2023")
      response.status shouldBe 404
      println("The Response Status Code is : " + response.status + " " + response.statusText)
      println(response.body)
    }

    Scenario("Forbidden Error") {
      val response =
        niccService.makeRequest(Request("1980-04-05"), "BB000403B", "2022", "2023")
      response.status shouldBe 500
      println("The Response Status Code is : " + response.status + " " + response.statusText)
    }

    Scenario("Internal Server Error") {
      val response =
        niccService.makeRequest(Request("1960-04-05"), "BB000500B", "2022", "2023")
      response.status shouldBe 500
      println("The Response Status Code is : " + response.status)
      println(response.body)
    }

  }

}