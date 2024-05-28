/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.requests

trait CommonRequestsOld {

  val individualMatchPayload1: String =
    s"""{
       |  "forename": "Luke",
       |  "surname": "Wood",
       |  "nino": "EG724113D",
       |  "dateOfBirth": "1960-04-06",
       |   "dateRange": "1960-04-06"
       |}""".stripMargin

  val individualMatchPayload2: String =
    s"""{
       |  "firstName": "John",
       |  "lastName": "Densmore",
       |  "nino": "CS700100A",
       |  "dateOfBirth": "1969-03-05"
       |}""".stripMargin

  val v1FromTaxYear: String = "2019-20"
  val v1ToTaxYear: String   = "2021-22"
  val v1FromDate: String    = "2019-01-01"
  val v1ToDate: String      = "2020-03-01"
  val v2FromTaxYear: String = "2018-19"
  val v2ToTaxYear: String   = "2019-20"
  val v2FromDate: String    = "2019-01-01"
  val v2ToDate: String      = "2019-12-31"
  val empPayeRef: String    = "247/ZT6767895A"

  val versionP1Accept: String = "application/vnd.hmrc.P1.0+json"
  val version1Accept: String  = "application/vnd.hmrc.1.0+json"
  val version2Accept: String  = "application/vnd.hmrc.2.0+json"

  val indMatchingV1: String    = versionP1Accept
  val indMatchingV2: String    = version2Accept
  val indBenefitsV1: String    = version1Accept
  val indDetailsV1: String     = version1Accept
  val indEmploymentsV1: String = versionP1Accept
  val indEmploymentsV2: String = version2Accept
  val indIncomeV1: String      = versionP1Accept
  val indIncomeV2: String      = version2Accept
  val orgMatchingV1: String    = version1Accept
  val orgDetailsV1: String     = version1Accept
}
