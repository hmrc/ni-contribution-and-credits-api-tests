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

package uk.gov.hmrc.api.specs.jsa_specs

import org.scalatest.BeforeAndAfterAll
import play.api.libs.json.*
import uk.gov.hmrc.api.helpers.BaseHelper
import uk.gov.hmrc.api.models.common.DownstreamErrorResponse
import uk.gov.hmrc.api.models.jsa.{JSARequest, JSAResponse, NIContributionsAndCredits}
import uk.gov.hmrc.api.specs.BaseSpec
import uk.gov.hmrc.api.utils.JsonUtils

class JSAScenarios extends BaseSpec with BaseHelper with BeforeAndAfterAll {

  var PayloadMapping: Map[String, JSARequest] = _

  override def beforeAll(): Unit = {
    super.beforeAll()
    val jsonString = JsonUtils.readJsonFile("src/test/scala/uk/gov/hmrc/api/testData/JSA_TestData.json")
    PayloadMapping = JsonUtils.parseJsonToJSARequestMap(jsonString) match {
      case Left(failure) => fail(s"Parsing failed: $failure")
      case Right(map)    => map
    }
  }

  Feature("Test Scenarios for JSA Benefit Type") {

    Scenario(
      "JSA_PTC001: Retrieve Class 1 and Class 2 contributions for given valid NINO with suffix"
    ) {
      Given("The Benefit eligibility Info API is up and running")
      When("A request for JSA is sent")
      // Get test payload
      val payload = PayloadMapping.getOrElse("JSA_PTC001", fail("JSA_PTC001 not found"))
      // Make API call and build the request
      val response = niccService.makeJSARequest(
        JSARequest(
          benefitType = payload.benefitType,
          nationalInsuranceNumber = payload.nationalInsuranceNumber,
          niContributionsAndCredits = NIContributionsAndCredits(
            dateOfBirth = payload.niContributionsAndCredits.dateOfBirth,
            startTaxYear = payload.niContributionsAndCredits.startTaxYear,
            endTaxYear = payload.niContributionsAndCredits.endTaxYear
          )
        )
      )
      response.status shouldBe 200
      // Parse JSON into case class
      val jsaResponse: JSAResponse = Json.parse(response.body).as[JSAResponse]
      val result                   = jsaResponse.niContributionsAndCreditsResult
      // Basic response checks
      jsaResponse.benefitType shouldBe payload.benefitType
      jsaResponse.nationalInsuranceNumber shouldBe payload.nationalInsuranceNumber

      Then("Class 1 and Class 2 contributions details should be returned")

      // Class 1 contributions: list non-empty + contains expected value
      result.class1ContributionAndCredits.get should not be empty
      result.class1ContributionAndCredits.get.exists(_.contributionCategory.contains("STANDARD RATE")) shouldBe true
      // Class 2 contributions: list non-empty + contains expected value
      result.class2ContributionAndCredits.get should not be empty
      result.class2ContributionAndCredits.get.exists(_.contributionCreditType == "2B") shouldBe true

      // Print response details
      println(s"The Response Status Code is : ${response.status} ${response.statusText}")
      println(s"""The Response Body is :
                ${Json.prettyPrint(Json.toJson(jsaResponse))}""")
    }

    Scenario("JSA_PTC002: Retrieve Class 1 and Class 2 contributions for given NINO without suffix") {
      Given("The Benefit eligibility Info is up and running")
      When("A request for JSA is sent")
      // Get test payload
      val payload = PayloadMapping.getOrElse("JSA_PTC002", fail("JSA_PTC002 not found"))
      // Make API call and build the request
      val response = niccService.makeJSARequest(
        JSARequest(
          benefitType = payload.benefitType,
          nationalInsuranceNumber = payload.nationalInsuranceNumber,
          niContributionsAndCredits = NIContributionsAndCredits(
            dateOfBirth = payload.niContributionsAndCredits.dateOfBirth,
            startTaxYear = payload.niContributionsAndCredits.startTaxYear,
            endTaxYear = payload.niContributionsAndCredits.endTaxYear
          )
        )
      )
      response.status shouldBe 200
      // Parse JSON into case class
      val jsaResponse: JSAResponse = Json.parse(response.body).as[JSAResponse]
      val result                   = jsaResponse.niContributionsAndCreditsResult

      // Basic response checks
      jsaResponse.benefitType shouldBe payload.benefitType
      jsaResponse.nationalInsuranceNumber shouldBe payload.nationalInsuranceNumber

      Then("Class 1 and Class 2 contributions details should be returned")

      // Class 1 contributions: list non-empty + contains expected value
      result.class1ContributionAndCredits.get should not be empty
      result.class1ContributionAndCredits.get.exists(_.contributionCategory.contains("STANDARD RATE")) shouldBe true
      // Class 2 contributions: list non-empty + contains expected value
      result.class2ContributionAndCredits.get should not be empty
      result.class2ContributionAndCredits.get.exists(_.contributionCreditType == "2B") shouldBe true

      // Print response details
      println(s"The Response Status Code is : ${response.status} ${response.statusText}")
      println(s"""The Response Body is :
      ${Json.prettyPrint(Json.toJson(jsaResponse))}""")
    }

    Scenario("JSA_PTC003: Retrieve only Class 2 contributions for given NINO") {
      Given("The Benefit eligibility Info API is up and running")
      When("A request for JSA is sent")
      // Get test payload
      val payload = PayloadMapping.getOrElse("JSA_PTC003", fail("JSA_PTC003 not found"))
      // Make API call and build the request
      val response = niccService.makeJSARequest(
        JSARequest(
          benefitType = payload.benefitType,
          nationalInsuranceNumber = payload.nationalInsuranceNumber,
          niContributionsAndCredits = NIContributionsAndCredits(
            dateOfBirth = payload.niContributionsAndCredits.dateOfBirth,
            startTaxYear = payload.niContributionsAndCredits.startTaxYear,
            endTaxYear = payload.niContributionsAndCredits.endTaxYear
          )
        )
      )
      response.status shouldBe 200
      // Parse JSON into case class
      val jsaResponse: JSAResponse = Json.parse(response.body).as[JSAResponse]
      val result                   = jsaResponse.niContributionsAndCreditsResult

      // Basic response checks
      jsaResponse.benefitType shouldBe payload.benefitType
      jsaResponse.nationalInsuranceNumber shouldBe payload.nationalInsuranceNumber

      Then("Only Class 2 contributions should be returned")

      // Assert Class 2 contributions exist
      result.class2ContributionAndCredits.get should not be empty
      // Assert Class 1 contributions do NOT exist
      result.class1ContributionAndCredits shouldBe empty

      // Print response details
      println(s"The Response Status Code is : ${response.status} ${response.statusText}")
      println(s"""The Response Body is :
      ${Json.prettyPrint(Json.toJson(jsaResponse))}""")
    }

    Scenario("JSA_PTC004: Retrieve Class 1 and Class 2 contributions with missing correlation ID") {
      Given("The Benefit eligibility Info API is up and running")
      When("A request for JSA is sent without correlation ID")
      // Get test payload
      val payload = PayloadMapping.getOrElse("JSA_PTC004", fail("JSA_PTC004 not found"))
      // Make API call and build the request
      val response = niccService.makeJSARequest(
        JSARequest(
          benefitType = payload.benefitType,
          nationalInsuranceNumber = payload.nationalInsuranceNumber,
          niContributionsAndCredits = NIContributionsAndCredits(
            dateOfBirth = payload.niContributionsAndCredits.dateOfBirth,
            startTaxYear = payload.niContributionsAndCredits.startTaxYear,
            endTaxYear = payload.niContributionsAndCredits.endTaxYear
          )
        )
      )
      response.status shouldBe 200
      // Parse JSON into case class
      val jsaResponse: JSAResponse = Json.parse(response.body).as[JSAResponse]
      val result                   = jsaResponse.niContributionsAndCreditsResult
      // Basic response checks
      jsaResponse.benefitType shouldBe payload.benefitType
      jsaResponse.nationalInsuranceNumber shouldBe payload.nationalInsuranceNumber

      Then("Class 1 and Class 2 contributions should be returned")

      // Class 1 contributions: list non-empty + contains expected value
      result.class1ContributionAndCredits.get should not be empty
      result.class1ContributionAndCredits.get.exists(_.contributionCategory.contains("STANDARD RATE")) shouldBe true
      // Class 2 contributions: list non-empty + contains expected value
      result.class2ContributionAndCredits.get should not be empty
      result.class2ContributionAndCredits.get.exists(_.contributionCreditType == "2B") shouldBe true

      // Print response details
      println(s"The Response Status Code is : ${response.status} ${response.statusText}")
      println(s"""The Response Body is :
      ${Json.prettyPrint(Json.toJson(jsaResponse))}""")
    }

    Scenario("JSA_PTC005: Verify response with empty Class 1 and Class 2 contributions") {
      Given("The Benefit eligibility Info API is up and running")
      When("A request for JSA is sent")
      // Get test payload
      val payload = PayloadMapping.getOrElse("JSA_PTC005", fail("JSA_PTC005 not found"))
      // Make API call and build the request
      val response = niccService.makeJSARequest(
        JSARequest(
          benefitType = payload.benefitType,
          nationalInsuranceNumber = payload.nationalInsuranceNumber,
          niContributionsAndCredits = NIContributionsAndCredits(
            dateOfBirth = payload.niContributionsAndCredits.dateOfBirth,
            startTaxYear = payload.niContributionsAndCredits.startTaxYear,
            endTaxYear = payload.niContributionsAndCredits.endTaxYear
          )
        )
      )
      response.status shouldBe 200
      // Parse JSON into case class
      val jsaResponse: JSAResponse = Json.parse(response.body).as[JSAResponse]
      val result                   = jsaResponse.niContributionsAndCreditsResult

      // Basic response checks
      jsaResponse.benefitType shouldBe payload.benefitType
      jsaResponse.nationalInsuranceNumber shouldBe payload.nationalInsuranceNumber

      Then("Only Class 2 contributions should be returned")

      // Assert Class 1 contributions do NOT exist
      result.class1ContributionAndCredits match {
        case Some(class1) if class1.isEmpty =>
        // Class 1 is empty, which is valid as expected
        case Some(_) =>
          fail("Class 1 contributions are present but should be empty")
        case None =>
        // Class 1 is None, which is valid if it's missing
      }

      // Assert Class 2 contributions do NOT exist
      result.class2ContributionAndCredits match {
        case Some(class2) if class2.isEmpty =>
        // Class 2 is empty, which is valid as expected
        case Some(_) =>
          fail("Class 2 contributions are present but should be empty")
        case None =>
        // Class 2 is None, which is valid if it's missing
      }

      // Print response details
      println(s"The Response Status Code is : ${response.status} ${response.statusText}")
      println(s"""The Response Body is :
          ${Json.prettyPrint(Json.toJson(jsaResponse))}""")
    }

    Scenario("JSA_PTC006: Request receives 502 error response from backend in response to 403") {

      Given("The Benefit eligibility Info API is up and running")

      When("A request for JSA is sent")
      val payload = PayloadMapping.getOrElse("JSA_PTC006", fail("JSA_PTC006 not found"))

      val response = niccService.makeJSARequest(
        JSARequest(
          benefitType = payload.benefitType,
          nationalInsuranceNumber = payload.nationalInsuranceNumber,
          niContributionsAndCredits = NIContributionsAndCredits(
            dateOfBirth = payload.niContributionsAndCredits.dateOfBirth,
            startTaxYear = payload.niContributionsAndCredits.startTaxYear,
            endTaxYear = payload.niContributionsAndCredits.endTaxYear
          )
        )
      )

      Then("Error response should be 502")
      response.status shouldBe 502

      // Parse JSON correctly
      val jsaErrorResponse = Json.parse(response.body).as[DownstreamErrorResponse]

      // Basic response checks
      jsaErrorResponse.overallResultStatus shouldBe "FAILURE"
      jsaErrorResponse.benefitType shouldBe payload.benefitType
      jsaErrorResponse.nationalInsuranceNumber shouldBe payload.nationalInsuranceNumber

      Then("The response body should include the backend error details")
      jsaErrorResponse.downStreams.head.status shouldBe "FAILURE"
      jsaErrorResponse.downStreams.head.error shouldBe "ACCESS_FORBIDDEN"

      println(s"Response Status: ${response.status}")
      println(s"Response Body: ${Json.prettyPrint(Json.parse(response.body))}")
    }
  }

}
