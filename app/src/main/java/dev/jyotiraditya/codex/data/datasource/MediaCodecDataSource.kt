package dev.jyotiraditya.codex.data.datasource

import android.media.MediaCodecInfo
import android.media.MediaCodecList
import android.util.Log

/**
 * Data source for accessing Android's MediaCodec information
 */
class MediaCodecDataSource {
    private val logTag = "MediaCodecDataSource"

    /**
     * Get all available codecs from the Android system
     */
    fun getAllCodecs(): Array<MediaCodecInfo> {
        return MediaCodecList(MediaCodecList.ALL_CODECS).codecInfos
    }

    /**
     * Safely get a MediaCodecInfo property that might throw an exception
     */
    fun <T> safeCodecInfoAccess(block: () -> T): T? {
        return try {
            block()
        } catch (e: Exception) {
            Log.e(logTag, "Error accessing codec info: ${e.message}")
            null
        }
    }
}
