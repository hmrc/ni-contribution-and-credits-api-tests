package uk.gov.hmrc.api.helpers
import java.time.{LocalDate, Period}
import java.time.format.{DateTimeFormatter, DateTimeParseException}

trait BaseHelper {
  def ValidateDOB(dateString: String): Boolean = {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
      try
        {
val date = LocalDate.parse(dateString,formatter)
          val today = LocalDate.now()
          val difference = Period.between(date,today)
          difference.getYears>16
        } catch{
        case e: DateTimeParseException =>
          println("error parsing date", e.getMessage)
          false
      }
  }

}
