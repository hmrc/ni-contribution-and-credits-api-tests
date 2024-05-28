/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.requests.individuals.auth

object HmctsC2 {

  val authPayload: String = s"""{
    |  "clientId": "id-123232",
    |  "authProvider": "PrivilegedApplication",
    |  "applicationId":"app-1",
    |  "applicationName": "App 1",
    |  "enrolments": ["read:individuals-benefits-and-credits-hmcts-c2",
    |  "read:individuals-employments-hmcts-c2",
    |  "read:individuals-income-hmcts-c2",
    |  "read:individuals-matching-hmcts-c2",
    |  "assigned"],
    |  "ttl": 5000
    |}""".stripMargin
}
