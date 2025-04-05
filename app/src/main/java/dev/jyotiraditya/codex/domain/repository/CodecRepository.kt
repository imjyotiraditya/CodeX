package dev.jyotiraditya.codex.domain.repository

import dev.jyotiraditya.codex.domain.model.CodecDetails
import dev.jyotiraditya.codex.domain.model.CodecInfo
import dev.jyotiraditya.codex.domain.model.FilterType

/**
 * Repository interface for codec operations
 */
interface CodecRepository {
    /**
     * Get a list of all available media codecs
     * @return List of [CodecInfo] objects
     */
    fun getCodecList(): List<CodecInfo>

    /**
     * Get detailed information about a specific codec
     * @param codecInfo The codec to get details for
     * @return [CodecDetails] with detailed information about the codec
     */
    fun getCodecDetails(codecInfo: CodecInfo): CodecDetails

    /**
     * Filter codecs based on query and type
     * @param codecs The list of codecs to filter
     * @param query Search query string
     * @param filterType Type filter to apply
     * @return Filtered list of [CodecInfo] objects
     */
    fun filterCodecs(
        codecs: List<CodecInfo>,
        query: String,
        filterType: FilterType
    ): List<CodecInfo>
}