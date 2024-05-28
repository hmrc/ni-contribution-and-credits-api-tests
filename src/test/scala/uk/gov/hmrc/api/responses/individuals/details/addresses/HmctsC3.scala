/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.details.addresses

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.test.api.models.UseCaseResponse
import uk.gov.hmrc.test.api.responses.CommonResponses

object HmctsC3 extends CommonResponses {

  val status: Int = OK

  def getFullResponse(matchId: String): UseCaseResponse =
    UseCaseResponse(getExpectedResponse(matchId), status)

  def getExpectedResponse(matchId: String): JsValue = Json.parse(s"""{
       |    "_links": {
       |        "self": {
       |            "href": "/individuals/details/addresses?matchId=$matchId"
       |        }
       |    },
       |    "residences": [
       |        {
       |            "residenceType": "NOMINATED",
       |            "address": {
       |                "line1": "24 Trinity Street",
       |                "line2": "Dawley Bank",
       |                "line3": "Telford",
       |                "line4": "Shropshire",
       |                "line5": "UK",
       |                "postcode": "TF3 4ER"
       |            },
       |            "inUse": true
       |        }
       |    ]
       |}""".stripMargin)
}
