/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.income.sa.source

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.test.api.models.UseCaseResponse
import uk.gov.hmrc.test.api.responses.CommonResponses

object NictsEjoC4 extends CommonResponses {

  val status: Int = OK

  def getFullResponse(matchId: String): UseCaseResponse =
    UseCaseResponse(getExpectedResponse(matchId), status)

  def getExpectedResponse(matchId: String): JsValue = Json.parse(s"""{
       |    "_links": {
       |        "self": {
       |            "href": "/individuals/income/sa/source?matchId=$matchId&fromTaxYear=2018-19&toTaxYear=2019-20"
       |        }
       |    },
       |    "selfAssessment": {
       |        "taxReturns": [
       |            {
       |                "taxYear": "2018-19",
       |                "sources": [
       |                    {
       |                        "businessDescription": "Chocolate fireguards",
       |                        "businessAddress": {
       |                            "line1": "ea",
       |                            "line2": "ad",
       |                            "line3": "fa",
       |                            "line4": "ag",
       |                            "postalCode": "NE54 6RT"
       |                        },
       |                        "telephoneNumber": "0191086432"
       |                    }
       |                ]
       |            }
       |        ]
       |    }
       |}""".stripMargin)
}
