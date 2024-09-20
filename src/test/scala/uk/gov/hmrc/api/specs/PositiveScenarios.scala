/*
 * Copyright 2024 HM Revenue & Customs
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

import org.scalatest.BeforeAndAfterAll
import play.api.libs.json.Format.GenericFormat
import play.api.libs.json._
import uk.gov.hmrc.api.helpers.BaseHelper
import uk.gov.hmrc.api.models.{Request, Response}
import uk.gov.hmrc.api.utils.JsonUtils

class PositiveScenarios extends BaseSpec with BaseHelper with BeforeAndAfterAll {

  var PayloadMapping: Map[String, Request] = _

  override def beforeAll(): Unit = {
    super.beforeAll()
    val jsonString = JsonUtils.readJsonFile("uk/gov/hmrc/api/testData/TestData_P001_to_P007.json")
    PayloadMapping = JsonUtils.parseJsonToMap(jsonString) match {
      case Left(failure) => fail(s"Parsing failed: $failure")
      case Right(map)    => map
    }
  }
  Feature("POSITIVE SCENARIOS") {

    Scenario("NICC_TC_P001: Retrieve Class 1 and Class 2 data for given NINO with suffix") {
      Given("The NICC API is up and running")
      When("A request for NICC is sent")

      val payload = PayloadMapping.getOrElse("NICC_TC_P001", fail("NICC_TC_P001 not found"))

      val response =
        niccService.makeRequest(
          Request(
            payload.nationalInsuranceNumber,
            payload.dateOfBirth,
            payload.customerCorrelationID,
            payload.startTaxYear,
            payload.endTaxYear
          )
        )

      println(Json.parse(response.body))
      val responseBody: Response = Json.parse(response.body).as[Response] // json to case class

      Then("Class 1 and Class 2 details are returned")
      response.status                    shouldBe 200
      response.body.contains("niClass1") shouldBe true
      response.body.contains("niClass2") shouldBe true
      println("The Response Status Code is : " + response.status + " " + response.statusText)
      println("The Response Body is : \n" + Json.prettyPrint(Json.toJson(responseBody)))

      responseBody.niClass1.get.head.contributionStatus shouldBe "COMPLIANCE & YIELD INCOMPLETE"
      responseBody.niClass2.get.head.contributionStatus shouldBe "NOT KNOWN/NOT APPLICABLE"
    }
    Scenario("NICC_TC_P002: Retrieve Class 1 and Class 2 data for given NINO without suffix") {
      Given("The NICC API is up and running")
      When("A request for NINC is sent")

      val payload = PayloadMapping.getOrElse("NICC_TC_P002", fail("NICC_TC_P002 not found"))

      val response =
        niccService.makeRequest(
          Request(
            payload.nationalInsuranceNumber,
            payload.dateOfBirth,
            payload.customerCorrelationID,
            payload.startTaxYear,
            payload.endTaxYear
          )
        )

      println(Json.parse(response.body))
      val responseBody: Response = Json.parse(response.body).as[Response] // json to case class

      Then("Class 1 and Class 2 details are returned")
      response.status                    shouldBe 200
      response.body.contains("niClass1") shouldBe true
      response.body.contains("niClass2") shouldBe true
      println("The Response Status Code is : " + response.status + " " + response.statusText)
      println("The Response Body is : \n" + Json.prettyPrint(Json.toJson(responseBody)))

      responseBody.niClass1.get.head.contributionStatus shouldBe "COMPLIANCE & YIELD INCOMPLETE"
      responseBody.niClass2.get.head.contributionStatus shouldBe "NOT KNOWN/NOT APPLICABLE"
    }

    Scenario("NICC_TC_P003: Retrieve Only Class 1 data for given NINO") {
      Given("The NICC API is up and running")
      When("A request for NINC is sent")
      val payload = PayloadMapping.getOrElse("NICC_TC_P003", fail("NICC_TC_P003 not found"))

      val response =
        niccService.makeRequest(
          Request(
            payload.nationalInsuranceNumber,
            payload.dateOfBirth,
            payload.customerCorrelationID,
            payload.startTaxYear,
            payload.endTaxYear
          )
        )

      //println(Json.parse(response.body))
      val responseBody = Json.parse(response.body)
      Then("Only Class 1 details are returned")
      response.status                    shouldBe 200
      response.body.contains("niClass1") shouldBe true
      response.body.contains("niClass2") shouldBe false
      println("The Response Status Code is : " + response.status + " " + response.statusText)
      println("The Response Body is : \n" + Json.prettyPrint(Json.toJson(responseBody)))

    }
    Scenario("NICC_TC_P004: Retrieve Class 1 and Class 2 data for given NINO without customer correlationID") {
      Given("The NICC API is up and running")
      When("A request for NICC is sent")
      val payload = PayloadMapping.getOrElse("NICC_TC_P004", fail("NICC_TC_P004 not found"))

      val response =
        niccService.makeRequest(
          Request(
            payload.nationalInsuranceNumber,
            payload.dateOfBirth,
            payload.customerCorrelationID,
            payload.startTaxYear,
            payload.endTaxYear
          )
        )

      println(Json.parse(response.body))
      val responseBody: Response = Json.parse(response.body).as[Response] // json to case class

      Then("Class 1 and Class 2 details are returned")
      response.status                    shouldBe 200
      response.body.contains("niClass1") shouldBe true
      response.body.contains("niClass2") shouldBe true
      println("The Response Status Code is : " + response.status + " " + response.statusText)
      println("The Response Body is : \n" + Json.prettyPrint(Json.toJson(responseBody)))

      responseBody.niClass1.get.head.contributionStatus shouldBe "COMPLIANCE & YIELD INCOMPLETE"
      responseBody.niClass2.get.head.contributionStatus shouldBe "NOT KNOWN/NOT APPLICABLE"
    }

    Scenario("NICC_TC_P005: Retrieve only Class 2 data for given NINO") {
      Given("The NICC API is up and running")
      When("A request for NICC is sent")

      val payload = PayloadMapping.getOrElse("NICC_TC_P005", fail("NICC_TC_P005 not found"))

      val response =
        niccService.makeRequest(
          Request(
            payload.nationalInsuranceNumber,
            payload.dateOfBirth,
            payload.customerCorrelationID,
            payload.startTaxYear,
            payload.endTaxYear
          )
        )
      /*val response =
        niccService.makeRequest(
          Request("WP103133", "1970-03-12", Some("e470d658-99f7-4292-a4a1-ed12c72f1337"), "2019", "2023")
        )*/

      println(Json.parse(response.body))
      val responseBody: Response = Json.parse(response.body).as[Response] // json to case class

      Then("Class 2 details are returned")
      response.status                    shouldBe 200
      response.body.contains("niClass2") shouldBe true
      println("The Response Status Code is : " + response.status + " " + response.statusText)
      println("The Response Body is : \n" + Json.prettyPrint(Json.toJson(responseBody)))

      responseBody.niClass2.get.head.contributionStatus shouldBe "VALID"
    }

    Scenario("NICC_TC_P006: Retrieve only Class 2 data for given NINO and date of birth is 1956-10-03") {
      Given("The NICC API is up and running")
      When("A request for NICC is sent")
      val payload = PayloadMapping.getOrElse("NICC_TC_P006", fail("NICC_TC_P006 not found"))

      val response =
        niccService.makeRequest(
          Request(
            payload.nationalInsuranceNumber,
            payload.dateOfBirth,
            payload.customerCorrelationID,
            payload.startTaxYear,
            payload.endTaxYear
          )
        )

      println(Json.parse(response.body))
      val responseBody: Response = Json.parse(response.body).as[Response]

      Then("Class 2 details are returned")
      response.status                    shouldBe 200
      response.body.contains("niClass2") shouldBe true
      println("The Response Status Code is : " + response.status + " " + response.statusText)
      println("The Response Body is : \n" + Json.prettyPrint(Json.toJson(responseBody)))

      responseBody.niClass2.get.head.contributionStatus shouldBe "VALID"
    }

    Scenario("NICC_TC_P007: Retrieve null for given NINO") {
      Given("The NICC API is up and running")
      And("Validate the given NINO is greater than 16 years old")
      val payload = PayloadMapping.getOrElse("NICC_TC_P007", fail("NICC_TC_P007 not found"))
      ValidateDOB(payload.dateOfBirth)
      ValidateStartTaxYear(payload.startTaxYear)

      When("A request for NICC is sent")
      val response =
        niccService.makeRequest(
          Request(
            payload.nationalInsuranceNumber,
            payload.dateOfBirth,
            payload.customerCorrelationID,
            payload.startTaxYear,
            payload.endTaxYear
          )
        )

      // println(Json.parse(response.body))
      val responseBody: Response = Json.parse(response.body).as[Response] // json to case class
      println("The Response Body is : \n" + Json.prettyPrint(Json.toJson(responseBody)))

      Then("No Response Body returned")
      response.status shouldBe 200
      println("The Response Status Code is : " + response.status + " " + response.statusText)

    }

  }

}
