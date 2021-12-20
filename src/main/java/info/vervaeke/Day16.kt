package info.vervaeke

class Day16 {

    companion object {
        fun parse(line: String): Packet {
            val bits = toBitString(line)
            return RawPacket(bits).parse()
        }

        fun toBitString(line: String) = line.map {
            it.toString().toInt(16).toString(2).padStart(4, '0')
        }.joinToString("")
    }

    interface Packet {
        fun version(): Int
        fun typeId(): Int
        fun usedBits(): Int
        fun versionSum(): Int
    }

    data class RawPacket(val payload: String) {
        fun parse(): Packet {
            println("parsing $payload")
            val typeId = payload.drop(3).take(3).toInt(2)

            if (typeId == 4) {
                return createLiteralPacket(payload)
            }

            return parseOperatorPayload(payload)
        }

        private fun createLiteralPacket(payload: String): LiteralPacket {
            val version = payload.take(3).toInt(2)
            val typeId = payload.drop(3).take(3).toInt(2)
            println("LITERAL $version $typeId ${payload.drop(6)}")
            val chunks = payload.drop(6).chunked(5)
            val usableChunks = chunks.takeWhile { it.startsWith("1") } + chunks.dropWhile { it.startsWith("1") }.take(1)
            println("chunks $chunks")
            println("usable chunks $usableChunks")
            val literalValue = usableChunks.joinToString("") { it.drop(1) }.toLong(2)
            println("literalValue $literalValue")
            return LiteralPacket(version, typeId, literalValue, 6 + usableChunks.size * 5)
        }

        fun parseOperatorPayload(payload: String): OperatorPacket {
            val version = payload.take(3).toInt(2)
            val typeId = payload.drop(3).take(3).toInt(2)
            val bitI = payload.drop(6).take(1).toInt(2)

            println("OPERATOR $version $typeId $bitI ${payload.drop(7)}")

            if (bitI == 0) {
                val lengthBits = payload.drop(7).take(15)
                println("lengthBits $lengthBits")
                val subpacketsLength = lengthBits.toInt(2)
                println("subpacketsLength $subpacketsLength")
                val subpackets = parseMultiPacketsFixedLength(payload.drop(7 + 15).take(subpacketsLength))
                return OperatorPacket(version, typeId, subpackets, 7 + 15 + subpacketsLength)
            }

            val subpacketsCount = payload.drop(7).take(11).toInt(2)
            println("subpacketsCount $subpacketsCount")
            val subpackets = parseMultiPacketsFixedCount(payload.drop(7 + 11), subpacketsCount)
            return OperatorPacket(version, typeId, subpackets, 7 + 11 + subpackets.sumOf { it.usedBits() })
        }

        private fun parseMultiPacketsFixedLength(bits: String): List<Packet> {
            println("fixed length multiple packets $bits")
            var remaining = bits
            val result = mutableListOf<Packet>()
            while (!remaining.all { it == '0' }) { //TODO: not sure how to properly detect trailing zeros
                println("remaining before $remaining")
                val packet = RawPacket(remaining).parse()
                result.add(packet)
                val used = packet.usedBits()
                println("used bits: $used")
                remaining = remaining.drop(used)
                println("remaining after $remaining")
            }
            return result
        }

        private fun parseMultiPacketsFixedCount(bits: String, count: Int): List<Packet> {
            println("fixed count multiple packets $bits $count")
            var remaining = bits
            val result = mutableListOf<Packet>()
            while (result.size < count && !remaining.all { it == '0' }) {
                val packet = RawPacket(remaining).parse()
                println("parsed subpacket $packet")
                result.add(packet)
                remaining = remaining.drop(packet.usedBits())
                println("remaining (fixed count): $remaining")
            }

            return result
        }
    }

    data class LiteralPacket(val version: Int, val typeId: Int, val value: Long, val usedBits: Int): Packet {
        override fun version(): Int {
            return version
        }

        override fun typeId(): Int {
            return typeId
        }

        override fun usedBits(): Int {
            return usedBits
        }

        override fun versionSum(): Int {
            return version
        }
    }

    data class OperatorPacket(val version: Int, val typeId: Int, val subPackets: List<Packet>, val usedBits: Int): Packet {
        override fun version(): Int {
            return version
        }

        override fun typeId(): Int {
            return typeId
        }

        override fun usedBits(): Int {
            return usedBits
        }

        override fun versionSum(): Int {
            return version + subPackets.sumOf { it.versionSum() }
        }
    }


}


