/*
 * Copyright 2024 HM Revenue & Customs
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

package uk.gov.hmrc.api.utils

import io.circe.*
import io.circe.generic.auto.*
import io.circe.parser.*
import uk.gov.hmrc.api.models.esajsa.EsaJsaRequest
import uk.gov.hmrc.api.models.gysp.GYSPRequest
import uk.gov.hmrc.api.models.nicc.v1.Request

import scala.io.Source

object JsonUtils {

  def readJsonFile(filePath: String): String = {
    val source = Source.fromFile(filePath)
    try source.getLines().mkString
    finally source.close()
  }

  def parseJsonToMap(jsonString: String): Either[Error, Map[String, Request]] =
    parse(jsonString).flatMap(_.as[Map[String, Request]])

  def parseJsonToEsaJsaRequestMap(jsonString: String): Either[Error, Map[String, EsaJsaRequest]] =
    parse(jsonString).flatMap(_.as[Map[String, EsaJsaRequest]])

  def parseJsonToGyspRequestMap(jsonString: String): Either[Error, Map[String, GYSPRequest]] =
    parse(jsonString).flatMap(_.as[Map[String, GYSPRequest]])

}
