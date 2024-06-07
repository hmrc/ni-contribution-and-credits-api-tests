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

class ZAPSpec extends BaseSpec {

  Feature("ZAP retrieving NICC details") {

    Scenario("ZAP NICC retrieval") {

      Given("The NICC API is up and running")
      //val authBearerToken: String    = authHelper.getAuthBearerToken
     // val individualsMatchId: String = testDataHelper.createAnIndividual(authBearerToken, ninoUser)

      When("A request for NINC is sent")
      val response =
        niccService.makeRequest("testBearerToken", Request("1960-04-05"), "A123456C", "2019", "2021")

      Then("Class 1 and Class 2 details are returned")
      response.status shouldBe 200

    }

  }

}
