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

  val host: String                   = TestConfiguration.url("nps")
  //val url: String = s"$host/nicc-json-service/nicc/v1/api/national-insurance"

  //val url: String = s"$host/nino-info?nino=PA622389C&forename=john&surname=doe&dateOfBirth=22051999&dateRange=someDate"
  /*def niccMakeRequest(authToken: String, nino: String): StandaloneWSRequest#Self#Response =
    Await.result(
      get(
        url + nino,
        ("Authorization", authToken),
        ("CorrelationId", "12345678"),
        ("Accept", "application/vnd.hmrc.P1.0+json")
      ),
      10.seconds
    )*/
  def makeRequest(authToken: String, request: Request, nationalInsuranceNumber: String, startTaxYear: String, endTaxYear: String, testScenario: String = "H001"): StandaloneWSRequest#Self#Response = {
    val url: String = s"$host/nicc-json-service/nicc/v1/api/national-insurance/$nationalInsuranceNumber/contributions-and-credits/from/$startTaxYear/to/$endTaxYear"
    val testUrl: String = s"https://localhost:9001/nps-json-service/nps/v1/api/national-insurance/:nationalInsuranceNumber/contributions-and-credits/from/:startTaxYear/to/:endTaxYear"

    val requestPayload = Json.toJsObject(request)
    Await.result(
      post(
        testUrl,
        Json.stringify(requestPayload),
        ("Authorization", authToken),
        ("CorrelationId", "e470d658-99f7-4292-a4a1-ed12c72f1337"),
        ("gov-uk-originator-d", "DWP"),
        ("testScenario", testScenario)
      ),
      10.seconds
    )
  }

  /*private def postWithProxyIfEnabled(
                                     url: String,request: Request
                                     headers: (String,String)*
                                   ): Future[StandaloneWSRequest#Self#Response] =
    if (Zap.enabled) {
      wsClient
        .url(url)
        .withHttpHeaders(headers: _*)
        .withProxyServer(DefaultWSProxyServer("localhost", 11000))
        .get()
    } else {
      get(url, headers: _*)
    }*/

}


