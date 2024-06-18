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

package uk.gov.hmrc.api.service

import play.api.libs.json.Json
import play.api.libs.ws.StandaloneWSRequest
import uk.gov.hmrc.api.client.HttpClient
import uk.gov.hmrc.api.conf.TestConfiguration
import uk.gov.hmrc.api.models.Request

import scala.concurrent.Await
import scala.concurrent.duration._

class NiccService extends HttpClient {

  val host: String                   = TestConfiguration.url("nicc")

  //def makeRequest(authToken: String, request: Request, nationalInsuranceNumber: String, startTaxYear: String, endTaxYear: String): StandaloneWSRequest#Self#Response = {
  def makeRequest(request: Request, nationalInsuranceNumber: String, startTaxYear: String, endTaxYear: String): StandaloneWSRequest#Self#Response = {
    val url: String = s"$host/nicc-json-service/nicc/v1/api/national-insurance/$nationalInsuranceNumber/from/$startTaxYear/to/$endTaxYear"

  //val url: String = s"$host/nps-json-service/nps/v1/api/national-insurance/$nationalInsuranceNumber/contributions-and-credits/from/$startTaxYear/to/$endTaxYear"
    //val testUrl: String = s"http://localhost:9001/nicc-json-service/nicc/v1/api/national-insurance/$nationalInsuranceNumber/contributions-and-credits/from/:startTaxYear/to/:endTaxYear"

    val requestPayload = Json.toJsObject(request)
    Await.result(
      post(
        url,
        Json.stringify(requestPayload),
        //("Authorization", authToken),
        ("CorrelationId", "e470d658-99f7-4292-a4a1-ed12c72f1337"),
        ("gov-uk-originator-id", "DWP"),
       // ("testScenario", testScenario)
      ),
      10.seconds
    )
  }
}


