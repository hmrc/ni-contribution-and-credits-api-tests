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

import play.api.libs.ws.StandaloneWSRequest
import uk.gov.hmrc.api.client.HttpClient
import uk.gov.hmrc.api.conf.TestConfiguration
import play.api.libs.ws.json.Json
import uk.gov.hmrc.api.models.User

import scala.concurrent.Await
import scala.concurrent.duration._

class putNICC extends HttpClient {

  val host: String                   = TestConfiguration.url("ims")
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
  val url: String = s"$host/nps-json-service/nps/v1/api/national-insurance/AB123456C/contributions-and-credits/from/2019/to/2023"
  def postniccMakeRequest(authToken: String, user: User): StandaloneWSRequest#Self#Response = {
    val ccPayload = Json.toJsObject(user)
    Await.result(
      post(
        url,
        Json.stringify(ccPayload),
        ("Authorization", authToken),
        ("CorrelationId", "e470d658-99f7-4292-a4a1-ed12c72f1337"),
        ("gov-uk-originator-d", "DWP")
      ),
      10.seconds
    )
  }
}


