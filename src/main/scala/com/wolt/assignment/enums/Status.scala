package com.wolt.assignment.enums

import enumeratum.{CirceEnum, Enum, EnumEntry}
import enumeratum.EnumEntry.Lowercase

/**
 * @author Mikhail Nemenko {@literal <nemenkoma@gmail.com>}
 */
sealed trait Status extends EnumEntry with Lowercase

object Status extends Enum[Status] with CirceEnum[Status] {
  case object Open  extends  Status
  case object Close extends  Status

  override def values: IndexedSeq[Status] = findValues
}
