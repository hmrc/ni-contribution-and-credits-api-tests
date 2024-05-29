/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.api.service

import play.api.libs.json.Json
import play.api.libs.ws.StandaloneWSRequest
import uk.gov.hmrc.api.client.HttpClient
import uk.gov.hmrc.api.conf.TestConfiguration
import uk.gov.hmrc.api.models.User

import scala.concurrent.Await
import scala.concurrent.duration._

class IndividualsMatchingService extends HttpClient {

  val host: String                   = TestConfiguration.url("ims")
  val individualsMatchingURL: String = s"$host/"

  def getIndividualByMatchId(authToken: String, matchId: String): StandaloneWSRequest#Self#Response =
    Await.result(
      get(
        individualsMatchingURL + matchId,
        ("Authorization", authToken),
        ("CorrelationId", "12345678"),
        ("Accept", "application/vnd.hmrc.P1.0+json")
      ),
      10.seconds
    )

  def postIndividualPayload(authToken: String, individual: User): StandaloneWSRequest#Self#Response = {
    val individualPayload = Json.toJsObject(individual)
    Await.result(
      post(
        individualsMatchingURL,
        Json.stringify(individualPayload),
        ("Content-Type", "application/json"),
        ("Authorization", authToken),
        ("CorrelationId", "12345678"),
        ("Accept", "application/vnd.hmrc.P1.0+json")
      ),
      10.seconds
    )
  }

}
