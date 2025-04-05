package dev.jyotiraditya.codex.presentation.components.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.jyotiraditya.codex.domain.model.FilterType
import dev.jyotiraditya.codex.presentation.theme.CodexTheme
import dev.jyotiraditya.codex.util.StringExtensions.capitalize

/**
 * Filter chips component for selecting filter types
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FilterChips(
    selectedFilter: FilterType,
    onFilterSelected: (FilterType) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterType.entries.forEach { filterType ->
            FilterChip(
                selected = selectedFilter == filterType,
                onClick = { onFilterSelected(filterType) },
                enabled = enabled,
                label = {
                    Text(
                        text = filterType.name.capitalize(),
                        style = MaterialTheme.typography.labelLarge
                    )
                },
                border = FilterChipDefaults.filterChipBorder(
                    enabled = enabled,
                    selected = selectedFilter == filterType,
                    borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                )
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun FilterChipsPreview() {
    CodexTheme {
        FilterChips(
            selectedFilter = FilterType.AUDIO,
            onFilterSelected = {},
            enabled = true
        )
    }
}