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
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.AttrRes

/**
 * Contains the collapsed version of the backlayer contents, its initial measurements are what will
 * be considered the collapsed measurements of the BackLayer.
 *
 * <p>This must be used only as a direct child to {@link BackLayerLayout}. BackLayerLayout uses this
 * to determine its minimum size, which describes the area of the back layer that is always exposed.
 *
 * <p>For more information on how to use it look at the {@link BackLayerLayout} javadoc.
 */
class CollapsedBackLayerContents @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr)