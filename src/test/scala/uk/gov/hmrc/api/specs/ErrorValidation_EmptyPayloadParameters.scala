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

import play.api.libs.json.Json
import uk.gov.hmrc.api.helpers.BaseHelper
import uk.gov.hmrc.api.models.Request
import play.api.libs.ws.DefaultBodyReadables.readableAsString

class ErrorValidation_EmptyPayloadParameters extends BaseSpec with BaseHelper {

  val badRequestErrorResponse =
    "{\"failures\":[{\"reason\":\"There was a problem with the request\",\"code\":\"400\"}]}"

  Feature("VALIDATION OF ERROR CODES FOR MISSING INPUT") {

    val expectedTypes = Map(
      "taxYear"                    -> "number",
      "niContributionCategory"     -> "string",
      "niContributionCategoryName" -> "string",
      "niContributionType"         -> "string",
      "totalPrimaryContribution"   -> "number",
      "contributionStatus"         -> "string",
      "totalEarningsFactor"        -> "number"
    )

    Scenario("Request with missing NINO receives error response 400 from MDTP") {
      val response =
        niccService.makeRequest(Request("", "1960-04-05", Some("e470d658-99f7-4292-a4a1-ed12c72f1337"), "2019", "2021"))
      response.status shouldBe 400
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body shouldBe badRequestErrorResponse
      val responseBody = Json.parse(response.body)
      println("The Response Body is : \n" + Json.prettyPrint(Json.toJson(responseBody)))
    }

    Scenario("Verify the request with missing Date of Birth receives error response 400") {
      val response =
        niccService.makeRequest(Request("BB000200B", "", Some("e470d658-99f7-4292-a4a1-ed12c72f1337"), "2019", "2021"))
      response.status shouldBe 400
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body shouldBe badRequestErrorResponse
      println("Response Body is: " + response.body)
    }

    Scenario("Verify the request with missing start tax year receives error response 400 from MDTP") {
      val response =
        niccService.makeRequest(
          Request("BB000200B", "1960-04-05", Some("e470d658-99f7-4292-a4a1-ed12c72f1337"), "", "2021")
        )
      response.status shouldBe 400
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body shouldBe badRequestErrorResponse
      println("Response Body is: " + response.body)
    }

    Scenario("Verify the request with missing end tax year receives error response 400 from MDTP") {
      val response =
        niccService.makeRequest(
          Request("BB000200B", "1960-04-05", Some("e470d658-99f7-4292-a4a1-ed12c72f1337"), "2019", "")
        )
      response.status shouldBe 400
      checkResponseStatus(response.status, 400)

      println("Response Status Code is : " + response.status + " " + response.statusText)
      println("Response Body is: " + response.body)
      response.body shouldBe badRequestErrorResponse
    }

    Scenario("Request with missing customer correlation ID receives error response 400 from MDTP") {
      Given("the NINO API is up and running")
      When(
        "user sends a POST request to retrieve niClass details for a valid NINO"
      )
      val ninono = "BB000200B"
      checkNINOFormat(ninono)

      val response =
        niccService.makeRequest(Request(ninono, "1960-04-05", Some(""), "2019", "2020"))

      Then("the response status code should be 200")

      response.status shouldBe 200
      checkResponseStatus(response.status, 200)

      And("the response body should have the array with niClass details as expected")

      val responseBody = response.body
      checkResponsePayload(responseBody, expectedTypes)
      val responseBody1 = Json.parse(response.body)
      println("The Response Body is : \n" + Json.prettyPrint(Json.toJson(responseBody1)))

      And("response header should consist of correlation ID")
      val correlationID = response.headers.get("Content-Type")
      correlationID should not be empty

    }

  }

}
