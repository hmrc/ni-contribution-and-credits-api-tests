/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.details.contactDetails

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.test.api.models.UseCaseResponse
import uk.gov.hmrc.test.api.responses.CommonResponses

object LaaC4 extends CommonResponses {

  val status: Int = OK

  def getFullResponse(matchId: String): UseCaseResponse =
    UseCaseResponse(getExpectedResponse(matchId), status)

  def getExpectedResponse(matchId: String): JsValue = Json.parse(s"""{
       |    "_links": {
       |        "self": {
       |            "href": "/individuals/details/contact-details?matchId=$matchId"
       |        }
       |    },
       |    "contactDetails": {
       |        "daytimeTelephones": [
       |            "07124 987656"
       |        ],
       |        "eveningTelephones": [
       |            "07124 987655"
       |        ],
       |        "mobileTelephones": [
       |            "07123 987654"
       |        ]
       |    }
       |}""".stripMargin)
}
