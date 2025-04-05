package dev.jyotiraditya.codex.di

import dev.jyotiraditya.codex.data.datasource.MediaCodecDataSource
import dev.jyotiraditya.codex.data.mapper.CodecMapper
import dev.jyotiraditya.codex.data.repository.CodecRepositoryImpl
import dev.jyotiraditya.codex.domain.repository.CodecRepository
import dev.jyotiraditya.codex.domain.usecase.FilterCodecsUseCase
import dev.jyotiraditya.codex.domain.usecase.GetCodecDetailsUseCase
import dev.jyotiraditya.codex.domain.usecase.GetCodecListUseCase

/**
 * Dependency Injection module for the app
 */
object AppModule {

    // Data sources
    private val mediaCodecDataSource by lazy { MediaCodecDataSource() }

    // Mappers
    private val codecMapper by lazy { CodecMapper(mediaCodecDataSource) }

    // Repositories
    private val codecRepository: CodecRepository by lazy {
        CodecRepositoryImpl(mediaCodecDataSource, codecMapper)
    }

    // Use cases
    val getCodecListUseCase by lazy { GetCodecListUseCase(codecRepository) }
    val getCodecDetailsUseCase by lazy { GetCodecDetailsUseCase(codecRepository) }
    val filterCodecsUseCase by lazy { FilterCodecsUseCase(codecRepository) }
}