package com.example.assignment4.coffee.data

/**
 * Provides the list of coffee shops in Corvallis, Oregon.
 */
object CoffeeRepository {

    private val shops = listOf(
        CoffeeShop(
            id = "interzone",
            name = "Interzone Coffee",
            address = "1563 NW Monroe Ave",
            knownFor = "Cozy study space, local art, sandwiches",
            popularDrinks = "Lattes, mochas",
            atmosphere = "Student-friendly, artsy"
        ),
        CoffeeShop(
            id = "tried-and-true",
            name = "Tried & True Coffee Co.",
            address = "210 SW 2nd St",
            knownFor = "Specialty espresso, house-roasted beans",
            popularDrinks = "Pour-over, cappuccino",
            atmosphere = "Modern, minimalist"
        ),
        CoffeeShop(
            id = "coffee-culture",
            name = "Coffee Culture",
            address = "124 SW 3rd St",
            knownFor = "Large seating area, community events",
            popularDrinks = "Cold brew, chai latte",
            atmosphere = "Downtown hangout"
        ),
        CoffeeShop(
            id = "greenhouse",
            name = "Greenhouse Coffee + Plants",
            address = "219 NW 2nd St",
            knownFor = "Coffee + plant shop combo",
            popularDrinks = "Herbal teas, seasonal lattes",
            atmosphere = "Bright, botanical"
        ),
        CoffeeShop(
            id = "human-bean",
            name = "The Human Bean",
            address = "2230 NW 9th St",
            knownFor = "Drive-thru convenience",
            popularDrinks = "Granitas, specialty iced drinks",
            atmosphere = "Quick stop"
        )
    )

    fun getAllShops(): List<CoffeeShop> = shops

    fun getShopById(id: String): CoffeeShop? = shops.find { it.id == id }
}
