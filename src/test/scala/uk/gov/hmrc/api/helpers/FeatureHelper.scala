/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.helpers

import org.scalatest.Assertion
import play.api.libs.json.Json
import uk.gov.hmrc.test.api.client.ServiceResponse
//import uk.gov.hmrc.test.api.models.{NewPersonUseCase, OrgUseCase, UseCaseResponse}
import uk.gov.hmrc.test.api.responses.CommonResponses
import uk.gov.hmrc.test.api.specs.BaseSpec

trait FeatureHelper extends BaseSpec with CommonResponses {

  def getNINOId(matchResponse: ServiceResponse): String = {
    val href             = (Json.parse(matchResponse.body.get) \ "_links" \ "individual" \ "href").get.as[String]
    val pattern          = "/nino
    val pattern(matchId) = href
    matchId
  }

  def getNewPersonNINOId(matchResponse: ServiceResponse): String =
    (Json.parse(matchResponse.body.get) \ "ninoiId").get.as[String]

  def verifyCall(actualResponse: => ServiceResponse)(expectedResponse: UseCaseResponse): Assertion = {
    actualResponse.status               shouldBe expectedResponse.statusCode
    Json.parse(actualResponse.body.get) shouldBe expectedResponse.payload
  }

  def getActualForNewPersonApiEndpoint(
    authToken: String,
    api: String,
    endpoint: String,
    ninoId: String
  ): ServiceResponse =
    api match {
      case NewPersonParam           => newPersonParamsService.getLinksFromNINOIdActual(authToken, ninoId, useCase, endpoint)
      case _                             => throw new Exception(s"Unable to perform match on service: $api")
    }

  def getExpectedForNewPersonApiEndpoint(
    api: String,
    endpoint: String,
    ninoId: String
  ): UseCaseResponse =
    api match {
      case NewPersonParam =>
        NewPersonParamsHelper.getEndpointResponse(endpoint, ninoId)
      case _                             => throw new Exception(s"Unable to perform match on service: $api")
    }

}
