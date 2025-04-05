package dev.jyotiraditya.codex.data.mapper

import android.media.MediaCodecInfo
import dev.jyotiraditya.codex.data.datasource.MediaCodecDataSource
import dev.jyotiraditya.codex.domain.model.CodecDetails
import dev.jyotiraditya.codex.domain.model.CodecInfo
import dev.jyotiraditya.codex.domain.model.CodecType
import dev.jyotiraditya.codex.util.Constants.CodecFeatures.BITRATE_MODES
import dev.jyotiraditya.codex.util.Constants.CodecFeatures.SUPPORTED_FEATURES

/**
 * Mapper class to convert between data types and domain models
 */
class CodecMapper(private val mediaCodecDataSource: MediaCodecDataSource) {

    /**
     * Maps MediaCodecInfo to domain CodecInfo
     */
    fun mapToCodecInfo(codecInfo: MediaCodecInfo): CodecInfo {
        val supportedTypes = codecInfo.supportedTypes.toList()
        val type = when {
            supportedTypes.any { it.startsWith("audio/") } -> CodecType.AUDIO
            supportedTypes.any { it.startsWith("video/") } -> CodecType.VIDEO
            else -> CodecType.OTHER
        }

        val isHardwareAccelerated = mediaCodecDataSource.safeCodecInfoAccess {
            codecInfo.isHardwareAccelerated
        }

        return CodecInfo(
            name = codecInfo.name,
            isEncoder = codecInfo.isEncoder,
            type = type,
            supportedTypes = supportedTypes,
            isHardwareAccelerated = isHardwareAccelerated,
            codecInfo = codecInfo
        )
    }

    /**
     * Maps CodecInfo to CodecDetails
     */
    fun mapToCodecDetails(codecItem: CodecInfo): CodecDetails {
        val mediaCodecInfo = codecItem.codecInfo

        val basicInfo = if (mediaCodecInfo != null) {
            CodecDetails.BasicInfo(
                name = mediaCodecInfo.name,
                canonicalName = mediaCodecDataSource.safeCodecInfoAccess {
                    mediaCodecInfo.canonicalName
                },
                type = codecItem.type.name,
                isEncoder = mediaCodecInfo.isEncoder,
                isSoftwareOnly = mediaCodecDataSource.safeCodecInfoAccess {
                    mediaCodecInfo.isSoftwareOnly
                },
                isHardwareAccelerated = mediaCodecDataSource.safeCodecInfoAccess {
                    mediaCodecInfo.isHardwareAccelerated
                },
                isVendor = mediaCodecDataSource.safeCodecInfoAccess {
                    mediaCodecInfo.isVendor
                },
                isAlias = mediaCodecDataSource.safeCodecInfoAccess {
                    mediaCodecInfo.isAlias
                }
            )
        } else {
            CodecDetails.BasicInfo(
                name = codecItem.name,
                type = codecItem.type.name,
                isEncoder = codecItem.isEncoder
            )
        }

        val supportedTypes = mediaCodecInfo?.supportedTypes?.toList() ?: codecItem.supportedTypes
        val capabilitiesMap = mutableMapOf<String, CodecDetails.TypeCapabilities>()

        if (mediaCodecInfo != null) {
            for (mimeType in supportedTypes) {
                try {
                    val caps = mediaCodecInfo.getCapabilitiesForType(mimeType)

                    val colorFormats = caps.colorFormats.toList()
                    val maxSupportedInstances = mediaCodecDataSource.safeCodecInfoAccess {
                        caps.maxSupportedInstances
                    }

                    val profileLevels = caps.profileLevels.map {
                        CodecDetails.ProfileLevel(it.profile, it.level)
                    }

                    val supportedFeatures = mutableListOf<String>()
                    val requiredFeatures = mutableListOf<String>()

                    for (feature in SUPPORTED_FEATURES) {
                        try {
                            if (caps.isFeatureSupported(feature)) {
                                supportedFeatures.add(feature)
                            }
                            if (caps.isFeatureRequired(feature)) {
                                requiredFeatures.add(feature)
                            }
                        } catch (_: Exception) {
                            // Ignore unsupported features
                        }
                    }

                    val audioCaps = if (mimeType.startsWith("audio/")) {
                        try {
                            val ac = caps.audioCapabilities
                            if (ac != null) {
                                CodecDetails.AudioCapabilities(
                                    bitrateRange = mediaCodecDataSource.safeCodecInfoAccess {
                                        ac.bitrateRange.toString()
                                    },
                                    maxInputChannelCount = mediaCodecDataSource.safeCodecInfoAccess {
                                        ac.maxInputChannelCount
                                    },
                                    minInputChannelCount = mediaCodecDataSource.safeCodecInfoAccess {
                                        ac.minInputChannelCount
                                    },
                                    supportedSampleRates = mediaCodecDataSource.safeCodecInfoAccess {
                                        ac.supportedSampleRates.toList()
                                    } ?: emptyList(),
                                    supportedSampleRateRanges = mediaCodecDataSource.safeCodecInfoAccess {
                                        ac.supportedSampleRateRanges.map { it.toString() }
                                    } ?: emptyList()
                                )
                            } else null
                        } catch (_: Exception) {
                            null
                        }
                    } else null

                    val videoCaps = if (mimeType.startsWith("video/")) {
                        try {
                            val vc = caps.videoCapabilities
                            if (vc != null) {
                                CodecDetails.VideoCapabilities(
                                    bitrateRange = mediaCodecDataSource.safeCodecInfoAccess {
                                        vc.bitrateRange.toString()
                                    },
                                    widthAlignment = mediaCodecDataSource.safeCodecInfoAccess {
                                        vc.widthAlignment
                                    },
                                    heightAlignment = mediaCodecDataSource.safeCodecInfoAccess {
                                        vc.heightAlignment
                                    },
                                    supportedWidths = mediaCodecDataSource.safeCodecInfoAccess {
                                        vc.supportedWidths.toString()
                                    },
                                    supportedHeights = mediaCodecDataSource.safeCodecInfoAccess {
                                        vc.supportedHeights.toString()
                                    },
                                    supportedFrameRates = mediaCodecDataSource.safeCodecInfoAccess {
                                        vc.supportedFrameRates.toString()
                                    },
                                    performancePoints = mediaCodecDataSource.safeCodecInfoAccess {
                                        vc.supportedPerformancePoints?.map { it.toString() }
                                    } ?: emptyList()
                                )
                            } else null
                        } catch (_: Exception) {
                            null
                        }
                    } else null

                    val encoderCaps = if (mediaCodecInfo.isEncoder) {
                        try {
                            val ec = caps.encoderCapabilities
                            if (ec != null) {
                                val supportedModes = mutableListOf<String>()
                                for ((mode, name) in BITRATE_MODES) {
                                    try {
                                        if (ec.isBitrateModeSupported(mode)) {
                                            supportedModes.add(name)
                                        }
                                    } catch (_: Exception) {
                                        // Ignore unsupported modes
                                    }
                                }

                                CodecDetails.EncoderCapabilities(
                                    complexityRange = mediaCodecDataSource.safeCodecInfoAccess {
                                        ec.complexityRange.toString()
                                    },
                                    qualityRange = mediaCodecDataSource.safeCodecInfoAccess {
                                        ec.qualityRange.toString()
                                    },
                                    supportedBitrateModes = supportedModes
                                )
                            } else null
                        } catch (_: Exception) {
                            null
                        }
                    } else null

                    val typeCapabilities = CodecDetails.TypeCapabilities(
                        colorFormats = colorFormats,
                        maxSupportedInstances = maxSupportedInstances,
                        profileLevels = profileLevels,
                        supportedFeatures = supportedFeatures,
                        requiredFeatures = requiredFeatures,
                        audioCapabilities = audioCaps,
                        videoCapabilities = videoCaps,
                        encoderCapabilities = encoderCaps
                    )

                    capabilitiesMap[mimeType] = typeCapabilities

                } catch (_: Exception) {
                    capabilitiesMap[mimeType] = CodecDetails.TypeCapabilities()
                }
            }
        }

        return CodecDetails(
            basicInfo = basicInfo,
            supportedTypes = supportedTypes,
            capabilitiesMap = capabilitiesMap
        )
    }
}