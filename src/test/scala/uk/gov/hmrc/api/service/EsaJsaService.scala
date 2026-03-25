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

import play.api.libs.json.{JsValue, Json}
import play.api.libs.ws.StandaloneWSRequest
import uk.gov.hmrc.api.client.HttpClient
import uk.gov.hmrc.api.models.esajsa.EsaJsaRequest

import java.util.UUID
import scala.concurrent.Await
import scala.concurrent.duration.*

class EsaJsaService extends HttpClient with MakesHttpRequestWithToken {

  // ── Private Helpers ────────────────────────────────────────────────────────

  private val url: String = s"$host/benefit-eligibility-info/"

  def makeRequest(
      request: EsaJsaRequest,
      timeoutDuration: Int = 10
  ): StandaloneWSRequest#Response =
    execute(
      payload = toJsonString(request),
      headers = buildHeaders(),
      timeoutDuration = timeoutDuration
    )

  def makeRawRequest(
      request: JsValue,
      timeoutDuration: Int = 10
  ): StandaloneWSRequest#Response =
    execute(
      payload = Json.stringify(request),
      headers = buildHeaders(),
      timeoutDuration = timeoutDuration
    )

  def makeRequestWithoutCorrelationId(
      request: EsaJsaRequest,
      timeoutDuration: Int = 10
  ): StandaloneWSRequest#Response =
    execute(
      payload = toJsonString(request),
      headers = buildHeaders(includeCorrelationId = false),
      timeoutDuration = timeoutDuration
    )

  def makeRequestWithoutBearerToken(
      request: EsaJsaRequest,
      timeoutDuration: Int = 10
  ): StandaloneWSRequest#Response =
    execute(
      payload = toJsonString(request),
      headers = buildHeaders(includeBearerToken = false),
      timeoutDuration = timeoutDuration
    )

  // ── Public Methods ─────────────────────────────────────────────────────────

  private def toJsonString(request: EsaJsaRequest): String =
    Json.stringify(Json.toJsObject(request))

  private def buildHeaders(
      includeCorrelationId: Boolean = true,
      includeBearerToken: Boolean = true
  ): Seq[(String, String)] = {
    val baseHeaders = Seq("Content-Type" -> "application/json")

    val withAuth =
      if (includeBearerToken)
        baseHeaders :+ ("Authorization" -> token)
      else
        baseHeaders

    val withCorrelation =
      if (includeCorrelationId)
        withAuth :+ ("CorrelationID" -> generateCorrelationId())
      else
        withAuth

    withCorrelation
  }

  private def generateCorrelationId(): String =
    s"${UUID.randomUUID()}"

  private def execute(
      payload: String,
      headers: Seq[(String, String)],
      timeoutDuration: Int
  ): StandaloneWSRequest#Response =
    Await.result(
      post(url, payload, headers *),
      timeoutDuration.seconds
    )

  def makeRequestWithInvalidBearerToken(
      request: EsaJsaRequest,
      timeoutDuration: Int = 10
  ): StandaloneWSRequest#Response = {
    val invalidTokenHeaders = buildHeaders(includeBearerToken = false) :+
      ("Authorization" -> "Bearer invalid-token")
    execute(
      payload = toJsonString(request),
      headers = invalidTokenHeaders,
      timeoutDuration = timeoutDuration
    )
  }

}
