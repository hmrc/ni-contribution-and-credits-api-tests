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

import org.scalatest.BeforeAndAfterAll
import play.api.libs.json.Json
import play.api.libs.ws.StandaloneWSRequest
import uk.gov.hmrc.api.helpers.BaseHelper
import uk.gov.hmrc.api.models.gysp.{GYSPRequest, GYSPResponse}
import uk.gov.hmrc.api.service.GyspService
import uk.gov.hmrc.api.specs.BaseSpec
import uk.gov.hmrc.api.utils.JsonUtils

class GYSPScenarios extends BaseSpec with BaseHelper with BeforeAndAfterAll {

  val gyspService                              = new GyspService
  var PayloadMapping: Map[String, GYSPRequest] = _

  override def beforeAll(): Unit = {
    super.beforeAll()
    val jsonString = JsonUtils.readJsonFile(
      "src/test/scala/uk/gov/hmrc/api/testData/Gysp_TestData.json"
    )
    println(jsonString)
    PayloadMapping = JsonUtils.parseJsonToGyspRequestMap(jsonString) match {
      case Left(failure) => fail(s"Parsing failed: $failure")
      case Right(map)    => map
    }
  }

  Feature(s"Test Scenarios for GYSP Benefit Type") {

    Scenario(s"GYSP_PTC001: Verify full GYSP response body for a valid NINO with Suffix") {

      Given("The Benefit eligibility Info API is up and running for GYSP")

      When("A request for GYSP is sent")

      val payloadKey = "GYSP_PTC001"
      val payload    = PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))

      val response = gyspService.makeRequest(payload, payloadKey)

      val result: GYSPResponse = assertGYSPResponse(payload, response)

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

      val result: GYSPResponse = assertGYSPResponse(payload, response)

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
      val contributions        = result.niContributionsAndCreditsResult.head

      // Basic response checks
      result.benefitType shouldBe payload.benefitType
      result.nationalInsuranceNumber shouldBe payload.nationalInsuranceNumber

      Then("Only Class 2 contributions should be returned")
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

//    Scenario(s"GYSP_PTC004: Validate full GYSP response structure and content with Nino with Suffix") {
//      Given(s"The Benefit eligibility Info API is up and running for GYSP")
//      When(s"A request for GYSP is sent")
//      // Get test payload
//      val payloadKey = s"GYSP_PTC003"
//      val payload    = PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))
//      println(payload)
//      // Make API call and build the request
//      val response = gyspService.makeRequest(payload, payloadKey)
//
//      response.status shouldBe 200
//      // Parse JSON into case class
//      println(response.body)
//      val result: GYSPResponse = Json.parse(response.body).as[GYSPResponse]
//      val contributions        = result.niContributionsAndCreditsResult.head
//
//      // Basic response checks
//      result.benefitType shouldBe payload.benefitType
//      result.nationalInsuranceNumber shouldBe payload.nationalInsuranceNumber
//
//      Then("Only Class 2 contributions should be returned")
//      // Class 1 should be empty
//      contributions.class1ContributionAndCredits shouldBe empty
//      // Class 2: must exist, not empty, and contain "c2"
//      contributions.class2ContributionAndCredits match {
//        case Some(list) =>
//          list should not be empty
//          list.exists(_.contributionCreditType == "C2") shouldBe true
//        case None => fail("Class 2 contributions are missing in the response")
//      }
//      // Print response details
//      println(s"The Response Status Code is : ${response.status} ${response.statusText}")
//      println(s"""The Response Body is :
//                              ${Json.prettyPrint(Json.toJson(result))}""")
//    }
//
//  }

//  Scenario(s"GYSP_PTC005: Verify minimal GYSP response body") {
//    Given(s"The Benefit eligibility Info API is up and running for GYSP")
//    When(s"A request for GYSP is sent")
//    // Get test payload
//    val payloadKey = s"GYSP_PTC005"
//    val payload    = PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))
//    println(payload)
//    // Make API call and build the request
//    val response = gyspService.makeRequest(payload, payloadKey)
//
//    response.status shouldBe 200
//    // Parse JSON into case class
//    println(response.body)
//   val result: GYSPResponse = Json.parse(response.body).as[GYSPResponse]
//    val contributions        = result.niContributionsAndCreditsResult.head
//
//    // Basic response checks
//    result.benefitType shouldBe payload.benefitType
//    result.nationalInsuranceNumber shouldBe payload.nationalInsuranceNumber
//
//    Then("Only Class 2 contributions should be returned")
//    // Class 1 should be empty
//    contributions.class1ContributionAndCredits shouldBe empty
//    // Class 2: must exist, not empty, and contain "c2"
//    contributions.class2ContributionAndCredits match {
//      case Some(list) =>
//        list should not be empty
//        list.exists(_.contributionCreditType == "C2") shouldBe true
//      case None => fail("Class 2 contributions are missing in the response")
//    }
//    // Print response details
//    println(s"The Response Status Code is : ${response.status} ${response.statusText}")
//    println(s"""The Response Body is :
//                               ${Json.prettyPrint(Json.toJson(result))}""")
//  }
//
//  Scenario(s"GYSP_PTC006: Verify partial failure GYSP response structure and content") {
//    Given(s"The Benefit eligibility Info API is up and running for GYSP")
//    When(s"A request for GYSP is sent and NICC, LTB Calc and LTB Notes return error")
//    // Get test payload
//    val payloadKey = s"GYSP_PTC006"
//    val payload    = PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))
//    println(payload)
//    // Make API call and build the request
//    val response = gyspService.makeRequest(payload, payloadKey)
//
//    response.status shouldBe 502
//    // Parse JSON into case class
//    println(response.body)
    //    val result: GYSPResponse = Json.parse(response.body).as[GYSPResponse]
    //    val contributions        = result.niContributionsAndCreditsResult.head
    //
    //    // Basic response checks
    //    result.benefitType shouldBe payload.benefitType
    //    result.nationalInsuranceNumber shouldBe payload.nationalInsuranceNumber
    //
//    Then("502 should be sent with partial failure content")
    //    // Class 1 should be empty
    //    contributions.class1ContributionAndCredits shouldBe empty
    //    // Class 2: must exist, not empty, and contain "c2"
    //    contributions.class2ContributionAndCredits match {
    //      case Some(list) =>
    //        list should not be empty
    //        list.exists(_.contributionCreditType == "C2") shouldBe true
    //      case None => fail("Class 2 contributions are missing in the response")
    //    }
    //    // Print response details
    //    println(s"The Response Status Code is : ${response.status} ${response.statusText}")
    //    println(s"""The Response Body is :
    //                               ${Json.prettyPrint(Json.toJson(result))}""")
  }

  private def assertGYSPResponse(payload: GYSPRequest, response: StandaloneWSRequest#Response) = {
    response.status shouldBe 200

    val result: GYSPResponse = Json.parse(response.body).as[GYSPResponse]

    Then("All major response sections should contain valid data")

    // --------------------------------------------------
    // Basic response checks
    // --------------------------------------------------

    result.benefitType shouldBe payload.benefitType
    result.nationalInsuranceNumber shouldBe payload.nationalInsuranceNumber

    // --------------------------------------------------
    // Benefit Scheme Details
    // --------------------------------------------------

    result.benefitSchemeDetailsResult should not be null

    // --------------------------------------------------
    // Marriage Details
    // --------------------------------------------------

    result.marriageDetailsResult.marriageDetails.activeMarriage shouldBe true

    // --------------------------------------------------
    // Long Term Benefit Calculation Details
    // --------------------------------------------------

    val calc = result.longTermBenefitCalculationDetailsResult
    calc.statePensionAgeBefore2010TaxYear.get shouldBe false
    calc.statePensionAgeAfter2016TaxYear.get shouldBe true

    calc.benefitCalculationDetailsList match {
      case Some(list) => list should not be empty
      case None       => succeed // optional field
    }

    // --------------------------------------------------
    // Long Term Benefit Notes
    // --------------------------------------------------

    result.longTermBenefitNotesResult.longTermBenefitNotes.foreach(notes => notes should not be empty)

    // --------------------------------------------------
    // Scheme Membership Details
    // --------------------------------------------------

    result.schemeMembershipDetailsResult.schemeMembershipDetailsSummaryList should not be empty

    // --------------------------------------------------
    // Individual State Pension Info
    // --------------------------------------------------

    val pensionInfo = result.individualStatePensionInfoResult

    pensionInfo.identifier should not be empty

    // --------------------------------------------------
    // NI Contributions & Credits
    // --------------------------------------------------

    result.niContributionsAndCreditsResult should not be empty

    val contributions = result.niContributionsAndCreditsResult.head

    // Class 1
    contributions.class1ContributionAndCredits match {
      case Some(list) =>
        list should not be empty
        list.exists(_.contributionCategory.contains("STANDARD RATE")) shouldBe true
      case None =>
        fail("Class 1 contributions are missing in the response")
    }

    // Class 2
    contributions.class2ContributionAndCredits match {
      case Some(list) =>
        list should not be empty
        list.exists(_.contributionCreditType == "2B") shouldBe true
      case None =>
        fail("Class 2 contributions are missing in the response")
    }
    result
  }

}
