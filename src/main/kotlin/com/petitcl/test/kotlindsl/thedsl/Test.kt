package com.petitcl.test.kotlindsl.thedsl

data class Village(val houses: List<House>)

data class House(val people: List<Person>, val items: List<Item>)

data class Person(val name: String, val age: Int)

interface Item

data class Gold(val amount: Int) : Item

interface Weapon : Item

data class Sword(val strength: Double) : Weapon

interface Armor : Item

data class Shield(val defense: Double) : Armor

@DslMarker
annotation class AdvancedDSL2

class HouseBuilder {

    internal val people = mutableListOf<Person>()
    internal val items = mutableListOf<Item>()

    fun build(): House {
        return House(people, items)
    }

    @AdvancedDSL2
    infix fun String.age(age: Int) {
        people.add(Person(this, age))
    }

    @AdvancedDSL2
    val Int.gold: Unit
        get() {
            items += Gold(this)
            return Unit
        }

}

class VillageBuilder {

    private val houses = mutableListOf<House>()

    @AdvancedDSL2
    operator fun House.unaryPlus() {
        houses += this
    }

    @AdvancedDSL2
    fun house(setup: HouseBuilder.() -> Unit = {}) {
        val houseBuilder = HouseBuilder()
        houseBuilder.setup()
        houses += houseBuilder.build()
    }

    fun build(): Village {
        return Village(houses)
    }

}

class ShieldContinuation(val house: HouseBuilder)

@AdvancedDSL2
object defense

@AdvancedDSL2
val HouseBuilder.shield: ShieldContinuation
    get() {
        this.items += Shield(0.0)
        return ShieldContinuation(this)
    }

@AdvancedDSL2
infix fun ShieldContinuation.with(d: defense): ShieldBuilder {
    house.items.removeAt(house.items.lastIndex)
    return ShieldBuilder(house)
}

class ShieldBuilder(val house: HouseBuilder)

@AdvancedDSL2
infix fun ShieldBuilder.value(defense: Double) {
    house.items += Shield(defense)
}

class SwordContinuation(val house: HouseBuilder)

@AdvancedDSL2
object strength

@AdvancedDSL2
val HouseBuilder.sword: SwordContinuation
    get() {
        this.items += Sword(0.0)
        return SwordContinuation(this)
    }

@AdvancedDSL2
infix fun SwordContinuation.with(d: strength): SwordBuilder {
    house.items.removeAt(house.items.lastIndex)
    return SwordBuilder(house)
}

class SwordBuilder(val house: HouseBuilder)

@AdvancedDSL2
infix fun SwordBuilder.value(strength: Double) {
    house.items += Sword(strength)
}

@AdvancedDSL2
infix fun SwordBuilder.level(strength: Double) = value(strength)

@AdvancedDSL2
fun village(setup: VillageBuilder.() -> Unit): Village {
    val villageBuilder = VillageBuilder()
    villageBuilder.setup()
    return villageBuilder.build()
}

fun main(args: Array<String>) {

    val v = village {
        house {
            "Alice" age 31
            "Bob" age 45
            500.gold
        }
        house {
            sword with strength value 24.2
            sword with strength level 16.7
            shield with defense value 15.3
        }
        house()
        house {
            "Charles" age 52
            2500.gold
            sword
            shield
        }
    }

    print(v)

}