import java.time.{LocalDate, LocalDateTime, ZonedDateTime}
import java.time.format.{FormatStyle, DateTimeFormatter}
import java.util.Locale
import scala.util.{Try, Success, Failure}

/**
  * Practice code used to learn about parsing dates in scala.
  */
class DateTime {

  case class window(start: LocalDate, end: LocalDate)

  var list = List.tabulate(20)(n => window(LocalDate.of(2019, 1, n + 1), LocalDate.of(2019, 1, n + 2)))

  println(list.isEmpty)
  println(list)

  //list.sortWith((d1, d2) => d1.end isBefore d2.end).map(println)

  // time strings with and without timezone offsets
  val dateStr = "2017-06-03T14:18:39.876828+05:30"
  val dateStrNoZone = "2017-06-03T14:18:39.876828+05:30".split("\\+")(0)

  // try to parse first as a string with timezone, then as a string without
  var tryDate = Try(ZonedDateTime.parse(dateStrNoZone)) match {
    case Success(m) => m
    case Failure(e) => {
      LocalDateTime.parse(dateStrNoZone)
    }
  }

  // removes time zone offset from ISO date-time strign
  var d1 = LocalDateTime.parse(dateStrNoZone)
  // var d1=tryDate

  // Creates a time-zoned date-time object
  var dz1 = ZonedDateTime.parse(dateStr)

  println("Parsed date string: " + tryDate)
  println(tryDate.getClass)
  //} else {
  //  LocalDateTime.parse(dateStrNoZone)
  //}

  println(d1)
  println(dz1)

  // format the datetime WITHOUT time zone in the medium format style, using FULL throws an error
  var d1f = d1.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Locale.US))

  // if there is a timezone offset you can use the full format
  var dz1f = dz1.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).withLocale(Locale.US))

  // split the formatted string on spaces so you can further process
  val dlist = d1f.split(" ")
  println("LocalDateTime with MEDIUM format, locale-US: " + d1f)
  println("ZonedDateTime with FULL format, locale-US: " + dz1f)

  // compose the string so it makes sense, need to remove the seconds from the time string,
  // the slice end needs to be after the start index.
  var outStr = dlist.slice(0, 3).mkString(" ") + " at " + dlist.slice(3, dlist.indexOf(dlist.last) + 1).mkString(" ")
  println("Final formatted string: " + outStr)
}