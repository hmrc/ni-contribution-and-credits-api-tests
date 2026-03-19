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

package uk.gov.hmrc.api.specs.gysp_specs

import play.api.libs.json.*
import uk.gov.hmrc.api.models.common.*

class GYSPNegativeScenarios extends GYSPBaseSpec {

  Feature("Negative Test Scenarios for GYSP Benefit Type") {

    Scenario("GYSP_NTC001: A GYSP request where some downstreams fail returns 502 with partial failure") {
      Given("The Benefit Eligibility Info API is up and running")
      When("A request for GYSP is sent and NICC and LTB Calc return errors")

      val payloadKey = "GYSP_NTC001"
      val payload    = getPayload(payloadKey)
      val response   = gyspService.makeRequest(payload, payloadKey)
      val result     = Json.parse(response.body).as[DownstreamErrorResponse]

      Then("A 502 should be returned with partial failure content")
      response.status shouldBe 502

      assertDownstreamFailure(
        result = result,
        payload = payload,
        expectedStatus = "PARTIAL FAILURE",
        expectedTotalCalls = 7,
        expectedSuccessful = 5,
        expectedFailed = 2
      )

      val failedDownStreams = result.downStreams.filter(_.status == "FAILURE")
      failedDownStreams should have size 2
      (failedDownStreams.map(_.apiName) should contain).allOf(
        "NI Contributions and credits",
        "Long Term Benefit Calculation Details"
      )

      failedDownStreams.foreach { ds =>
        ds.apiName match {
          case "NI Contributions and credits" =>
            ds.error shouldBe defined
            ds.error.get.code shouldBe "ACCESS_FORBIDDEN"
            ds.error.get.downstreamStatus shouldBe 403

          case "Long Term Benefit Calculation Details" =>
            ds.error shouldBe defined
            ds.error.get.code shouldBe "BAD_REQUEST"
            ds.error.get.downstreamStatus shouldBe 400
        }
      }

      result.downStreams.filter(_.status == "SUCCESS") should have size 5

      printRawResponse(response)
    }

    Scenario("GYSP_NPS_ERROR_ALL_DOWNSTREAMS: A GYSP request where all downstreams fail returns 502") {
      Given("The Benefit Eligibility Info API is up and running")
      When("A request for GYSP is sent and all downstreams return errors")

      val payloadKey = "GYSP_NPS_ERROR_ALL_DOWNSTREAMS"
      val payload    = getPayload(payloadKey)
      val response   = gyspService.makeRequest(payload, payloadKey)
      val result     = Json.parse(response.body).as[DownstreamErrorResponse]

      Then("A 502 should be returned with all downstreams failed")
      response.status shouldBe 502

      assertDownstreamFailure(
        result = result,
        payload = payload,
        expectedStatus = "FAILURE",
        expectedTotalCalls = 5,
        expectedSuccessful = 0,
        expectedFailed = 5
      )

      result.downStreams should have size 5
      result.downStreams.foreach { ds =>
        ds.status shouldBe "FAILURE"
        ds.error.get shouldBe NpsNormalizedError(
          "BAD_REQUEST",
          "downstream received a malformed request",
          400
        )
      }

      (result.downStreams.map(_.apiName) should contain).allOf(
        "NI Contributions and credits",
        "Marriage Details",
        "Scheme Membership Details",
        "Long Term Benefit Calculation Details",
        "Individual State Pension Information"
      )

      printRawResponse(response)
    }

    Scenario("GYSP_NTC002: A GYSP request with an invalid NI number returns 422") {
      Given("The Benefit Eligibility Info API is up and running")
      When("A request for GYSP is sent with an invalid NI number")

      val payloadKey = "GYSP_NTC002"
      val payload    = getPayload(payloadKey)
      val response   = gyspService.makeRequest(payload, payloadKey)
      val json       = Json.parse(response.body)

      Then("A 422 should be returned with unprocessable entity error")
      response.status shouldBe 422
      assertErrorResponse(json, "UNPROCESSABLE_ENTITY", "invalid national insurance number format")

      printRawResponse(response)
    }

    Scenario("GYSP_NTC003: A GYSP request with a missing NI number returns 400") {
      Given("The Benefit Eligibility Info API is up and running")
      When("A request for GYSP is sent with a missing NI number")

      val payloadKey = "GYSP_NTC003"
      val payload    = getPayload(payloadKey)
      val response   = gyspService.makeRequest(payload, payloadKey)
      val json       = Json.parse(response.body)

      Then("A 400 should be returned with schema mismatch error")
      response.status shouldBe 400
      assertErrorResponse(json, "BAD_REQUEST", "incompatible json, request body does not match schema")

      printRawResponse(response)
    }

    Scenario(
      "GYSP_NTC004: A GYSP request where NICC downstream fail with 404 error and returns 502 with partial failure"
    ) {

      Given("The Benefit Eligibility Info API is up and running")
      When("A request for GYSP is sent and NICC fail with 404 error")

      val payloadKey = "GYSP_NTC004"
      val payload    = getPayload(payloadKey)
      val response   = gyspService.makeRequest(payload, payloadKey)
      val result     = Json.parse(response.body).as[DownstreamErrorResponse]

      Then("A 502 should be returned with partial failure content")
      response.status shouldBe 502

      val failedDownStreams = result.downStreams.filter(_.status == "FAILURE")
      failedDownStreams should have size 1
      (failedDownStreams.map(_.apiName) should contain).only(
        "NI Contributions and credits"
      )

      assertDownstreamFailure(
        result = result,
        payload = payload,
        expectedStatus = "PARTIAL FAILURE",
        expectedTotalCalls = 8,
        expectedSuccessful = 7,
        expectedFailed = 1
      )

      failedDownStreams.foreach { ds =>
        ds.apiName match {
          case "NI Contributions and credits" =>
            ds.error shouldBe defined
            ds.error.get.code shouldBe "NOT_FOUND"
            ds.error.get.downstreamStatus shouldBe 404
        }
      }

      result.downStreams.filter(_.status == "SUCCESS") should have size 7

      printRawResponse(response)
    }

  }

}
