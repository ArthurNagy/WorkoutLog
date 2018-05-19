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
import java.util.*

/**
 * Class that overrides the `importantForAccessibility` and `focusable` properties for
 * child views. This is necessary to offer the same experience for vision impaired users that depend
 * on accessibility services such as TalkBack by making sure the AccesibilityNodeInfo tree matches
 * the views with which it is possible to interact in both backlayer states, expanded and collapsed.
 * These changes are also important for the growing number of users who use a physical keyboard with
 * a TAB key (i.e. tablet users, Chromebook with Android apps ...)
 *
 *
 * When the back layer is collapsed, this is used by the back layer to disable all views outside
 * of CollapsedBackLayerContents.
 *
 *
 * When the back layer is expanded it is used by the [BackLayerSiblingBehavior] to disable
 * all subviews of the content layer, making clicks on the content layer collapse the backlayer.
 *
 *
 * This class implements [android.view.ViewGroup.OnHierarchyChangeListener] for two
 * purposes: to start tracking the status of newly-added subviews, and to stop tracking the status
 * of removed views. It is particularly important for the latter purpose because in order to track
 * the status of a view, the [ChildViewAccessibilityHelper] keeps a reference to the view and
 * could be leaking memory if the view in question is removed from its parent.
 */
internal class ChildViewAccessibilityHelper(private val parent: ViewGroup) : ViewGroup.OnHierarchyChangeListener {
    private val importantForAccessibilityMap = HashMap<View, Int>()
    private val focusableMap = HashMap<View, Boolean>()
    private var isChildFocusEnabled = true

    /**
     * Disable focus on child views.
     *
     *
     * It is possible to restore the original status by calling [.restoreChildFocus]. For
     * that purpose, this method also keeps a copy of the `importantForAccessibility` and `focusable` properties for each subview whose focus is disabled.
     *
     *
     * Caveat: This method does not change [CollapsedBackLayerContents] or its child views
     * because these views must be always visible and accessible to all users, vision-impaired or not.
     */
    fun disableChildFocus() {
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            if (shouldOverrideView(child)) {
                importantForAccessibilityMap[child] = ViewCompat.getImportantForAccessibility(child)
                focusableMap[child] = child.isFocusable
                ViewCompat.setImportantForAccessibility(
                    child, ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS
                )
                child.isFocusable = false
            }
        }
        isChildFocusEnabled = false
    }

    /**
     * Restores the values of focusable and importantForAccessibility to the values stored when [ ][.disableChildFocus] was called.
     *
     *
     * If [.disableChildFocus] was never called this method is essentially a no-op.
     */
    fun restoreChildFocus() {
        for ((key, value) in importantForAccessibilityMap) {
            ViewCompat.setImportantForAccessibility(key, value)
        }
        for ((key, value) in focusableMap) {
            key.isFocusable = value
        }
        importantForAccessibilityMap.clear()
        focusableMap.clear()
        isChildFocusEnabled = true
    }

    // We shouldn't change anything in the CollapsedBackLayerContents.
    private fun shouldOverrideView(view: View): Boolean {
        return view !is CollapsedBackLayerContents
    }

    override fun onChildViewAdded(parent: View, child: View) {
        if (!isChildFocusEnabled && shouldOverrideView(child)) {
            importantForAccessibilityMap[child] = ViewCompat.getImportantForAccessibility(child)
            focusableMap[child] = child.isFocusable
            ViewCompat.setImportantForAccessibility(
                child, ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS
            )
            child.isFocusable = false
        }
    }

    override fun onChildViewRemoved(parent: View, child: View) {
        // Remove mappings for removed views to avoid memory leaks
        importantForAccessibilityMap.remove(child)
        focusableMap.remove(child)
    }
}