/*
 * Copyright 2026 HM Revenue & Customs
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

package uk.gov.hmrc.api.specs.ma_specs

import play.api.libs.json.*

class MAScenarios extends MABaseSpec {

  Feature("Test Scenarios for MA Benefit Type") {

    Scenario("MA_PTC001: Verify full MA response body for a valid NINO with suffix") {
      Given("The Benefit Eligibility Info API is up and running for MA")
      When("A valid request for MA is sent with a NINO with suffix")

      val payloadKey         = "MA_PTC001"
      val payload            = getPayload(payloadKey)
      val (response, result) = makeAndParseRequest(payload, payloadKey)

      Then("All major response sections should contain valid data")
      assertMAResponse(payload, response)

      printResponse(response, result)
    }

    Scenario("MA_PTC002: Verify full MA response body for a valid NINO without suffix") {
      Given("The Benefit Eligibility Info API is up and running for MA")
      When("A valid request for MA is sent with a NINO without suffix")

      val payloadKey         = "MA_PTC002"
      val payload            = getPayload(payloadKey)
      val (response, result) = makeAndParseRequest(payload, payloadKey)

      Then("All major response sections should contain valid data")
      assertMAResponse(payload, response)

      printResponse(response, result)
    }

    Scenario("MA_PTC003: Verify MA partial failure response when some downstream services return errors") {
      Given("The Benefit Eligibility Info API is up and running for MA")
      When("A request for MA is sent and Class2MAReceipts and LiabilitySummary return errors")

      val payloadKey   = "MA_PTC003"
      val payload      = getPayload(payloadKey)
      val response     = maService.makeRequest(payload)
      val responseBody = Json.parse(response.body)

      Then("A 502 should be returned with partial failure content")
      response.status shouldBe 502

      And("The response should contain both SUCCESS and FAILURE downstream statuses")
      assertPartialFailureResponse(responseBody)

      printRawResponse(response)
    }

    Scenario("MA_PTC004: Verify MA complete failure response when all downstream services return errors") {
      Given("The Benefit Eligibility Info API is up and running for MA")
      When("A request for MA is sent and all downstream services return errors")

      val payloadKey   = "MA_PTC004"
      val payload      = getPayload(payloadKey)
      val response     = maService.makeRequest(payload)
      val responseBody = Json.parse(response.body)

      Then("A 502 should be returned indicating complete downstream failure")
      response.status shouldBe 502

      And("All downstream services should have failed")
      assertCompleteFailureResponse(responseBody)

      printRawResponse(response)
    }

    Scenario("MA_PTC005: Verify API validation failure when required liability fields are empty") {
      Given("The Benefit Eligibility Info API is up and running for MA")
      When("A request for MA is sent with empty searchCategories in liabilities")

      val payloadKey = "MA_PTC005"
      val payload    = getPayload(payloadKey)
      val response   = maService.makeRequest(payload)
      val json       = Json.parse(response.body)

      Then("A 400 should be returned indicating request validation failure")
      response.status shouldBe 400
      assertErrorResponse(json, "BAD_REQUEST", "incompatible json, request body does not match schema")

      printRawResponse(response)
    }

    ignore("MA_PTC006: Verify API validation failure when using invalid liability field") {
      Given("The Benefit Eligibility Info API is up and running for MA")
      When("A request for MA is sent with invalid searchCategories entry")

      val payloadKey = "MA_PTC006"
      val payload    = getPayload(payloadKey)
      val response   = maService.makeRequest(payload)
      val json       = Json.parse(response.body)

      Then("A 422 should be returned indicating request validation failure")
      response.status shouldBe 422
      assertErrorResponse(json, "UNPROCESSABLE_ENTITY", "Missing Header CorrelationId")

      printRawResponse(response)
    }
  }

}
