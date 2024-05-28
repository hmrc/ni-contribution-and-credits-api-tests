/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.income.sa.source

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.test.api.models.UseCaseResponse
import uk.gov.hmrc.test.api.responses.CommonResponses

object HmctsC4 extends CommonResponses {

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
       |                        "businessDescription": "Dogs body",
       |                        "businessAddress": {
       |                            "line1": "a",
       |                            "line2": "b",
       |                            "line3": "c",
       |                            "line4": "d",
       |                            "postalCode": "NE11 1ZZ"
       |                        },
       |                        "telephoneNumber": "0191111222"
       |                    }
       |                ]
       |            }
       |        ]
       |    }
       |}""".stripMargin)
}
