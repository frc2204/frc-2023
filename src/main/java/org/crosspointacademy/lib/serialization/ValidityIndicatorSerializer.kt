package org.crosspointacademy.lib.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object ValidityIndicatorSerializer : KSerializer<Boolean> {

    override fun deserialize(decoder: Decoder): Boolean {
        return decoder.decodeInt() == 1
    }

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("validityIndicator", PrimitiveKind.INT)
    override fun serialize(encoder: Encoder, value: Boolean) {
        encoder.encodeInt(if (value) 1 else 0)
    }

}