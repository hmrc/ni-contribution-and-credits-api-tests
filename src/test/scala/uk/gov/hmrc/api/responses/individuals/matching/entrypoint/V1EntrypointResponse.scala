/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.matching.entrypoint

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.test.api.models.UseCaseResponse
import uk.gov.hmrc.test.api.responses.CommonResponses

object V1EntrypointResponse extends CommonResponses {

  val status = OK

  def getFullResponse(matchId: String) =
    UseCaseResponse(getExpectedResponse(matchId), status)

  def getExpectedResponse(matchId: String): JsValue = Json.parse(s"""{
         |    "_links": {
         |        "individual": {
         |            "name": "GET",
         |            "href": "/individuals/matching/$matchId",
         |            "title": "Individual Details"
         |        },
         |        "self": {
         |            "href": "/individuals/matching/"
         |        }
         |    }
         |}""".stripMargin)
}
