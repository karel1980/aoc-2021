package info.vervaeke

import java.io.File

fun inputFileOfDay(n: Int) = "day${n}.txt"
fun sampleFileOfDay(n: Int) = "day${n}.sample.txt"

fun inputLinesOfDay(n: Int) = File(inputFileOfDay(n))
    .readLines(Charsets.UTF_8)

fun sampleLinesOfDay(n: Int) = File(sampleFileOfDay(n))
    .readLines(Charsets.UTF_8)


