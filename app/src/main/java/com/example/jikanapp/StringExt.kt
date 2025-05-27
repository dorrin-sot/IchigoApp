package com.example.jikanapp

import java.util.Locale

fun String.capitalize(): String =
  split(" ").joinToString(" ") {
    replaceFirstChar {
      if (it.isLowerCase())
        it.titlecase(Locale.ROOT)
      else it.toString()
    }
  }