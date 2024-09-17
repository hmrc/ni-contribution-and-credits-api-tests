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

/*package uk.gov.hmrc.api.utils

import com.google.inject.Inject
import play.api.libs.json.{JsValue, Json}
import play.api.Environment

import java.io.{FileInputStream, InputStream}

import scala.io.Source

class JsonUtils @Inject()(environment: Environment) {

  def readJsonFile(filePath: String): JsValue = {
    val jsonSchemaFile = environment.getFile(filePath)
    val inputStream = new FileInputStream(jsonSchemaFile)
    Json.parse(readStreamToString(inputStream))
  }

  private def readStreamToString(is: InputStream): String =
    try Source.fromInputStream(is).mkString
    finally is.close()

  def readJsonIfFileFound(filePath: String): Option[JsValue] = {
    val jsonSchemaFile = environment.getExistingFile(filePath)
    jsonSchemaFile match {
      case Some(schemaFile) =>
        val inputStream = new FileInputStream(schemaFile)
        val jsonString: String = readStreamToString(inputStream)
        Some(Json.parse(jsonString))
      case _ =>
        None
    }
  }

}*/
