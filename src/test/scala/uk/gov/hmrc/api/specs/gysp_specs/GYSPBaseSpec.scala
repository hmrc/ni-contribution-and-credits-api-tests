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

import org.scalatest.BeforeAndAfterAll
import play.api.libs.json.*
import play.api.libs.ws.StandaloneWSRequest
import uk.gov.hmrc.api.helpers.BaseHelper
import uk.gov.hmrc.api.models.common.*
import uk.gov.hmrc.api.models.esajsa.NIContributionsAndCreditsResult
import uk.gov.hmrc.api.models.gysp.*
import uk.gov.hmrc.api.service.GyspService
import uk.gov.hmrc.api.specs.BaseSpec
import uk.gov.hmrc.api.utils.JsonUtils

class GYSPBaseSpec extends BaseSpec with BaseHelper with BeforeAndAfterAll {

  // ── Configuration ──────────────────────────────────────────────────────────

  val gyspService                              = new GyspService
  var PayloadMapping: Map[String, GYSPRequest] = _

  // ── Setup ──────────────────────────────────────────────────────────────────

  override def beforeAll(): Unit = {
    super.beforeAll()
    val jsonString = JsonUtils.readJsonFile(
      "src/test/scala/uk/gov/hmrc/api/testData/Gysp_TestData.json"
    )
    PayloadMapping = JsonUtils.parseJsonToGyspRequestMap(jsonString) match {
      case Left(failure) => fail(s"Parsing failed: $failure")
      case Right(map)    => map
    }
  }

  // ── Payload Helpers ────────────────────────────────────────────────────────

  def getPayload(payloadKey: String): GYSPRequest =
    PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))

  def makeRequestAndParseResponse(
      payload: GYSPRequest,
      payloadKey: String
  ): (StandaloneWSRequest#Response, GYSPResponse) = {
    val response = gyspService.makeRequest(payload, payloadKey)
    val result   = Json.parse(response.body).as[GYSPResponse]
    (response, result)
  }

  // ── Assertion Helpers ──────────────────────────────────────────────────────

  def assertBasicResponseFields(
      result: GYSPResponse,
      payload: GYSPRequest,
      expectedStatus: Int,
      response: StandaloneWSRequest#Response
  ): Unit = {
    response.status shouldBe expectedStatus
    result.benefitType shouldBe payload.benefitType
    result.nationalInsuranceNumber shouldBe payload.nationalInsuranceNumber
  }

  def assertGYSPResponseHasSectionsFromAllDownstreamAPIs(
      payload: GYSPRequest,
      result: GYSPResponse
  ): Unit = {
    result.benefitType shouldBe payload.benefitType
    result.nationalInsuranceNumber shouldBe payload.nationalInsuranceNumber

    result.marriageDetailsResult.marriageDetails should not be null
    result.schemeMembershipDetailsResult.schemeMembershipDetails should not be empty
    result.longTermBenefitCalculationDetailsResult.benefitCalculationDetails should not be empty
    result.individualStatePensionInfoResult.contributionsByTaxYear should not be empty
    result.niContributionsAndCreditsResult should not be null
  }

  def assertRequiredFieldsInMarriageDetails(
      marriageDetails: List[FilteredMarriageDetailsItem]
  ): Unit =
    marriageDetails.foreach { item =>
      require(
        item.status != null && item.status.trim.nonEmpty,
        s"Marriage status is required but was missing for item: $item"
      )
    }

  def assertClass2Contributions(
      contributions: NIContributionsAndCreditsResult,
      expectedCreditType: String
  ): Unit =
    contributions.class2ContributionAndCredits match {
      case Some(list) =>
        list should not be empty
        list.exists(_.contributionCreditType == expectedCreditType) shouldBe true
      case None => fail("Class 2 contributions are missing in the response")
    }

  def assertEmptyResponse(result: GYSPResponse, payload: GYSPRequest): Unit = {
    result.benefitType shouldBe payload.benefitType
    result.nationalInsuranceNumber shouldBe payload.nationalInsuranceNumber

    result.marriageDetailsResult.marriageDetails shouldBe empty
    result.longTermBenefitCalculationDetailsResult.benefitCalculationDetails shouldBe empty
    result.schemeMembershipDetailsResult.schemeMembershipDetails shouldBe empty
    result.individualStatePensionInfoResult.contributionsByTaxYear shouldBe empty
    result.niContributionsAndCreditsResult.class1ContributionAndCredits shouldBe empty
    result.niContributionsAndCreditsResult.class2ContributionAndCredits shouldBe empty
  }

  def assertErrorResponse(
      json: JsValue,
      expectedCode: String,
      expectedReason: String
  ): Unit = {
    (json \ "code").as[String] shouldBe expectedCode
    (json \ "reason").as[String] shouldBe expectedReason
  }

  def assertDownstreamFailure(
      result: DownstreamErrorResponse,
      payload: GYSPRequest,
      expectedStatus: String,
      expectedTotalCalls: Int,
      expectedSuccessful: Int,
      expectedFailed: Int
  ): Unit = {
    result.status shouldBe expectedStatus
    result.benefitType shouldBe payload.benefitType
    result.nationalInsuranceNumber shouldBe payload.nationalInsuranceNumber
    result.summary.totalCalls shouldBe expectedTotalCalls
    result.summary.successful shouldBe expectedSuccessful
    result.summary.failed shouldBe expectedFailed
  }

  // ── Print Helpers ──────────────────────────────────────────────────────────

  def printResponse(
      response: StandaloneWSRequest#Response,
      result: GYSPResponse
  ): Unit = {
    println(s"The Response Status Code is : ${response.status} ${response.statusText}")
    println(s"The Response Body is : ${Json.prettyPrint(Json.toJson(result))}")
  }

  def printRawResponse(response: StandaloneWSRequest#Response): Unit = {
    println(s"The Response Status Code is : ${response.status} ${response.statusText}")
    println(s"The Response Body is : ${Json.prettyPrint(Json.parse(response.body))}")
  }

}
