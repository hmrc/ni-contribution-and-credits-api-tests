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

package uk.gov.hmrc.api.specs.bsp_specs

import play.api.libs.json.*
import uk.gov.hmrc.api.models.common.DownstreamErrorResponse

//import uk.gov.hmrc.api.service.BSPService
//import uk.gov.hmrc.api.specs.BaseSpec
//import uk.gov.hmrc.api.utils.JsonUtils

class BSPScenarios extends BSPBaseSpec {

  Feature(s"Test Scenarios for BSP Benefit Type") {

    Scenario("BSP_PTC001: Verify full BSP response body for a valid NINO with suffix") {
      Given("The Benefit Eligibility Info API is up and running for BSP")
      When("A valid request for BSP is sent with a NINO with suffix")

      val payloadKey         = "BSP_PTC001"
      val payload            = getPayload(payloadKey)
      val (response, result) = makeAndParseRequest(payload)

      Then("All major response sections should contain valid data")
      assertBSPResponse(payload, response)

      printResponse(response, result)
    }

    Scenario(s"BSP_PTC002: Verify full BSP response body for a valid NINO without Suffix") {

      Given("The Benefit eligibility Info API is up and running for BSP")
      When("A request for BSP is sent")

      val payloadKey         = "BSP_PTC002"
      val payload            = getPayload(payloadKey)
      val (response, result) = makeAndParseRequest(payload)

      Then("All major response sections should contain valid data")
      assertBSPResponse(payload, response)

      printResponse(response, result)
    }

    Scenario("BSP_PTC003: Verify BSP partial failure response when some downstream services return errors") {
      Given("The Benefit Eligibility Info API is up and running for BSP")
      When("A request for BSP is sent and some downstream services return errors")

      val payloadKey = "BSP_PTC003"
      val payload    = getPayload(payloadKey)
      val response   = bspService.makeRequest(payload)
      val result     = Json.parse(response.body).as[DownstreamErrorResponse]

      Then("A 502 should be returned with partial failure content")
      response.status shouldBe 502

      assertDownstreamFailure(
        result = result,
        payload = payload,
        expectedStatus = "PARTIAL FAILURE",
        expectedTotalCalls = 2,
        expectedSuccessful = 1,
        expectedFailed = 1
      )

      val failedDownStreams = result.downStreams.filter(_.status == "FAILURE")
      failedDownStreams should have size 1
      failedDownStreams.map(_.apiName) should contain("Marriage Details")

      failedDownStreams.foreach { ds =>
        ds.apiName match {
          case "Marriage Details" =>
            ds.error shouldBe defined
            ds.error.get.code shouldBe "BAD_REQUEST"
            ds.error.get.downstreamStatus shouldBe 400
        }
      }

      val successfulDownStreams = result.downStreams.filter(_.status == "SUCCESS")
      successfulDownStreams should have size 1
      successfulDownStreams.map(_.apiName) should contain("NI Contributions and credits")

      printRawResponse(response)
    }

    Scenario("BSP_PTC004: Verify BSP complete failure response when all downstream services return errors") {

      Given("The Benefit Eligibility Info API is up and running for BSP")
      When("A request for BSP is sent and all downstream services return errors")

      val payloadKey   = "BSP_PTC004"
      val payload      = getPayload(payloadKey)
      val response     = bspService.makeRequest(payload)
      val responseBody = Json.parse(response.body)

      Then("A 502 should be returned indicating complete downstream failure")
      response.status shouldBe 502

      And("All downstream services should have failed")
      (responseBody \ "status").as[String] shouldBe "FAILURE"
      (responseBody \ "downStreams").as[JsArray].value.foreach { downstream =>
        (downstream \ "status").as[String] shouldBe "FAILURE"
      }

      printRawResponse(response)
    }

    Scenario("BSP_PTC005: Verify API validation failure when required NICC details field is empty") {
      Given("The Benefit Eligibility Info API is up and running for BSP")
      When("A request for BSP is sent with empty dateOfBirth in NICC")

      val payloadKey = "BSP_PTC005"
      val payload    = getPayload(payloadKey)
      val response   = bspService.makeRequest(payload)
      val json       = Json.parse(response.body)

      Then("A 400 status should be returned indicating request validation failure")
      response.status shouldBe 400
      assertErrorResponse(json, "BAD_REQUEST", "incompatible json, request body does not match schema")

      printRawResponse(response)
    }

    Scenario("BSP_PTC006: Verify API validation failure when using invalid NICC field") {
      Given("The Benefit Eligibility Info API is up and running for BSP")
      When("A request for BSP is sent with invalid dateOfBirth entry")

      val payloadKey = s"BSP_PTC006"
      val payload    = getPayload(payloadKey)
      val response   = bspService.makeRequest(payload)
      val json       = Json.parse(response.body)

      Then("A 422 should be returned indicating request validation failure")
      response.status shouldBe 400
      assertErrorResponse(json, "BAD_REQUEST", "incompatible json, request body does not match schema")

      printRawResponse(response)
    }

    Scenario("BSP_PTC007: Verify when tax year over 6 years for BSP, then it should not return 422") {
      Given("The Benefit Eligibility Info API is up and running for BSP")
      When("A request for BSP is sent with tax year over 6 years")

      val payloadKey = "BSP_PTC007"
      val payload    = getPayload(payloadKey)
      val response   = bspService.makeRequest(payload)
      val json       = Json.parse(response.body)

      Then("Response code 422 should NOT be returned indicating request validation failure")
      response.status shouldBe 200

      printRawResponse(response)
    }
  }

}
