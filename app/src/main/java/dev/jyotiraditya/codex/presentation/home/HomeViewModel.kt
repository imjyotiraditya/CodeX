package dev.jyotiraditya.codex.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jyotiraditya.codex.domain.model.CodecInfo
import dev.jyotiraditya.codex.domain.model.FilterType
import dev.jyotiraditya.codex.domain.usecase.FilterCodecsUseCase
import dev.jyotiraditya.codex.domain.usecase.GetCodecListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * State holder for the Home screen
 */
data class HomeScreenState(
    val codecs: List<CodecInfo> = emptyList(),
    val filteredCodecs: List<CodecInfo> = emptyList(),
    val searchQuery: String = "",
    val selectedFilterType: FilterType = FilterType.ALL,
    val isLoading: Boolean = false,
    val error: String? = null
)

/**
 * ViewModel for the Home screen
 */
class HomeViewModel(
    private val getCodecListUseCase: GetCodecListUseCase,
    private val filterCodecsUseCase: FilterCodecsUseCase
) : ViewModel() {

    private val _codecs = MutableStateFlow<List<CodecInfo>>(emptyList())
    private val _searchQuery = MutableStateFlow("")
    private val _selectedFilterType = MutableStateFlow(FilterType.ALL)
    private val _isLoading = MutableStateFlow(false)
    private val _error = MutableStateFlow<String?>(null)

    /**
     * The current UI state
     */
    val state: StateFlow<HomeScreenState> = combine(
        _codecs,
        _searchQuery,
        _selectedFilterType,
        _isLoading,
        _error
    ) { codecs, query, filterType, isLoading, error ->
        val filteredCodecs = filterCodecsUseCase(codecs, query, filterType)
        HomeScreenState(
            codecs = codecs,
            filteredCodecs = filteredCodecs,
            searchQuery = query,
            selectedFilterType = filterType,
            isLoading = isLoading,
            error = error
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeScreenState(isLoading = true)
    )

    init {
        loadCodecs()
    }

    /**
     * Load all available codecs
     */
    private fun loadCodecs() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _codecs.value = getCodecListUseCase()
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Update the search query
     */
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    /**
     * Clear the search query
     */
    fun clearSearchQuery() {
        _searchQuery.value = ""
    }

    /**
     * Update the selected filter type
     */
    fun updateFilterType(filterType: FilterType) {
        _selectedFilterType.value = filterType
    }
}