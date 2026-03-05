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

import org.scalatest.{BeforeAndAfterAll, Ignore}
import play.api.libs.json.Json
import play.api.libs.ws.StandaloneWSRequest
import uk.gov.hmrc.api.helpers.BaseHelper
import uk.gov.hmrc.api.models.common.{
  Class1ContributionAndCredits,
  Class2ContributionAndCredits,
  DownstreamErrorResponse,
  DownstreamStatus,
  NpsNormalizedError,
  Summary
}
import uk.gov.hmrc.api.models.esajsa.NIContributionsAndCreditsResult
import uk.gov.hmrc.api.models.gysp.{
  FilteredIndividualStatePensionContributionsByTaxYear,
  FilteredIndividualStatePensionInfo,
  FilteredLongTermBenefitCalculationDetails,
  FilteredLongTermBenefitCalculationDetailsItem,
  FilteredMarriageDetails,
  FilteredMarriageDetailsItem,
  FilteredSchemeMembershipDetails,
  FilteredSchemeMembershipDetailsItem,
  GYSPRequest,
  GYSPResponse
}
import uk.gov.hmrc.api.service.GyspService
import uk.gov.hmrc.api.specs.BaseSpec
import uk.gov.hmrc.api.utils.JsonUtils

import java.time.LocalDate

@Ignore
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

    Scenario("A GYSP request is processed successfully") {
      Given(s"The Benefit eligibility Info API is up and running")
      When(s"A request for GYSP is sent and all the requested down stream NPS service respond successfully")
      // Get test payload
      val payloadKey = s"GYSP_PTC004"
      val payload    = PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))
      println(payload)
      // Make API call and build the request
      val response = gyspService.makeRequest(payload, payloadKey)
      println(response.body)

      Then("A 200 is returned with the filtered benefit eligibility data")
      val result: GYSPResponse = Json.parse(response.body).as[GYSPResponse]
      val expectedResponse = GYSPResponse(
        "GYSP",
        "AA000004",
        FilteredMarriageDetails(
          List(
            FilteredMarriageDetailsItem(
              "CIVIL PARTNER",
              Some(LocalDate.parse("2020-04-06")),
              Some("VERIFIED"),
              Some(LocalDate.parse("2025-04-06")),
              Some("VERIFIED"),
              Some("AA000002"),
              None,
              None
            )
          )
        ),
        FilteredLongTermBenefitCalculationDetails(
          List(
            FilteredLongTermBenefitCalculationDetailsItem(
              None,
              None,
              None,
              None,
              List(
                "Invalid Note Type Encountered.",
                "Married Woman's/Widow's Reduced Rate Authority recorded on this account between 07/04/2020 and 07/04/2025.",
                "Married Woman's/Widow's Reduced Rate Authority recorded on this account from 07/04/2025"
              )
            )
          )
        ),
        FilteredSchemeMembershipDetails(
          List(
            FilteredSchemeMembershipDetailsItem(
              Some("EXAMPLE PENSION SCHEME"),
              Some(LocalDate.parse("2022-06-27")),
              Some(LocalDate.parse("2022-06-27")),
              Some("S3123456B")
            ),
            FilteredSchemeMembershipDetailsItem(
              Some("EXAMPLE PENSION SCHEME"),
              Some(LocalDate.parse("2022-06-27")),
              Some(LocalDate.parse("2022-06-27")),
              Some("S3123456B")
            )
          )
        ),
        FilteredIndividualStatePensionInfo(
          Some(50),
          List(
            FilteredIndividualStatePensionContributionsByTaxYear(
              Some(350.91),
              Some(true)
            ),
            FilteredIndividualStatePensionContributionsByTaxYear(
              Some(1377.39),
              Some(true)
            ),
            FilteredIndividualStatePensionContributionsByTaxYear(
              Some(2666.09),
              Some(true)
            )
          )
        ),
        NIContributionsAndCreditsResult(
          Some(1.2),
          Some(
            List(
              Class1ContributionAndCredits(
                2019,
                Some(10000),
                Some("STANDARD RATE"),
                Some("A"),
                Some(1200),
                Some("VALID"),
                "2B",
                Some("JSA TAPE INPUT"),
                15,
                Some("Temp"),
                Some("L")
              ),
              Class1ContributionAndCredits(
                2021,
                Some(12000),
                Some("STANDARD RATE"),
                Some("A"),
                Some(1440),
                Some("VALID"),
                "2B",
                Some("JSA TAPE INPUT"),
                20,
                Some("Temp"),
                Some("L")
              )
            )
          ),
          Some(
            List(
              Class2ContributionAndCredits(
                2022,
                Some(1.1),
                None,
                Some("VALID"),
                "2B",
                Some("JSA TAPE INPUT"),
                30,
                Some("ZZ")
              ),
              Class2ContributionAndCredits(
                2023,
                Some(0.9),
                None,
                Some("VALID"),
                "2B",
                Some("JSA PAPER INPUT"),
                25,
                Some("L")
              )
            )
          )
        )
      )

      response.status shouldBe 200
      result shouldBe expectedResponse

      // Print response details
      println(s"The Response Status Code is : ${response.status} ${response.statusText}")
      println(s"""The Response Body is : ${Json.prettyPrint(Json.toJson(result))}""")
    }

    Scenario(s"GYSP_PTC006: A GYSP request is processed, however some down streams fail") {
      Given(s"The Benefit eligibility Info API is up and running")
      When(s"A request for GYSP is sent and NICC and LTB Calc return error")

      val payloadKey = s"GYSP_PTC006"
      val payload    = PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))
      println(payload)

      // Make API call and build the request
      val response = gyspService.makeRequest(payload, payloadKey)

      Then("A 502 should be returned with partial failure content")

      println(response.body)

      val result: DownstreamErrorResponse = Json.parse(response.body).as[DownstreamErrorResponse]

      val expectedResponse =
        DownstreamErrorResponse(
          "PARTIAL FAILURE",
          payload.benefitType,
          payload.nationalInsuranceNumber,
          Summary(totalCalls = 7, successful = 5, failed = 2),
          List(
            DownstreamStatus("Benefit Scheme Details", "SUCCESS", None),
            DownstreamStatus("Benefit Scheme Details", "SUCCESS", None),
            DownstreamStatus(
              "NI Contributions and credits",
              "FAILURE",
              Some(
                NpsNormalizedError(
                  "ACCESS_FORBIDDEN",
                  "downstream resource cannot be accessed by the calling client",
                  403
                )
              )
            ),
            DownstreamStatus("Marriage Details", "SUCCESS", None),
            DownstreamStatus("Scheme Membership Details", "SUCCESS", None),
            DownstreamStatus(
              "Long Term Benefit Calculation Details",
              "FAILURE",
              Some(NpsNormalizedError("BAD_REQUEST", "downstream received a malformed request", 400))
            ),
            DownstreamStatus("Individual State Pension Information", "SUCCESS", None)
          )
        )

      // Assertions
      response.status shouldBe 502
      result shouldBe expectedResponse

      // Print response details
      println(s"The Response Status Code is : ${response.status} ${response.statusText}")
      println(s"""The Response Body is : ${Json.prettyPrint(Json.toJson(result))}""")
    }

    Scenario(s"GYSP_NPS_ERROR_ALL_DOWNSTREAMS: A GYSP request is processed, however all down streams fail") {
      Given(s"The Benefit eligibility Info API is up and running")
      When(s"A request for GYSP is sent and NICC and LTB Calc return error")

      val payloadKey = s"GYSP_NPS_ERROR_ALL_DOWNSTREAMS"
      val payload    = PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))
      println(payload)

      // Make API call and build the request
      val response = gyspService.makeRequest(payload, payloadKey)

      Then("A 502 should be returned with only failure content")

      println(response.body)

      val result: DownstreamErrorResponse = Json.parse(response.body).as[DownstreamErrorResponse]

      val expectedResponse =
        DownstreamErrorResponse(
          status = "FAILURE",
          benefitType = payload.benefitType,
          nationalInsuranceNumber = payload.nationalInsuranceNumber,
          summary = Summary(totalCalls = 5, successful = 0, failed = 5),
          downStreams = List(
            DownstreamStatus(
              "NI Contributions and credits",
              "FAILURE",
              Some(NpsNormalizedError("BAD_REQUEST", "downstream received a malformed request", 400))
            ),
            DownstreamStatus(
              "Marriage Details",
              "FAILURE",
              Some(NpsNormalizedError("BAD_REQUEST", "downstream received a malformed request", 400))
            ),
            DownstreamStatus(
              "Scheme Membership Details",
              "FAILURE",
              Some(NpsNormalizedError("BAD_REQUEST", "downstream received a malformed request", 400))
            ),
            DownstreamStatus(
              "Long Term Benefit Calculation Details",
              "FAILURE",
              Some(NpsNormalizedError("BAD_REQUEST", "downstream received a malformed request", 400))
            ),
            DownstreamStatus(
              "Individual State Pension Information",
              "FAILURE",
              Some(NpsNormalizedError("BAD_REQUEST", "downstream received a malformed request", 400))
            )
          )
        )

      // Assertions
      response.status shouldBe 502
      result shouldBe expectedResponse

      // Print response details
      println(s"The Response Status Code is : ${response.status} ${response.statusText}")
      println(s"""The Response Body is : ${Json.prettyPrint(Json.toJson(result))}""")
    }

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
      val contributions        = result.niContributionsAndCreditsResult

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

    Scenario(s"GYSP_PTC004: Validate full GYSP response structure and content with Nino with Suffix") {
      Given(s"The Benefit eligibility Info API is up and running for GYSP")
      When(s"A request for GYSP is sent")
      // Get test payload
      val payloadKey = s"GYSP_PTC004"
      val payload    = PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))
      println(payload)
      // Make API call and build the request
      val response = gyspService.makeRequest(payload, payloadKey)

      println(response.body)
      val result: GYSPResponse = Json.parse(response.body).as[GYSPResponse]

      val expectedResponse = GYSPResponse(
        "GYSP",
        "AA000004",
        FilteredMarriageDetails(
          List(
            FilteredMarriageDetailsItem(
              "CIVIL PARTNER",
              Some(LocalDate.parse("2020-04-06")),
              Some("VERIFIED"),
              Some(LocalDate.parse("2025-04-06")),
              Some("VERIFIED"),
              Some("AA000002"),
              None,
              None
            )
          )
        ),
        FilteredLongTermBenefitCalculationDetails(
          List(
            FilteredLongTermBenefitCalculationDetailsItem(
              None,
              None,
              None,
              None,
              List(
                "Invalid Note Type Encountered.",
                "Married Woman's/Widow's Reduced Rate Authority recorded on this account between 07/04/2020 and 07/04/2025.",
                "Married Woman's/Widow's Reduced Rate Authority recorded on this account from 07/04/2025"
              )
            )
          )
        ),
        FilteredSchemeMembershipDetails(
          List(
            FilteredSchemeMembershipDetailsItem(
              Some("EXAMPLE PENSION SCHEME"),
              Some(LocalDate.parse("2022-06-27")),
              Some(LocalDate.parse("2022-06-27")),
              Some("S3123456B")
            ),
            FilteredSchemeMembershipDetailsItem(
              Some("EXAMPLE PENSION SCHEME"),
              Some(LocalDate.parse("2022-06-27")),
              Some(LocalDate.parse("2022-06-27")),
              Some("S3123456B")
            )
          )
        ),
        FilteredIndividualStatePensionInfo(
          Some(50),
          List(
            FilteredIndividualStatePensionContributionsByTaxYear(
              Some(350.91),
              Some(true)
            ),
            FilteredIndividualStatePensionContributionsByTaxYear(
              Some(1377.39),
              Some(true)
            ),
            FilteredIndividualStatePensionContributionsByTaxYear(
              Some(2666.09),
              Some(true)
            )
          )
        ),
        NIContributionsAndCreditsResult(
          Some(1.2),
          Some(
            List(
              Class1ContributionAndCredits(
                2019,
                Some(10000),
                Some("STANDARD RATE"),
                Some("A"),
                Some(1200),
                Some("VALID"),
                "2B",
                Some("JSA TAPE INPUT"),
                15,
                Some("Temp"),
                Some("L")
              ),
              Class1ContributionAndCredits(
                2021,
                Some(12000),
                Some("STANDARD RATE"),
                Some("A"),
                Some(1440),
                Some("VALID"),
                "2B",
                Some("JSA TAPE INPUT"),
                20,
                Some("Temp"),
                Some("L")
              )
            )
          ),
          Some(
            List(
              Class2ContributionAndCredits(
                2022,
                Some(1.1),
                None,
                Some("VALID"),
                "2B",
                Some("JSA TAPE INPUT"),
                30,
                Some("ZZ")
              ),
              Class2ContributionAndCredits(
                2023,
                Some(0.9),
                None,
                Some("VALID"),
                "2B",
                Some("JSA PAPER INPUT"),
                25,
                Some("L")
              )
            )
          )
        )
      )

      response.status shouldBe 200
      result shouldBe expectedResponse

      // Print response details
      println(s"The Response Status Code is : ${response.status} ${response.statusText}")
      println(s"""The Response Body is : ${Json.prettyPrint(Json.toJson(result))}""")
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
      val contributions        = result.niContributionsAndCreditsResult

      // Basic response checks
      result.benefitType shouldBe payload.benefitType
      result.nationalInsuranceNumber shouldBe payload.nationalInsuranceNumber

      Then("Only an empty response should be returned")
      // Class 1 should be empty
      contributions.class1ContributionAndCredits shouldBe empty
      // Class 2: must exist, not empty, and contain "c2"
      contributions.class1ContributionAndCredits shouldBe empty
      // Print response details
      println(s"The Response Status Code is : ${response.status} ${response.statusText}")
      println(s"""The Response Body is :
                               ${Json.prettyPrint(Json.toJson(result))}""")
    }

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

    result.schemeMembershipDetailsResult should not be null

    // --------------------------------------------------
    // Marriage Details
    // --------------------------------------------------

    result.marriageDetailsResult.marriageDetails should not be null

    // --------------------------------------------------
    // Long Term Benefit Calculation Details
    // --------------------------------------------------

    val calc = result.longTermBenefitCalculationDetailsResult

    calc.benefitCalculationDetails should not be empty

    // --------------------------------------------------
    // Long Term Benefit Notes
    // --------------------------------------------------

    // --------------------------------------------------
    // Scheme Membership Details
    // --------------------------------------------------

    result.schemeMembershipDetailsResult.schemeMembershipDetails should not be empty

    // --------------------------------------------------
    // Individual State Pension Info
    // --------------------------------------------------

    val pensionInfo = result.individualStatePensionInfoResult

    pensionInfo.contributionsByTaxYear should not be empty
    pensionInfo.numberOfQualifyingYears shouldBe Some(50)

    // --------------------------------------------------
    // NI Contributions & Credits
    // --------------------------------------------------

    val contributions = result.niContributionsAndCreditsResult

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
