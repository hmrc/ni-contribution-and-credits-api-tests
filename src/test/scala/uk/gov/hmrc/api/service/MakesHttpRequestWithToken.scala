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

package uk.gov.hmrc.api.service

import org.scalatest.Assertions.fail
import play.api.libs.json.Json
import play.api.libs.ws.StandaloneWSRequest
import uk.gov.hmrc.api.conf.TestConfiguration
import uk.gov.hmrc.api.models.nicc.v1.Request

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

trait MakesHttpRequestWithToken {
  this: uk.gov.hmrc.api.client.HttpClient =>

  val host: String = TestConfiguration.url("nicc")

  val token: String = new AuthService().postLogin.headers
    .get("Authorization")
    .flatMap(_.headOption)
    .getOrElse(fail("Couldn't retrieve Auth Token"))

  println(token)

  def makeRequestWithBearerToken(request: Request, bearerToken: String): StandaloneWSRequest#Response = {

    val url: String    = s"$host/contribution-and-credits/"
    val requestPayload = Json.toJsObject(request)
    Await.result(
      post(
        url,
        Json.stringify(requestPayload),
        ("Authorization", bearerToken),
        ("Content-Type", "application/json")
      ),
      10.seconds
    )
  }

}
