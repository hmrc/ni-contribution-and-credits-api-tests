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
import java.time.format.{DateTimeFormatter, DateTimeParseException}
import java.time.{LocalDate, Period}
import java.util.Calendar

trait BaseHelper {
  def ValidateDOB(dateString: String): Boolean = {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    try {
      val date       = LocalDate.parse(dateString, formatter)
      val today      = LocalDate.now()
      val difference = Period.between(date, today)
      difference.getYears > 16
    } catch {
      case e: DateTimeParseException =>
        println("error parsing date", e.getMessage)
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
