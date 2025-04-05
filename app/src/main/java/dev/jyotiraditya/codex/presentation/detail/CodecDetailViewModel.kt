package dev.jyotiraditya.codex.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jyotiraditya.codex.domain.model.CodecDetails
import dev.jyotiraditya.codex.domain.model.CodecInfo
import dev.jyotiraditya.codex.domain.usecase.GetCodecDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * State holder for the Codec Detail screen
 */
data class CodecDetailState(
    val codecInfo: CodecInfo? = null,
    val codecDetails: CodecDetails? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

/**
 * ViewModel for the Codec Detail screen
 */
class CodecDetailViewModel(
    private val getCodecDetailsUseCase: GetCodecDetailsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CodecDetailState(isLoading = true))
    val state: StateFlow<CodecDetailState> = _state.asStateFlow()

    /**
     * Load details for a specific codec
     */
    fun loadCodecDetails(codecInfo: CodecInfo) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, codecInfo = codecInfo) }
            try {
                val details = getCodecDetailsUseCase(codecInfo)
                _state.update {
                    it.copy(
                        codecDetails = details,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error"
                    )
                }
            }
        }
    }
}