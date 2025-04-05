package dev.jyotiraditya.codex.presentation.components.codec

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.jyotiraditya.codex.R
import dev.jyotiraditya.codex.domain.model.CodecInfo
import dev.jyotiraditya.codex.presentation.components.common.EmptyState
import dev.jyotiraditya.codex.presentation.theme.CodexTheme

/**
 * Component that displays a list of codecs or an empty state if no codecs are available
 */
@Composable
fun CodecList(
    codecs: List<CodecInfo>,
    onCodecClick: (CodecInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    if (codecs.isEmpty()) {
        EmptyState(
            title = stringResource(R.string.no_codecs_found),
            description = stringResource(R.string.no_codecs_found_description),
            icon = Icons.Outlined.Code,
            modifier = modifier
        )
    } else {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(codecs.size) { index ->
                val codec = codecs[index]
                CodecListItem(
                    codec = codec,
                    onClick = { onCodecClick(codec) }
                )
                if (index < codecs.size - 1) {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CodecEmptyListPreview() {
    CodexTheme {
        CodecList(
            codecs = emptyList(),
            onCodecClick = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}