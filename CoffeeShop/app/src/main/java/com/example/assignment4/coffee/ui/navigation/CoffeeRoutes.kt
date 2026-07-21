package com.example.assignment4.coffee.ui.navigation

object CoffeeRoutes {
    const val HOME = "home"
    const val SHOP_LIST = "shop_list"
    const val SHOP_DETAIL = "shop_detail/{shopId}"
    const val TRANSLATION = "translation"
    const val SETTINGS = "settings"

    fun shopDetail(shopId: String) = "shop_detail/$shopId"
}
