package dev.jyotiraditya.codex.domain.usecase

import dev.jyotiraditya.codex.domain.model.CodecInfo
import dev.jyotiraditya.codex.domain.repository.CodecRepository

/**
 * Use case for retrieving the list of all available codecs
 */
class GetCodecListUseCase(private val codecRepository: CodecRepository) {
    operator fun invoke(): List<CodecInfo> {
        return codecRepository.getCodecList()
    }
}