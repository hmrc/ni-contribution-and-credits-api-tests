/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.details.entrypoint

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.test.api.models.UseCaseResponse
import uk.gov.hmrc.test.api.responses.CommonResponses

object LsaniC1 extends CommonResponses {

  val status: Int = OK

  def getFullResponse(matchId: String): UseCaseResponse =
    UseCaseResponse(getExpectedResponse(matchId), status)

  def getExpectedResponse(matchId: String): JsValue = Json.parse(s"""{
       |    "_links": {
       |        "addresses": {
       |            "href": "/individuals/details/addresses?matchId=$matchId",
       |            "title": "Get addresses"
       |        },
       |        "self": {
       |            "href": "/individuals/details/?matchId=$matchId"
       |        }
       |    }
       |}""".stripMargin)
}
