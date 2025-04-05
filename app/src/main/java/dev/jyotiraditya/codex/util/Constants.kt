package dev.jyotiraditya.codex.util

/**
 * Application-wide constants
 */
object Constants {

    /**
     * Feature flags for codec capabilities
     */
    object CodecFeatures {
        val SUPPORTED_FEATURES = listOf(
            "adaptive-playback",
            "secure-playback",
            "tunneled-playback",
            "detached-surface",
            "dynamic-color-aspects",
            "dynamic-timestamp",
            "encoding-statistics",
            "frame-parsing",
            "hdr-editing",
            "hlg-editing",
            "intra-refresh",
            "low-latency",
            "multiple-frames",
            "partial-frame",
            "qp-bounds",
            "region-of-interest"
        )

        /**
         * Bitrate mode names mapping
         */
        val BITRATE_MODES = mapOf(
            0 to "CQ (Constant Quality)",
            1 to "VBR (Variable Bitrate)",
            2 to "CBR (Constant Bitrate)",
            3 to "CBR_FD (Constant Bitrate with Frame Drops)"
        )
    }
}