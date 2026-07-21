package com.example.myapplication.model

import java.util.UUID

/**
 * Model layer: holds categories in memory and exposes an API for the ViewModel.
 * Enforces the business rule that category names must be unique.
 */
class CityCategoriesRepository {

    private val _categories = mutableListOf<Category>().apply {
        add(Category(id = UUID.randomUUID().toString(), name = "Food"))
        add(Category(id = UUID.randomUUID().toString(), name = "Drinks"))
        add(Category(id = UUID.randomUUID().toString(), name = "Entertainment"))
        add(Category(id = UUID.randomUUID().toString(), name = "Shopping"))
        add(Category(id = UUID.randomUUID().toString(), name = "Parks"))
        add(Category(id = UUID.randomUUID().toString(), name = "Arts"))
    }

    /**
     * Returns the current list of categories.
     */
    fun getCategories(): List<Category> = _categories.toList()

    /**
     * Adds a category with the given name. Returns true if added, false if the name
     * is already present (enforces uniqueness).
     */
    fun addCategory(name: String): Boolean {
        val trimmed = name.trim()
        if (trimmed.isBlank()) return false
        if (_categories.any { it.name.equals(trimmed, ignoreCase = true) }) return false
        _categories.add(Category(id = UUID.randomUUID().toString(), name = trimmed))
        return true
    }

    /**
     * Removes the category with the given id. Returns true if removed.
     */
    fun deleteCategory(id: String): Boolean =
        _categories.removeIf { it.id == id }
}
