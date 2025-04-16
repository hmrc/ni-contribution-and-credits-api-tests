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

package uk.gov.hmrc.api.helpers

import play.api.libs.json._

import java.time.format.{DateTimeFormatter, DateTimeParseException}
import java.time.{LocalDate, Period}
import java.util.Calendar

trait BaseHelper {

  def checkNINOFormat(nino: String): Unit = {
    val expectedRegex = """^[A-Z]{2}[0-9]{6}[A-Z]{0,1}$""".r
    val isMatch       = expectedRegex.matches(nino)
    assert(isMatch)
  }

  def checkResponseStatus(status: Int, expected: Int): Unit =
    assert(status == expected, message = s"Expected a Status of $expected : Actual Status is $status")

  def checkResponsePayload(response: String, expectedTypes: Map[String, Any]): Unit = {
    val responseBody: JsValue    = Json.parse(response)
    val balances: List[JsObject] = (responseBody \ "niClass1").as[List[JsObject]] // Update to balanceDetails
    balances.foreach { niClass1 =>
      expectedTypes.foreach { case (key, expectedType) =>
        val value = (niClass1 \ key).get
        val actualType = value match {
          case _: JsString => "string"
          case _: JsNumber => "number"
          case _           => "unknown"
        }
        assert(actualType == expectedType, s"Expected type is $expectedType, but got $actualType.")
      }
    }
  }
  /*def checkErrorResponsePayload(response: String, expectedTypes: Map[String, Any]): Unit = {
  /*val badRequestErrorResponse =
    "{\"failures\":[{\"reason\":\"There was a problem with the request\",\"code\":\"400\"}]}"*/
  val responseBody: JsValue    = Json.parse(response)
  val balances: List[JsObject] = (responseBody \ "failures").as[List[JsObject]]
  balances.foreach { failures =>
    expectedTypes.foreach { case (key, expectedType) =>
      val value      = (failures \ key).get
      val actualType = value match {
        case _: JsString => "string"
        case _: JsNumber => "number"
        case _           => "unknown"
      }
      assert(actualType == expectedType, s"Expected type is $expectedType, but got $actualType.")
    }
  }
}*/

  def ValidateDOB(dateString: String): Boolean = {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    try {
      val date       = LocalDate.parse(dateString, formatter)
      val today      = LocalDate.now()
      val difference = Period.between(date, today)
      difference.getYears > 16
    } catch {
      case e: DateTimeParseException =>
        println(s"error parsing date: ${e.getMessage}")
        false
    }
  }

  def ValidateStartTaxYear(yearString: String): Boolean = {
    val minYear: Int     = 1975
    val cal: Calendar    = Calendar.getInstance()
    val currentYear: Int = cal.get(Calendar.YEAR)
    yearString.matches("""\d{4}""") && {
      val year = yearString.toInt
      year >= minYear && year <= currentYear
    }
  }

}
