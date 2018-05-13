package com.katzoft.archcomponents.sample.main.view

data class MainViewState(val switchStates: List<SwitchViewState>)
data class SwitchViewState(val viewTag: String, val isChecked: Boolean, val isEnabled: Boolean)