/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.requests.individuals.auth

object HmctsC3 {

  val authPayload: String = s"""{
    |  "clientId": "id-123232",
    |  "authProvider": "PrivilegedApplication",
    |  "applicationId":"app-1",
    |  "applicationName": "App 1",
    |  "enrolments": ["read:individuals-benefits-and-credits-hmcts-c3",
    |  "read:individuals-details-hmcts-c3",
    |  "read:individuals-employments-hmcts-c3",
    |  "read:individuals-income-hmcts-c3",
    |  "read:individuals-matching-hmcts-c3",
    |  "assigned"],
    |  "ttl": 5000
    |}""".stripMargin
}
