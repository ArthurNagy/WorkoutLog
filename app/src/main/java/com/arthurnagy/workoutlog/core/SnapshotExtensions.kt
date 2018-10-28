package com.arthurnagy.workoutlog.core

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

inline fun <reified T> DocumentSnapshot.serialize(): T = toObject(T::class.java) ?: throw Exception("Couldn't serialize $this to ${T::class.java}")

inline fun <reified T> QuerySnapshot.serialize(): List<T> = toObjects(T::class.java)