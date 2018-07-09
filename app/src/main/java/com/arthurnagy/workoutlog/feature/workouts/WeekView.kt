package com.arthurnagy.workoutlog.feature.workouts

import android.content.Context
import android.util.AttributeSet
import com.arthurnagy.workoutlog.R
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.MaterialCalendarView

class WeekView @JvmOverloads constructor(context: Context, attr: AttributeSet? = null) : MaterialCalendarView(context, attr) {

    init {
        topbarVisible = false
        setDateTextAppearance(R.style.TextAppearanceBody2)
        state().edit()
            .setCalendarDisplayMode(CalendarMode.WEEKS)
            .isCacheCalendarPositionEnabled(true)
            .commit()
    }


}