/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.service

import uk.gov.hmrc.test.api.StableIdentifiers
import uk.gov.hmrc.test.api.client.HttpClient
import uk.gov.hmrc.test.api.conf.TestConfiguration
import uk.gov.hmrc.test.api.models.IndUseCase

import scala.concurrent.Await
import scala.concurrent.duration._

class NewPersonParamsService(client: HttpClient) extends StableIdentifiers {
  val host: String = TestConfiguration.url(NewPersonParams)


  def getNiInfo(authToken: String, niInfo: String, correlationId: String): String =
    Await.result(
      client.get(
        s"$host/nino/$niInfo",
        ("Authorization", authToken),
        ("CorrelationId", s"$correlationId"),
        ("Accept", "application/json")
      ),
      10.seconds
    )
}
