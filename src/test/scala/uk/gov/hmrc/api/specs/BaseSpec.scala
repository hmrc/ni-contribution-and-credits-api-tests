/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.specs
import org.scalatest._
import org.scalatest.concurrent.Eventually
import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.matchers.should.Matchers
import uk.gov.hmrc.test.api.StableIdentifiers
import uk.gov.hmrc.test.api.client.HttpClient
import uk.gov.hmrc.test.api.service._

trait BaseSpec
    extends AnyFeatureSpec
    with GivenWhenThen
    with BeforeAndAfterAll
    with Matchers
    with Eventually
    with StableIdentifiers {
  val httpClient                           = new HttpClient
  val authService                          = new AuthService(httpClient)
  val newPersonParamsService               = new NewPersonParamsService(httpClient)

}
