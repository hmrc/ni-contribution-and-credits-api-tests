/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.api.specs

import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.GivenWhenThen
import uk.gov.hmrc.api.helpers.{AuthHelper, IndividualsMatchingHelper, TestDataHelper}

trait BaseSpec extends AnyFeatureSpec with GivenWhenThen with Matchers {

  val authHelper                = new AuthHelper
  val testDataHelper            = new TestDataHelper
  val individualsMatchingHelper = new IndividualsMatchingHelper

}
