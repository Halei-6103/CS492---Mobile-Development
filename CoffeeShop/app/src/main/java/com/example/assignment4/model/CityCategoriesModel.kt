package com.example.assignment4.model

/**
 * Model that holds city categories in memory.
 * Enforces business rule: category names must be unique.
 */
class CityCategoriesModel {

    private val _categories = mutableListOf<Category>()
    private var nextId: Long = 0

    /**
     * Returns the current list of categories.
     */
    fun getCategories(): List<Category> = _categories.toList()

    /**
     * Adds a category with the given name.
     * Enforces uniqueness: returns false if a category with this name already exists.
     */
    fun addCategory(name: String): Boolean {
        val trimmed = name.trim()
        if (_categories.any { it.name.equals(trimmed, ignoreCase = true) }) {
            return false
        }
        _categories.add(Category(id = nextId++, name = trimmed))
        return true
    }

    /**
     * Removes the category with the given id.
     */
    fun deleteCategory(id: Long) {
        _categories.removeAll { it.id == id }
    }
}
