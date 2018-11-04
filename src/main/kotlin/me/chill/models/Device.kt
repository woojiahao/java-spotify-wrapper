package me.chill.models

enum class DeviceType { Computer, Smartphone, Speaker; }

data class Device(
  val id: String?,
  val isActive: Boolean,
  val isPrivateSession: Boolean,
  val isRestricted: Boolean,
  val name: String,
  val type: DeviceType,
  val volumePercentage: Int?
)