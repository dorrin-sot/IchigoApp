package com.example.jikanapp.service

enum class DatabaseStatus {
  Updating, Updated;

  companion object {
    val Default = Updating
  }
}
