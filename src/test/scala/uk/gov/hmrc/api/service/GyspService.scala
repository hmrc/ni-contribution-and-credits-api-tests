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
import uk.gov.hmrc.api.models.gysp.GYSPRequest

import java.util.UUID
import scala.concurrent.Await
import scala.concurrent.duration.*

class GyspService extends HttpClient with MakesHttpRequestWithToken {

  def makeRequest(
      request: GYSPRequest,
      testDataKey: String,
      timeoutDuration: Int = 10
  ): StandaloneWSRequest#Response = {
    val url: String    = s"$host/benefit-eligibility-info/"
    val requestPayload = Json.toJsObject(request)
    val correlationId  = s"$testDataKey-${UUID.randomUUID()}"

    Await.result(
      post(
        url,
        Json.stringify(requestPayload),
        ("Authorization", token),
        ("Content-Type", "application/json"),
        ("CorrelationID", correlationId)
      ),
      timeoutDuration.seconds
    )
  }

}
