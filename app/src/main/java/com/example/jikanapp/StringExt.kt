package com.example.jikanapp

fun String.capitalize(): String =
  split(" ").joinToString(" ") { str -> str.replaceFirstChar { ch -> ch.titlecase() } }