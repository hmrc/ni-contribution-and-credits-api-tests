/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.income.paye

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.test.api.models.UseCaseResponse
import uk.gov.hmrc.test.api.responses.CommonResponses

object LaaC4 extends CommonResponses {

  val status = OK

  def getFullResponse(matchId: String) =
    UseCaseResponse(getExpectedResponse(matchId), status)

  def getExpectedResponse(matchId: String): JsValue = Json.parse("""{
       |    "_links": {
       |        "self": {
       |            "href": "/individuals/income/paye?matchId=<matchId>&fromDate=2019-01-01&toDate=2019-12-31"
       |        }
       |    },
       |    "paye": {
       |        "income": [
       |            {
       |                "payroll": {
       |                    "id": "}\\^W7 ci|)pENG;62$"
       |                },
       |                "paymentDate": "2006-02-27",
       |                "grossEarningsForNics": {
       |                    "inPayPeriod1": 995979.04,
       |                    "inPayPeriod2": 606456.38,
       |                    "inPayPeriod3": 797877.34,
       |                    "inPayPeriod4": 166334.69
       |                }
       |            }
       |        ]
       |    }
       |}""".replace("<matchId>", matchId).stripMargin)
}
