/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.income.sa

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.test.api.models.UseCaseResponse
import uk.gov.hmrc.test.api.responses.CommonResponses

object HmctsC4 extends CommonResponses {

  val status = OK

  def getFullResponse(matchId: String) =
    UseCaseResponse(getExpectedResponse(matchId), status)

  def getExpectedResponse(matchId: String): JsValue = Json.parse(s"""{
       |    "_links": {
       |        "source": {
       |            "href": "/individuals/income/sa/source?matchId=$matchId{&fromTaxYear,toTaxYear}",
       |            "title": "Get an individual's SA source data"
       |        },
       |        "self": {
       |            "href": "/individuals/income/sa?matchId=$matchId&fromTaxYear=2018-19&toTaxYear=2019-20"
       |        }
       |    },
       |    "selfAssessment": {
       |        "registrations": [
       |            {
       |                "registrationDate": "2018-02-28"
       |            }
       |        ],
       |        "taxReturns": [
       |            {
       |                "taxYear": "2018-19",
       |                "submissions": []
       |            }
       |        ]
       |    }
       |}""".stripMargin)
}
