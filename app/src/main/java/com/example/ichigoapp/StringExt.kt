package com.example.ichigoapp

fun String.capitalize(): String =
  split(" ").joinToString(" ") { str -> str.replaceFirstChar { ch -> ch.titlecase() } }