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

package uk.gov.hmrc.api.specs.searchlight_specs

import play.api.libs.json.{JsArray, Json}

class SEARCHLIGHTScenarios extends SEARCHLIGHTBaseSpec {

  Feature(s"Test Scenarios for SEARCHLIGHT Benefit Type") {

    Scenario("SEARCHLIGHT_PTC001: Verify full SEARCHLIGHT response body for a valid NINO with suffix") {
      Given("The Benefit Eligibility Info API is up and running for SEARCHLIGHT")
      When("A valid request for SEARCHLIGHT is sent with a NINO with suffix")

      val payloadKey         = "SEARCHLIGHT_PTC001"
      val payload            = getPayload(payloadKey)
      val (response, result) = makeAndParseRequest(payload)

      Then("All major response sections should contain valid data")
      assertSEARCHLIGHTResponse(payload, response)

      printResponse(response, result)
    }

    Scenario(s"SEARCHLIGHT_PTC002: Verify full SEARCHLIGHT response body for a valid NINO without Suffix") {

      Given("The Benefit eligibility Info API is up and running for SEARCHLIGHT")
      When("A request for SEARCHLIGHT is sent")

      val payloadKey         = "SEARCHLIGHT_PTC002"
      val payload            = getPayload(payloadKey)
      val (response, result) = makeAndParseRequest(payload)

      Then("All major response sections should contain valid data")
      assertSEARCHLIGHTResponse(payload, response)

      printResponse(response, result)
    }

    Scenario(
      "SEARCHLIGHT_PTC003: Verify SEARCHLIGHT complete failure response when all downstream services return errors"
    ) {

      Given("The Benefit Eligibility Info API is up and running for SEARCHLIGHT")
      When("A request for SEARCHLIGHT is sent and all downstream services return errors")

      val payloadKey   = "SEARCHLIGHT_PTC003"
      val payload      = getPayload(payloadKey)
      val response     = searchlightService.makeRequest(payload)
      val responseBody = Json.parse(response.body)

      Then("A 500 should be returned indicating complete downstream failure")
      response.status shouldBe 500

      And("All downstream services should have failed")
      (responseBody \ "status").as[String] shouldBe "FAILURE"
      (responseBody \ "downStreams").as[JsArray].value.foreach { downstream =>
        (downstream \ "status").as[String] shouldBe "FAILURE"
      }

      printRawResponse(response)
    }

    Scenario("SEARCHLIGHT_PTC004: Verify API validation failure when required NICC details field is empty") {
      Given("The Benefit Eligibility Info API is up and running for SEARCHLIGHT")
      When("A request for SEARCHLIGHT is sent with empty dateOfBirth in NICC")

      val payloadKey = "SEARCHLIGHT_PTC004"
      val payload    = getPayload(payloadKey)
      val response   = searchlightService.makeRequest(payload)
      val json       = Json.parse(response.body)

      Then("A 400 status should be returned indicating request validation failure")
      response.status shouldBe 400
      assertErrorResponse(json, "BAD_REQUEST", "incompatible json, request body does not match schema")

      printRawResponse(response)
    }

    Scenario("SEARCHLIGHT_PTC005: Verify API validation failure when using invalid NICC field") {
      Given("The Benefit Eligibility Info API is up and running for SEARCHLIGHT")
      When("A request for SEARCHLIGHT is sent with invalid dateOfBirth entry")

      val payloadKey = s"SEARCHLIGHT_PTC005"
      val payload    = getPayload(payloadKey)
      val response   = searchlightService.makeRequest(payload)
      val json       = Json.parse(response.body)

      Then("A 422 should be returned indicating request validation failure")
      response.status shouldBe 400
      assertErrorResponse(json, "BAD_REQUEST", "incompatible json, request body does not match schema")

      printRawResponse(response)
    }

    Scenario("SEARCHLIGHT_PTC006: Verify when tax year over 6 years for SEARCHLIGHT, then it should not return 422") {
      Given("The Benefit Eligibility Info API is up and running for SEARCHLIGHT")
      When("A request for SEARCHLIGHT is sent with tax year over 6 years")

      val payloadKey = "SEARCHLIGHT_PTC006"
      val payload    = getPayload(payloadKey)
      val response   = searchlightService.makeRequest(payload)

      Then("Response code 422 should NOT be returned indicating request validation failure")
      response.status shouldBe 200

      printRawResponse(response)
    }
  }

}
