/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.income.paye

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.test.api.models.UseCaseResponse
import uk.gov.hmrc.test.api.responses.CommonResponses

object LaaC2 extends CommonResponses {

  val status = OK

  def getFullResponse(matchId: String) =
    UseCaseResponse(getExpectedResponse(matchId), status)

  def getExpectedResponse(matchId: String): JsValue = Json.parse(s"""{
       |    "_links": {
       |        "self": {
       |            "href": "/individuals/income/paye?matchId=$matchId&fromDate=2019-01-01&toDate=2019-12-31"
       |        }
       |    },
       |    "paye": {
       |        "income": [
       |            {
       |                "taxYear": "18-19",
       |                "payFrequency": "W4",
       |                "monthPayNumber": 3,
       |                "weekPayNumber": 2,
       |                "paymentDate": "2006-02-27",
       |                "paidHoursWorked": "36",
       |                "taxablePayToDate": 19157.5,
       |                "totalTaxToDate": 3095.89,
       |                "dednsFromNetPay": 119186.46,
       |                "statutoryPayYTD": {
       |                    "maternity": 628562.9,
       |                    "paternity": 98600.58,
       |                    "adoption": 48703.26,
       |                    "parentalBereavement": 39708.7
       |                },
       |                "grossEarningsForNics": {
       |                    "inPayPeriod1": 995979.04,
       |                    "inPayPeriod2": 606456.38,
       |                    "inPayPeriod3": 797877.34,
       |                    "inPayPeriod4": 166334.69
       |                }
       |            }
       |        ]
       |    }
       |}""".stripMargin)
}
