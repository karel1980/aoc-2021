package info.vervaeke

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class Day19Test {

    @Test
    fun parseScanners() {
        val scannerReports = Day19.parseScanners(sampleLinesOfDay(19))
        assertThat(scannerReports)
            .hasSize(5)
    }

    @Test
    fun orientations() {
        val scannerReport = Day19.parseScanners(sampleLinesOfDay(19)).first()
        assertThat(scannerReport.orientations())
            .hasSize(48)
    }

    @Test
    fun countMatches() {
        val scannerReport0 = Day19.parseScanners(sampleLinesOfDay(19)).first()
        assertThat(scannerReport0.matches(scannerReport0))
            .isEqualTo(25)

        val scannerReport1 = Day19.ScannerReport("test", scannerReport0.measures.take(5).toSet())
        assertThat(scannerReport0.matches(scannerReport1))
            .isEqualTo(5)
    }

    @Test
    fun findBestOrientation() {
        val scanners = Day19.parseScanners(sampleLinesOfDay(19))
        val scanner0 = scanners.first()
        val scanner1 = scanners.drop(1).first()

        println(scanner0.id)
        println(scanner0.measures.joinToString("\n"))
        println(scanner1.id)
        println(scanner1.measures.joinToString("\n"))

        assertThat(scanner1.findBestOrientation(scanner0, 12))
            .isNotNull
    }

    @Test
    fun `part1 sample`() {
        val scanners = Day19.parseScanners(sampleLinesOfDay(19))

        assertThat(Day19.part1(scanners))
            .isEqualTo(79)
    }

}
