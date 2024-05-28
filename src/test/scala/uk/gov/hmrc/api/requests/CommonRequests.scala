/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.requests

trait CommonRequests {

  val newPersonParamsPayload1: String =
    s"""{
       |  "forename": "Luke",
       |  "surname": "Wood",
       |  "nino": "EG724113D",
       |  "dateOfBirth": "1960-04-06",
       |   "dateRange": "1960-04-06"
       |}""".stripMargin

  val newPersonParamsPayload2: String =
    s"""{
       |  "forename": "John",
       |  "surname": "Densmore",
       |  "nino": "CS700100A",
       |  "dateOfBirth": "1969-03-05"
       |  "dateRange": "1960-04-06"
       |}""".stripMargin

}
