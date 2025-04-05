package dev.jyotiraditya.codex.domain.model

/**
 * Domain model for detailed codec information
 */
data class CodecDetails(
    val basicInfo: BasicInfo,
    val supportedTypes: List<String>,
    val capabilitiesMap: Map<String, TypeCapabilities>
) {
    data class BasicInfo(
        val name: String,
        val canonicalName: String? = null,
        val type: String,
        val isEncoder: Boolean,
        val isSoftwareOnly: Boolean? = null,
        val isHardwareAccelerated: Boolean? = null,
        val isVendor: Boolean? = null,
        val isAlias: Boolean? = null
    )

    data class TypeCapabilities(
        val colorFormats: List<Int> = emptyList(),
        val maxSupportedInstances: Int? = null,
        val profileLevels: List<ProfileLevel> = emptyList(),
        val supportedFeatures: List<String> = emptyList(),
        val requiredFeatures: List<String> = emptyList(),
        val audioCapabilities: AudioCapabilities? = null,
        val videoCapabilities: VideoCapabilities? = null,
        val encoderCapabilities: EncoderCapabilities? = null
    )

    data class ProfileLevel(
        val profile: Int,
        val level: Int
    )

    data class AudioCapabilities(
        val bitrateRange: String? = null,
        val maxInputChannelCount: Int? = null,
        val minInputChannelCount: Int? = null,
        val supportedSampleRates: List<Int> = emptyList(),
        val supportedSampleRateRanges: List<String> = emptyList()
    )

    data class VideoCapabilities(
        val bitrateRange: String? = null,
        val widthAlignment: Int? = null,
        val heightAlignment: Int? = null,
        val supportedWidths: String? = null,
        val supportedHeights: String? = null,
        val supportedFrameRates: String? = null,
        val performancePoints: List<String> = emptyList()
    )

    data class EncoderCapabilities(
        val complexityRange: String? = null,
        val qualityRange: String? = null,
        val supportedBitrateModes: List<String> = emptyList()
    )
}