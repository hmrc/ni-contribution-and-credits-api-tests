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

import org.scalatest.BeforeAndAfterAll
import play.api.libs.json.*
import play.api.libs.ws.StandaloneWSRequest
import uk.gov.hmrc.api.helpers.BaseHelper
import uk.gov.hmrc.api.models.common.DownstreamErrorResponse
import uk.gov.hmrc.api.models.esajsa.*
import uk.gov.hmrc.api.service.EsaJsaService
import uk.gov.hmrc.api.specs.BaseSpec
import uk.gov.hmrc.api.utils.JsonUtils

class EsaJsaBaseSpec extends BaseSpec with BaseHelper with BeforeAndAfterAll {

  // ── Configuration ──────────────────────────────────────────────────────────

  val benefitTypes                               = Seq("JSA", "ESA")
  val esaJsaService                              = new EsaJsaService
  var PayloadMapping: Map[String, EsaJsaRequest] = _

  // ── Setup ──────────────────────────────────────────────────────────────────

  override def beforeAll(): Unit = {
    super.beforeAll()
    val jsonString = JsonUtils.readJsonFile(
      "src/test/scala/uk/gov/hmrc/api/testData/EsaJsa_TestData.json"
    )
    PayloadMapping = JsonUtils.parseJsonToEsaJsaRequestMap(jsonString) match {
      case Left(failure) => fail(s"Parsing failed: $failure")
      case Right(map)    => map
    }
  }

  // ── Payload Helpers ────────────────────────────────────────────────────────

  def getPayload(payloadKey: String): EsaJsaRequest =
    PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))

  def makeRequestAndParseResponse(
      payload: EsaJsaRequest
  ): (StandaloneWSRequest#Response, EsaJsaResponse) = {
    val response = esaJsaService.makeRequest(payload)
    val result   = Json.parse(response.body).as[EsaJsaResponse]
    (response, result)
  }

  // ── Assertion Helpers ──────────────────────────────────────────────────────

  def assertBasicResponseFields(
      result: EsaJsaResponse,
      payload: EsaJsaRequest,
      expectedStatus: Int,
      response: StandaloneWSRequest#Response
  ): Unit = {
    response.status shouldBe expectedStatus
    result.benefitType shouldBe payload.benefitType
    result.nationalInsuranceNumber shouldBe payload.nationalInsuranceNumber
    val correlationId = response.header("correlationid").getOrElse("")
    correlationId should not be empty
  }

  def assertClass1Contributions(
      contributions: NIContributionsAndCreditsResult,
      expectedCategory: String
  ): Unit =
    contributions.class1ContributionAndCredits match {
      case Some(list) =>
        list should not be empty
        list.exists(_.contributionCategory.contains(expectedCategory)) shouldBe true
      case None => fail("Class 1 contributions are missing in the response")
    }

  def assertClass2Contributions(
      contributions: NIContributionsAndCreditsResult,
      expectedCreditType: String
  ): Unit =
    contributions.class2Or3ContributionAndCredits match {
      case Some(list) =>
        list should not be empty
        list.exists(_.contributionCreditType == expectedCreditType) shouldBe true
      case None => fail("Class 2 contributions are missing in the response")
    }

  def assertEmptyContributions(contributions: NIContributionsAndCreditsResult): Unit = {
    contributions.class1ContributionAndCredits match {
      case Some(list) if list.nonEmpty => fail("Class 1 contributions are present but should be empty")
      case _                           =>
    }
    contributions.class2Or3ContributionAndCredits match {
      case Some(list) if list.nonEmpty => fail("Class 2 contributions are present but should be empty")
      case _                           =>
    }
  }

  def assertErrorResponse(
      json: JsValue,
      expectedCode: String,
      expectedReason: String
  ): Unit = {
    (json \ "code").as[String] shouldBe expectedCode
    (json \ "reason").as[String] should include(expectedReason)
  }

  def assertDownstreamFailure(
      result: DownstreamErrorResponse,
      payload: EsaJsaRequest,
      expectedStatus: String
  ): Unit = {
    result.status shouldBe expectedStatus
    result.benefitType shouldBe payload.benefitType
    result.nationalInsuranceNumber shouldBe payload.nationalInsuranceNumber
    result.downStreams.head.status shouldBe "FAILURE"
    result.downStreams.head.error shouldBe defined
  }

  // ── Print Helpers ──────────────────────────────────────────────────────────

  def printResponse(
      response: StandaloneWSRequest#Response,
      result: EsaJsaResponse
  ): Unit = {
    val correlationId = response.header("correlationid").getOrElse("N/A")
    println(s"The Response correlationId is: $correlationId")
    println(s"The Response Status Code is : ${response.status} ${response.statusText}")
    println(s"The Response Body is : ${Json.prettyPrint(Json.toJson(result))}")
  }

  def printRawResponse(response: StandaloneWSRequest#Response): Unit = {
    println(s"The Response Status Code is : ${response.status} ${response.statusText}")
    println(s"The Response Body is : ${Json.prettyPrint(Json.parse(response.body))}")
  }

}
