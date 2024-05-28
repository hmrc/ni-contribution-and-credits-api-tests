/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.income.sa.furtherDetails

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.test.api.models.UseCaseResponse
import uk.gov.hmrc.test.api.responses.CommonResponses

object LsaniC3 extends CommonResponses {

  val status = OK

  def getFullResponse(matchId: String) =
    UseCaseResponse(getExpectedResponse(matchId), status)

  def getExpectedResponse(matchId: String): JsValue = Json.parse(s"""{
       |    "_links": {
       |        "self": {
       |            "href": "/individuals/income/sa/further-details?matchId=$matchId&fromTaxYear=2018-19&toTaxYear=2019-20"
       |        }
       |    },
       |    "selfAssessment": {
       |        "taxReturns": [
       |            {
       |                "taxYear": "2018-19",
       |                "furtherDetails": [
       |                    {
       |                        "busStartDate": "2018-02-24",
       |                        "busEndDate": "2018-02-25",
       |                        "totalTaxPaid": -99.99,
       |                        "totalNIC": 99999999999.83,
       |                        "deducts": {
       |                            "totalBusExpenses": 99999999999.84
       |                        }
       |                    }
       |                ]
       |            }
       |        ]
       |    }
       |}""".stripMargin)
}
