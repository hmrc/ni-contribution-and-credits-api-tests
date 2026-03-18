/*
 * Copyright 2026 HM Revenue & Customs
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

import org.scalatest.{BeforeAndAfterAll, Ignore}
import play.api.libs.json.{JsArray, JsValue, Json}
import play.api.libs.ws.StandaloneWSRequest
import uk.gov.hmrc.api.helpers.BaseHelper
import uk.gov.hmrc.api.models.bsp.{BSPRequest, BSPResponse}
import uk.gov.hmrc.api.service.BSPService
import uk.gov.hmrc.api.specs.BaseSpec
import uk.gov.hmrc.api.utils.JsonUtils

class BSPScenarios extends BaseSpec with BaseHelper with BeforeAndAfterAll {

  val bspService                              = new BSPService
  var PayloadMapping: Map[String, BSPRequest] = _

  override def beforeAll(): Unit = {
    super.beforeAll()
    val jsonString = JsonUtils.readJsonFile(
      "src/test/scala/uk/gov/hmrc/api/testData/BSP_TestData.json"
    )
    println(jsonString)
    PayloadMapping = JsonUtils.parseJsonToBSPRequestMap(jsonString) match {
      case Left(failure) => fail(s"Parsing failed: $failure")
      case Right(map)    => map
    }
  }

  Feature(s"Test Scenarios for BSP Benefit Type") {

    Scenario(s"BSP_PTC001: Verify full BSP response body for a valid NINO with Suffix") {

      Given("The Benefit eligibility Info API is up and running for BSP")

      When("A request for BSP is sent")

      val payloadKey = "BSP_PTC001"
      val payload    = PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))

      val response = bspService.makeRequest(payload, payloadKey)

      val result: BSPResponse = assertBSPResponse(payload, response)

      println(s"Response Status: ${response.status} ${response.statusText}")
      println(Json.prettyPrint(Json.toJson(result)))
    }

    Scenario(s"BSP_PTC002: Verify full BSP response body for a valid NINO without Suffix") {

      Given("The Benefit eligibility Info API is up and running for BSP")

      When("A request for BSP is sent")

      val payloadKey = "BSP_PTC002"
      val payload    = PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))

      val response = bspService.makeRequest(payload, payloadKey)

      val result: BSPResponse = assertBSPResponse(payload, response)

      println(s"Response Status: ${response.status} ${response.statusText}")
      println(Json.prettyPrint(Json.toJson(result)))
    }

    Scenario(s"BSP_PTC003: Verify BSP partial failure response when some services return errors") {

      Given(s"The Benefit eligibility Info API is up and running for BSP")

      When(s"A request for BSP is sent and some downstream services return errors")

      val payloadKey = "BSP_PTC003"
      val payload    = PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))
      println(payload)

      val response = bspService.makeRequest(payload, payloadKey)

      Then("A 502 status should be returned indicating partial failure")
      response.status shouldBe 502

      And("The response should contain partial failure status")
      val responseBody = Json.parse(response.body)

      // Verify top-level partial failure status
      (responseBody \ "status").as[String] shouldBe "PARTIAL FAILURE"

      // Verify we have both SUCCESS and FAILURE statuses in downstream services
      val downStreams = (responseBody \ "downStreams").as[JsArray]
      val statuses    = downStreams.value.map(ds => (ds \ "status").as[String])

      statuses should contain("SUCCESS")
      statuses should contain("FAILURE")

      println(s"The Response Status Code is : ${response.status} ${response.statusText}")
      println(s"The Response Body is : ${response.body}")
    }

    Scenario(s"BSP_PTC004: Verify BSP complete failure response when all downstream services return errors") {

      Given(s"The Benefit eligibility Info API is up and running for BSP")

      When(s"A request for BSP is sent and all downstream services return errors")

      val payloadKey = "BSP_PTC004"
      val payload    = PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))
      println(payload)

      val response = bspService.makeRequest(payload, payloadKey)

      Then("A 502 status should be returned indicating complete downstream failure")
      response.status shouldBe 502

      And("The response should contain failure status")
      val responseBody = Json.parse(response.body)

      (responseBody \ "status").as[String] shouldBe "FAILURE"

      // Verify all downstream services failed
      val downStreams = (responseBody \ "downStreams").as[JsArray]
      downStreams.value.foreach(downstream => (downstream \ "status").as[String] shouldBe "FAILURE")

      println(s"The Response Status Code is : ${response.status} ${response.statusText}")
      println(s"Complete Failure Response Body : ${response.body}")
    }

    Scenario(s"BSP_PTC005_400: Verify API validation failure when required NICC details field is empty") {
      Given(s"The Benefit eligibility Info API is up and running for BSP")
      When(s"A request for BSP is sent with empty dateOfBirth in NICC")

      val payloadKey = s"BSP_PTC005"
      val payload    = PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))
      println(payload)
      val response = bspService.makeRequest(payload, payloadKey)
      val json     = Json.parse(response.body)

      Then("A 400 status should be returned indicating request validation failure")
      response.status shouldBe 400
      assertErrorResponse(json, "BAD_REQUEST", "incompatible json, request body does not match schema")

      println(s"The Response Status Code is : ${response.status} ${response.statusText}")
      println(s"The Response Body is : ${response.body}")
    }

    ignore(s"BSP_PTC006_422: Verify API validation failure when using invalid NICC field") {
      Given(s"The Benefit eligibility Info API is up and running for BSP")
      When(s"A request for BSP is sent with invalid searchCategories entry")

      val payloadKey = s"BSP_PTC006"
      val payload    = PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))
      println(payload)
      val response = bspService.makeRequest(payload, payloadKey)
      val json     = Json.parse(response.body)

      Then("A 422 status should be returned indicating request validation failure")
      response.status shouldBe 422
      assertErrorResponse(json, "UNPROCESSABLE_ENTITY", "Missing Header CorrelationId")

      println(s"The Response Status Code is : ${response.status} ${response.statusText}")
      println(s"The Response Body is : ${response.body}")
    }

  }

  def assertErrorResponse(
      json: JsValue,
      expectedCode: String,
      expectedReason: String
  ): Unit = {
    (json \ "code").as[String] shouldBe expectedCode
    (json \ "reason").as[String] shouldBe expectedReason
  }

  private def assertBSPResponse(payload: BSPRequest, response: StandaloneWSRequest#Response) = {
    response.status shouldBe 200

    val result: BSPResponse = Json.parse(response.body).as[BSPResponse]

    Then("All major response sections should contain valid data")

    // --------------------------------------------------
    // Basic response checks
    // --------------------------------------------------
    result.benefitType shouldBe payload.benefitType
    result.nationalInsuranceNumber shouldBe payload.nationalInsuranceNumber

    // --------------------------------------------------
    // Marriage Details Result
    // --------------------------------------------------
    result.marriageDetailsResult should not be null
    result.marriageDetailsResult.marriageDetails should not be empty

    val firstMarriageDetail = result.marriageDetailsResult.marriageDetails.head
    firstMarriageDetail.status should not be empty

    // --------------------------------------------------
    // NI Contributions & Credits
    // --------------------------------------------------
    result.niContributionsAndCreditsResult should not be null

    // Class 1 contributions (optional for BSP)
    result.niContributionsAndCreditsResult.class1ContributionAndCredits match {
      case Some(list) =>
        list should not be empty
        list.exists(_.contributionCategory.contains("STANDARD RATE")) shouldBe true
      case None => succeed // optional for BSP
    }

    // Class 2 contributions (optional for BSP)
    result.niContributionsAndCreditsResult.class2ContributionAndCredits match {
      case Some(list) =>
        list should not be empty
        list.exists(_.contributionCreditType == "2B") shouldBe true
      case None => succeed // optional for BSP
    }

    result
  }

}
