/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.income.sa.source

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
       |            "href": "/individuals/income/sa/source?matchId=$matchId&fromTaxYear=2018-19&toTaxYear=2019-20"
       |        }
       |    },
       |    "selfAssessment": {
       |        "taxReturns": [
       |            {
       |                "taxYear": "2018-19",
       |                "sources": [
       |                    {
       |                        "businessDescription": "Stilt walker",
       |                        "businessAddress": {
       |                            "line1": "sc",
       |                            "line2": "dc",
       |                            "line3": "fc",
       |                            "line4": "gc",
       |                            "postalCode": "hc"
       |                        },
       |                        "telephoneNumber": "NE98 8IO"
       |                    }
       |                ]
       |            }
       |        ]
       |    }
       |}""".stripMargin)
}
