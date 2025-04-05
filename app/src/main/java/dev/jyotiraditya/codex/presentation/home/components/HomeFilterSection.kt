package dev.jyotiraditya.codex.presentation.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.jyotiraditya.codex.domain.model.FilterType
import dev.jyotiraditya.codex.presentation.components.common.SearchField
import dev.jyotiraditya.codex.presentation.components.filter.FilterChips

/**
 * Combined search and filter section for the home screen
 */
@Composable
fun HomeFilterSection(
    query: String,
    onQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit,
    selectedFilter: FilterType,
    onFilterSelected: (FilterType) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Column(
        modifier = modifier
    ) {
        SearchField(
            query = query,
            onQueryChange = onQueryChange,
            onClear = onClearQuery,
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        FilterChips(
            selectedFilter = selectedFilter,
            onFilterSelected = onFilterSelected,
            enabled = enabled,
            modifier = Modifier.fillMaxWidth()
        )
    }
}