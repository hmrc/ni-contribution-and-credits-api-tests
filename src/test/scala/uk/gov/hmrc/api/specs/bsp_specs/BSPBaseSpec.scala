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

import org.scalatest.BeforeAndAfterAll
import play.api.libs.json.*
import play.api.libs.ws.StandaloneWSRequest
import uk.gov.hmrc.api.helpers.BaseHelper
import uk.gov.hmrc.api.models.bsp.*
import uk.gov.hmrc.api.models.common.DownstreamErrorResponse
import uk.gov.hmrc.api.service.BSPService
import uk.gov.hmrc.api.specs.BaseSpec
import uk.gov.hmrc.api.utils.JsonUtils

class BSPBaseSpec extends BaseSpec with BaseHelper with BeforeAndAfterAll {

  // ── Configuration ──────────────────────────────────────────────────────────

  val bspService                              = new BSPService
  var PayloadMapping: Map[String, BSPRequest] = _

  // ── Setup ──────────────────────────────────────────────────────────────────

  override def beforeAll(): Unit = {
    super.beforeAll()
    val jsonString = JsonUtils.readJsonFile(
      "src/test/scala/uk/gov/hmrc/api/testData/BSP_TestData.json"
    )
    PayloadMapping = JsonUtils.parseJsonToBSPRequestMap(jsonString) match {
      case Left(failure) => fail(s"Parsing failed: $failure")
      case Right(map)    => map
    }
  }

  // ── Payload Helpers ────────────────────────────────────────────────────────

  def getPayload(payloadKey: String): BSPRequest =
    PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))

  def makeAndParseRequest(
      payload: BSPRequest
  ): (StandaloneWSRequest#Response, BSPResponse) = {
    val response = bspService.makeRequest(payload)
    val result   = Json.parse(response.body).as[BSPResponse]
    (response, result)
  }

  // ── Assertion Helpers ──────────────────────────────────────────────────────

  def assertBSPResponse(
      payload: BSPRequest,
      response: StandaloneWSRequest#Response
  ): BSPResponse = {
    val result = Json.parse(response.body).as[BSPResponse]

    Then("All major response sections should contain valid data")
    assertBasicResponseFields(result, payload, 200, response)
    assertMarriageDetails(result)
    assertNiContributions(result)

    result
  }

  def assertBasicResponseFields(
      result: BSPResponse,
      payload: BSPRequest,
      expectedStatus: Int,
      response: StandaloneWSRequest#Response
  ): Unit = {
    response.status shouldBe expectedStatus
    result.benefitType shouldBe payload.benefitType
    result.nationalInsuranceNumber shouldBe payload.nationalInsuranceNumber
  }

  def assertMarriageDetails(result: BSPResponse): Unit = {
    result.marriageDetailsResult should not be null
    result.marriageDetailsResult.marriageDetails should not be empty
    result.marriageDetailsResult.marriageDetails.head.status should not be empty
  }

  def assertNiContributions(result: BSPResponse): Unit = {
    result.niContributionsAndCreditsResult should not be null

    result.niContributionsAndCreditsResult.class1ContributionAndCredits match {
      case Some(list) =>
        list should not be empty
        list.exists(_.contributionCategory.contains("STANDARD RATE")) shouldBe true
      case None => // Class1 is optional
    }

    result.niContributionsAndCreditsResult.class2ContributionAndCredits match {
      case Some(list) =>
        list should not be empty
        list.exists(_.contributionCreditType == "2B") shouldBe true
      case None => // Class2 is optional
    }
  }

  def assertDownstreamFailure(
      result: DownstreamErrorResponse,
      payload: BSPRequest,
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

  def assertErrorResponse(
      json: JsValue,
      expectedCode: String,
      expectedReason: String
  ): Unit = {
    (json \ "code").as[String] shouldBe expectedCode
    (json \ "reason").as[String] shouldBe expectedReason
  }

  // ── Print Helpers ──────────────────────────────────────────────────────────

  def printResponse(
      response: StandaloneWSRequest#Response,
      result: BSPResponse
  ): Unit = {
    println(s"The Response Status Code is : ${response.status} ${response.statusText}")
    println(s"The Response Body is : ${Json.prettyPrint(Json.toJson(result))}")
  }

  def printRawResponse(response: StandaloneWSRequest#Response): Unit = {
    println(s"The Response Status Code is : ${response.status} ${response.statusText}")
    println(s"The Response Body is : ${Json.prettyPrint(Json.parse(response.body))}")
  }

}
