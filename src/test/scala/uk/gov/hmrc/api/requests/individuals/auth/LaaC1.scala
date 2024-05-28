/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.requests.individuals.auth

object LaaC1 {

  val authPayload: String = s"""{
    |  "clientId": "id-123232",
    |  "authProvider": "PrivilegedApplication",
    |  "applicationId":"app-1",
    |  "applicationName": "App 1",
    |  "enrolments": ["read:individuals-matching-laa-c1",
    |  "read:individuals-benefits-and-credits-laa-c1",
    |  "read:individuals-employments-laa-c1",
    |  "read:individuals-income-laa-c1",
    |  "assigned"],
    |  "ttl": 5000
    |}""".stripMargin
}
