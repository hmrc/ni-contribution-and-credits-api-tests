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

import uk.gov.hmrc.api.models.gysp.*

class GYSPPositiveScenarios extends GYSPBaseSpec {

  Feature("Positive Test Scenarios for GYSP Benefit Type") {

    Scenario("GYSP_PTC001: Verify full GYSP response body for a valid NINO with suffix") {
      Given("The Benefit Eligibility Info API is up and running for GYSP")
      When("A valid request for GYSP is sent with a NINO with suffix")

      val payloadKey         = "GYSP_PTC001"
      val payload            = getPayload(payloadKey)
      val (response, result) = makeRequestAndParseResponse(payload, payloadKey)

      Then("All downstream API response sections should be present")
      assertBasicResponseFields(result, payload, 200, response)
      assertGYSPResponseHasSectionsFromAllDownstreamAPIs(payload, result)

      And("All downstream API response sections should have required fields")
      assertRequiredFieldsInMarriageDetails(result.marriageDetailsResult.marriageDetails)

      printResponse(response, result)
    }

    Scenario("GYSP_PTC002: Verify full GYSP response body for a valid NINO without suffix") {
      Given("The Benefit Eligibility Info API is up and running for GYSP")
      When("A valid request for GYSP is sent with a NINO without suffix")

      val payloadKey         = "GYSP_PTC002"
      val payload            = getPayload(payloadKey)
      val (response, result) = makeRequestAndParseResponse(payload, payloadKey)

      Then("All downstream API response sections should be present")
      assertBasicResponseFields(result, payload, 200, response)
      assertGYSPResponseHasSectionsFromAllDownstreamAPIs(payload, result)

      And("All downstream API response sections should have required fields")
      assertRequiredFieldsInMarriageDetails(result.marriageDetailsResult.marriageDetails)

      printResponse(response, result)
    }

    Scenario("GYSP_PTC003: Verify response having only Class 2 contributions for given valid NINO") {
      Given("The Benefit Eligibility Info API is up and running for GYSP")
      When("A valid request for GYSP is sent for a self-employed claimant")

      val payloadKey         = "GYSP_PTC003"
      val payload            = getPayload(payloadKey)
      val (response, result) = makeRequestAndParseResponse(payload, payloadKey)
      val contributions      = result.niContributionsAndCreditsResult

      Then("All downstream API response sections should be present")
      assertBasicResponseFields(result, payload, 200, response)
      assertGYSPResponseHasSectionsFromAllDownstreamAPIs(payload, result)

      And("Only Class 2 contributions should be returned")
      contributions.class1ContributionAndCredits shouldBe empty
      assertClass2Contributions(contributions, "C2")

      printResponse(response, result)
    }

    Scenario("GYSP_PTC004: Verify minimal GYSP response body returns all empty sections") {
      Given("The Benefit Eligibility Info API is up and running for GYSP")
      When("A valid request for GYSP is sent for a claimant with no records")

      val payloadKey         = "GYSP_PTC004"
      val payload            = getPayload(payloadKey)
      val (response, result) = makeRequestAndParseResponse(payload, payloadKey)

      Then("All response sections should be empty")
      assertBasicResponseFields(result, payload, 200, response)
      assertEmptyResponse(result, payload)

      printResponse(response, result)
    }
  }

}
