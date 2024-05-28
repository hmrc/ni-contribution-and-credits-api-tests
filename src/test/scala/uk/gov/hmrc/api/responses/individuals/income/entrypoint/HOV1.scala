/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.income.entrypoint

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.test.api.models.UseCaseResponse
import uk.gov.hmrc.test.api.responses.CommonResponses

object HOV1 extends CommonResponses {

  val status = OK

  def getFullResponse(matchId: String) =
    UseCaseResponse(getExpectedResponse(matchId), status)

  def getExpectedResponse(matchId: String): JsValue = Json.parse(s"""{
       |    "_links": {
       |        "paye": {
       |            "href": "/individuals/income/paye?matchId=$matchId{&fromDate,toDate}",
       |            "title": "View individual's income per employment"
       |        },
       |        "self": {
       |            "href": "/individuals/income/?matchId=$matchId"
       |        },
       |        "selfAssessment": {
       |            "href": "/individuals/income/sa?matchId=$matchId{&fromTaxYear,toTaxYear}",
       |            "title": "View individual's self-assessment income"
       |        }
       |    }
       |}""".stripMargin)
}
