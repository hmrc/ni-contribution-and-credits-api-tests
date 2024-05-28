/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.benefitsAndCredits.entrypoint

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.test.api.models.UseCaseResponse
import uk.gov.hmrc.test.api.responses.CommonResponses

object HmctsC2 extends CommonResponses {

  val status: Int = OK

  def getFullResponse(matchId: String): UseCaseResponse =
    UseCaseResponse(getExpectedResponse(matchId), status)

  def getExpectedResponse(matchId: String): JsValue = Json.parse(s"""{
       |    "_links": {
       |        "working-tax-credit": {
       |            "href": "/individuals/benefits-and-credits/working-tax-credit?matchId=$matchId{&fromDate,toDate}",
       |            "title": "Get Working Tax Credit details"
       |        },
       |        "child-tax-credit": {
       |            "href": "/individuals/benefits-and-credits/child-tax-credit?matchId=$matchId{&fromDate,toDate}",
       |            "title": "Get Child Tax Credit details"
       |        },
       |        "self": {
       |            "href": "/individuals/benefits-and-credits/?matchId=$matchId"
       |        }
       |    }
       |}""".stripMargin)
}
