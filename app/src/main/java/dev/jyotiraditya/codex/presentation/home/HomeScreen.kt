package dev.jyotiraditya.codex.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import dev.jyotiraditya.codex.di.AppModule
import dev.jyotiraditya.codex.domain.model.CodecInfo
import dev.jyotiraditya.codex.presentation.components.codec.CodecList
import dev.jyotiraditya.codex.presentation.components.common.AppBar
import dev.jyotiraditya.codex.presentation.home.components.HomeFilterSection

/**
 * Home screen that displays a list of available codecs with search and filtering
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onCodecClick: (CodecInfo, Int) -> Unit,
    viewModel: HomeViewModel = remember {
        HomeViewModel(AppModule.getCodecListUseCase, AppModule.filterCodecsUseCase)
    }
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val state by viewModel.state.collectAsState()

    val handleCodecClick: (CodecInfo) -> Unit = { codec ->
        val codecIndex = state.codecs.indexOfFirst { it.name == codec.name }
        if (codecIndex >= 0) {
            onCodecClick(codec, codecIndex)
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppBar(
                scrollBehavior = scrollBehavior
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            HomeFilterSection(
                query = state.searchQuery,
                onQueryChange = viewModel::updateSearchQuery,
                onClearQuery = viewModel::clearSearchQuery,
                selectedFilter = state.selectedFilterType,
                onFilterSelected = viewModel::updateFilterType,
                enabled = !state.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        top = 16.dp,
                        end = 16.dp,
                        bottom = 8.dp
                    )
            )

            HorizontalDivider()

            CodecList(
                codecs = state.filteredCodecs,
                onCodecClick = handleCodecClick,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}