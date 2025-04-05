package dev.jyotiraditya.codex.domain.model

import android.media.MediaCodecInfo

/**
 * Domain model representing basic codec information
 */
data class CodecInfo(
    val name: String,
    val isEncoder: Boolean,
    val type: CodecType,
    val supportedTypes: List<String>,
    val isHardwareAccelerated: Boolean? = null,
    val codecInfo: MediaCodecInfo? = null
)
