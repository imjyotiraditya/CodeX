package dev.jyotiraditya.codex.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.jyotiraditya.codex.R
import dev.jyotiraditya.codex.di.AppModule
import dev.jyotiraditya.codex.domain.model.CodecInfo
import dev.jyotiraditya.codex.presentation.detail.components.CapabilitiesSection
import dev.jyotiraditya.codex.presentation.detail.components.ContentSection
import dev.jyotiraditya.codex.presentation.detail.components.DetailRow
import dev.jyotiraditya.codex.presentation.detail.components.SectionHeader
import dev.jyotiraditya.codex.util.StringExtensions.capitalize
import java.util.Locale

/**
 * Screen that displays detailed information about a specific codec
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodecDetailScreen(
    codecInfo: CodecInfo,
    onBackClick: () -> Unit,
    viewModel: CodecDetailViewModel = remember {
        CodecDetailViewModel(AppModule.getCodecDetailsUseCase)
    }
) {
    val scrollState = rememberScrollState()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(codecInfo) {
        viewModel.loadCodecDetails(codecInfo)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(codecInfo.name) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.cd_back)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { innerPadding ->
        if (state.isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(top = 32.dp)
                )
            }
        } else if (state.error != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Error: ${state.error}",
                    color = MaterialTheme.colorScheme.error
                )
            }
        } else {
            val codecDetails = state.codecDetails
            if (codecDetails != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(scrollState)
                ) {
                    // Basic Information
                    SectionHeader("Basic Information")
                    ContentSection {
                        DetailRow("Name", codecDetails.basicInfo.name)

                        if (codecDetails.basicInfo.canonicalName != null) {
                            DetailRow("Canonical Name", codecDetails.basicInfo.canonicalName)
                        }

                        DetailRow(
                            "Type",
                            codecDetails.basicInfo.type.capitalize(Locale.getDefault())
                        )
                        DetailRow("Encoder", if (codecDetails.basicInfo.isEncoder) "Yes" else "No")

                        if (codecDetails.basicInfo.isSoftwareOnly != null) {
                            DetailRow(
                                "Software Only",
                                if (codecDetails.basicInfo.isSoftwareOnly) "Yes" else "No"
                            )
                        }

                        if (codecDetails.basicInfo.isHardwareAccelerated != null) {
                            DetailRow(
                                "Hardware Accelerated",
                                if (codecDetails.basicInfo.isHardwareAccelerated) "Yes" else "No"
                            )
                        }

                        if (codecDetails.basicInfo.isVendor != null) {
                            DetailRow(
                                "Vendor",
                                if (codecDetails.basicInfo.isVendor) "Android" else "OEM"
                            )
                        }

                        if (codecDetails.basicInfo.isAlias != null) {
                            DetailRow(
                                "Alias",
                                if (codecDetails.basicInfo.isAlias) "Yes" else "No"
                            )
                        }
                    }

                    // Supported Types
                    SectionHeader("Supported Types")
                    ContentSection {
                        codecDetails.supportedTypes.forEachIndexed { index, type ->
                            DetailRow("MIME Type", type)
                        }
                    }

                    // Display capabilities for each supported type
                    codecDetails.supportedTypes.forEach { mimeType ->
                        val capabilities = codecDetails.capabilitiesMap[mimeType]
                        if (capabilities != null) {
                            CapabilitiesSection(
                                mimeType = mimeType,
                                capabilities = capabilities
                            )
                        }
                    }

                    Spacer(modifier = Modifier.padding(bottom = 24.dp))
                }
            }
        }
    }
}