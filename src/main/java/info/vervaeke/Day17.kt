package info.vervaeke

import kotlin.math.max
import kotlin.math.min

class Day17 {

    companion object {
        fun parse(line: String): Pair<Coordinate, Coordinate> {
            println(line)
            val match = "target area: x=(-?\\d+)..(-?\\d+), y=(-?\\d+)..(-?\\d+)".toRegex()
                .find(line)!!

            val x1 = match.groups.get(1)!!.value.toInt()
            val x2 = match.groups.get(2)!!.value.toInt()
            val y1 = match.groups.get(3)!!.value.toInt()
            val y2 = match.groups.get(4)!!.value.toInt()

            return Coordinate(min(x1, x2), min(y1, y2)) to Coordinate(max(x1, x2), max(y1, y2))

        }
    }

    fun part1(target: Pair<Coordinate, Coordinate>): Int {
        val minx = target.first.x
        val maxx = target.second.x
        val miny = target.first.y
        val maxy = target.second.y

        val dxs = (0..maxx).filter {
            intialDxReachesTarget(it, target)
        }
        var dys = (-10000..10000).filter {
            initialDyReachesTarget(it, target)
        }

        val highestY = dxs.asSequence().flatMap { dx ->
            dys.map { dy -> dx to dy }
        }.map {
            maxHeightIfHitsTarget(it.first, it.second, target)
        }.filterNotNull()
            .maxOf { it }

        return highestY
    }

    fun part2(target: Pair<Coordinate, Coordinate>): Int {
        val minx = target.first.x
        val maxx = target.second.x
        val miny = target.first.y
        val maxy = target.second.y

        val dxs = (0..maxx).filter {
            intialDxReachesTarget(it, target)
        }
        var dys = (-10000..10000).filter {
            initialDyReachesTarget(it, target)
        }

        return dxs.asSequence().flatMap { dx ->
            dys.map { dy -> dx to dy }
        }.map {
            maxHeightIfHitsTarget(it.first, it.second, target)
        }.filterNotNull()
            .count()
    }

    private fun maxHeightIfHitsTarget(dx: Int, dy: Int, target: Pair<Coordinate, Coordinate>): Int? {
        var x = 0
        var y = 0

        val minx = target.first.x
        val maxx = target.second.x
        val miny = target.first.y
        val maxy = target.second.y

        var dx = dx
        var dy = dy

        var maxHeight = 0

        while (y >= miny) {
            y += dy
            x += dx

            if (y > maxHeight) {
                maxHeight = y
            }

            dy -= 1
            dx = max(dx - 1, 0)

            if (minx <= x && x <= maxx && miny <= y && y <= maxy) {
                return maxHeight
            }
        }

        return null
    }

    private fun initialDyReachesTarget(initialDy: Int, target: Pair<Coordinate, Coordinate>): Boolean {
        var y = 0
        var dy = initialDy

        val miny = target.first.y
        val maxy = target.second.y

        while (y >= miny) {
            y += dy
            dy -= 1

            if (miny <= y && y <= maxy) {
                return true
            }
        }
        return false
    }

    private fun intialDxReachesTarget(initialDx: Int, target: Pair<Coordinate, Coordinate>): Boolean {
        var x = 0
        var dx = initialDx
        val minx = target.first.x
        val maxx = target.second.x
        while (dx > 0) {
            x += dx
            dx -= 1

            if (minx <= x && x <= maxx) {
                return true
            }
        }
        return false
    }


    data class Coordinate(val x: Int, val y: Int)

}

