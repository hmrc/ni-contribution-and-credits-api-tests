/*
 * Copyright 2026 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.api.specs.gysp_specs

import org.scalatest.{BeforeAndAfterAll, Ignore}
import uk.gov.hmrc.api.helpers.BaseHelper
import uk.gov.hmrc.api.models.gysp.GYSPRequest
import uk.gov.hmrc.api.service.GyspService
import uk.gov.hmrc.api.specs.BaseSpec
import uk.gov.hmrc.api.utils.JsonUtils

@Ignore
class GYSPBaseSpec extends BaseSpec with BaseHelper with BeforeAndAfterAll {

  val gyspService                              = new GyspService
  var PayloadMapping: Map[String, GYSPRequest] = _

  override def beforeAll(): Unit = {
    super.beforeAll()
    val jsonString = JsonUtils.readJsonFile(
      "src/test/scala/uk/gov/hmrc/api/testData/Gysp_TestData.json"
    )

    println(jsonString)

    PayloadMapping = JsonUtils.parseJsonToGyspRequestMap(jsonString) match {
      case Left(failure) => fail(s"Parsing failed: $failure")
      case Right(map)    => map
    }
  }

}
