package com.arthurnagy.workoutlog.feature.workouts

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.arthurnagy.workoutlog.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.MaterialCalendarView

class WeekView @JvmOverloads constructor(context: Context, attr: AttributeSet? = null) : MaterialCalendarView(context, attr) {

    init {
        topbarVisible = false
        setDateTextAppearance(R.style.TextAppearanceBody2)
        selectionMode = SELECTION_MODE_SINGLE

        state().edit()
            .setCalendarDisplayMode(CalendarMode.WEEKS)
            .isCacheCalendarPositionEnabled(true)
            .commit()

        addDecorator(SelectionDecorator(context))
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        val today = CalendarDay.today()
        currentDate = today
        setDateSelected(today, true)
    }

    private class SelectionDecorator(private val context: Context) : DayViewDecorator {

        override fun shouldDecorate(day: CalendarDay?): Boolean = true

        override fun decorate(dayViewFacade: DayViewFacade?) {
            ContextCompat.getDrawable(context, R.drawable.bg_week_day)?.let {
                dayViewFacade?.setSelectionDrawable(it)
            }
        }

    }

}