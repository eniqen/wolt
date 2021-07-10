package com.wolt.assignment.model

import cats.data.Chain
import com.wolt.assignment.enums.{DayOfWeek, Status}

/**
 * @author Mikhail Nemenko {@literal <nemenkoma@gmail.com>}
 */
final case class TimeTable(
  monday:    Chain[Info] = Chain.empty,
  tuesday:   Chain[Info] = Chain.empty,
  wednesday: Chain[Info] = Chain.empty,
  thursday:  Chain[Info] = Chain.empty,
  friday:    Chain[Info] = Chain.empty,
  saturday:  Chain[Info] = Chain.empty,
  sunday:    Chain[Info] = Chain.empty
)

object TimeTable {

  implicit final class TimeTableOps(private val table: TimeTable) extends AnyVal {
    def toList: List[(DayOfWeek, Chain[Info])] = List(
      DayOfWeek.Sunday    -> table.sunday,
      DayOfWeek.Saturday  -> table.saturday,
      DayOfWeek.Friday    -> table.friday,
      DayOfWeek.Thursday  -> table.thursday,
      DayOfWeek.Wednesday -> table.wednesday,
      DayOfWeek.Tuesday   -> table.tuesday,
      DayOfWeek.Monday    -> table.monday
    )

    def asFormattedResult: List[Formatted] = {
      val timeTable = table.toList
      val keys      = timeTable.map(_._1)

      keys.zip(keys.tail).foldLeft(timeTable.toMap) {
        case (acc, (firstKey, secondKey)) =>
          (for {
            firstV         <- acc.get(firstKey)
            secondV        <- acc.get(secondKey)

            (start ,tail)  <- firstV.uncons.filter { case (info, _) => info.`type` == Status.Close}
            _              <- secondV.lastOption.filter(_.`type` == Status.Open)
          } yield (firstKey -> tail, secondKey -> secondV.append(start)))
            .fold(acc) { case (firstToUpdate, secondToUpdate) => acc + firstToUpdate + secondToUpdate
            }
      }.map { case (k, v) => Formatted(k, Info.toFromTo(v)) }.toList.sortBy(_.dayOfWeek.value)
    }
  }
}