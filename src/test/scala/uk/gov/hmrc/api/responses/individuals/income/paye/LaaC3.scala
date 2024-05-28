/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.income.paye

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.test.api.models.UseCaseResponse
import uk.gov.hmrc.test.api.responses.CommonResponses

object LaaC3 extends CommonResponses {

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
       |                "taxCode": "BR",
       |                "taxablePayToDate": 19157.5,
       |                "taxablePay": 16533.95,
       |                "totalTaxToDate": 3095.89,
       |                "taxDeductedOrRefunded": 16025.64,
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
       |                },
       |                "totalEmployerNics": {
       |                    "inPayPeriod1": 18290.8,
       |                    "inPayPeriod2": 192417.2,
       |                    "inPayPeriod3": 14881.1,
       |                    "inPayPeriod4": 17460.88,
       |                    "ytd1": 530979.47,
       |                    "ytd2": 197448.92,
       |                    "ytd3": 172265.64,
       |                    "ytd4": 122452.65
       |                },
       |                "employeeNics": {
       |                    "inPayPeriod1": 15797.45,
       |                    "inPayPeriod2": 13170.69,
       |                    "inPayPeriod3": 16193.76,
       |                    "inPayPeriod4": 30846.56,
       |                    "ytd1": 10633.5,
       |                    "ytd2": 15579.18,
       |                    "ytd3": 110849.27,
       |                    "ytd4": 162081.23
       |                }
       |            }
       |        ]
       |    }
       |}""".stripMargin)
}
