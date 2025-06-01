package com.example.ichigoapp

operator fun <T> List<T>.times(number: Int): List<T> =
  List(size * number) { idx -> get(idx % size) }