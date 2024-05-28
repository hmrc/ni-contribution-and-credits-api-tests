/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.requests.individuals.auth

object LaaC3 {

  val authPayload: String = s"""{
    |  "clientId": "id-123232",
    |  "authProvider": "PrivilegedApplication",
    |  "applicationId":"app-1",
    |  "applicationName": "App 1",
    |  "enrolments": ["read:individuals-benefits-and-credits-laa-c3",
    |  "read:individuals-details-laa-c3",
    |  "read:individuals-employments-laa-c3",
    |  "read:individuals-income-laa-c3",
    |  "read:individuals-matching-laa-c3",
    |  "assigned"],
    |  "ttl": 5000
    |}""".stripMargin
}
