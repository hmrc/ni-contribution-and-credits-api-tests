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

import org.scalatest.BeforeAndAfterAll
import play.api.libs.json.Json
import uk.gov.hmrc.api.helpers.BaseHelper
import uk.gov.hmrc.api.models.{Request, Response}
import uk.gov.hmrc.api.utils.JsonUtils

class ErrorValidation_InvalidPayloadParameters extends BaseSpec with BaseHelper with BeforeAndAfterAll {

  val badRequestErrorResponse =
    "{\"failures\":[{\"reason\":\"There was a problem with the request\",\"code\":\"400\"}]}"

  var PayloadMapping: Map[String, Request] = _

  override def beforeAll(): Unit = {
    super.beforeAll()
    val jsonString = JsonUtils.readJsonFile("uk/gov/hmrc/api/testData/TestData_N001_to_N007.json")
    PayloadMapping = JsonUtils.parseJsonToMap(jsonString) match {
      case Left(failure) => fail(s"Parsing failed: $failure")
      case Right(map)    => map
    }
  }

  Feature("VALIDATION OF ERROR CODES FOR INVALID INPUT") {


    Scenario("Request with Invalid NINO receives error response 400 from MDTP") {
      val payload = PayloadMapping.getOrElse("NICC_TC_N001", fail("NICC_TC_N001 not found"))
      val response=
        niccService.makeRequest(
          Request(
            payload.nationalInsuranceNumber,
            payload.dateOfBirth,
            payload.customerCorrelationID,
            payload.startTaxYear,
            payload.endTaxYear

        )
        )
     /* val response =
        niccService.makeRequest(
          Request("xx", "1960-04-05", Some("e470d658-99f7-4292-a4a1-ed12c72f1337"), "2019", "2021")
        )*/
      response.status shouldBe 400
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body   shouldBe badRequestErrorResponse
      println("Response Body is: " + response.body)
      val responseBody = Json.parse(response.body)
      println("The Response Body is : \n" + Json.prettyPrint(Json.toJson(responseBody)))
    }

    Scenario("Verify the request with Date of Birth with invalid format receives error response 400") {
      val response =
        niccService.makeRequest(
          Request("BB000200B", "1960/04/05", Some("e470d658-99f7-4292-a4a1-ed12c72f1337"), "2019", "2021")
        )
      response.status shouldBe 400
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body   shouldBe badRequestErrorResponse
      println("Response Body is: " + response.body)
    }

    Scenario("Verify the request with start tax year with invalid format receives error response 400") {
      val response =
        niccService.makeRequest(
          Request("BB000200B", "1960-04-05", Some("e470d658-99f7-4292-a4a1-ed12c72f1337"), ":2019", "2021")
        )
      response.status shouldBe 400
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body   shouldBe badRequestErrorResponse
      println("Response Body is: " + response.body)
    }

    Scenario("Verify the request with end tax year with invalid format receives error response 400") {
      val response =
        niccService.makeRequest(
          Request("BB000200B", "1960-04-05", Some("e470d658-99f7-4292-a4a1-ed12c72f1337"), "2019", ":2021")
        )
      response.status shouldBe 400
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body   shouldBe badRequestErrorResponse
      println("Response Body is: " + response.body)
    }

    Scenario("Verify the request with Start tax year after CY-1, receives error response 422") {
      val response =
        niccService.makeRequest(
          Request("AA271213C", "1969-12-09", Some("e470d658-99f7-4292-a4a1-ed12c72f1337"), "2024", "2023")
        )
      response.status shouldBe 422
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body   shouldBe "{\"failures\":[{\"reason\":\"Start tax year after CY-1\",\"code\":\"63498\"}]}"
      println("Response Body is: " + response.body)
    }

    Scenario("Verify the request with Tax year range greater than six years, receives error response 422") {
      val response =
        niccService.makeRequest(
          Request("AA271213C", "1969-12-09", Some("e470d658-99f7-4292-a4a1-ed12c72f1337"), "2016", "2023")
        )
      response.status shouldBe 422
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body   shouldBe "{\"failures\":[{\"reason\":\"Tax year range greater than six years\",\"code\":\"63500\"}]}"
      println("Response Body is: " + response.body)
    }

    Scenario(
      "Verify the request with endTaxYear is 2024 as startTaxYear and endTaxYear cannot be this year 2024, receives error response 422"
    ) {
      val response =
        niccService.makeRequest(
          Request("AA271213C", "1969-12-09", Some("e470d658-99f7-4292-a4a1-ed12c72f1337"), "2017", "2024")
        )
      response.status shouldBe 422
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body   shouldBe "{\"failures\":[{\"reason\":\"Start tax year after CY-1\",\"code\":\"63498\"}]}"
      println("Response Body is: " + response.body)
    }

    Scenario("Verify the request with Date of Birth Year >= 16 receives error response 400") {
      val response =
        niccService.makeRequest(
          Request("AA271213C", "2008-10-13", Some("e470d658-99f7-4292-a4a1-ed12c72f1337"), "2023", "2023")
        )
      response.status shouldBe 400
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body   shouldBe "{\"failures\":[{\"reason\":\"Constraint Violation - Invalid/Missing input parameter\",\"code\":\"400.1\"}]}"
      println("Response Body is: " + response.body)
    }

    Scenario("Verify 422 Unprocessable Entity response when calling nino BE699233A with incorrect start tax year ") {
      val response =
        niccService.makeRequest(
          Request("BE699233A", "1965-05-08", Some("e470d658-99f7-4292-a4a1-ed12c72f1337"), "1973", "1975")
        )
      response.status shouldBe 422
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body   shouldBe "{\"failures\":[{\"reason\":\"Start tax year before 1975\",\"code\":\"63497\"}]}"
      println("Response Body is: " + response.body)
    }

    Scenario("Verify 422 Unprocessable Entity response when calling nino AA123456C with invalid tax year range") {
      And("Validate the given NINO is greater than 16 years old")
      val d0b          = "1999-01-27"
      ValidateDOB(d0b)
      val startTaxYear = "1974"
      val year         = ValidateStartTaxYear(startTaxYear)
      println("valid year", year)
      val response     =
        niccService.makeRequest(
          Request("AA123456C", "1969-12-09", Some("e470d658-99f7-4292-a4a1-ed12c72f1337"), "1974", "2021")
        )
      response.status shouldBe 422
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body   shouldBe "{\"failures\":[{\"reason\":\"Tax year range greater than six years\",\"code\":\"63500\"}]}"
      println("Response Body is: " + response.body)
    }

  }
}
