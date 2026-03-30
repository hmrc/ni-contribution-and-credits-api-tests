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

package uk.gov.hmrc.api.specs.esajsa_specs

import play.api.libs.json.*
import uk.gov.hmrc.api.models.common.{DownstreamErrorResponse, NpsNormalizedError}

class EsaJsaNegativeScenarios extends EsaJsaBaseSpec {

  benefitTypes.foreach { benefitType =>
    Feature(s"Negative Test Scenarios for $benefitType Benefit Type") {

      Scenario(s"${benefitType}_NTC001: Request with missing CorrelationID header returns 400") {
        Given(s"The Benefit Eligibility Info API is up and running for $benefitType")
        When(s"A request for $benefitType is sent without a CorrelationID header")

        val payloadKey = s"${benefitType}_NTC001"
        val payload    = getPayload(payloadKey)
        val response   = esaJsaService.makeRequestWithoutCorrelationId(payload)
        val json       = Json.parse(response.body)

        Then("The API should return 400 with missing header error")
        response.status shouldBe 400
        assertErrorResponse(json, "BAD_REQUEST", "Missing Header CorrelationId")

        printRawResponse(response)
      }

      Scenario(s"${benefitType}_NTC002: Request receives 502 when downstream returns 403") {
        Given(s"The Benefit Eligibility Info API is up and running for $benefitType")
        When(s"A request for $benefitType is sent and downstream returns 403")

        val payloadKey = s"${benefitType}_NTC002"
        val payload    = getPayload(payloadKey)
        val response   = esaJsaService.makeRequest(payload)
        val result     = Json.parse(response.body).as[DownstreamErrorResponse]

        Then("The API should return 502 with downstream failure details")
        response.status shouldBe 502
        result.status shouldBe "FAILURE"
        result.benefitType shouldBe payload.benefitType
        result.nationalInsuranceNumber shouldBe payload.nationalInsuranceNumber
        result.downStreams.head.status shouldBe "FAILURE"
        result.downStreams.head.error.head shouldBe NpsNormalizedError(
          "ACCESS_FORBIDDEN",
          "downstream resource cannot be accessed by the calling client",
          403
        )

        printRawResponse(response)
      }

      Scenario(s"${benefitType}_NTC003: Request with missing authorisation returns 403") {
        Given(s"The Benefit Eligibility Info API is up and running for $benefitType")
        When(s"A request for $benefitType is sent without a bearer token")

        val payloadKey = s"${benefitType}_NTC003"
        val payload    = getPayload(payloadKey)
        val response   = esaJsaService.makeRequestWithoutBearerToken(payload)
        val json       = Json.parse(response.body)

        Then("The API should return 403 with forbidden error")
        response.status shouldBe 403
        assertErrorResponse(json, "FORBIDDEN", "Bearer token not supplied")

        printRawResponse(response)
      }

      Scenario(s"${benefitType}_NTC004: Request with invalid NI number returns 422") {
        Given(s"The Benefit Eligibility Info API is up and running for $benefitType")
        When(s"A request for $benefitType is sent with an invalid NI number")

        val payloadKey = s"${benefitType}_NTC004"
        val payload    = getPayload(payloadKey)
        val response   = esaJsaService.makeRequest(payload)
        val json       = Json.parse(response.body)

        Then("The API should return 422 with unprocessable entity error")
        response.status shouldBe 422
        assertErrorResponse(json, "UNPROCESSABLE_ENTITY", "invalid national insurance number format")

        printRawResponse(response)
      }

      Scenario(s"${benefitType}_NTC005: Request with invalid date of birth returns 400") {
        Given(s"The Benefit Eligibility Info API is up and running for $benefitType")
        When(s"A request for $benefitType is sent with an invalid date of birth")

        val payloadKey = s"${benefitType}_NTC005"
        val payload    = getPayload(payloadKey)
        val response   = esaJsaService.makeRequest(payload)
        val json       = Json.parse(response.body)

        Then("The API should return 400 with schema mismatch error")
        response.status shouldBe 400
        assertErrorResponse(json, "BAD_REQUEST", "incompatible json, request body does not match schema")

        printRawResponse(response)
      }

      Scenario(s"${benefitType}_NTC006: Request with invalid start tax year format returns 422") {
        Given(s"The Benefit Eligibility Info API is up and running for $benefitType")
        When(s"A request for $benefitType is sent with a start tax year before 1975")

        val payloadKey = s"${benefitType}_NTC006"
        val payload    = getPayload(payloadKey)
        val response   = esaJsaService.makeRequest(payload)
        val json       = Json.parse(response.body)

        Then("The API should return 422 with invalid tax year error")
        response.status shouldBe 422
        assertErrorResponse(json, "UNPROCESSABLE_ENTITY", "Start tax year before 1975")

        printRawResponse(response)
      }

      Scenario(s"${benefitType}_NTC007: Request with multiple invalid fields returns 422") {
        Given(s"The Benefit Eligibility Info API is up and running for $benefitType")
        When(s"A request for $benefitType is sent with multiple invalid fields")

        val payloadKey = s"${benefitType}_NTC007"
        val payload    = getPayload(payloadKey)
        val response   = esaJsaService.makeRequest(payload)
        val json       = Json.parse(response.body)

        Then("The API should return 422 with multiple validation errors")
        response.status shouldBe 422
        assertErrorResponse(
          json,
          "UNPROCESSABLE_ENTITY",
          "Start tax year after CY-1,End tax year after CY-1,Start tax year after end tax year"
        )

        printRawResponse(response)
      }

      Scenario(s"${benefitType}_NTC008: Request with invalid bearer token returns 403") {
        Given(s"The Benefit Eligibility Info API is up and running for $benefitType")
        When(s"A request for $benefitType is sent without a bearer token")

        val payloadKey = s"${benefitType}_NTC008"
        val payload    = getPayload(payloadKey)
        val response   = esaJsaService.makeRequestWithInvalidBearerToken(payload)
        val json       = Json.parse(response.body)

        Then("The API should return 403 with forbidden error")
        response.status shouldBe 403
        assertErrorResponse(json, "FORBIDDEN", "Invalid bearer token")

        printRawResponse(response)
      }

      Scenario(s"${benefitType}_NTC011: Request with missing benefit type returns 400") {
        Given(s"The Benefit Eligibility Info API is up and running for $benefitType")
        When(s"A request for $benefitType is sent with missing benefit type")

        val payload: JsValue = Json.parse(
          """
                  {
                    "nationalInsuranceNumber": "AA000012",
                    "niContributionsAndCredits": {
                      "dateOfBirth": "2000-01-10",
                      "startTaxYear": 2026,
                      "endTaxYear": 2026
                    }
                  }
                """
        )
        val response = esaJsaService.makeRawRequest(payload)
        val json     = Json.parse(response.body)

        Then("The API should return 400 with schema mismatch error")
        response.status shouldBe 400
        assertErrorResponse(json, "BAD_REQUEST", "incompatible json, request body does not match schema")

        printRawResponse(response)
      }

    }
  }

}
