package com.arthurnagy.workoutlog.feature

import android.content.Context
import android.util.AttributeSet
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.MaterialCalendarView

class WeekView @JvmOverloads constructor(context: Context, attr: AttributeSet? = null) : MaterialCalendarView(context, attr) {

    init {
        topbarVisible = false
        state().edit()
            .setCalendarDisplayMode(CalendarMode.WEEKS)
            .isCacheCalendarPositionEnabled(true)
            .commit()
    }


}