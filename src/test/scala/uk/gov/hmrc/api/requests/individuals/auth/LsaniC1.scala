/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.requests.individuals.auth

object LsaniC1 {

  val authPayload: String = s"""{
    |  "clientId": "id-123232",
    |  "authProvider": "PrivilegedApplication",
    |  "applicationId":"app-1",
    |  "applicationName": "App 1",
    |  "enrolments": ["read:individuals-benefits-and-credits-lsani-c1",
    |  "read:individuals-details-lsani-c1",
    |  "read:individuals-employments-lsani-c1",
    |  "read:individuals-income-lsani-c1",
    |  "read:individuals-matching-lsani-c1",
    |  "assigned"],
    |  "ttl": 5000
    |}""".stripMargin
}
