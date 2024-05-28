/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.requests.individuals.auth

object LaaC4 {

  val authPayload: String = s"""{
    |  "clientId": "id-123232",
    |  "authProvider": "PrivilegedApplication",
    |  "applicationId":"app-1",
    |  "applicationName": "App 1",
    |  "enrolments": ["read:individuals-details-laa-c4",
    |  "read:individuals-employments-laa-c4",
    |  "read:individuals-income-laa-c4",
    |  "read:individuals-matching-laa-c4",
    |  "assigned"],
    |  "ttl": 5000
    |}""".stripMargin
}
