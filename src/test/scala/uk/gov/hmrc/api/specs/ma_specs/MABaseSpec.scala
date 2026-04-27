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

package uk.gov.hmrc.api.specs.ma_specs

import org.scalatest.BeforeAndAfterAll
import play.api.libs.json.*
import play.api.libs.ws.StandaloneWSRequest
import uk.gov.hmrc.api.helpers.BaseHelper
import uk.gov.hmrc.api.models.ma.*
import uk.gov.hmrc.api.service.MAService
import uk.gov.hmrc.api.specs.BaseSpec
import uk.gov.hmrc.api.utils.JsonUtils

class MABaseSpec extends BaseSpec with BaseHelper with BeforeAndAfterAll {

  // ── Configuration ──────────────────────────────────────────────────────────

  val maService                              = new MAService
  var PayloadMapping: Map[String, MARequest] = _

  // ── Setup ──────────────────────────────────────────────────────────────────

  override def beforeAll(): Unit = {
    super.beforeAll()
    val jsonString = JsonUtils.readJsonFile(
      "src/test/scala/uk/gov/hmrc/api/testData/MA_TestData.json"
    )
    PayloadMapping = JsonUtils.parseJsonToMARequestMap(jsonString) match {
      case Left(failure) => fail(s"Parsing failed: $failure")
      case Right(map)    => map
    }
  }

  // ── Payload Helpers ────────────────────────────────────────────────────────

  def getPayload(payloadKey: String): MARequest =
    PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))

  def makeAndParseRequest(
      payload: MARequest,
      payloadKey: String
  ): (StandaloneWSRequest#Response, MAResponse) = {
    val response = maService.makeRequest(payload)
    val result   = Json.parse(response.body).as[MAResponse]
    (response, result)
  }

  // ── Assertion Helpers ──────────────────────────────────────────────────────

  def assertMAResponse(
      payload: MARequest,
      response: StandaloneWSRequest#Response
  ): MAResponse = {
    val result = Json.parse(response.body).as[MAResponse]

    Then("All major response sections should contain valid data")
    assertBasicResponseFields(result, payload, 200, response)
    assertLiabilitySummaryDetails(result)
    assertNiContributions(result)
    result
  }

  def assertBasicResponseFields(
      result: MAResponse,
      payload: MARequest,
      expectedStatus: Int,
      response: StandaloneWSRequest#Response
  ): Unit = {
    response.status shouldBe expectedStatus
    result.benefitType shouldBe payload.benefitType
    result.nationalInsuranceNumber shouldBe payload.nationalInsuranceNumber
  }

  def assertLiabilitySummaryDetails(result: MAResponse): Unit = {
    result.liabilitySummaryDetailsResult should not be null
    result.liabilitySummaryDetailsResult should not be empty

    val firstLiabilityResult = result.liabilitySummaryDetailsResult.head
    firstLiabilityResult.liabilityDetails should not be empty
    firstLiabilityResult.liabilityDetails.size should be >= 1

    val firstLiabilityDetail = firstLiabilityResult.liabilityDetails.head
    firstLiabilityDetail.startDate should not be null
    firstLiabilityDetail.startDate.getYear should be >= 2000
    firstLiabilityDetail.startDate.getYear should be <= 2030

    firstLiabilityDetail.endDate match {
      case Some(endDate) =>
        endDate should not be null
        endDate.getYear should be >= 2000
        endDate.getYear should be <= 2030
        endDate.isBefore(firstLiabilityDetail.startDate) shouldBe false
      case None =>
    }
  }

  def assertNiContributions(result: MAResponse): Unit = {
    result.niContributionsAndCreditsResult should not be null

    result.niContributionsAndCreditsResult.class1ContributionAndCredits match {
      case Some(list) =>
        list should not be empty
        list.exists(_.contributionCategory.contains("STANDARD RATE")) shouldBe true
      case None =>
    }

    result.niContributionsAndCreditsResult.class2Or3ContributionAndCredits match {
      case Some(list) =>
        list should not be empty
        list.exists(_.contributionCreditType == "2B") shouldBe true
      case None =>
    }
  }

  def assertPartialFailureResponse(responseBody: JsValue): Unit = {
    (responseBody \ "status").as[String] shouldBe "PARTIAL FAILURE"

    val statuses = (responseBody \ "downStreams")
      .as[JsArray]
      .value
      .map(ds => (ds \ "status").as[String])

    statuses should contain("SUCCESS")
    statuses should contain("FAILURE")
  }

  def assertCompleteFailureResponse(responseBody: JsValue): Unit = {
    (responseBody \ "status").as[String] shouldBe "FAILURE"

    (responseBody \ "downStreams")
      .as[JsArray]
      .value
      .foreach(downstream => (downstream \ "status").as[String] shouldBe "FAILURE")
  }

  def assertErrorResponse(
      json: JsValue,
      expectedCode: String,
      expectedReason: String
  ): Unit = {
    (json \ "code").as[String] shouldBe expectedCode
    (json \ "reason").as[String] should include(expectedReason)
  }

  // ── Print Helpers ──────────────────────────────────────────────────────────

  def printResponse(
      response: StandaloneWSRequest#Response,
      result: MAResponse
  ): Unit = {
    println(s"The Response Status Code is : ${response.status} ${response.statusText}")
    println(s"The Response Body is : ${Json.prettyPrint(Json.toJson(result))}")
  }

  def printRawResponse(response: StandaloneWSRequest#Response): Unit = {
    println(s"The Response Status Code is : ${response.status} ${response.statusText}")
    println(s"The Response Body is : ${Json.prettyPrint(Json.parse(response.body))}")
  }

}
