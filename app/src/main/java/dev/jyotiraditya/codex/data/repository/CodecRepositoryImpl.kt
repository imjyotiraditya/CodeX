package dev.jyotiraditya.codex.data.repository

import dev.jyotiraditya.codex.data.datasource.MediaCodecDataSource
import dev.jyotiraditya.codex.data.mapper.CodecMapper
import dev.jyotiraditya.codex.domain.model.CodecDetails
import dev.jyotiraditya.codex.domain.model.CodecInfo
import dev.jyotiraditya.codex.domain.model.CodecType
import dev.jyotiraditya.codex.domain.model.FilterType
import dev.jyotiraditya.codex.domain.repository.CodecRepository

/**
 * Implementation of CodecRepository interface
 */
class CodecRepositoryImpl(
    private val mediaCodecDataSource: MediaCodecDataSource,
    private val codecMapper: CodecMapper
) : CodecRepository {

    override fun getCodecList(): List<CodecInfo> {
        return mediaCodecDataSource.getAllCodecs().map { codecInfo ->
            codecMapper.mapToCodecInfo(codecInfo)
        }
    }

    override fun getCodecDetails(codecInfo: CodecInfo): CodecDetails {
        return codecMapper.mapToCodecDetails(codecInfo)
    }

    override fun filterCodecs(
        codecs: List<CodecInfo>,
        query: String,
        filterType: FilterType
    ): List<CodecInfo> {
        return codecs.filter { codec ->
            val typeMatches = when (filterType) {
                FilterType.ALL -> true
                FilterType.AUDIO -> codec.type == CodecType.AUDIO
                FilterType.VIDEO -> codec.type == CodecType.VIDEO
            }

            typeMatches && (
                    query.isEmpty() ||
                            codec.name.contains(query, ignoreCase = true) ||
                            codec.supportedTypes.any {
                                it.contains(query, ignoreCase = true)
                            }
                    )
        }
    }
}