package info.vervaeke

class Day19 {

    companion object {
        val orienters = listOf(
            { b: BeaconMeasure -> BeaconMeasure(b.x, b.y, b.z) },
            { b: BeaconMeasure -> BeaconMeasure(-b.x, b.y, b.z) },
            { b: BeaconMeasure -> BeaconMeasure(b.x, -b.y, b.z) },
            { b: BeaconMeasure -> BeaconMeasure(-b.x, -b.y, b.z) },
            { b: BeaconMeasure -> BeaconMeasure(b.x, b.y, -b.z) },
            { b: BeaconMeasure -> BeaconMeasure(-b.x, b.y, -b.z) },
            { b: BeaconMeasure -> BeaconMeasure(b.x, -b.y, -b.z) },
            { b: BeaconMeasure -> BeaconMeasure(-b.x, -b.y, -b.z) },

            { b: BeaconMeasure -> BeaconMeasure(b.y, b.x, b.z) },
            { b: BeaconMeasure -> BeaconMeasure(b.y, -b.x, b.z) },
            { b: BeaconMeasure -> BeaconMeasure(-b.y, b.x, b.z) },
            { b: BeaconMeasure -> BeaconMeasure(-b.y, -b.x, b.z) },
            { b: BeaconMeasure -> BeaconMeasure(b.y, b.x, -b.z) },
            { b: BeaconMeasure -> BeaconMeasure(b.y, -b.x, -b.z) },
            { b: BeaconMeasure -> BeaconMeasure(-b.y, b.x, -b.z) },
            { b: BeaconMeasure -> BeaconMeasure(-b.y, -b.x, -b.z) },

            { b: BeaconMeasure -> BeaconMeasure(b.x, b.z, b.y) },
            { b: BeaconMeasure -> BeaconMeasure(-b.x, b.z, b.y) },
            { b: BeaconMeasure -> BeaconMeasure(b.x, -b.z, b.y) },
            { b: BeaconMeasure -> BeaconMeasure(-b.x, -b.z, b.y) },
            { b: BeaconMeasure -> BeaconMeasure(b.x, b.z, -b.y) },
            { b: BeaconMeasure -> BeaconMeasure(-b.x, b.z, -b.y) },
            { b: BeaconMeasure -> BeaconMeasure(b.x, -b.z, -b.y) },
            { b: BeaconMeasure -> BeaconMeasure(-b.x, -b.z, -b.y) },

            { b: BeaconMeasure -> BeaconMeasure(b.y, b.z, b.x) },
            { b: BeaconMeasure -> BeaconMeasure(-b.y, b.z, b.x) },
            { b: BeaconMeasure -> BeaconMeasure(b.y, -b.z, b.x) },
            { b: BeaconMeasure -> BeaconMeasure(-b.y, -b.z, b.x) },
            { b: BeaconMeasure -> BeaconMeasure(b.y, b.z, -b.x) },
            { b: BeaconMeasure -> BeaconMeasure(-b.y, b.z, -b.x) },
            { b: BeaconMeasure -> BeaconMeasure(b.y, -b.z, -b.x) },
            { b: BeaconMeasure -> BeaconMeasure(-b.y, -b.z, -b.x) },

            { b: BeaconMeasure -> BeaconMeasure(b.z, b.x, b.y) },
            { b: BeaconMeasure -> BeaconMeasure(-b.z, b.x, b.y) },
            { b: BeaconMeasure -> BeaconMeasure(b.z, -b.x, b.y) },
            { b: BeaconMeasure -> BeaconMeasure(-b.z, -b.x, b.y) },
            { b: BeaconMeasure -> BeaconMeasure(b.z, b.x, -b.y) },
            { b: BeaconMeasure -> BeaconMeasure(-b.z, b.x, -b.y) },
            { b: BeaconMeasure -> BeaconMeasure(b.z, -b.x, -b.y) },
            { b: BeaconMeasure -> BeaconMeasure(-b.z, -b.x, -b.y) },

            { b: BeaconMeasure -> BeaconMeasure(b.z, b.y, b.x) },
            { b: BeaconMeasure -> BeaconMeasure(-b.z, b.y, b.x) },
            { b: BeaconMeasure -> BeaconMeasure(b.z, -b.y, b.x) },
            { b: BeaconMeasure -> BeaconMeasure(-b.z, -b.y, b.x) },
            { b: BeaconMeasure -> BeaconMeasure(b.z, b.y, -b.x) },
            { b: BeaconMeasure -> BeaconMeasure(-b.z, b.y, -b.x) },
            { b: BeaconMeasure -> BeaconMeasure(b.z, -b.y, -b.x) },
            { b: BeaconMeasure -> BeaconMeasure(-b.z, -b.y, -b.x) },
        );

        fun parseScanners(lines: List<String>): List<ScannerReport> {
            var remaining = lines
            val result = mutableListOf<ScannerReport>()
            while (!remaining.isEmpty()) {
                val reportLines = remaining.takeWhile { !it.isEmpty() }
                result.add(ScannerReport(reportLines.first(), reportLines.drop(1).map {
                    val values = it.split(",").map { it.toInt() }
                    BeaconMeasure(values[0], values[1], values[2])
                }.toSet()))

                remaining = remaining.drop(reportLines.size + 1)
            }

            return result
        }

        fun part1(reports: List<Day19.ScannerReport>): Int {
            val scannerZero = reports.first().normalizeOn(reports.first().measures.first())
            val origin = scannerZero.measures.first()
            val queue = mutableListOf(scannerZero)
            val result = mutableListOf(scannerZero)
            while (queue.isNotEmpty()) {
                val current = queue.removeFirst()
                println("Removed ${current.id} from queue")

                val remaining = reports.filter { it.id !in result.map { it.id } }

                println("remaining: ${remaining.map { it.id }}")
                val setsToAdd = remaining.map { it.findBestOrientation(current, 12) }
                    .filterNotNull()
                    .map { it.normalizeOn(origin)}

                queue.addAll(setsToAdd)
                result.addAll(setsToAdd)
                println("result: ${result.map { it.id }}")
            }

            return result.fold(emptySet<BeaconMeasure>()) { acc, t -> acc.union(t.measures) }.size
        }


    }

    data class ScannerReport(val id: String, val measures: Set<BeaconMeasure>) {
        fun normalizeOn(origin: BeaconMeasure): ScannerReport {
            return ScannerReport(id, measures.map {
                BeaconMeasure(it.x - origin.x, it.y - origin.y, it.z - origin.z)
            }.toSet())
        }

        fun orient(orienter: (BeaconMeasure) -> BeaconMeasure): ScannerReport {
            return ScannerReport(id, measures.map { orienter(it) }.toSet())
        }

        fun orientations(): List<ScannerReport> {
            return orienters.map { orient(it) }
        }

        fun normalizations(): List<ScannerReport> {
            return measures.map { this.normalizeOn(it) }
        }

        fun matches(other: Day19.ScannerReport): Int {
            return measures.intersect(other.measures).size
        }

        fun matches(measures: Set<BeaconMeasure>): Int {
            return this.measures.intersect(measures).size
        }

        fun findBestOrientation(zero: ScannerReport, minimumScore: Int = 12): ScannerReport? {
            val all = mutableSetOf<ScannerReport>()

            zero.normalizations().forEach { zeroNorm ->
                normalizations().forEach { norm ->
                    norm.orientations().forEach { orientation ->
                        if (zeroNorm.matches(orientation) >= minimumScore) {
                            all.add(norm)
                        }
                    }
                }
            }

            return all.firstOrNull()
        }
    }

    data class BeaconMeasure(val x: Int, val y: Int, val z: Int)

}

