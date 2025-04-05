package dev.jyotiraditya.codex.domain.usecase

import dev.jyotiraditya.codex.domain.model.CodecDetails
import dev.jyotiraditya.codex.domain.model.CodecInfo
import dev.jyotiraditya.codex.domain.repository.CodecRepository

/**
 * Use case for retrieving detailed information about a specific codec
 */
class GetCodecDetailsUseCase(private val codecRepository: CodecRepository) {
    operator fun invoke(codecInfo: CodecInfo): CodecDetails {
        return codecRepository.getCodecDetails(codecInfo)
    }
}