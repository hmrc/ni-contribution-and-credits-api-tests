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

package uk.gov.hmrc.api.specs.gysp_specs

import org.scalatest.Ignore
import play.api.libs.json.Json
import uk.gov.hmrc.api.models.common.*

@Ignore
class GYSPErrorScenarios extends GYSPBaseSpec {

  Feature(s"Error Scenarios for GYSP Benefit Type") {

    Scenario(s"GYSP_PTC006: A GYSP request is processed, however some down streams fail") {
      Given(s"The Benefit eligibility Info API is up and running")
      When(s"A request for GYSP is sent and NICC and LTB Calc return error")

      val payloadKey = s"GYSP_PTC006"
      val payload    = PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))
      println(payload)

      // Make API call and build the request
      val response = gyspService.makeRequest(payload, payloadKey)

      Then("A 502 should be returned with partial failure content")
      // Assertions
      response.status shouldBe 502

      val result: DownstreamErrorResponse = Json.parse(response.body).as[DownstreamErrorResponse]

      val failedDownStreams = result.downStreams.filter(_.status == "FAILURE")
      failedDownStreams should have size 2
      (failedDownStreams.map(_.apiName) should contain).allOf(
        "NI Contributions and credits",
        "Long Term Benefit Calculation Details"
      )

      failedDownStreams.foreach { ds =>
        ds.apiName match {

          case "NI Contributions and credits" =>
            ds.error shouldBe defined
            ds.error.get.code shouldBe "ACCESS_FORBIDDEN"
            ds.error.get.downstreamStatus shouldBe 403

          case "Long Term Benefit Calculation Details" =>
            ds.error shouldBe defined
            ds.error.get.code shouldBe "BAD_REQUEST"
            ds.error.get.downstreamStatus shouldBe 400
        }
      }

      val successDownStreams = result.downStreams.filter(_.status == "SUCCESS")
      successDownStreams should have size 5

      // Print response details
      println(s"The Response Status Code is : ${response.status} ${response.statusText}")
      println(s"""The Response Body is : ${Json.prettyPrint(Json.toJson(result))}""")
    }

    Scenario(s"GYSP_NPS_ERROR_ALL_DOWNSTREAMS: A GYSP request is processed, however all down streams fail") {
      Given(s"The Benefit eligibility Info API is up and running")
      When(s"A request for GYSP is sent and NICC and LTB Calc return error")

      val payloadKey = s"GYSP_NPS_ERROR_ALL_DOWNSTREAMS"
      val payload    = PayloadMapping.getOrElse(payloadKey, fail(s"$payloadKey not found"))
      println(payload)

      // Make API call and build the request
      val response = gyspService.makeRequest(payload, payloadKey)

      Then("A 502 should be returned with only failure content")

      response.status shouldBe 502

      val result: DownstreamErrorResponse = Json.parse(response.body).as[DownstreamErrorResponse]

      result.status shouldBe "FAILURE"
      result.benefitType shouldBe payload.benefitType
      result.nationalInsuranceNumber shouldBe payload.nationalInsuranceNumber

      result.summary.totalCalls shouldBe 5
      result.summary.successful shouldBe 0
      result.summary.failed shouldBe 5

      result.downStreams should have size 5

      result.downStreams.foreach { ds =>
        ds.status shouldBe "FAILURE"
        ds.error.get shouldBe NpsNormalizedError(
          "BAD_REQUEST",
          "downstream received a malformed request",
          400
        )
      }

      (result.downStreams.map(_.apiName) should contain).allOf(
        "NI Contributions and credits",
        "Marriage Details",
        "Scheme Membership Details",
        "Long Term Benefit Calculation Details",
        "Individual State Pension Information"
      )

      // Print response details
      println(s"The Response Status Code is : ${response.status} ${response.statusText}")
      println(s"""The Response Body is : ${Json.prettyPrint(Json.toJson(result))}""")
    }

  }

}
