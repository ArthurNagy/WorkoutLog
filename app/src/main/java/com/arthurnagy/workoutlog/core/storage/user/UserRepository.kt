/*
 * Copyright (c) 2018 Halcyon Mobile
 * http://www.halcyonmobile.com
 * All rights reserved.
 */

package com.arthurnagy.workoutlog.core.storage.user

import com.arthurnagy.workoutlog.core.injection.AppScope
import javax.inject.Inject

@AppScope
class UserRepository @Inject constructor(userRemoteService: UserRemoteService)