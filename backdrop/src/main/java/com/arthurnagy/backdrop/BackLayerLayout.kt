/*
 * Copyright 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.arthurnagy.backdrop

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.expandable.ExpandableWidget
import com.google.android.material.expandable.ExpandableWidgetHelper
import com.google.android.material.stateful.ExtendableSavedState
import java.util.concurrent.CopyOnWriteArrayList

/**
 * BackLayerLayout implements the Material back layer concept, and can be used to implement
 * navigation drawers and other surfaces.
 *
 *
 * The back layer concept represents a background layer overlapped by a foreground layer. When
 * the background layer is expanded to show additional content (usually as a result of user
 * interaction), it pushes the foreground layer partially off-screen.
 *
 *
 * This view depends heavily on being used as a direct child within a [CoordinatorLayout].
 *
 *
 * Notice BackLayerLayout is a LinearLayout, so you need to make sure you're using the correct
 * orientation that matches the position you've chosen for the back layer (i.e. use `android:orientation="vertical"` in conjunction with `android:gravity="top"` or `android:gravity="bottom"`).
 *
 *
 * **Usage guide:**
 *
 *
 *  * There has to be **exactly ONE** other direct child of the same CoordinatorLayout that
 * uses [BackLayerSiblingBehavior] as its behavior (set `app:layout_behavior="@string/design_backlayer_sibling_behavior"`). This is the content
 * layer. Clicks on the content layer while the back layer is exposed will cause the back
 * layer to collapse.
 *  * The `BackLayerLayout` can contain an arbitrary number of subviews, however **exactly
 * ONE** of them must be a [CollapsedBackLayerContents], anything inside this view
 * will be considered the contents of the back layer that will always be visible. All other
 * views will be extra content under the content layer. You can support multiple experiences
 * under the back layer by changing the visibility or swapping out these other views.
 *  * You must use match_parent for the `BackLayerLayout`'s width and height.
 *  * Set both `android:gravity` and `android:layout_gravity` for the `BackLayerLayout` to the same value. This value is the edge to which the back layer is
 * attached and can be any of `left`, `start`, `left|start`, `top`,
 * `right`, `right|end`, `end`, `bottom`.
 *  * Set `BackLayerLayout`'s `android:orientation` to `vertical` or `horizontal` matching the gravity (`vertical` for gravities `top` or `bottom`, otherwise use `horizontal`).
 *  * Add UI elements and behavior to expose the back layer. `BackLayerLayout` does not try
 * to be smart about when to expand, so you must add UI to expand the back layer (using an
 * OnClickListener on a button, for example). `BackLayerLayout` offers a [ ][.setExpanded] method that you can call in response to clicks or other events.
 *  * Add [BackLayerCallback]s using [.addBackLayerCallback] in
 * order to listen to changes in the back layer's status. This also may be useful if your back
 * layer needs extra animations, you could use [BackLayerCallback.onBeforeExpand] and
 * [BackLayerCallback.onBeforeCollapse] for this purpose.
 *  * If you [BackLayerCallback] at all you probably need to implement[ ][BackLayerCallback.onRestoringExpandedBackLayer]. This method must not use any animations
 * while replicating the effects of calling [BackLayerCallback.onBeforeExpand]
 * followed by [BackLayerCallback.onAfterExpand]. When restoring the expanded status
 * on activity restarts, no animation will be used and thus [ ][BackLayerCallback.onBeforeExpand] and [BackLayerCallback.onAfterExpand] will not
 * be called.
 *  * You MUST NOT use a [ViewGroup.OnHierarchyChangeListener] on the back layer as it is
 * used for internal housekeeping.
 *
 *
 * <pre>`<CoordinatorLayout ...>
 * <BackLayerLayout
 * android:layout_width="match_parent"
 * android:layout_height="match_parent"
 * android:layout_gravity="top"
 * android:gravity="top"
 * android:orientation="vertical">
 * <CollapsedBackLayerContents
 * android:layout_width="match_parent"
 * android:layout_height="wrap_content">
 * <include layout="@layout/always_visible_content"/>
 * </CollapsedBackLayerContents>
 * <include layout="@layout/default_content_hidden_behind_content_layer"/>
 * <include
 * layout="@layout/secondary_content_hidden_behind_content_layer"
 * android:visibility="GONE"/>
 * </BackLayerLayout>
 * <YourContentLayerView
 * android:layout_width="match_parent"
 * android:layout_height="match_parent"
 * app:layout_behavior="@string/design_backlayer_sibling_behavior"/>
 * </CoordinatorLayout>
`</pre> *
 *
 *
 * The reason you need to specify both `android:gravity` and `android:layout_gravity`
 * and they must match is that they are used for different purposes:
 *
 *
 *  * `layout_gravity` is used to specify to the [BackLayerSiblingBehavior] what edge
 * the back layer is anchored to. `layout_gravity` is used by `BackLayerLayout` to
 * corectly measure its expanded state (setting the moving dimension's [MeasureSpec] to
 * [MeasureSpec.AT_MOST]). `layout_gravity` is also used by the [ ] to measure and lay out the content layer view to cover the area
 * of the back layer that does not contain the [CollapsedBackLayerContents] (when the
 * back layer is collapsed).
 *  * `gravity` is used to have the contents of the back layer gravitate to the same edge,
 * see [LinearLayout.setGravity] for more information on this.
 *
 */
// Implementation detail ahead, since it's not relevant to the user this has been pulled out of the
// Javadoc:
// Considering the usages for gravity and layout_gravity spelled out above, and that both values
// must match in order for the BackLayerLayout to work correctly, we thought of ways to depend only
// on one of those two values. We decided to attempt to remove the dependency on layout_gravity for
// the following reasons:
// 1. The way we use gravity to have the contents of the back layer gravitate to the correct edge is
// actually implemented in LinearLayout and the dependence on gravity is deeply ingrained in this
// code, it would be prohibitively hard to rework layout_gravity for this purpose.
// 2. It is not recommended for widgets themselves to depend on LayoutParams, and we would only
// worsen the situation adding another dependency on layout_gravity. While it is true that
// BackLayerLayout is only supported while used inside a CoordinatorLayout and it is already tightly
// coupled to it, it seems backwards to try to retrofit this into code that comes from the
// superclass.
//
// When trying to depend only on gravity we found the following two issues:
// 1. LinearLayout does not expose getGravity() prior to API  24, that is solvable through
// reflection (and it would likely work in all devices though that is not guaranteed).
// 2. LinearLayout does not just take an edge gravity (like top, left, right....), if it is an edge
// gravity it forces it to become a corner gravity (top|start, for example). This is problematic
// because BackLayerLayout and BackLayerSiblingBehavior constantly check the edge gravity to do the
// following:
//   - Measure the expanded content of the back layer.
//   - Measure and layout the content layer.
//   - Slide the content layer out of view when the back layer is expanded.
// All of these operations depend on having an edge gravity instead of a corner gravity, so short of
// rewriting the relevant parts of LinearLayout in BackLayerLayout, using the same gravity value
// that LinearLayout depends on is not an option for BackLayerLayout and BackLayerSiblingBehavior.
class BackLayerLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs), ExpandableWidget {

    private var expandedHeight: Int = 0
    private var expandedWidth: Int = 0
    private var expandedSizeMeasured = false
    private var originalMeasureSpecsSaved = false
    private var originalHeightMeasureSpec: Int = 0
    private var originalWidthMeasureSpec: Int = 0
    private var childViewAccessibilityHelper: ChildViewAccessibilityHelper? = null

    private val callbacks = CopyOnWriteArrayList<BackLayerCallback>()
    private val expandableWidgetHelper = ExpandableWidgetHelper(this)

    /**
     * Add a new [BackLayerCallback] to listen to back layer events.
     */
    fun addBackLayerCallback(callback: BackLayerCallback) {
        if (!callbacks.contains(callback)) {
            callbacks.add(callback)
        }
    }

    /**
     * Expands or collapses the back layer.
     *
     *
     * Notice that this method does not automatically change visibility on child views of the back
     * layer, the developer has to prepare the contents of the back layer either before calling this
     * method or in [BackLayerCallback.onBeforeExpand] or [ ][BackLayerCallback.onBeforeCollapse].
     */
    override fun setExpanded(expanded: Boolean): Boolean {
        return expandableWidgetHelper.setExpanded(expanded)
    }

    override fun isExpanded(): Boolean {
        return expandableWidgetHelper.isExpanded
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        childViewAccessibilityHelper = ChildViewAccessibilityHelper(this)
        setOnHierarchyChangeListener(childViewAccessibilityHelper)
        childViewAccessibilityHelper!!.disableChildFocus()
    }

    override fun requestLayout() {
        super.requestLayout()
        expandedSizeMeasured = false
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (!originalMeasureSpecsSaved) {
            originalWidthMeasureSpec = widthMeasureSpec
            originalHeightMeasureSpec = heightMeasureSpec
            originalMeasureSpecsSaved = true
        }
        // Measure the minimum size only if it's not previously set, for example in XML layout.
        if (ViewCompat.getMinimumHeight(this) == 0 && ViewCompat.getMinimumWidth(this) == 0) {
            // Find the CollapsedBackLayerContents
            var foundCollapsed = false
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                if (child is CollapsedBackLayerContents) {
                    if (foundCollapsed) {
                        throw IllegalStateException("More than one CollapsedBackLayerContents found inside BackLayerLayout")
                    }
                    foundCollapsed = true
                    val childLayoutParams = child.getLayoutParams() as LinearLayout.LayoutParams
                    child.measure(childLayoutParams.width, childLayoutParams.height)
                    minimumHeight = (child.getMeasuredHeight()
                            + childLayoutParams.bottomMargin
                            + childLayoutParams.topMargin)
                    minimumWidth = (child.getMeasuredWidth()
                            + childLayoutParams.leftMargin
                            + childLayoutParams.rightMargin)
                }
            }
            if (!foundCollapsed) {
                throw IllegalStateException("No CollapsedBackLayerContents found inside BackLayerLayout")
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    /**
     * Call this to measure the BackLayerLayout's expanded size on-demand. This must be called before
     * [.calculateExpandedWidth] and [.calculateExpandedHeight] are queried.
     */
    private fun remeasureExpandedSize() {
        if (expandedSizeMeasured) {
            return
        }

        val layoutParams = layoutParams as CoordinatorLayout.LayoutParams
        val absoluteGravity = Gravity.getAbsoluteGravity(layoutParams.gravity, ViewCompat.getLayoutDirection(this))
        var heightMeasureSpec = originalHeightMeasureSpec
        var widthMeasureSpec = originalWidthMeasureSpec
        // In order to know the measurements for a expanded version of the back layer we need to
        // measure the back layer with one dimension set to MeasureSpec.UNSPECIFIED instead of the
        // setting
        // that came in the original MeasureSpec (MeasureSpec.EXACTLY, since the BackLayerLayout must
        // use match_parent for both dimensions).
        //
        // While it would seem natural to use MeasureSpec.AT_MOST, this method can be called from
        // onRestoreInstanceState(Parcelable) which would happen before the first measure pass, and thus
        // the original measure specs would be 0, causing a wrong measurement.
        when (absoluteGravity) {
            Gravity.START, Gravity.END -> widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                View.MeasureSpec.getSize(widthMeasureSpec), View.MeasureSpec.UNSPECIFIED
            )
            Gravity.TOP, Gravity.BOTTOM -> {
                val size = View.MeasureSpec.getSize(heightMeasureSpec)
                heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(size, View.MeasureSpec.UNSPECIFIED)
            }
            else -> {
            }
        }
        measure(widthMeasureSpec, heightMeasureSpec)
        expandedHeight = measuredHeight
        expandedWidth = measuredWidth
        // Recalculate with the original measure specs, so it fits the entire coordinator layout.
        measure(originalWidthMeasureSpec, originalHeightMeasureSpec)

        expandedSizeMeasured = true
    }

    public override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        val state = ExtendableSavedState(superState!!)

        state.extendableStates.put(
            "expandableWidgetHelper", expandableWidgetHelper.onSaveInstanceState()
        )

        return state
    }

    public override fun onRestoreInstanceState(state: Parcelable) {
        if (state !is ExtendableSavedState) {
            super.onRestoreInstanceState(state)
            return
        }

        super.onRestoreInstanceState(state.superState)

        expandableWidgetHelper.onRestoreInstanceState(
            state.extendableStates.get("expandableWidgetHelper")
        )
    }

    internal fun onExpandAnimationStart() {
        for (callback in callbacks) {
            callback.onBeforeExpand()
        }
    }

    internal fun onExpandAnimationEnd() {
        childViewAccessibilityHelper!!.restoreChildFocus()
        for (callback in callbacks) {
            callback.onAfterExpand()
        }
    }

    internal fun onCollapseAnimationStart() {
        for (callback in callbacks) {
            callback.onBeforeCollapse()
        }
    }

    internal fun onCollapseAnimationEnd() {
        childViewAccessibilityHelper!!.disableChildFocus()
        for (callback in callbacks) {
            callback.onAfterCollapse()
        }
    }

    /**
     * The measured height for the expanded version of the back layer.
     */
    internal fun calculateExpandedHeight(): Int {
        if (!expandedSizeMeasured) {
            remeasureExpandedSize()
        }
        return expandedHeight
    }

    /**
     * The measured width for the expanded version of the back layer.
     */
    internal fun calculateExpandedWidth(): Int {
        if (!expandedSizeMeasured) {
            remeasureExpandedSize()
        }
        return expandedWidth
    }
}