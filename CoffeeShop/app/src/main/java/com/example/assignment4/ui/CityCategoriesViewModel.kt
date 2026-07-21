package com.example.assignment4.ui

import androidx.lifecycle.ViewModel
import com.example.assignment4.model.Category
import com.example.assignment4.model.CityCategoriesModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Result of adding a category (for validation feedback).
 */
sealed class AddCategoryResult {
    data object Success : AddCategoryResult()
    data object EmptyName : AddCategoryResult()
    data object Duplicate : AddCategoryResult()
}

/**
 * ViewModel for the city categories screen.
 * Exposes state via StateFlow and methods for the View to send events (UDF).
 */
class CityCategoriesViewModel : ViewModel() {

    private val model = CityCategoriesModel()

    private val _categories: MutableStateFlow<List<Category>> = MutableStateFlow(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    init {
        model.addCategory("Food")
        model.addCategory("Drinks")
        model.addCategory("Entertainment")
        _categories.value = model.getCategories()
    }

    /**
     * Adds a category. Validates: non-empty name and uniqueness.
     */
    fun addCategory(name: String): AddCategoryResult {
        if (name.isBlank()) return AddCategoryResult.EmptyName
        if (!model.addCategory(name)) return AddCategoryResult.Duplicate
        _categories.update { model.getCategories() }
        return AddCategoryResult.Success
    }

    /**
     * Deletes the category with the given id.
     */
    fun deleteCategory(id: Long) {
        model.deleteCategory(id)
        _categories.update { model.getCategories() }
    }
}
