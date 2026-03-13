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

/*
 * Copyright 2026 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */

package uk.gov.hmrc.api.specs.gysp_specs

import org.scalatest.Ignore
import play.api.libs.json.Json
import uk.gov.hmrc.api.models.common.*
import uk.gov.hmrc.api.models.gysp.{FilteredMarriageDetailsItem, GYSPRequest, GYSPResponse}

@Ignore
class GYSPScenarios extends GYSPBaseSpec {

  Feature(s"Test Scenarios for GYSP Benefit Type") {

    Scenario(s"GYSP_PTC001: Verify full GYSP response body for a valid NINO with Suffix") {

      Given("The Benefit eligibility Info API is up and running for GYSP")

      When("A request for GYSP is sent")

      val payloadKey = "GYSP_PTC001"
      val payload    = PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))

      val response = gyspService.makeRequest(payload, payloadKey)

      response.status shouldBe 200

      val result: GYSPResponse = Json.parse(response.body).as[GYSPResponse]

      Then("All downstream APIs response sections should be present")
      assertGYSPResponseHasSectionsFromAllDownstreamAPIs(payload, result)

      And("All downstream APIs response sections should have required fields")
      assertRequiredFieldsInMarriageDetails(result.marriageDetailsResult.marriageDetails)

      println(s"Response Status: ${response.status} ${response.statusText}")
      println(Json.prettyPrint(Json.toJson(result)))
    }

    Scenario(s"GYSP_PTC002: Verify full GYSP response body for a valid NINO without suffix") {
      Given(s"The Benefit eligibility Info API is up and running for GYSP")
      When(s"A request for GYSP is sent")
      // Get test payload
      val payloadKey = s"GYSP_PTC002"
      val payload    = PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))
      println(payload)
      // Make API call and build the request
      val response = gyspService.makeRequest(payload, payloadKey)

      response.status shouldBe 200

      val result: GYSPResponse = Json.parse(response.body).as[GYSPResponse]

      Then("All downstream APIs response sections should be present")
      assertGYSPResponseHasSectionsFromAllDownstreamAPIs(payload, result)

      And("All downstream APIs response sections should have required fields")
      assertRequiredFieldsInMarriageDetails(result.marriageDetailsResult.marriageDetails)

      // Print response details
      println(s"The Response Status Code is : ${response.status} ${response.statusText}")
      println(s"""The Response Body is :
                      ${Json.prettyPrint(Json.toJson(result))}""")
    }

    Scenario(s"GYSP_PTC003: Verify response having only Class 2 contributions for given valid NINO") {
      Given(s"The Benefit eligibility Info API is up and running for GYSP")
      When(s"A request for GYSP is sent")
      // Get test payload
      val payloadKey = s"GYSP_PTC003"
      val payload    = PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))
      println(payload)
      // Make API call and build the request
      val response = gyspService.makeRequest(payload, payloadKey)

      response.status shouldBe 200
      // Parse JSON into case class
      val result: GYSPResponse = Json.parse(response.body).as[GYSPResponse]
      Then("All downstream APIs response sections should be present")
      assertGYSPResponseHasSectionsFromAllDownstreamAPIs(payload, result)

      val contributions = result.niContributionsAndCreditsResult

      // Basic response checks
      result.benefitType shouldBe payload.benefitType
      result.nationalInsuranceNumber shouldBe payload.nationalInsuranceNumber

      And("Only Class 2 contributions should be returned")
      // Class 1 should be empty
      contributions.class1ContributionAndCredits shouldBe empty
      // Class 2: must exist, not empty, and contain "c2"
      contributions.class2ContributionAndCredits match {
        case Some(list) =>
          list should not be empty
          list.exists(_.contributionCreditType == "C2") shouldBe true
        case None => fail("Class 2 contributions are missing in the response")
      }
      // Print response details
      println(s"The Response Status Code is : ${response.status} ${response.statusText}")
      println(s"""The Response Body is :
                          ${Json.prettyPrint(Json.toJson(result))}""")
    }

    Scenario(s"GYSP_PTC005: Verify minimal GYSP response body") {
      Given(s"The Benefit eligibility Info API is up and running for GYSP")
      When(s"A request for GYSP is sent")
      // Get test payload
      val payloadKey = s"GYSP_PTC005"
      val payload    = PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))
      println(payload)
      // Make API call and build the request
      val response = gyspService.makeRequest(payload, payloadKey)

      response.status shouldBe 200
      // Parse JSON into case class
      println(response.body)
      val result: GYSPResponse = Json.parse(response.body).as[GYSPResponse]

      val marriageDetailsResult                   = result.marriageDetailsResult
      val longTermBenefitCalculationDetailsResult = result.longTermBenefitCalculationDetailsResult
      val schemeMembershipDetailsResult           = result.schemeMembershipDetailsResult
      val individualStatePensionInfoResult        = result.individualStatePensionInfoResult
      val contributionsAndCreditsResult           = result.niContributionsAndCreditsResult

      Then("Only an empty response should be returned")

      // Basic response checks
      result.benefitType shouldBe payload.benefitType
      result.nationalInsuranceNumber shouldBe payload.nationalInsuranceNumber

      marriageDetailsResult.marriageDetails shouldBe empty

      longTermBenefitCalculationDetailsResult.benefitCalculationDetails shouldBe empty

      schemeMembershipDetailsResult.schemeMembershipDetails shouldBe empty

      individualStatePensionInfoResult.contributionsByTaxYear shouldBe empty

      contributionsAndCreditsResult.class1ContributionAndCredits shouldBe empty
      contributionsAndCreditsResult.class2ContributionAndCredits shouldBe empty

      // Print response details
      println(s"The Response Status Code is : ${response.status} ${response.statusText}")
      println(s"""The Response Body is :
                               ${Json.prettyPrint(Json.toJson(result))}""")
    }

  }

  private def assertGYSPResponseHasSectionsFromAllDownstreamAPIs(payload: GYSPRequest, result: GYSPResponse) = {

    // Basic response checks

    result.benefitType shouldBe payload.benefitType
    result.nationalInsuranceNumber shouldBe payload.nationalInsuranceNumber

    // Benefit Scheme Details

    val schemeMembershipDetailsResult = result.schemeMembershipDetailsResult
    schemeMembershipDetailsResult.schemeMembershipDetails should not be empty

    // Marriage Details

    result.marriageDetailsResult.marriageDetails should not be null

    // Long Term Benefit Calculation Details

    val calc = result.longTermBenefitCalculationDetailsResult
    calc.benefitCalculationDetails should not be empty

    // Scheme Membership Details

    result.schemeMembershipDetailsResult.schemeMembershipDetails should not be empty

    // Individual State Pension Info

    val pensionInfo = result.individualStatePensionInfoResult

    pensionInfo.contributionsByTaxYear should not be empty

    // NI Contributions & Credits

    val contributions = result.niContributionsAndCreditsResult
    contributions should not be null
  }

  private def assertRequiredFieldsInMarriageDetails(
      marriageDetails: List[FilteredMarriageDetailsItem]
  ): Unit =
    marriageDetails.foreach { item =>
      require(
        item.status != null && item.status.trim.nonEmpty,
        s"Marriage status is required but was missing for item: $item"
      )
    }

}
