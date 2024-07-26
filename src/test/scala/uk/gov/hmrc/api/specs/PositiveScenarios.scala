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

import play.api.libs.json.Format.GenericFormat
import play.api.libs.json._
import uk.gov.hmrc.api.models.{Request, Response}

class PositiveScenarios extends BaseSpec {

  Feature("POSITIVE SCENARIOS") {

    Scenario("NICC_TC_P001: Retrieve Class 1 and Class 2 data for given NINO with suffix") {
      Given("The NICC API is up and running")
      When("A request for NINC is sent")
      val response =
        niccService.makeRequest(Request("BB000200B", "1960-04-05", "e470d658-99f7-4292-a4a1-ed12c72f1337", "2019", "2021"))

      println(Json.parse(response.body))
      val responseBody: Response = Json.parse(response.body).as[Response] //json to case class

      Then("Class 1 and Class 2 details are returned")
      response.status shouldBe 200
      response.body.contains("niClass1") shouldBe true
      response.body.contains("niClass2") shouldBe true
      println("The Response Status Code is : " + response.status + " " + response.statusText)
      println("The Response Body is : \n" + Json.prettyPrint(Json.toJson(responseBody)))

      responseBody.niClass1.head.contributionStatus shouldBe "COMPLIANCE & YIELD INCOMPLETE"
      responseBody.niClass2.head.contributionStatus shouldBe "NOT KNOWN/NOT APPLICABLE"
    }
    Scenario("NICC_TC_P002: Retrieve Class 1 and Class 2 data for given NINO without suffix") {
      Given("The NICC API is up and running")
      When("A request for NINC is sent")
      val response =
        niccService.makeRequest(Request("BB000200", "1960-04-05", "e470d658-99f7-4292-a4a1-ed12c72f1337", "2019", "2021"))

      println(Json.parse(response.body))
      val responseBody: Response = Json.parse(response.body).as[Response] //json to case class

      Then("Class 1 and Class 2 details are returned")
      response.status shouldBe 200
      response.body.contains("niClass1") shouldBe true
      response.body.contains("niClass2") shouldBe true
      println("The Response Status Code is : " + response.status + " " + response.statusText)
      println("The Response Body is : \n" + Json.prettyPrint(Json.toJson(responseBody)))

      responseBody.niClass1.head.contributionStatus shouldBe "COMPLIANCE & YIELD INCOMPLETE"
      responseBody.niClass2.head.contributionStatus shouldBe "NOT KNOWN/NOT APPLICABLE"
    }
    Scenario("NICC_TC_P003: Retrieve Only Class 1 data for given NINO") {
      Given("The NICC API is up and running")
      When("A request for NINC is sent")
      //pending
    }

  }


}