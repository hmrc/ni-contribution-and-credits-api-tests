/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.api.helpers

import play.api.libs.json.Json
import play.api.libs.ws.StandaloneWSRequest
import uk.gov.hmrc.api.models.User
import uk.gov.hmrc.api.service.IndividualsMatchingService

class IndividualsMatchingHelper {

  val individualsMatchingServiceAPI: IndividualsMatchingService = new IndividualsMatchingService

  def getIndividualByMatchId(authBearerToken: String, individualsMatchId: String): User = {
    val individualsMatchGetResponse: StandaloneWSRequest#Self#Response =
      individualsMatchingServiceAPI.getIndividualByMatchId(authBearerToken, individualsMatchId)
    (Json.parse(individualsMatchGetResponse.body) \ "individual").as[User]
  }

}
