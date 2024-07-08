/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.api.service

import javax.inject.Inject
import org.scalatest.time.SpanSugar.convertIntToGrainOfTime
import play.api.libs.json.Json
import play.api.libs.ws.StandaloneWSRequest
import uk.gov.hmrc.api.client.HttpClient
import uk.gov.hmrc.api.conf.TestConfiguration

import scala.concurrent.Await

class AuthService @Inject() () extends HttpClient {

  val host: String = TestConfiguration.url("auth-login-api")

  val bearerPayload: String =
    """
      |{
      |  "clientId": "id-123232",
      |  "authProvider": "PrivilegedApplication",
      |  "applicationId":"app-1",
      |  "applicationName": "Ni Contribution and Credits",
      |  "enrolments": ["notFromEnrolmentStore"],
      |  "ttl": 5000
      |}
      |""".stripMargin

  def getBearerToken: StandaloneWSRequest#Self#Response =
    Await.result(
      postWithJson(
        s"$host/application/session/login",
        Json.parse(bearerPayload)
      ),
      10.seconds
    )
}
