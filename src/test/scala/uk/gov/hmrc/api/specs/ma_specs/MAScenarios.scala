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

import org.scalatest.{BeforeAndAfterAll, Ignore}
import play.api.libs.json.{JsArray, Json}
import play.api.libs.ws.StandaloneWSRequest
import uk.gov.hmrc.api.helpers.BaseHelper
import uk.gov.hmrc.api.models.ma.{MARequest, MAResponse}
import uk.gov.hmrc.api.service.MAService
import uk.gov.hmrc.api.specs.BaseSpec
import uk.gov.hmrc.api.utils.JsonUtils

class MAScenarios extends BaseSpec with BaseHelper with BeforeAndAfterAll {

  val maService                              = new MAService
  var PayloadMapping: Map[String, MARequest] = _

  override def beforeAll(): Unit = {
    super.beforeAll()
    val jsonString = JsonUtils.readJsonFile(
      "src/test/scala/uk/gov/hmrc/api/testData/MA_TestData.json"
    )
    println(jsonString)
    PayloadMapping = JsonUtils.parseJsonToMARequestMap(jsonString) match {
      case Left(failure) => fail(s"Parsing failed: $failure")
      case Right(map)    => map
    }
  }

  Feature(s"Test Scenarios for MA Benefit Type") {

    Scenario(s"MA_PTC001: Verify full MA response body for a valid NINO with Suffix") {

      Given("The Benefit eligibility Info API is up and running for MA")

      When("A request for MA is sent")

      val payloadKey = "MA_PTC001"
      val payload    = PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))

      val response = maService.makeRequest(payload, payloadKey)

      val result: MAResponse = assertMAResponse(payload, response)

      println(s"Response Status: ${response.status} ${response.statusText}")
      println(Json.prettyPrint(Json.toJson(result)))
    }

    Scenario(s"MA_PTC002: Verify full MA response body for a valid NINO without suffix") {
      Given(s"The Benefit eligibility Info API is up and running for MA")
      When(s"A request for MA is sent")

      val payloadKey = s"MA_PTC002"
      val payload    = PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))
      println(payload)

      val response = maService.makeRequest(payload, payloadKey)

      val result: MAResponse = assertMAResponse(payload, response)

      println(s"The Response Status Code is : ${response.status} ${response.statusText}")
      println(s"""The Response Body is :
                          ${Json.prettyPrint(Json.toJson(result))}""")
    }

    Scenario(s"MA_PTC003_502: Verify MA partial failure response when some services return errors") {
      Given(s"The Benefit eligibility Info API is up and running for MA")
      When(s"A request for MA is sent and Class2MAReceipts and LiabilitySummary return errors")

      val payloadKey = s"MA_PTC003"
      val payload    = PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))
      println(payload)

      val response = maService.makeRequest(payload, payloadKey)

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

    Scenario(s"MA_PTC004_502: Verify MA complete failure response when all downstream services return errors") {
      Given(s"The Benefit eligibility Info API is up and running for MA")
      When(s"A request for MA is sent and all downstream services return errors")

      val payloadKey = s"MA_PTC004"
      val payload    = PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))
      println(payload)

      val response = maService.makeRequest(payload, payloadKey)

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

    Scenario(s"MA_PTC005_400: Verify API validation failure when required liability fields are empty") {
      Given(s"The Benefit eligibility Info API is up and running for MA")
      When(s"A request for MA is sent with empty searchCategories in liabilities")

      val payloadKey = s"MA_PTC005"
      val payload    = PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))
      println(payload)

      val response = maService.makeRequest(payload, payloadKey)

      Then("A 400 status should be returned indicating request validation failure")
      response.status shouldBe 400

      println(s"The Response Status Code is : ${response.status} ${response.statusText}")
      println(s"The Response Body is : ${response.body}")
    }

    ignore(s"MA_PTC006_422: Verify API validation failure when using invalid liability field") {
      Given(s"The Benefit eligibility Info API is up and running for MA")
      When(s"A request for MA is sent with invalid searchCategories entry")

      val payloadKey = s"MA_PTC006"
      val payload    = PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))
      println(payload)

      val response = maService.makeRequest(payload, payloadKey)

      Then("A 422 status should be returned indicating request validation failure")
      response.status shouldBe 422
      // assertMAResponse()

      println(s"The Response Status Code is : ${response.status} ${response.statusText}")
      println(s"The Response Body is : ${response.body}")
    }

  }

  private def assertMAResponse(payload: MARequest, response: StandaloneWSRequest#Response) = {
    response.status shouldBe 200

    val result: MAResponse = Json.parse(response.body).as[MAResponse]

    Then("All major response sections should contain valid data")

    // --------------------------------------------------
    // Basic response checks
    // --------------------------------------------------
    result.benefitType shouldBe payload.benefitType
    result.nationalInsuranceNumber shouldBe payload.nationalInsuranceNumber

    // --------------------------------------------------
    // Class 2 MA Receipts Result
    // --------------------------------------------------
    result.class2MAReceiptsResult should not be null
    result.class2MAReceiptsResult.receiptDates should not be empty

    // Validate receipt dates are valid and not null
    result.class2MAReceiptsResult.receiptDates.foreach { receiptDate =>
      receiptDate should not be null
      receiptDate.toString should not be empty
    }

    // Check we have at least one receipt date
    result.class2MAReceiptsResult.receiptDates.size should be >= 1

    // Validate receipt dates are in reasonable date range (not in far future/past)
    result.class2MAReceiptsResult.receiptDates.foreach { date =>
      date.getYear should be >= 2000
      date.getYear should be <= 2030
    }

    // --------------------------------------------------
    // Liability Summary Details Result
    // --------------------------------------------------
    result.liabilitySummaryDetailsResult should not be null
    result.liabilitySummaryDetailsResult should not be empty

    // Get the first item from the list and check its liability details
    val firstLiabilityResult = result.liabilitySummaryDetailsResult.head
    firstLiabilityResult.liabilityDetails should not be empty

    // Validate individual liability details
    val firstLiabilityDetail = firstLiabilityResult.liabilityDetails.head
    firstLiabilityDetail.startDate should not be null

    // Validate start date is reasonable
    firstLiabilityDetail.startDate.getYear should be >= 2000
    firstLiabilityDetail.startDate.getYear should be <= 2030

    // If end date exists, validate it
    firstLiabilityDetail.endDate match {
      case Some(endDate) =>
        endDate should not be null
        endDate.getYear should be >= 2000
        endDate.getYear should be <= 2030
        endDate.isBefore(firstLiabilityDetail.startDate) shouldBe false
      case None => succeed
    }

    // Check we have at least one liability detail per liability result
    firstLiabilityResult.liabilityDetails.size should be >= 1

    // --------------------------------------------------
    // NI Contributions & Credits
    // --------------------------------------------------
    result.niContributionsAndCreditsResult should not be null

    // Class 1 contributions (optional for MA)
    result.niContributionsAndCreditsResult.class1ContributionAndCredits match {
      case Some(list) =>
        list should not be empty
        list.exists(_.contributionCategory.contains("STANDARD RATE")) shouldBe true
      case None => succeed // optional for MA
    }

    // Class 2 contributions (important for MA)
    result.niContributionsAndCreditsResult.class2ContributionAndCredits match {
      case Some(list) =>
        list should not be empty
        list.exists(_.contributionCreditType == "2B") shouldBe true
      case None => succeed // optional but expected for MA
    }

    result
  }

}
