/*
 * Ian Hale
 * OSU - halei@oregonstate.edu
 * CS 492
 */
package com.example.myapplication.ui

import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Category
import com.example.myapplication.model.CityCategoriesRepository
import com.example.myapplication.model.Recommendation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * UI state for the My City app. Single shared state across all screens.
 * Navigation is driven by selectedCategoryId and selectedRecommendationId.
 */
data class MyCityUiState(
    val categories: List<Category> = emptyList(),
    val selectedCategoryId: String? = null,
    val selectedCategoryName: String? = null,
    val recommendations: List<Recommendation> = emptyList(),
    val selectedRecommendationId: String? = null,
    val selectedRecommendation: Recommendation? = null,
    val validationMessage: String? = null
)

/**
 * Single shared ViewModel for the app. Holds all state and handles events (UDF).
 * UI sends events -> ViewModel updates state -> UI recomposes.
 */
class MyCityViewModel(
    private val repository: CityCategoriesRepository = CityCategoriesRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyCityUiState())
    val uiState: StateFlow<MyCityUiState> = _uiState.asStateFlow()

    init {
        _uiState.update { it.copy(categories = repository.getCategories()) }
    }

    /** Navigate to recommendations for this category. */
    fun selectCategory(categoryId: String) {
        val category = repository.getCategory(categoryId) ?: return
        _uiState.update {
            it.copy(
                selectedCategoryId = categoryId,
                selectedCategoryName = category.name,
                recommendations = repository.getRecommendations(categoryId),
                selectedRecommendationId = null,
                selectedRecommendation = null,
                validationMessage = null
            )
        }
    }

    /** Navigate back to category list. */
    fun backToCategories() {
        _uiState.update {
            it.copy(
                selectedCategoryId = null,
                selectedCategoryName = null,
                recommendations = emptyList(),
                selectedRecommendationId = null,
                selectedRecommendation = null,
                validationMessage = null
            )
        }
    }

    /** Navigate to recommendation detail. */
    fun selectRecommendation(recommendationId: String) {
        val rec = repository.getRecommendation(recommendationId) ?: return
        _uiState.update {
            it.copy(
                selectedRecommendationId = recommendationId,
                selectedRecommendation = rec,
                validationMessage = null
            )
        }
    }

    /** Navigate back to recommendations list (same category). */
    fun backToRecommendations() {
        val categoryId = _uiState.value.selectedCategoryId ?: return
        _uiState.update {
            it.copy(
                selectedRecommendationId = null,
                selectedRecommendation = null,
                recommendations = repository.getRecommendations(categoryId),
                validationMessage = null
            )
        }
    }

    /** Add a new recommendation in the current category. Enforces unique place names. */
    fun addRecommendation(placeName: String) {
        val categoryId = _uiState.value.selectedCategoryId ?: return
        val trimmed = placeName.trim()
        if (trimmed.isBlank()) {
            _uiState.update { it.copy(validationMessage = "Place name cannot be empty") }
            return
        }
        if (!repository.addRecommendation(categoryId, trimmed)) {
            _uiState.update { it.copy(validationMessage = "A recommendation with that name already exists") }
            return
        }
        _uiState.update {
            it.copy(
                recommendations = repository.getRecommendations(categoryId),
                validationMessage = null
            )
        }
    }

    /** Delete a recommendation and refresh list. */
    fun deleteRecommendation(recommendationId: String) {
        repository.deleteRecommendation(recommendationId)
        val categoryId = _uiState.value.selectedCategoryId ?: return
        val updated = repository.getRecommendations(categoryId)
        _uiState.update {
            it.copy(
                recommendations = updated,
                selectedRecommendationId = if (it.selectedRecommendationId == recommendationId) null else it.selectedRecommendationId,
                selectedRecommendation = if (it.selectedRecommendationId == recommendationId) null else it.selectedRecommendation,
                validationMessage = null
            )
        }
    }

    /** Save detail text for the currently selected recommendation. */
    fun saveRecommendationDetails(detailText: String) {
        val id = _uiState.value.selectedRecommendationId ?: return
        repository.updateRecommendationDetails(id, detailText)
        val updated = repository.getRecommendation(id) ?: return
        val categoryId = _uiState.value.selectedCategoryId ?: return
        _uiState.update {
            it.copy(
                selectedRecommendation = updated,
                recommendations = repository.getRecommendations(categoryId),
                validationMessage = null
            )
        }
    }

    fun clearValidationMessage() {
        _uiState.update { it.copy(validationMessage = null) }
    }
}
