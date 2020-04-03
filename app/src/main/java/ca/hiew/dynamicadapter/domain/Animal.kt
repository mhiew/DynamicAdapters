package ca.hiew.dynamicadapter.domain

sealed class Animal {
    data class Cat(val id: Int) : Animal()
    data class Dog(val id: Int) : Animal()
}
