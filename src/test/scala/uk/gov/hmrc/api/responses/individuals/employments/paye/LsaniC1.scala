/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.employments.paye

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.test.api.models.UseCaseResponse
import uk.gov.hmrc.test.api.responses.CommonResponses

object LsaniC1 extends CommonResponses {

  val status = OK

  def getFullResponse(matchId: String) =
    UseCaseResponse(getExpectedResponse(matchId), status)

  def getExpectedResponse(matchId: String): JsValue = Json.parse(s"""{
       |    "_links": {
       |        "self": {
       |            "href": "/individuals/employments/paye?matchId=$matchId&fromDate=2019-01-01&toDate=2019-12-31"
       |        }
       |    },
       |    "employments": [
       |        {
       |            "startDate": "2019-01-01",
       |            "endDate": "2019-03-31",
       |            "employer": {
       |                "name": "ACME Industries Limited"
       |            }
       |        }
       |    ]
       |}""".stripMargin)
}
