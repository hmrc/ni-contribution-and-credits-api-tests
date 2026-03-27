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

import uk.gov.hmrc.api.models.esajsa.*

class EsaJsaPositiveScenarios extends EsaJsaBaseSpec {

  benefitTypes.foreach { benefitType =>
    Feature(s"Positive Test Scenarios for $benefitType Benefit Type") {

      Scenario(s"${benefitType}_PTC001: Retrieve Class 1 and Class 2 contributions for NINO with suffix") {
        Given(s"The Benefit Eligibility Info API is up and running for $benefitType")
        When(s"A valid request for $benefitType is sent with a NINO with suffix")

        val payloadKey         = s"${benefitType}_PTC001"
        val payload            = getPayload(payloadKey)
        val (response, result) = makeRequestAndParseResponse(payload)
        val contributions      = result.niContributionsAndCreditsResult

        Then("Class 1 and Class 2 contributions details should be returned")
        assertBasicResponseFields(result, payload, 200, response)
        assertClass1Contributions(contributions, "STANDARD RATE")
        assertClass2Contributions(contributions, "2B")

        printResponse(response, result)
      }

      Scenario(s"${benefitType}_PTC002: Retrieve Class 1 and Class 2 contributions for NINO without suffix") {
        Given(s"The Benefit Eligibility Info API is up and running for $benefitType")
        When(s"A valid request for $benefitType is sent with a NINO without suffix")

        val payloadKey         = s"${benefitType}_PTC002"
        val payload            = getPayload(payloadKey)
        val (response, result) = makeRequestAndParseResponse(payload)
        val contributions      = result.niContributionsAndCreditsResult

        Then("Class 1 and Class 2 contributions details should be returned")
        assertBasicResponseFields(result, payload, 200, response)
        assertClass1Contributions(contributions, "STANDARD RATE")
        assertClass2Contributions(contributions, "2B")

        printResponse(response, result)
      }

      Scenario(s"${benefitType}_PTC003: Retrieve only Class 2 contributions for given NINO") {
        Given(s"The Benefit Eligibility Info API is up and running for $benefitType")
        When(s"A valid request for $benefitType is sent for a self-employed claimant")

        val payloadKey         = s"${benefitType}_PTC003"
        val payload            = getPayload(payloadKey)
        val (response, result) = makeRequestAndParseResponse(payload)
        val contributions      = result.niContributionsAndCreditsResult

        Then("Only Class 2 contributions should be returned")
        assertBasicResponseFields(result, payload, 200, response)
        contributions.class1ContributionAndCredits shouldBe empty
        assertClass2Contributions(contributions, "C2")

        printResponse(response, result)
      }

      Scenario(s"${benefitType}_PTC005: Verify response with empty Class 1 and Class 2 contributions") {
        Given(s"The Benefit Eligibility Info API is up and running for $benefitType")
        When(s"A valid request for $benefitType is sent for a claimant with no contributions")

        val payloadKey         = s"${benefitType}_PTC005"
        val payload            = getPayload(payloadKey)
        val (response, result) = makeRequestAndParseResponse(payload)
        val contributions      = result.niContributionsAndCreditsResult

        Then("Class 1 and Class 2 contributions should both be empty")
        assertBasicResponseFields(result, payload, 200, response)
        assertEmptyContributions(contributions)

        printResponse(response, result)
      }

    }
  }

}
