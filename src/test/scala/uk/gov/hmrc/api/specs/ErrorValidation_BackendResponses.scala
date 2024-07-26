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

import uk.gov.hmrc.api.models.Request

class ErrorValidation_BackendResponses extends BaseSpec {
  Feature("VALIDATION OF ERROR CODES FOR BACKEND RESPONSES") {

    Scenario("Request receives 500 error response from backend in response to 400"){
      val response =
        niccService.makeRequest(Request("BB000400", "1960-04-05", "e470d658-99f7-4292-a4a1-ed12c72f1337", "1970", "2019"))
      response.status shouldBe 500
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body shouldBe ""
      println("Response Body is: " + response.body)
    }

    Scenario("Request receives 500 error response from backend in response to 400A"){
      val response =
        niccService.makeRequest(Request("BB000400A", "1960-04-05", "e470d658-99f7-4292-a4a1-ed12c72f1337", "1970", "2019"))
      response.status shouldBe 400
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body shouldBe "{\"failures\":[{\"reason\":\"HTTP message not readable\",\"code\":\"\"},{\"reason\":\"Constraint Violation - Invalid/Missing input parameter\",\"code\":\"BAD_REQUEST\"}]}"
      println("Response Body is: " + response.body)
    }

    Scenario("Request receives 500 error response from backend in response to 400B"){
      val response =
        niccService.makeRequest(Request("BB000500", "1960-04-05", "e470d658-99f7-4292-a4a1-ed12c72f1337", "1970", "2019"))
      response.status shouldBe 500
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body shouldBe ""
      println("Response Body is: " + response.body)
    }

    Scenario("Request receives 422 error response from backend"){
      val response =
        niccService.makeRequest(Request("BB000422B", "1960-04-05", "e470d658-99f7-4292-a4a1-ed12c72f1337", "1970", "2019"))
      response.status shouldBe 422
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body shouldBe "{\"failures\":[{\"reason\":\"Start tax year after end tax year\",\"code\":\"63496\"}]}"
      println("Response Body is: " + response.body)
    }

    Scenario("Request receives 500 error response from backend in response to 404"){
      val response =
        niccService.makeRequest(Request("BB000404", "1960-04-05", "e470d658-99f7-4292-a4a1-ed12c72f1337", "1970", "2019"))
      response.status shouldBe 500
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body shouldBe ""
      println("Response Body is: " + response.body)
    }

    Scenario("Request receives 500 error response from backend in response to 403"){
      val response =
        niccService.makeRequest(Request("BB000403", "1960-04-05", "e470d658-99f7-4292-a4a1-ed12c72f1337", "1970", "2019"))
      response.status shouldBe 500
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body shouldBe ""
      println("Response Body is: " + response.body)
    }

    Scenario("Request receives 500 error response from backend in response to 500"){
      val response =
        niccService.makeRequest(Request("BB000500", "1960-04-05", "e470d658-99f7-4292-a4a1-ed12c72f1337", "1970", "2019"))
      response.status shouldBe 500
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body shouldBe ""
      println("Response Body is: " + response.body)
    }
}
}
