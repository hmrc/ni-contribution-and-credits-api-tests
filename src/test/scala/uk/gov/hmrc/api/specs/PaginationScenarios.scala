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

package uk.gov.hmrc.api.specs

import play.api.libs.json.Json
import play.api.libs.ws.StandaloneWSRequest
import uk.gov.hmrc.api.service.{GyspService, MAService, PaginationService}
import uk.gov.hmrc.api.specs.bsp_specs.BSPBaseSpec
import uk.gov.hmrc.api.utils.JsonUtils

class PaginationScenarios extends BSPBaseSpec {

  val paginationService = new PaginationService
  val gyspService       = new GyspService
  val maService         = new MAService

  def assertValidPagination(
      postResponse: StandaloneWSRequest#Response,
      expectedBenefitType: String,
      expectedNino: String
  ): Unit = {

    Then("the POST response should contain a valid cursor")

    postResponse.status shouldBe 200

    val postJson = Json.parse(postResponse.body)
    val cursor   = (postJson \ "nextCursor").as[String]

    cursor should not be empty

    When("the returned cursor is used to retrieve the next page")

    val getResponse = paginationService.getPage(cursor)

    Then("the GET request should return a successful response")

    getResponse.status shouldBe 200

    val getJson = Json.parse(getResponse.body)

    (getJson \ "benefitType").as[String] shouldBe expectedBenefitType
    (getJson \ "nationalInsuranceNumber").as[String] shouldBe expectedNino
  }

  Feature("Benefit Eligibility API Pagination") {

    Scenario("BSP: retrieve next page using cursor returned from POST") {
      Given("a valid BSP request")
      val payload = getPayload("BSP_PTC001")
      When("the POST request is submitted")
      val response = bspService.makeRequest(payload)

      assertValidPagination(
        response,
        payload.benefitType,
        payload.nationalInsuranceNumber
      )
    }

    Scenario("GYSP: retrieve next page using cursor returned from POST") {
      Given("a valid GYSP request")
      val jsonString = JsonUtils.readJsonFile(
        "src/test/scala/uk/gov/hmrc/api/testData/Gysp_TestData.json"
      )
      val payloadMap = JsonUtils.parseJsonToGyspRequestMap(jsonString) match {
        case Left(error) => fail(s"GYSP test data parsing failed: $error")
        case Right(map)  => map
      }

      val payload = payloadMap.getOrElse(
        "GYSP_PTC001",
        fail("GYSP_PTC001 not found")
      )

      When("the POST request is submitted")

      val response = gyspService.makeRequest(payload)

      assertValidPagination(
        response,
        payload.benefitType,
        payload.nationalInsuranceNumber
      )
    }

    Scenario("MA: retrieve next page using cursor returned from POST") {

      Given("a valid MA request")
      val jsonString = JsonUtils.readJsonFile(
        "src/test/scala/uk/gov/hmrc/api/testData/MA_TestData.json"
      )

      val payloadMap = JsonUtils.parseJsonToMARequestMap(jsonString) match {
        case Left(error) => fail(s"MA test data parsing failed: $error")
        case Right(map)  => map
      }

      val payload = payloadMap.getOrElse(
        "MA_PTC001",
        fail("MA_PTC001 not found")
      )

      When("the POST request is submitted")

      val response = maService.makeRequest(payload)

      assertValidPagination(
        response,
        payload.benefitType,
        payload.nationalInsuranceNumber
      )
    }

    Scenario("Pagination: empty cursorId returns 400") {

      Given("an empty cursorId")
      When("a GET request is submitted with an empty cursor")
      val response = paginationService.getPage("")
      Then("the API should return a bad request response")

      response.status shouldBe 400

      val json = Json.parse(response.body)

      (json \ "code").as[String] shouldBe "BAD_REQUEST"
      (json \ "reason").as[String] shouldBe "invalid cursorId"
    }

    Scenario("Pagination: invalid cursorId returns 400") {
      Given("an invalid cursorId")
      When("a GET request is submitted with an invalid cursor")
      val response = paginationService.getPage("invalid-cursor")
      Then("the API should return a bad request response")

      response.status shouldBe 400

      val json = Json.parse(response.body)

      (json \ "code").as[String] shouldBe "BAD_REQUEST"
      (json \ "reason").as[String] shouldBe "invalid cursorId"
    }

    Scenario("Pagination: missing cursorId returns 400") {

      Given("a GET request without cursorId")

      When("the request is submitted")

      val response = paginationService.getPageWithoutCursor()

      Then("the API should return a missing cursor error")

      response.status shouldBe 400

      val json = Json.parse(response.body)

      (json \ "code").as[String] shouldBe "BAD_REQUEST"
      (json \ "reason").as[String] shouldBe
        "Batch request sent without cursorId"
    }

    Scenario("Pagination: unknown cursorId returns 404") {

      Given("a valid UUID that does not identify an existing batch")

      val cursor = java.util.UUID.randomUUID().toString

      When("the GET request is submitted")

      val response = paginationService.getPage(cursor)

      Then("the API should return a record not found error")

      response.status shouldBe 404

      val json = Json.parse(response.body)

      (json \ "code").as[String] shouldBe "NOT_FOUND"
      (json \ "reason").as[String] shouldBe
        s"record not found for cursorId: $cursor"
    }
  }

}
