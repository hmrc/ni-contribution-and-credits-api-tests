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

class GYSPNegativeScenarios extends GYSPBaseSpec {

  Feature("Negative Test Scenarios for GYSP Benefit Type") {

    Scenario("GYSP_NTC001: A GYSP request where some downstreams fail returns 500 with partial failure") {
      Given("The Benefit Eligibility Info API is up and running")
      When("A request for GYSP is sent and NICC and LTB Calc return errors")

      val payloadKey = "GYSP_NTC001"
      val payload    = getPayload(payloadKey)
      val response   = gyspService.makeRequest(payload)
      val jsonResult = Json.parse(response.body).as[JsObject]
      assertErrorResponse(jsonResult, "INTERNAL_SERVER_ERROR", "Unexpected internal failure")

      Then("A 500 should be returned with partial failure content")
      response.status shouldBe 500

      printRawResponse(response)
    }

    Scenario("GYSP_NTC002: A GYSP request with an invalid NI number returns 422") {
      Given("The Benefit Eligibility Info API is up and running")
      When("A request for GYSP is sent with an invalid NI number")

      val payloadKey = "GYSP_NTC002"
      val payload    = getPayload(payloadKey)
      val response   = gyspService.makeRequest(payload)
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
      val response   = gyspService.makeRequest(payload)
      val json       = Json.parse(response.body)

      Then("A 400 should be returned with schema mismatch error")
      response.status shouldBe 400
      assertErrorResponse(json, "BAD_REQUEST", "incompatible JSON, request body does not match schema")

      printRawResponse(response)
    }

    Scenario(
      "GYSP_NTC004: A GYSP request where NICC downstream fail with 404 error and returns 500 with partial failure"
    ) {

      Given("The Benefit Eligibility Info API is up and running")
      When("A request for GYSP is sent and NICC fail with 404 error")

      val payloadKey = "GYSP_NTC004"
      val payload    = getPayload(payloadKey)
      val response   = gyspService.makeRequest(payload)

      val jsonResult = Json.parse(response.body).as[JsObject]
      assertErrorResponse(jsonResult, "INTERNAL_SERVER_ERROR", "Unexpected internal failure")

      Then("A 500 should be returned with partial failure content")
      response.status shouldBe 500

      printRawResponse(response)
    }

    Scenario("GYSP_NTC005: A GYSP request where all downstreams fail returns 500") {
      Given("The Benefit Eligibility Info API is up and running")
      When("A request for GYSP is sent and all downstreams return errors")

      val payloadKey = "GYSP_NTC005"
      val payload    = getPayload(payloadKey)
      val response   = gyspService.makeRequest(payload)

      val jsonResult = Json.parse(response.body).as[JsObject]
      assertErrorResponse(jsonResult, "INTERNAL_SERVER_ERROR", "Unexpected internal failure")

      Then("A 500 should be returned with all downstreams failed")
      response.status shouldBe 500

      printRawResponse(response)
    }

  }

}
