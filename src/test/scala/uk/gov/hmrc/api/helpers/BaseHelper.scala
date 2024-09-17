package uk.gov.hmrc.api.helpers
import java.time.{LocalDate, Period}
import java.time.format.{DateTimeFormatter, DateTimeParseException}

trait BaseHelper {
  def ValidateDOB(dateString: String): Boolean = {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    try {
      val date = LocalDate.parse(dateString, formatter)
      val today = LocalDate.now()
      val difference = Period.between(date, today)
      difference.getYears > 16
    } catch {
      case e: DateTimeParseException =>
        println("error parsing date", e.getMessage)
        false
    }
  }

  def ValidateStartTaxYear(yearString: String, minYear: Int = 1975, maxYear: Int = 2023): Boolean = {

yearString.matches("""\d{4}""") && {
  val year = yearString.toInt
  year >= minYear && year  <= maxYear
}
  }
}
