package com.example.myapplication.ui

import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Category
import com.example.myapplication.model.CityCategoriesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * UI state for the city categories screen.
 */
data class CityCategoriesUiState(
    val categories: List<Category> = emptyList(),
    val validationMessage: String? = null
)

/**
 * ViewModel: bridges Model and View. Exposes state via StateFlow and methods to modify it.
 * Implements simple validation (categories must be unique) and delegates to the model.
 */
class CityCategoriesViewModel(
    private val repository: CityCategoriesRepository = CityCategoriesRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(CityCategoriesUiState())
    val uiState: StateFlow<CityCategoriesUiState> = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    /**
     * Loads categories from the model into state (for initial display and after changes).
     */
    fun loadCategories() {
        _uiState.update { it.copy(categories = repository.getCategories(), validationMessage = null) }
    }

    /**
     * Adds a category. Validates uniqueness (and non-empty); updates state or sets validation message.
     */
    fun addCategory(name: String) {
        val trimmed = name.trim()
        if (trimmed.isBlank()) {
            _uiState.update { it.copy(validationMessage = "Category name cannot be empty") }
            return
        }
        if (repository.getCategories().any { it.name.equals(trimmed, ignoreCase = true) }) {
            _uiState.update { it.copy(validationMessage = "Category already exists") }
            return
        }
        repository.addCategory(trimmed)
        _uiState.update {
            it.copy(
                categories = repository.getCategories(),
                validationMessage = null
            )
        }
    }

    /**
     * Deletes the category with the given id and refreshes state.
     */
    fun deleteCategory(id: String) {
        repository.deleteCategory(id)
        _uiState.update {
            it.copy(
                categories = repository.getCategories(),
                validationMessage = null
            )
        }
    }

    /**
     * Clears any validation message (e.g. when user starts typing again).
     */
    fun clearValidationMessage() {
        _uiState.update { it.copy(validationMessage = null) }
    }
}
