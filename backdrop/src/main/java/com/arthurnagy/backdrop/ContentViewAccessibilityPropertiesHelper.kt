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

import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat


class ContentViewAccessibilityPropertiesHelper(private val view: View) {

    private var childViewAccessibilityHelper: ChildViewAccessibilityHelper? = null
    private var previousFocusability: Boolean = false
    private var previousImportantForAccesibility: Int = 0
    private var previousContentDescription: CharSequence? = null

    init {
        previousContentDescription = view.contentDescription
        previousFocusability = view.isFocusable
        previousImportantForAccesibility = ViewCompat.getImportantForAccessibility(view)
        if (view is ViewGroup) {
            val viewGroup = view
            childViewAccessibilityHelper = ChildViewAccessibilityHelper(viewGroup)
            viewGroup.setOnHierarchyChangeListener(childViewAccessibilityHelper)
        }
    }

    /**
     * Overrides the values of accessibility-related properties to make the content view focusable in
     * accessibility mode.
     *
     *
     * This method sets the view's `contentDescription` to this method's only argument and
     * sets both `importantForAccessibility` and `focusable` to true.
     *
     *
     * This method also stores previous values of these properties so they can be restored by
     * calling [.restoreAccessibilityProperties].
     *
     *
     * If the content view is actually a ViewGroup, this method uses a [ ] to disable focus on the subviews while the content view is forced
     * to be focusable. Child views' focusability also is reverted in [ ][.restoreAccessibilityProperties].
     */
    fun makeFocusableWithContentDescription(contentDescription: CharSequence) {
        previousContentDescription = view.contentDescription
        previousFocusability = view.isFocusable
        previousImportantForAccesibility = ViewCompat.getImportantForAccessibility(view)
        view.isFocusable = true
        view.contentDescription = contentDescription
        ViewCompat.setImportantForAccessibility(view, ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_YES)
        if (childViewAccessibilityHelper != null) {
            childViewAccessibilityHelper!!.disableChildFocus()
        }
    }

    /**
     * Restores the values of `focusable`, `importantForAccessibility` and `contentDescription` to the values stored when [ ][.makeFocusableWithContentDescription] was called.
     *
     *
     * If this [ContentViewAccessibilityPropertiesHelper] is being used with a [ ], this also restores the original values of these properties for its child views.
     *
     *
     * This method is a no-op if not called after to [ ][.makeFocusableWithContentDescription]
     */
    fun restoreAccessibilityProperties() {
        view.contentDescription = previousContentDescription
        view.isFocusable = previousFocusability
        ViewCompat.setImportantForAccessibility(view, previousImportantForAccesibility)
        if (childViewAccessibilityHelper != null) {
            childViewAccessibilityHelper!!.restoreChildFocus()
        }
    }

}