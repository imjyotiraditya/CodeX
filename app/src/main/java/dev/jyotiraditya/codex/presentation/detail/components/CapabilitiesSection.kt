package dev.jyotiraditya.codex.presentation.detail.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.jyotiraditya.codex.domain.model.CodecDetails

/**
 * Component to display codec capabilities for a specific MIME type
 */
@Composable
fun CapabilitiesSection(
    mimeType: String,
    capabilities: CodecDetails.TypeCapabilities,
    modifier: Modifier = Modifier
) {
    SectionHeader("Capabilities for $mimeType", modifier)

    ContentSection {
        if (capabilities.maxSupportedInstances != null) {
            DetailRow(
                "Max Instances",
                capabilities.maxSupportedInstances.toString()
            )
        }

        if (capabilities.colorFormats.isNotEmpty()) {
            SubsectionHeader("Color Formats")
            capabilities.colorFormats.forEach { format ->
                ListItem("$format (0x${format.toString(16).padStart(8, '0')})")
            }
        }

        if (capabilities.profileLevels.isNotEmpty()) {
            SubsectionHeader("Profile Levels")
            capabilities.profileLevels.forEach { pl ->
                ListItem("Profile: ${pl.profile}, Level: ${pl.level}")
            }
        }

        if (capabilities.supportedFeatures.isNotEmpty()) {
            SubsectionHeader("Supported Features")
            capabilities.supportedFeatures.forEach { feature ->
                ListItem(feature)
            }
        }

        if (capabilities.requiredFeatures.isNotEmpty()) {
            SubsectionHeader("Required Features")
            capabilities.requiredFeatures.forEach { feature ->
                ListItem(feature)
            }
        }
    }

    // Audio capabilities
    if (capabilities.audioCapabilities != null) {
        SectionHeader("Audio Capabilities")
        ContentSection {
            with(capabilities.audioCapabilities) {
                if (bitrateRange != null) {
                    DetailRow("Bitrate Range", bitrateRange)
                }

                if (maxInputChannelCount != null) {
                    DetailRow("Max Input Channels", maxInputChannelCount.toString())
                }

                if (minInputChannelCount != null) {
                    DetailRow("Min Input Channels", minInputChannelCount.toString())
                }

                if (supportedSampleRates.isNotEmpty()) {
                    SubsectionHeader("Supported Sample Rates")
                    ListItem(supportedSampleRates.joinToString(", "))
                }

                if (supportedSampleRateRanges.isNotEmpty()) {
                    SubsectionHeader("Sample Rate Ranges")
                    supportedSampleRateRanges.forEach { range ->
                        ListItem(range)
                    }
                }
            }
        }
    }

    // Video capabilities
    if (capabilities.videoCapabilities != null) {
        SectionHeader("Video Capabilities")
        ContentSection {
            with(capabilities.videoCapabilities) {
                if (bitrateRange != null) {
                    DetailRow("Bitrate Range", bitrateRange)
                }

                if (widthAlignment != null) {
                    DetailRow("Width Alignment", widthAlignment.toString())
                }

                if (heightAlignment != null) {
                    DetailRow("Height Alignment", heightAlignment.toString())
                }

                if (supportedWidths != null) {
                    DetailRow("Supported Widths", supportedWidths)
                }

                if (supportedHeights != null) {
                    DetailRow("Supported Heights", supportedHeights)
                }

                if (supportedFrameRates != null) {
                    DetailRow("Supported Frame Rates", supportedFrameRates)
                }

                if (performancePoints.isNotEmpty()) {
                    SubsectionHeader("Performance Points")
                    performancePoints.forEach { point ->
                        ListItem(point)
                    }
                }
            }
        }
    }

    // Encoder capabilities
    if (capabilities.encoderCapabilities != null) {
        SectionHeader("Encoder Capabilities")
        ContentSection {
            with(capabilities.encoderCapabilities) {
                if (complexityRange != null) {
                    DetailRow("Complexity Range", complexityRange)
                }

                if (qualityRange != null) {
                    DetailRow("Quality Range", qualityRange)
                }

                if (supportedBitrateModes.isNotEmpty()) {
                    SubsectionHeader("Supported Bitrate Modes")
                    supportedBitrateModes.forEach { mode ->
                        ListItem(mode)
                    }
                }
            }
        }
    }
}