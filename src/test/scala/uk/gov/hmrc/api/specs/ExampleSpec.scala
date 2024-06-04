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

class ExampleSpec extends BaseSpec {

  Feature("Example of using the Contributions and Credits API") {

    Scenario("Retrieve Class 1 and Class 2 data for given NINO") {

      Given("There is an existing NINO and range of tax years")
      //val authBearerToken: String    = authHelper.getAuthBearerToken
     // val individualsMatchId: String = testDataHelper.createAnIndividual(authBearerToken, ninoUser)

      When("I use the date of birth to retrieve the Class 1 and Class2 details")
      val response =
        niccService.postniccMakeRequest("testBearerToken", "testuser")

      Then("I am returned the Class 1 and Class 2 details")
      response.header("correlationId")
      response.body.contains("niContribution").shouldBe().toString
      response.body.contains("niCredit").equals()
      response.status shouldBe 200
    }
    Scenario("Verify Nino Endpoints happy path") {
     // val consignorToken = givenGetToken(ninoUser.nino)
     // val response       = newUser(token, nino)
     // thenValidateResponseCode(response, 200)
      // checkJsonValue(response, "tfc_account_status", "active")
    }

  }

}
