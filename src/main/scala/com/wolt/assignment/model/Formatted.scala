package com.wolt.assignment.model

import cats.Show
import com.wolt.assignment.enums.DayOfWeek

/**
 * @author Mikhail Nemenko {@literal <nemenkoma@gmail.com>}
 */
final case class Formatted(dayOfWeek: DayOfWeek, time: List[FromTo])

object Formatted {
  implicit val formattedShow: Show[Formatted] = Show.show {
    formatted =>
      val value = if(formatted.time.isEmpty) {
        List("Closed")
      } else formatted.time.map(ft => s"${TimeValue.convert(ft.from)} - ${TimeValue.convert(ft.to)}")

      s"${formatted.dayOfWeek.entryName.capitalize}: ${value.mkString(", ")}"
  }
}
