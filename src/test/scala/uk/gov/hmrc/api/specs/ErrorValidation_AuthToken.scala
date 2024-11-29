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
import uk.gov.hmrc.api.models.Request
import uk.gov.hmrc.api.utils.JsonUtils

class ErrorValidation_AuthToken extends BaseSpec with BeforeAndAfterAll {

  var PayloadMapping: Map[String, Request] = _

  override def beforeAll(): Unit = {
    super.beforeAll()
    val jsonString = JsonUtils.readJsonFile("src/test/scala/uk/gov/hmrc/api/testData/TestData_P001_to_P021.json")
    PayloadMapping = JsonUtils.parseJsonToMap(jsonString) match {
      case Left(failure) => fail(s"Parsing failed: $failure")
      case Right(map)    => map
    }
  }

  Feature("VALIDATION OF ERROR CODES FOR AUTH TOKEN") {

    Scenario("Request with invalid bearer token receives error response 500 from MDTP") {

      val payload = PayloadMapping.getOrElse("NICC_TC_P002", fail("NICC_TC_P002 not found"))

      val response =
        niccService.makeRequestWithBearerToken(
          Request(
            payload.nationalInsuranceNumber,
            payload.dateOfBirth,
            payload.customerCorrelationID,
            payload.startTaxYear,
            payload.endTaxYear
          ),
          "invalidToken"
        )

      response.status shouldBe 500
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body   shouldBe ""
    }

    Scenario("Request with empty bearer token receives error response 500 from MDTP") {

      val payload = PayloadMapping.getOrElse("NICC_TC_P002", fail("NICC_TC_P002 not found"))

      val response =
        niccService.makeRequestWithBearerToken(
          Request(
            payload.nationalInsuranceNumber,
            payload.dateOfBirth,
            payload.customerCorrelationID,
            payload.startTaxYear,
            payload.endTaxYear
          ),
          "invalidToken"
        )

      response.status shouldBe 500
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body   shouldBe ""
    }

    // Expired token to be tested manually
  }
}
