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

class ErrorValidation_InvalidPayloadParameters extends BaseSpec with BaseHelper with BeforeAndAfterAll {

  val badRequestErrorResponse =
    "{\"failures\":[{\"reason\":\"There was a problem with the request\",\"code\":\"400\"}]}"

  var PayloadMapping: Map[String, Request] = _

  override def beforeAll(): Unit = {
    super.beforeAll()
    val jsonString = JsonUtils.readJsonFile("src/test/scala/uk/gov/hmrc/api/testData/TestData_N001_to_N010.json")
    PayloadMapping = JsonUtils.parseJsonToMap(jsonString) match {
      case Left(failure) => fail(s"Parsing failed: $failure")
      case Right(map)    => map
    }
  }

  Feature("VALIDATION OF ERROR CODES FOR INVALID INPUT") {

    Scenario("NICC_TC_N001 : Request with Invalid NINO receives error response 400 from MDTP") {
      val payload  = PayloadMapping.getOrElse("NICC_TC_N001", fail("NICC_TC_N001 not found"))
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
      response.body   shouldBe badRequestErrorResponse
      val responseBody = Json.parse(response.body)
      println("The Response Body is : \n" + Json.prettyPrint(Json.toJson(responseBody)))
    }

    Scenario("NICC_TC_N002: Verify the request with Date of Birth with invalid format receives error response 400") {
      val payload  = PayloadMapping.getOrElse("NICC_TC_N002", fail("NICC_TC_N002 not found"))
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
      response.body   shouldBe badRequestErrorResponse
      val responseBody = Json.parse(response.body)
      println("The Response Body is : \n" + Json.prettyPrint(Json.toJson(responseBody)))
    }

    Scenario("NICC_TC_N003: Verify the request with start tax year with invalid format receives error response 400") {
      val payload  = PayloadMapping.getOrElse("NICC_TC_N003", fail("NICC_TC_N003 not found"))
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
      response.body   shouldBe badRequestErrorResponse
      val responseBody = Json.parse(response.body)
      println("The Response Body is : \n" + Json.prettyPrint(Json.toJson(responseBody)))
    }

    Scenario("NICC_TC_N004: Verify the request with end tax year with invalid format receives error response 400") {
      val payload  = PayloadMapping.getOrElse("NICC_TC_N004", fail("NICC_TC_N004 not found"))
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
      response.body   shouldBe badRequestErrorResponse
      val responseBody = Json.parse(response.body)
      println("The Response Body is : \n" + Json.prettyPrint(Json.toJson(responseBody)))
    }

    Scenario("NICC_TC_N005: Verify the request with Start tax year after CY-1, receives error response 422") {
      val payload  = PayloadMapping.getOrElse("NICC_TC_N005", fail("NICC_TC_N005 not found"))
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
      response.status shouldBe 422
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body   shouldBe "{\"failures\":[{\"reason\":\"Start tax year after CY-1\",\"code\":\"63498\"}]}"
      println("Response Body is: " + response.body)
    }

    Scenario(
      "NICC_TC_N006: Verify the request with Tax year range greater than six years, receives error response 422"
    ) {
      val payload  = PayloadMapping.getOrElse("NICC_TC_N006", fail("NICC_TC_N006 not found"))
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
      response.status shouldBe 422
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body   shouldBe "{\"failures\":[{\"reason\":\"Tax year range greater than six years\",\"code\":\"63500\"}]}"
      val responseBody = Json.parse(response.body)
      println("The Response Body is : \n" + Json.prettyPrint(Json.toJson(responseBody)))
    }

    Scenario(
      "NICC_TC_N007: Verify the request with endTaxYear is 2024 as startTaxYear and endTaxYear cannot be this year 2024, receives error response 422"
    ) {
      val payload  = PayloadMapping.getOrElse("NICC_TC_N007", fail("NICC_TC_N007 not found"))
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
      response.status shouldBe 422
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body   shouldBe "{\"failures\":[{\"reason\":\"End tax year after CY-1\",\"code\":\"63499\"}]}"
      val responseBody = Json.parse(response.body)
      println("The Response Body is : \n" + Json.prettyPrint(Json.toJson(responseBody)))
    }

    Scenario("NICC_TC_N008: Verify the request with Date of Birth Year >= 16 receives error response 400") {
      val payload  = PayloadMapping.getOrElse("NICC_TC_N008", fail("NICC_TC_N008 not found"))
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
      response.body   shouldBe "{\"failures\":[{\"reason\":\"Constraint Violation - Invalid/Missing input parameter\",\"code\":\"400.1\"}]}"
      val responseBody = Json.parse(response.body)
      println("The Response Body is : \n" + Json.prettyPrint(Json.toJson(responseBody)))
    }

    Scenario(
      "NICC_TC_N009: Verify 422 Unprocessable Entity response when calling nino BE699233A with incorrect start tax year "
    ) {
      val payload  = PayloadMapping.getOrElse("NICC_TC_N009", fail("NICC_TC_N009 not found"))
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
      response.status shouldBe 422
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body   shouldBe "{\"failures\":[{\"reason\":\"Start tax year before 1975\",\"code\":\"63497\"}]}"
      val responseBody = Json.parse(response.body)
      println("The Response Body is : \n" + Json.prettyPrint(Json.toJson(responseBody)))
    }

    Scenario(
      "NICC_TC_N010: Verify 422 Unprocessable Entity response when calling nino AA123456C with invalid tax year range"
    ) {
      And("Validate the given NINO is greater than 16 years old")
      val payload = PayloadMapping.getOrElse("NICC_TC_N010", fail("NICC_TC_N010 not found"))
      ValidateDOB(payload.dateOfBirth)
      val dob     = ValidateStartTaxYear(payload.startTaxYear)
      println("valid year", dob)
      ValidateStartTaxYear(payload.startTaxYear)
      val year    = ValidateStartTaxYear(payload.startTaxYear)
      println("valid year", year)

      When("A request for NICC is sent")
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
      response.status shouldBe 422
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body   shouldBe "{\"failures\":[{\"reason\":\"Tax year range greater than six years\",\"code\":\"63500\"}]}"
      val responseBody = Json.parse(response.body)
      println("The Response Body is : \n" + Json.prettyPrint(Json.toJson(responseBody)))
    }

    Scenario(
      "NICC_TC_N011: Verify 404 Not Found response when calling nino AA123456C with invalid tax year range"
    ) {
      And("Validate the given NINO is greater than 16 years old")
      val payload = PayloadMapping.getOrElse("NICC_TC_N011", fail("NICC_TC_N011 not found"))
      ValidateDOB(payload.dateOfBirth)
      val dob     = ValidateStartTaxYear(payload.startTaxYear)
      println("valid year", dob)
      ValidateStartTaxYear(payload.startTaxYear)
      val year    = ValidateStartTaxYear(payload.startTaxYear)
      println("valid year", year)

      When("A request for NICC is sent")
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
      response.body   shouldBe "{\"failures\":[{\"reason\":\"Not Found\",\"code\":\"404\"}]}"
      val responseBody = Json.parse(response.body)
      println("The Response Body is : \n" + Json.prettyPrint(Json.toJson(responseBody)))
    }

  }
}
