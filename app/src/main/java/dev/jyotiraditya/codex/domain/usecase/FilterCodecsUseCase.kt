package dev.jyotiraditya.codex.domain.usecase

import dev.jyotiraditya.codex.domain.model.CodecInfo
import dev.jyotiraditya.codex.domain.model.FilterType
import dev.jyotiraditya.codex.domain.repository.CodecRepository

/**
 * Use case for filtering codecs based on query and type
 */
class FilterCodecsUseCase(private val codecRepository: CodecRepository) {
    operator fun invoke(
        codecs: List<CodecInfo>,
        query: String,
        filterType: FilterType
    ): List<CodecInfo> {
        return codecRepository.filterCodecs(codecs, query, filterType)
    }
}