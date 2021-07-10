package com.wolt.assignments
import java.util.concurrent.TimeUnit

import com.wolt.assignment.model.TimeValue
import eu.timepit.refined.api.Refined
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatest.prop.TableDrivenPropertyChecks


/**
 * @author Mikhail Nemenko {@literal <nemenkoma@gmail.com>}
 */
class TimeValueTest extends AnyFlatSpec with TableDrivenPropertyChecks {

  it should "convert value to 12 hours representation" in {
    //given
    val table = Table(
      "timeInSeconds"                       -> "expectingTime",
      TimeUnit.HOURS.toSeconds(0)  -> "12:00 AM",
      TimeUnit.HOURS.toSeconds(1)  -> "1:00 AM",
      TimeUnit.HOURS.toSeconds(2)  -> "2:00 AM",
      TimeUnit.HOURS.toSeconds(3)  -> "3:00 AM",
      TimeUnit.HOURS.toSeconds(4)  -> "4:00 AM",
      TimeUnit.HOURS.toSeconds(5)  -> "5:00 AM",
      TimeUnit.HOURS.toSeconds(6)  -> "6:00 AM",
      TimeUnit.HOURS.toSeconds(7)  -> "7:00 AM",
      TimeUnit.HOURS.toSeconds(8)  -> "8:00 AM",
      TimeUnit.HOURS.toSeconds(9)  -> "9:00 AM",
      TimeUnit.HOURS.toSeconds(10) -> "10:00 AM",
      TimeUnit.HOURS.toSeconds(11) -> "11:00 AM",
      TimeUnit.HOURS.toSeconds(12) -> "12:00 PM",
      TimeUnit.HOURS.toSeconds(13) -> "1:00 PM",
      TimeUnit.HOURS.toSeconds(14) -> "2:00 PM",
      TimeUnit.HOURS.toSeconds(15) -> "3:00 PM",
      TimeUnit.HOURS.toSeconds(16) -> "4:00 PM",
      TimeUnit.HOURS.toSeconds(17) -> "5:00 PM",
      TimeUnit.HOURS.toSeconds(18) -> "6:00 PM",
      TimeUnit.HOURS.toSeconds(19) -> "7:00 PM",
      TimeUnit.HOURS.toSeconds(20) -> "8:00 PM",
      TimeUnit.HOURS.toSeconds(21) -> "9:00 PM",
      TimeUnit.HOURS.toSeconds(22) -> "10:00 PM",
      TimeUnit.HOURS.toSeconds(23) -> "11:00 PM"
    )

    forAll(table) {
      case (timeInSeconds, expectingTime) =>

        //when
        val timeValue             = TimeValue(Refined.unsafeApply(timeInSeconds.toInt))
        val convertedResult       = TimeValue.convert(timeValue)

        //then
        expectingTime shouldBe convertedResult
    }
  }

  it should "convert seconds to minutes" in {
    (0 to 59).foreach {
      min: Int =>
        //given
        def withPattern(sec: Int) = s"12:${if (sec < 10) "0" + min else min} AM"

        val sec              = TimeUnit.MINUTES.toSeconds(min)
        val timeValue        = TimeValue(Refined.unsafeApply(sec.toInt))

        //then
        val convertedResult  = TimeValue.convert(timeValue)

        //then
        convertedResult shouldBe withPattern(min)
    }
  }
}
