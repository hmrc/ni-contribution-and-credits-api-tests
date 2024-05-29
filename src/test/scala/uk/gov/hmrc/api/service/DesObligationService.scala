/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.api.service

import play.api.libs.ws.StandaloneWSRequest
import uk.gov.hmrc.api.client.HttpClient
import uk.gov.hmrc.api.conf.TestConfiguration
import TestConfiguration.{desBearerToken, desEnvironment}

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

class DesObligationService extends HttpClient {

  val obligationsUrl: String = TestConfiguration.url("des")

  def getObligationData(
    vrn: String,
    from: String,
    to: String,
    status: String
  ): StandaloneWSRequest#Self#Response =
    Await.result(
      get(
        s"$obligationsUrl/obligation-data/vrn/$vrn/VATC?from=$from&to=$to&status=$status",
        ("Content-Type", "application/json"),
        ("Environment", desEnvironment),
        ("Authorization", desBearerToken)
      ),
      10.seconds
    )

}
