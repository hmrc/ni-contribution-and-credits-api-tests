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

import uk.gov.hmrc.api.models.{Request, Response}


class ErrorValidation_InvalidPayloadParameters extends BaseSpec{

  val badRequestErrorResponse = "{\"failures\":[{\"reason\":\"There was a problem with the request\",\"code\":\"400\"}]}"

  Feature("VALIDATION OF ERROR CODES FOR INVALID INPUT") {

    Scenario("Request with Invalid NINO receives error response 400 from MDTP"){
      val response =
        niccService.makeRequest(Request("xx", "1960-04-05", Some("e470d658-99f7-4292-a4a1-ed12c72f1337"), "2019", "2021"))
      response.status shouldBe 400
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body shouldBe badRequestErrorResponse
      println("Response Body is: " + response.body)
    }

    Scenario("Request with Date of Birth with invalid format receives error response 400 from MDTP"){
      val response =
        niccService.makeRequest(Request("BB000200B", "1960/04/05", Some("e470d658-99f7-4292-a4a1-ed12c72f1337"), "2019", "2021"))
      response.status shouldBe 400
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body shouldBe badRequestErrorResponse
      println("Response Body is: " + response.body)
    }

    Scenario("Request with start tax year with invalid format receives error response 400 from MDTP"){
      val response =
        niccService.makeRequest(Request("BB000200B", "1960-04-05", Some("e470d658-99f7-4292-a4a1-ed12c72f1337"), ":2019", "2021"))
      response.status shouldBe 400
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body shouldBe badRequestErrorResponse
      println("Response Body is: " + response.body)
    }

    Scenario("Request with end tax year with invalid format receives error response 400 from MDTP"){
      val response =
        niccService.makeRequest(Request("BB000200B", "1960-04-05", Some("e470d658-99f7-4292-a4a1-ed12c72f1337"), "2019", ":2021"))
      response.status shouldBe 400
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body shouldBe badRequestErrorResponse
      println("Response Body is: " + response.body)
    }

  }

}
