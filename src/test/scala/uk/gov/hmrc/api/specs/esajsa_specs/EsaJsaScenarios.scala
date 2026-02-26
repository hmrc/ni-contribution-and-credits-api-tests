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

package uk.gov.hmrc.api.specs.esajsa_specs

import org.scalatest.BeforeAndAfterAll
import play.api.libs.json
import play.api.libs.json.*
import uk.gov.hmrc.api.helpers.BaseHelper
import uk.gov.hmrc.api.models.common.DownstreamErrorResponse
import uk.gov.hmrc.api.models.esajsa.{EsaJsaRequest, EsaJsaResponse}
import uk.gov.hmrc.api.service.EsaJsaService
import uk.gov.hmrc.api.specs.BaseSpec
import uk.gov.hmrc.api.utils.JsonUtils

class EsaJsaScenarios extends BaseSpec with BaseHelper with BeforeAndAfterAll {

  val benefitTypes                               = Seq("JSA", "ESA") // Run tests for both benefit types
  val esaJsaService                              = new EsaJsaService
  var PayloadMapping: Map[String, EsaJsaRequest] = _

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

  benefitTypes.foreach { benefitType =>
    Feature(s"Test Scenarios for $benefitType Benefit Type") {

      Scenario(s"${benefitType}_PTC001: Retrieve Class 1 and Class 2 contributions for given valid NINO with suffix") {
        Given(s"The Benefit eligibility Info API is up and running for $benefitType")
        When(s"A request for $benefitType is sent")
        // Get test payload
        val payloadKey = s"${benefitType}_PTC001"
        val payload    = PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))
        // Make API call and build the request
        val response = esaJsaService.makeRequest(payload, payloadKey)

        response.status shouldBe 200
        // Parse JSON into case class
        val result: EsaJsaResponse = Json.parse(response.body).as[EsaJsaResponse]
        val contributions          = result.niContributionsAndCreditsResult

        // Basic response checks
        result.benefitType shouldBe payload.benefitType
        result.nationalInsuranceNumber shouldBe payload.nationalInsuranceNumber

        Then("Class 1 and Class 2 contributions details should be returned")

        // Class 1: must exist, not empty, and contain "STANDARD RATE"
        contributions.class1ContributionAndCredits match {
          case Some(list) =>
            list should not be empty
            list.exists(_.contributionCategory.contains("STANDARD RATE")) shouldBe true
          case None => fail("Class 1 contributions are missing in the response")
        }
        // Class 2: must exist, not empty, and contain "2B"
        contributions.class2ContributionAndCredits match {
          case Some(list) =>
            list should not be empty
            list.exists(_.contributionCreditType == "2B") shouldBe true
          case None => fail("Class 2 contributions are missing in the response")
        }
        // Print response details
        println(s"The Response Status Code is : ${response.status} ${response.statusText}")
        println(s"""The Response Body is :
                  ${Json.prettyPrint(Json.toJson(result))}""")
      }

      Scenario(s"${benefitType}_PTC002: Retrieve Class 1 and Class 2 contributions for given NINO without suffix") {
        Given(s"The Benefit eligibility Info API is up and running for $benefitType")
        When(s"A request for $benefitType is sent")
        // Get test payload
        val payloadKey = s"${benefitType}_PTC002"
        val payload    = PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))
        // Make API call and build the request
        val response = esaJsaService.makeRequest(payload, payloadKey)

        response.status shouldBe 200
        // Parse JSON into case class
        val result: EsaJsaResponse = Json.parse(response.body).as[EsaJsaResponse]
        val contributions          = result.niContributionsAndCreditsResult
        // Basic response checks
        result.benefitType shouldBe payload.benefitType
        result.nationalInsuranceNumber shouldBe payload.nationalInsuranceNumber

        Then("Class 1 and Class 2 contributions details should be returned")
        // Class 1: must exist, not empty, and contain "STANDARD RATE"
        contributions.class1ContributionAndCredits match {
          case Some(list) =>
            list should not be empty
            list.exists(_.contributionCategory.contains("STANDARD RATE")) shouldBe true
          case None => fail("Class 1 contributions are missing in the response")
        }
        // Class 2: must exist, not empty, and contain "2B"
        contributions.class2ContributionAndCredits match {
          case Some(list) =>
            list should not be empty
            list.exists(_.contributionCreditType == "2B") shouldBe true
          case None => fail("Class 2 contributions are missing in the response")
        }
        println(s"The Response Status Code is : ${response.status} ${response.statusText}")
        println(s"""The Response Body is :
                  ${Json.prettyPrint(Json.toJson(result))}""")
      }

      Scenario(s"${benefitType}_PTC003: Retrieve only Class 2 contributions for given NINO") {
        Given(s"The Benefit eligibility Info API is up and running for $benefitType")
        When(s"A request for $benefitType is sent")
        // Get test payload
        val payloadKey = s"${benefitType}_PTC003"
        val payload    = PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))
        // Make API call and build the request
        val response = esaJsaService.makeRequest(payload, payloadKey)

        response.status shouldBe 200
        // Parse JSON into case class
        val result: EsaJsaResponse = Json.parse(response.body).as[EsaJsaResponse]
        val contributions          = result.niContributionsAndCreditsResult
        // Basic response checks
        result.benefitType shouldBe payload.benefitType
        result.nationalInsuranceNumber shouldBe payload.nationalInsuranceNumber

        Then("Only Class 2 contributions should be returned")
        // Class 1 should be empty
        contributions.class1ContributionAndCredits shouldBe empty
        // Class 2: exists, not empty, contains "C2"
        contributions.class2ContributionAndCredits match {
          case Some(list) =>
            list should not be empty
            list.exists(_.contributionCreditType == "C2") shouldBe true
          case None => fail("Class 2 contributions are missing in the response")
        }
        // Print the response details
        println(s"The Response Status Code is : ${response.status} ${response.statusText}")
        println(s"""The Response Body is :
                  ${Json.prettyPrint(Json.toJson(result))}""")
      }

      Scenario(s"${benefitType}_PTC005: Verify response with empty Class 1 and Class 2 contributions") {
        Given(s"The Benefit eligibility Info API is up and running for $benefitType")
        When(s"A request for $benefitType is sent")
        // Get test payload
        val payloadKey = s"${benefitType}_PTC005"
        val payload    = PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))
        // Make API call and build the request
        val response = esaJsaService.makeRequest(payload, payloadKey)

        response.status shouldBe 200
        // Parse JSON into case class
        val result: EsaJsaResponse = Json.parse(response.body).as[EsaJsaResponse]
        val contributions          = result.niContributionsAndCreditsResult
        // Basic response checks
        result.benefitType shouldBe payload.benefitType
        result.nationalInsuranceNumber shouldBe payload.nationalInsuranceNumber

        Then("Verify Class 1 and Class 2 contributions are empty")
        // Class 1: must be empty or None
        contributions.class1ContributionAndCredits match {
          case Some(list) if list.isEmpty =>
          case Some(_)                    => fail("Class 1 contributions are present but should be empty")
          case None                       =>
        }
        // Class 2: must be empty or None
        contributions.class2ContributionAndCredits match {
          case Some(list) if list.isEmpty =>
          case Some(_)                    => fail("Class 2 contributions are present but should be empty")
          case None                       =>
        }
        // Print the response details
        println(s"The Response Status Code is : ${response.status} ${response.statusText}")
        println(s"""The Response Body is :
                  ${Json.prettyPrint(Json.toJson(result))}""")
      }

      Scenario(s"${benefitType}_NTC001: Retrieve Class 1 and Class 2 contributions with missing correlation ID") {
        Given(s"The Benefit eligibility Info API is up and running for $benefitType")
        When(s"A request for $benefitType is sent without correlation ID")
        // Get test payload
        val payloadKey = s"${benefitType}_NTC001"
        val payload    = PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))
        // Make API call and build the request
        val response = esaJsaService.makeRequestWithoutCorrelationId(payload)
        val json     = Json.parse(response.body)

        response.status shouldBe 400
        (json \ "code").as[String] shouldBe "BAD_REQUEST"
        (json \ "reason").as[String] shouldBe "Missing Header CorrelationId"

        println(s"The Response Status Code is : ${response.status} ${response.statusText}")
        println(s"""The Response Body is :
                  ${Json.prettyPrint(Json.toJson(response.body))}""")
      }

      Scenario(s"${benefitType}_NTC002: Request receives 502 error response from backend in response to 403") {
        Given(s"The Benefit eligibility Info API is up and running for $benefitType")
        When(s"A request for $benefitType is sent")
        // Get test payload
        val payloadKey = s"${benefitType}_NTC002"
        val payload    = PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))
        // Make API call and build the request
        val response = esaJsaService.makeRequest(payload, payloadKey)

        Then("Error response should be 502")
        response.status shouldBe 502
        // Parse JSON into case class
        val errorResponse = Json.parse(response.body).as[DownstreamErrorResponse]

        // Basic response checks
        errorResponse.overallResultStatus shouldBe "FAILURE"
        errorResponse.benefitType shouldBe payload.benefitType
        errorResponse.nationalInsuranceNumber shouldBe payload.nationalInsuranceNumber

        Then("The response body should include the backend error details")
        errorResponse.downStreams.head.status shouldBe "FAILURE"
        errorResponse.downStreams.head.error shouldBe "ACCESS_FORBIDDEN"
        // Print response details
        println(s"Response Status: ${response.status}")
        println(s"Response Body: ${Json.prettyPrint(Json.parse(response.body))}")
      }

    }
  }

}
