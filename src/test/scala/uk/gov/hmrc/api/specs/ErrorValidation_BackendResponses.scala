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
import uk.gov.hmrc.api.models.Request
import uk.gov.hmrc.api.utils.JsonUtils

class ErrorValidation_BackendResponses extends BaseSpec with BaseHelper with BeforeAndAfterAll {

  var PayloadMapping: Map[String, Request] = _

  override def beforeAll(): Unit = {
    super.beforeAll()
    val jsonString = JsonUtils.readJsonFile("src/test/scala/uk/gov/hmrc/api/testData/TestData_B001_to_B011.json")
    PayloadMapping = JsonUtils.parseJsonToMap(jsonString) match {
      case Left(failure) => fail(s"Parsing failed: $failure")
      case Right(map)    => map
    }
  }

  Feature("VALIDATION OF ERROR CODES FOR BACKEND RESPONSES") {
    Scenario("NICC_TC_B001: Retrieve 400 Bad Request from Backend") {

      val payload = PayloadMapping.getOrElse("NICC_TC_B001", fail("NICC_TC_B001 not found"))

      val response =
        niccService.makeRequest(
          Request(
            payload.nationalInsuranceNumber,
            payload.dateOfBirth,
            payload.customerCorrelationID,
            payload.startTaxYear,
            payload.endTaxYear
          )
        )

      response.status shouldBe 400
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body shouldBe "{\"failures\":[{\"reason\":\"HTTP message not readable\",\"code\":\"\"},{\"reason\":\"Constraint Violation - Invalid/Missing input parameter\",\"code\":\"BAD_REQUEST\"}]}"
      val responseBody = Json.parse(response.body)
      println("The Response Body is : \n" + Json.prettyPrint(Json.toJson(responseBody)))
    }

    Scenario("NICC_TC_B002: Retrieve 400 Bad Request from Backend") {

      val payload = PayloadMapping.getOrElse("NICC_TC_B002", fail("NICC_TC_B002 not found"))

      val response =
        niccService.makeRequest(
          Request(
            payload.nationalInsuranceNumber,
            payload.dateOfBirth,
            payload.customerCorrelationID,
            payload.startTaxYear,
            payload.endTaxYear
          )
        )
      response.status shouldBe 400
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body shouldBe "{\"failures\":[{\"reason\":\"HTTP message not readable\",\"code\":\"\"},{\"reason\":\"Constraint Violation - Invalid/Missing input parameter\",\"code\":\"BAD_REQUEST\"}]}"
      val responseBody = Json.parse(response.body)
      println("The Response Body is : \n" + Json.prettyPrint(Json.toJson(responseBody)))
    }

    Scenario("NICC_TC_B003: Retrieve 404 Bad Request from Backend if NINO doesn't exist") {

      val payload = PayloadMapping.getOrElse("NICC_TC_B003", fail("NICC_TC_B003 not found"))

      val response =
        niccService.makeRequest(
          Request(
            payload.nationalInsuranceNumber,
            payload.dateOfBirth,
            payload.customerCorrelationID,
            payload.startTaxYear,
            payload.endTaxYear
          )
        )
      response.status shouldBe 404
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body shouldBe "{\"failures\":[{\"reason\":\"Not Found\",\"code\":\"404\"}]}"
      val responseBody = Json.parse(response.body)
      println("The Response Body is : \n" + Json.prettyPrint(Json.toJson(responseBody)))
    }

    Scenario("NICC_TC_B004: Retrieve 422 error response from backend if statTaxYear after endTaxYear") {
      Given("the NICC API is up and running")
      And("validate the given dob is greater than 16 years old")
      val payload = PayloadMapping.getOrElse("NICC_TC_B004", fail("NICC_TC_B004 not found"))
      ValidateDOB(payload.dateOfBirth)
      ValidateStartTaxYear(payload.startTaxYear)

      When("user sends a POST request to retrieve niClass details for a start tax year after end tax year")

      val response =
        niccService.makeRequest(
          Request(
            payload.nationalInsuranceNumber,
            payload.dateOfBirth,
            payload.customerCorrelationID,
            payload.startTaxYear,
            payload.endTaxYear
          )
        )

      Then("the error response should be 422")
      response.status shouldBe 422
      checkResponseStatus(response.status, 422)

      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body shouldBe "{\"failures\":[{\"reason\":\"Start tax year after end tax year\",\"code\":\"63496\"}]}"
      val responseBody = Json.parse(response.body)
      println("the Response Body is : \n" + Json.prettyPrint(Json.toJson(responseBody)))

    }

    Scenario("NICC_TC_B005: Request receives 500 error response from backend in response to 403") {

      val payload = PayloadMapping.getOrElse("NICC_TC_B005", fail("NICC_TC_B005 not found"))

      val response =
        niccService.makeRequest(
          Request(
            payload.nationalInsuranceNumber,
            payload.dateOfBirth,
            payload.customerCorrelationID,
            payload.startTaxYear,
            payload.endTaxYear
          )
        )
      response.status shouldBe 500
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body shouldBe ""
    }

    Scenario("NICC_TC_B006: Request receives 500 error response from API when backend times out") {

      val payload = PayloadMapping.getOrElse("NICC_TC_B006", fail("NICC_TC_B006 not found"))

      val response =
        niccService.makeRequest(
          Request(
            payload.nationalInsuranceNumber,
            payload.dateOfBirth,
            payload.customerCorrelationID,
            payload.startTaxYear,
            payload.endTaxYear
          ),
          15
        )
      response.status shouldBe 500
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body shouldBe ""
    }

    Scenario(
      "NICC_TC_B007: Request receives 404 error response from backend for the incorrect dob to the given NINO "
    ) {

      val payload = PayloadMapping.getOrElse("NICC_TC_B007", fail("NICC_TC_B007 not found"))

      val response =
        niccService.makeRequest(
          Request(
            payload.nationalInsuranceNumber,
            payload.dateOfBirth,
            payload.customerCorrelationID,
            payload.startTaxYear,
            payload.endTaxYear
          )
        )
      response.status shouldBe 404
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body shouldBe "{\"failures\":[{\"reason\":\"Not Found\",\"code\":\"404\"}]}"
      val responsebody = Json.parse(response.body)
      println("The Response Body is : \n" + Json.prettyPrint(responsebody))
    }

    Scenario(
      "NICC_TC_B008: Request receives 404 error response from backend for given NINO is NY634367C and incorrect dob "
    ) {

      val payload = PayloadMapping.getOrElse("NICC_TC_B008", fail("NICC_TC_B008 not found"))

      val response =
        niccService.makeRequest(
          Request(
            payload.nationalInsuranceNumber,
            payload.dateOfBirth,
            payload.customerCorrelationID,
            payload.startTaxYear,
            payload.endTaxYear
          )
        )
      response.status shouldBe 404
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body shouldBe "{\"failures\":[{\"reason\":\"Not Found\",\"code\":\"404\"}]}"
      val responsebody = Json.parse(response.body)
      println("The Response Body is : \n" + Json.prettyPrint(responsebody))
    }

    Scenario(
      "NICC_TC_B009: Request receives 404 error response from backend for given NINO is WP103133 and incorrect dob "
    ) {

      val payload = PayloadMapping.getOrElse("NICC_TC_B009", fail("NICC_TC_B009 not found"))

      val response =
        niccService.makeRequest(
          Request(
            payload.nationalInsuranceNumber,
            payload.dateOfBirth,
            payload.customerCorrelationID,
            payload.startTaxYear,
            payload.endTaxYear
          )
        )
      response.status shouldBe 404
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body shouldBe "{\"failures\":[{\"reason\":\"Not Found\",\"code\":\"404\"}]}"
      val responsebody = Json.parse(response.body)
      println("The Response Body is : \n" + Json.prettyPrint(responsebody))
    }

    Scenario(
      "NICC_TC_B010: Request receives 404 error response from backend for given NINO is AA271213 and incorrect dob "
    ) {

      val payload = PayloadMapping.getOrElse("NICC_TC_B010", fail("NICC_TC_B010 not found"))

      val response =
        niccService.makeRequest(
          Request(
            payload.nationalInsuranceNumber,
            payload.dateOfBirth,
            payload.customerCorrelationID,
            payload.startTaxYear,
            payload.endTaxYear
          )
        )
      response.status shouldBe 404
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body shouldBe "{\"failures\":[{\"reason\":\"Not Found\",\"code\":\"404\"}]}"
      val responsebody = Json.parse(response.body)
      println("The Response Body is : \n" + Json.prettyPrint(responsebody))
    }

    Scenario("NICC_TC_B011: Request receives 500 error response from backend for given NINO is AA271213  ") {

      val payload = PayloadMapping.getOrElse("NICC_TC_B011", fail("NICC_TC_B011 not found"))

      val response =
        niccService.makeRequest(
          Request(
            payload.nationalInsuranceNumber,
            payload.dateOfBirth,
            payload.customerCorrelationID,
            payload.startTaxYear,
            payload.endTaxYear
          )
        )

      Then("the error response should be 500")
      response.status shouldBe 500
      println("Response Status Code is : " + response.status + " " + response.statusText)

      response.statusText shouldBe "Internal Server Error"

      And("response header should consist of correlation ID")
      // val correlationID = response.headers.get("correlationid")
      response.headers.get("correlationid") should not be empty

    }
  }

}
