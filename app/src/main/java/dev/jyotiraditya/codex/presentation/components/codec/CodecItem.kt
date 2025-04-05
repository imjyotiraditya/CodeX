package dev.jyotiraditya.codex.presentation.components.codec

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import dev.jyotiraditya.codex.domain.model.CodecInfo
import dev.jyotiraditya.codex.domain.model.CodecType
import dev.jyotiraditya.codex.presentation.theme.CodexTheme
import java.util.Locale

/**
 * List item component for displaying a codec in a list
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodecListItem(
    codec: CodecInfo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val codecTypeText = codec.type.name.uppercase(Locale.getDefault())

    ListItem(
        modifier = modifier.clickable { onClick() },
        overlineContent = {
            Text(
                text = codecTypeText,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        headlineContent = {
            Text(
                text = codec.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.fillMaxWidth()
            )
        },
        supportingContent = {
            Text(
                text = codec.supportedTypes.joinToString(", "),
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
        },
        trailingContent = {
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = if (codec.isEncoder) "E" else "D",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                if (codec.isHardwareAccelerated == true) {
                    Text(
                        text = "HW",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun CodecItemEncoderPreview() {
    CodexTheme {
        CodecListItem(
            codec = CodecInfo(
                name = "OMX.google.aac.encoder",
                isEncoder = true,
                type = CodecType.AUDIO,
                supportedTypes = listOf("audio/mp4a-latm"),
                isHardwareAccelerated = false
            ),
            onClick = {}
        )
    }
}