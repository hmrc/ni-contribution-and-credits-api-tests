/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.service

import uk.gov.hmrc.test.api.StableIdentifiers
import uk.gov.hmrc.test.api.client.{HttpClient, ServiceResponse}
import uk.gov.hmrc.test.api.conf.TestConfiguration
import uk.gov.hmrc.test.api.models.{IndUseCase, OrgUseCase}

import scala.concurrent.Await
import scala.concurrent.duration._

class AuthService(client: HttpClient) extends StableIdentifiers {
  val host: String = TestConfiguration.url("auth")
  val authUrl      = s"$host/$Session"

  def getLocalBearer[T](useCase: T): ServiceResponse = {
    val authPayload = useCase match {
      case useCase: IndUseCase => useCase.asInstanceOf[IndUseCase].authPayload
      case useCase: OrgUseCase => useCase.asInstanceOf[OrgUseCase].authPayload
      case _                   => throw new Exception(s"Unable to perform match on use case: $useCase")
    }
    Await.result(client.post(authUrl, authPayload, ("Content-Type", "application/json")), 10.seconds)
  }
}
