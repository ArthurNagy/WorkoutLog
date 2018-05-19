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


import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.transformation.ExpandableBehavior

/**
 * Behavior to apply to the content view when using a BackLayerLayout.
 *
 *
 * Using this behavior requires **exactly** one sibling view of type [BackLayerLayout]
 * which will be used to calculate the measurements and positions for the content layer view.
 *
 *
 * You MUST NOT use a [ViewGroup.OnHierarchyChangeListener] on the view to which you apply
 * this behavior, as this behavior uses OnHierarchyChangedListener for internal housekeeping.
 */
class BackLayerSiblingBehavior : ExpandableBehavior {

    private var layoutDirection: Int = 0
    private var expandedContentDescription: CharSequence? = null
    private var contentViewAccessibilityHelper: ContentViewAccessibilityPropertiesHelper? = null
    private var currentAnimator: Animator? = null

    constructor()

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(attrs, R.styleable.BackLayerSiblingBehavior, 0, 0)
            try {
                expandedContentDescription = a.getString(R.styleable.BackLayerSiblingBehavior_behavior_expandedContentDescription)
                if (expandedContentDescription == null) {
                    expandedContentDescription = context
                        .resources
                        .getString(R.string.design_backlayer_expanded_content_layer_content_description)
                }
            } finally {
                a.recycle()
            }
        }
    }

    /**
     * Sets the content description for accessibility services to be used on the content layer view.
     */
    fun setExpandedContentDescription(expandedContentDescription: CharSequence) {
        this.expandedContentDescription = expandedContentDescription
    }

    //  Implementation of Behavior Methods

    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        return dependency is BackLayerLayout
    }

    override fun onMeasureChild(
        parent: CoordinatorLayout,
        child: View,
        parentWidthMeasureSpec: Int,
        widthUsed: Int,
        parentHeightMeasureSpec: Int,
        heightUsed: Int
    ): Boolean {
        // The first time onMeasureChild is called it should initialize the
        // contentViewAccessibilitHelper. This method is called before any other method that could
        // potentially need this field. Since the field is stateful we need to guarantee it is only set
        // the first time this is called.
        if (contentViewAccessibilityHelper == null) {
            contentViewAccessibilityHelper = ContentViewAccessibilityPropertiesHelper(child)
        }

        val backLayerLayout = findExpandableWidget(parent, child) as BackLayerLayout? ?: throw IllegalStateException(
            "There is no BackLayerLayout and a view is using BackLayerSiblingBehavior"
        )

        val backLayerLayoutParams = backLayerLayout.layoutParams as CoordinatorLayout.LayoutParams
        val childLayoutParams = child.layoutParams as CoordinatorLayout.LayoutParams
        if (!checkGravity(backLayerLayoutParams)) {
            throw IllegalStateException(
                "The gravity for BackLayerLayout is not set to one of {top,bottom,left,right,start,end}"
            )
        }

        // Use the back layer's original dimensions to measure, always, even when these have changed
        // thanks to expansion.
        var usedWidth = if (Gravity.isHorizontal(backLayerLayoutParams.gravity))
            ViewCompat.getMinimumWidth(backLayerLayout)
        else
            0
        usedWidth += (parent.paddingLeft
                + parent.paddingRight
                + childLayoutParams.leftMargin
                + childLayoutParams.rightMargin)
        var usedHeight = if (Gravity.isVertical(backLayerLayoutParams.gravity))
            ViewCompat.getMinimumHeight(backLayerLayout)
        else
            0
        usedHeight += (parent.paddingTop
                + parent.paddingBottom
                + childLayoutParams.topMargin
                + childLayoutParams.bottomMargin)

        val widthMeasureSpec = ViewGroup.getChildMeasureSpec(parentWidthMeasureSpec, usedWidth, childLayoutParams.width)
        val heightMeasureSpec = ViewGroup.getChildMeasureSpec(
            parentHeightMeasureSpec, usedHeight, childLayoutParams.height
        )

        child.measure(widthMeasureSpec, heightMeasureSpec)
        return true
    }

    override fun onLayoutChild(parent: CoordinatorLayout, child: View, layoutDirection: Int): Boolean {
        // Super contains support for detecting first layout after configuration change.
        super.onLayoutChild(parent, child, layoutDirection)

        val backLayerLayout = findExpandableWidget(parent, child) as BackLayerLayout? ?: throw IllegalStateException(
            "There is no BackLayerLayout and a view is using BackLayerSiblingBehavior"
        )

        this.layoutDirection = layoutDirection
        val backLayerLayoutParams = backLayerLayout.layoutParams as CoordinatorLayout.LayoutParams
        val absoluteGravity = Gravity.getAbsoluteGravity(backLayerLayoutParams.gravity, layoutDirection)
        val collapsedWidth = ViewCompat.getMinimumWidth(backLayerLayout)
        val collapsedHeight = ViewCompat.getMinimumHeight(backLayerLayout)

        // Do actual layout using measured dimensions from #onMeasureChild().
        parent.onLayoutChild(child, layoutDirection)

        // Adjust position - note that this places the child correctly in its collapsed state.
        when (absoluteGravity) {
            Gravity.TOP -> {
                child.offsetTopAndBottom(collapsedHeight - child.top)
            }
            Gravity.START -> {
                child.offsetLeftAndRight(collapsedWidth - child.left)
            }
            Gravity.BOTTOM -> {
                val bottom = parent.height - collapsedHeight
                child.offsetTopAndBottom(bottom - child.bottom)
            }
            Gravity.END -> {
                val right = parent.width - collapsedWidth
                child.offsetLeftAndRight(right - child.right)
            }
        }// do nothing

        if (backLayerLayout.isExpanded) {
            // If we went through layout due to BackLayerLayout's children changing size, we need to
            // translate to the new expanded position.
            animateTranslation(backLayerLayout, child, true, null)
        }
        return true
    }

    override fun onInterceptTouchEvent(parent: CoordinatorLayout, child: View, ev: MotionEvent): Boolean {
        val backLayerLayout = findExpandableWidget(parent, child) as BackLayerLayout? ?: throw IllegalStateException(
            "There is no BackLayerLayout and a view is using BackLayerSiblingBehavior"
        )

        if (backLayerLayout.isExpanded) {
            // onInterceptTouchEvent is called for every touch in the CoordinatorLayout. Because of this
            // we need to check that the MotionEvent's coordinates are inside of the Child View.
            if (parent.isPointInChildBounds(child, ev.x.toInt(), ev.y.toInt())) {
                backLayerLayout.isExpanded = false
                return true
            }
        }
        return false
    }

    override fun onExpandedStateChange(
        dependency: View, child: View, expanded: Boolean, animated: Boolean
    ): Boolean {
        // Translate the content layer to the desired position.
        val backLayerLayout = dependency as BackLayerLayout
        animateTranslation(
            backLayerLayout,
            child,
            animated,
            object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    if (expanded) {
                        contentViewAccessibilityHelper!!.makeFocusableWithContentDescription(
                            expandedContentDescription!!
                        )
                        backLayerLayout.onExpandAnimationStart()
                    } else {
                        backLayerLayout.onCollapseAnimationStart()
                    }
                }

                override fun onAnimationEnd(animation: Animator) {
                    if (expanded) {
                        backLayerLayout.onExpandAnimationEnd()
                    } else {
                        contentViewAccessibilityHelper!!.restoreAccessibilityProperties()
                        backLayerLayout.onCollapseAnimationEnd()
                    }
                }
            })
        return true
    }

    // Private methods

    private fun animateTranslation(
        backLayerLayout: BackLayerLayout,
        child: View,
        animated: Boolean,
        listener: AnimatorListener?
    ) {
        if (currentAnimator != null) {
            currentAnimator!!.cancel()
        }

        currentAnimator = if (backLayerLayout.isExpanded) {
            createExpandAnimation(backLayerLayout, child)
        } else {
            createCollapseAnimation(backLayerLayout, child)
        }

        if (listener != null) {
            currentAnimator!!.addListener(listener)
        }

        currentAnimator!!.start()
        if (!animated) {
            // Synchronously end the animation, jumping to the end state.
            currentAnimator!!.end()
        }
    }

    private fun createExpandAnimation(backLayerLayout: BackLayerLayout, child: View): Animator {
        val expandedWidth = backLayerLayout.calculateExpandedWidth()
        val expandedHeight = backLayerLayout.calculateExpandedHeight()
        val collapsedWidth = ViewCompat.getMinimumWidth(backLayerLayout)
        val collapsedHeight = ViewCompat.getMinimumHeight(backLayerLayout)

        val deltaX = expandedWidth - collapsedWidth
        val deltaY = expandedHeight - collapsedHeight

        val backLayerLayoutParams = backLayerLayout.layoutParams as CoordinatorLayout.LayoutParams
        val absoluteGravity = Gravity.getAbsoluteGravity(backLayerLayoutParams.gravity, layoutDirection)
        val animator: Animator = when (absoluteGravity) {
            Gravity.TOP -> ObjectAnimator.ofFloat<View>(child, View.TRANSLATION_Y, deltaY.toFloat())
            Gravity.START -> ObjectAnimator.ofFloat<View>(child, View.TRANSLATION_X, deltaX.toFloat())
            Gravity.BOTTOM -> ObjectAnimator.ofFloat<View>(child, View.TRANSLATION_Y, -deltaY.toFloat())
            Gravity.END -> ObjectAnimator.ofFloat<View>(child, View.TRANSLATION_X, -deltaX.toFloat())
            else -> ObjectAnimator.ofFloat<View>(child, View.TRANSLATION_X, -deltaX.toFloat())
        }
        animator.duration = ANIMATION_DURATION.toLong()
        return animator
    }

    private fun createCollapseAnimation(backLayerLayout: BackLayerLayout, child: View): Animator {
        val backLayerLayoutParams = backLayerLayout.layoutParams as CoordinatorLayout.LayoutParams
        val absoluteGravity = Gravity.getAbsoluteGravity(backLayerLayoutParams.gravity, layoutDirection)
        val animator: Animator = when (absoluteGravity) {
            Gravity.TOP -> ObjectAnimator.ofFloat(child, View.TRANSLATION_Y, 0f)
            Gravity.START -> ObjectAnimator.ofFloat(child, View.TRANSLATION_X, 0f)
            Gravity.BOTTOM -> ObjectAnimator.ofFloat(child, View.TRANSLATION_Y, 0f)
            Gravity.END -> ObjectAnimator.ofFloat(child, View.TRANSLATION_X, 0f)
            else -> ObjectAnimator.ofFloat(child, View.TRANSLATION_X, 0f)
        }
        animator.duration = ANIMATION_DURATION.toLong()
        return animator
    }

    /**
     * Checks that the gravity is set to one of the 4 directions.
     */
    private fun checkGravity(layoutParams: CoordinatorLayout.LayoutParams): Boolean {
        val gravity = layoutParams.gravity
        return (gravity == Gravity.TOP
                || gravity == Gravity.BOTTOM
                || gravity == Gravity.START
                || gravity == Gravity.END
                || gravity == Gravity.START
                || gravity == Gravity.END)
    }

    companion object {
        private const val ANIMATION_DURATION = 225
    }
}