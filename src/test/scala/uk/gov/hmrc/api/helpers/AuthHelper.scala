/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.api.helpers

import javax.inject.Inject
import org.scalatest.Assertions.fail
import uk.gov.hmrc.api.service.AuthService

class AuthHelper @Inject() (authService: AuthService) {

  def getBearerToken: String = {
    val token = authService.getBearerToken
    token.header("Authorization").getOrElse(fail("Could not obtain bearer token"))
  }
}
