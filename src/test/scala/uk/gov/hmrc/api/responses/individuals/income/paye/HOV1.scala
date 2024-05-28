/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.income.paye

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.test.api.models.UseCaseResponse
import uk.gov.hmrc.test.api.responses.CommonResponses

object HOV1 extends CommonResponses {

  val status = OK

  def getFullResponse(matchId: String) =
    UseCaseResponse(getExpectedResponse(matchId), status)

  def getExpectedResponse(matchId: String): JsValue = Json.parse(s"""{
       |    "_links": {
       |        "self": {
       |            "href": "/individuals/income/paye?matchId=$matchId&fromDate=2019-01-01&toDate=2020-03-01"
       |        }
       |    },
       |    "paye": {
       |        "income": [
       |            {
       |                "taxablePayment": 100,
       |                "paymentDate": "2019-11-28",
       |                "employerPayeReference": "584/AD12345",
       |                "weekPayNumber": 48
       |            },
       |            {
       |                "taxablePayment": 100,
       |                "paymentDate": "2019-11-14",
       |                "employerPayeReference": "584/AD12345",
       |                "weekPayNumber": 46
       |            },
       |            {
       |                "taxablePayment": 50,
       |                "paymentDate": "2019-03-15",
       |                "employerPayeReference": "123/AB12345",
       |                "monthPayNumber": 12
       |            },
       |            {
       |                "taxablePayment": 50,
       |                "paymentDate": "2019-02-15",
       |                "employerPayeReference": "123/AB12345",
       |                "monthPayNumber": 11
       |            },
       |            {
       |                "taxablePayment": 50,
       |                "paymentDate": "2019-01-15",
       |                "employerPayeReference": "123/AB12345",
       |                "monthPayNumber": 10
       |            }
       |        ]
       |    }
       |}""".stripMargin)
}
