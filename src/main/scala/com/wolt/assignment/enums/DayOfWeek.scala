package com.wolt.assignment.enums

import enumeratum.{CirceEnum, Enum}
import enumeratum.EnumEntry.Lowercase
import enumeratum.values.IntEnumEntry

/**
 * @author Mikhail Nemenko {@literal <nemenkoma@gmail.com>}
 */
sealed abstract class DayOfWeek(override val value: Int) extends IntEnumEntry with Lowercase

object DayOfWeek extends Enum[DayOfWeek] with CirceEnum[DayOfWeek]  {
  override def values: IndexedSeq[DayOfWeek] = findValues

  case object Monday    extends DayOfWeek(1)
  case object Tuesday   extends DayOfWeek(2)
  case object Wednesday extends DayOfWeek(3)
  case object Thursday  extends DayOfWeek(4)
  case object Friday    extends DayOfWeek(5)
  case object Saturday  extends DayOfWeek(6)
  case object Sunday    extends DayOfWeek(7)
}

