/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.api.helpers

import play.api.libs.json.Json
import play.api.libs.ws.StandaloneWSRequest
import uk.gov.hmrc.api.models.{IndividualsLinks, User}
import uk.gov.hmrc.api.service.IndividualsMatchingService

import scala.util.matching.Regex

class TestDataHelper {

  val individualsMatchingServiceAPI: IndividualsMatchingService = new IndividualsMatchingService

  def createAnIndividual(authBearerToken: String, expectedIndividual: User): String = {
    val individualsMatchPostResponse: StandaloneWSRequest#Self#Response =
      individualsMatchingServiceAPI.postIndividualPayload(authBearerToken, expectedIndividual)
    obtainMatchIdFromHref(individualsMatchPostResponse)
  }

  def obtainMatchIdFromHref(individualsMatchPostResponse: StandaloneWSRequest#Self#Response): String = {
    val individualsLinks: IndividualsLinks =
      (Json.parse(individualsMatchPostResponse.body) \ "_links" \ "individual").as[IndividualsLinks]
    val pattern: Regex                     = "/individuals/matching/([0-9a-z-]+)".r
    val pattern(matchId: String)           = individualsLinks.href
    matchId
  }

}
