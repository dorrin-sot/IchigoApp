package com.example.ichigoapp.service

enum class DatabaseStatus {
  Updating, Updated;

  companion object {
    val Default = Updating
  }
}
