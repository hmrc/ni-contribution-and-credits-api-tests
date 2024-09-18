package uk.gov.hmrc.api.helpers
import java.time.format.{DateTimeFormatter, DateTimeParseException}
import java.time.{LocalDate, Period}
import java.util.Calendar


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

  def ValidateStartTaxYear(yearString: String): Boolean = {
    val minYear: Int = 1975
    val cal: Calendar = Calendar.getInstance()
    val currentYear: Int = cal.get(Calendar.YEAR)
yearString.matches("""\d{4}""") && {
  val year = yearString.toInt
  year >= minYear && year  <= currentYear
}
  }
}
