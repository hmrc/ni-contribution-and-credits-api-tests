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

package uk.gov.hmrc.api.specs.JSA_Spec

import org.scalatest.BeforeAndAfterAll
import play.api.libs.json.*
import uk.gov.hmrc.api.helpers.BaseHelper
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

  Feature("POSITIVE SCENARIOS for JSA Benefit Type") {

    Scenario("JSA_PTC001: Retrieve Class 1 and Class 2 contributions for given NINO") {
      Given("The NICC API is up and running")
      When("A request for JSA is sent")

      val payload = PayloadMapping.getOrElse("JSA_PTC001", fail("JSA_PTC001 not found"))

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

      val jsaResponse: JSAResponse = Json.parse(response.body).as[JSAResponse]

      println(s"""The Response Body is :
      ${Json.prettyPrint(Json.toJson(jsaResponse))}""")

      Then("Class 1 and Class 2 contributions details should be returned")
      response.status shouldBe 200
      jsaResponse.benefitType shouldBe "JSA"
      jsaResponse.nationalInsuranceNumber shouldBe "AA000002A"

      println(s"The Response Status Code is : ${response.status} ${response.statusText}")
      jsaResponse.niContributionsAndCreditsResult.class1ContributionAndCredits.get.head.contributionCategory.get shouldBe "REDUCED RATE"
      jsaResponse.niContributionsAndCreditsResult.class2ContributionAndCredits.get.head.contributionCreditType shouldBe "C2"
    }

//    Scenario("JSA_TC_P002: Retrieve only Class 1 contributions for given NINO") {
//      Given("The JSA API is up and running")
//      When("A request for JSA is sent")
//      val payload = PayloadMapping.getOrElse("JSA_TC_P002", fail("JSA_TC_P002 not found"))
//
//      val response =
//        niccService.makeJSARequest(
//          JSARequest(
//            payload.nationalInsuranceNumber,
//            payload.dateOfBirth,
//            payload.startTaxYear,
//            payload.endTaxYear
//          )
//        )
//
//      val responseBody = Json.parse(response.body)
//
//      Then("Only Class 1 contributions should be returned")
//      response.status shouldBe 200
//      response.body.contains("class1") shouldBe true
//      response.body.contains("class2") shouldBe false
//      println(s"The Response Status Code is : ${response.status} ${response.statusText}")
//      println(s"""The Response Body is :
//${Json.prettyPrint(Json.toJson(responseBody))}""")
//    }
//
//    Scenario("JSA_TC_P003: Retrieve only Class 2 contributions for given NINO") {
//      Given("The JSA API is up and running")
//      When("A request for JSA is sent")
//      val payload = PayloadMapping.getOrElse("JSA_TC_P003", fail("JSA_TC_P003 not found"))
//
//      val response =
//        niccService.makeJSARequest(
//          JSARequest(
//            payload.nationalInsuranceNumber,
//            payload.dateOfBirth,
//            payload.startTaxYear,
//            payload.endTaxYear
//          )
//        )
//
//      Then("Only Class 2 contributions should be returned")
//      response.status shouldBe 200
//      response.body.contains("class2") shouldBe true
//      response.body.contains("class1") shouldBe false
//      println(s"The Response Status Code is : ${response.status} ${response.statusText}")
//      println(s"""The Response Body is :
//${Json.prettyPrint(Json.parse(response.body))}""")
//    }
//
//    Scenario("JSA_TC_P004: Retrieve Class 1 and Class 2 contributions with missing correlation ID") {
//      Given("The JSA API is up and running")
//      When("A request for JSA is sent")
//      val payload = PayloadMapping.getOrElse("JSA_TC_P004", fail("JSA_TC_P004 not found"))
//
//      val response =
//        niccService.makeJSARequest(
//          JSARequest(
//            payload.nationalInsuranceNumber,
//            payload.dateOfBirth,
//            payload.startTaxYear,
//            payload.endTaxYear
//          )
//        )
//
//      val responseBody: JSAResponse = Json.parse(response.body).as[JSAResponse]
//
//      Then("Class 1 and Class 2 contributions should be returned")
//      response.status shouldBe 200
//      response.body.contains("class1") shouldBe true
//      response.body.contains("class2") shouldBe true
//      println(s"The Response Status Code is : ${response.status} ${response.statusText}")
//      println(s"""The Response Body is :
//${Json.prettyPrint(Json.toJson(responseBody))}""")
//    }
//
//    Scenario("JSA_TC_P005: Retrieve empty response for invalid NINO") {
//      Given("The JSA API is up and running")
//      When("A request for JSA is sent with an invalid NINO")
//      val payload = PayloadMapping.getOrElse("JSA_TC_P005", fail("JSA_TC_P005 not found"))
//
//      val response =
//        niccService.makeJSARequest(
//          JSARequest(
//            "INVALID_NINO", // Invalid NINO
//            payload.dateOfBirth,
//            payload.startTaxYear,
//            payload.endTaxYear
//          )
//        )
//
//      Then("An empty response should be returned")
//      response.status shouldBe 200
//      response.body shouldBe Json.obj()
//      println(s"The Response Status Code is : ${response.status} ${response.statusText}")
//      println(s"""The Response Body is :
//${Json.prettyPrint(Json.parse(response.body))}""")
//    }
//
//    Scenario("JSA_TC_P006: Retrieve response for NINO and DOB greater than 16 years old") {
//      Given("The JSA API is up and running")
//      And("Validate the given dob is greater than 16 years old")
//      val payload = PayloadMapping.getOrElse("JSA_TC_P006", fail("JSA_TC_P006 not found"))
//      ValidateDOB(payload.dateOfBirth)
//
//      When("A request for JSA is sent")
//      val response =
//        niccService.makeJSARequest(
//          JSARequest(
//            payload.nationalInsuranceNumber,
//            payload.dateOfBirth,
//            payload.startTaxYear,
//            payload.endTaxYear
//          )
//        )
//
//      Then("The response should be 200")
//      response.status shouldBe 200
//      println(s"The Response Status Code is : ${response.status} ${response.statusText}")
//
//      And("Class 1 and Class 2 contributions should be returned")
//      val responseBody: JSAResponse = Json.parse(response.body).as[JSAResponse]
//      println(s"""The Response Body is :
//${Json.prettyPrint(Json.toJson(responseBody))}""")
//      response.body.contains("class1") shouldBe true
//      response.body.contains("class2") shouldBe true
//    }
//
//    Scenario("JSA_TC_P007: Verify response with empty Class 1 and Class 2 contributions") {
//      Given("The JSA API is up and running")
//      When("A request for JSA is sent")
//      val payload = PayloadMapping.getOrElse("JSA_TC_P007", fail("JSA_TC_P007 not found"))
//
//      val response =
//        niccService.makeJSARequest(
//          JSARequest(
//            payload.nationalInsuranceNumber,
//            payload.dateOfBirth,
//            payload.startTaxYear,
//            payload.endTaxYear
//          )
//        )
//
//      Then("The response should be 200 with empty Class 1 and Class 2")
//      response.status shouldBe 200
//      response.body.contains("class1") shouldBe false
//      response.body.contains("class2") shouldBe false
//      println(s"The Response Status Code is : ${response.status} ${response.statusText}")
//      println(s"""The Response Body is :
//${Json.prettyPrint(Json.parse(response.body))}""")
//    }

  }

}
