package net.idt.testtask.grid.ui.navigation

import kotlinx.serialization.Serializable

interface NavRoute

@Serializable
object GridBuilderRoute: NavRoute

@Serializable
data class GridRoute(
    val colNumber: Int,
    val rowNumber: Int
): NavRoute