/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.income.sa.furtherDetails

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.test.api.models.UseCaseResponse
import uk.gov.hmrc.test.api.responses.CommonResponses

object LaaC1 extends CommonResponses {

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
      |                        "busStartDate": "2018-02-21",
      |                        "busEndDate": "2018-02-22",
      |                        "totalTaxPaid": -99.85,
      |                        "totalNIC": 99999999999.82,
      |                        "turnover": 345435.03,
      |                        "otherBusIncome": 34545.88,
      |                        "tradingIncomeAllowance": 100,
      |                        "deducts": {
      |                            "totalBusExpenses": 99999999999.84,
      |                            "totalDisallowBusExp": 99999999999.83
      |                        }
      |                    }
      |                ]
      |            }
      |        ]
      |    }
      |}""".stripMargin)
}
