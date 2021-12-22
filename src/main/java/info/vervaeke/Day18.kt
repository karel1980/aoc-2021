package info.vervaeke

import java.util.StringTokenizer

class Day18 {

    companion object {
        fun parseNumber(str: String): PairNumber {
            println("parsing $str")
            val tokenizer = StringTokenizer(str, "[,]", true)
            val tokens = mutableListOf<String>()
            while (tokenizer.hasMoreTokens()) {
                tokens.add(tokenizer.nextToken())
            }
            return buildNumber(tokens).first as PairNumber
        }

        private fun buildNumber(tokens: List<String>): Pair<SnailNumber, List<String>> {
            if (tokens[0] == "[") {
                var (left, remaining) = buildNumber(tokens.drop(1)) // drop the [
                var (right, remaining2) = buildNumber(remaining.drop(1)) // drop the ,
                return pair(left, right) to remaining2.drop(1) // drop the ]
            }
            if (tokens[0] == "]" || tokens[0] == ",") {
                throw RuntimeException("Unexpected symbol: ${tokens[0]}")
            }
            return regular(tokens[0].toInt()) to tokens.drop(1)
        }

        fun pair(left: Int, right: Int) = PairNumber(regular(left), regular(right))
        fun pair(left: SnailNumber, right: Int) = PairNumber(left, regular(right))
        fun pair(left: Int, right: SnailNumber) = PairNumber(regular(left), right)
        fun pair(left: SnailNumber, right: SnailNumber) = PairNumber(left, right)

        fun regular(value: Int) = RegularNumber(value)
        fun part1(numbers: List<PairNumber>): Int {
            return numbers.reduce { a, b -> a.add(b) }.magnitude()
        }

        fun part2(numbers: List<String>): Int {
            val pairs = numbers
                .flatMap { a ->
                    numbers
                        .filter { a != it } // not clear if reuse is allowed
                        .map { a to it }

                }
            val maxMagnitude = pairs
                .maxOf { parseNumber(it.first).add(parseNumber(it.second)).magnitude() }

//            println("Max magnitude $maxMagnitude with these numbers: ")

            return maxMagnitude
        }

        interface SnailNumber {
            var parent: PairNumber?
            fun add(num: PairNumber) = pair(this, num).reduce()
            fun magnitude(): Int
        }

        class PairNumber(var _left: SnailNumber, var _right: SnailNumber) : SnailNumber {
            override var parent: PairNumber? = null
            override fun magnitude() = left.magnitude() * 3 + right.magnitude() * 2

            var left: SnailNumber
                get() = _left
                set(left) {
                    _left = left
                    _left.parent = this
                }
            var right: SnailNumber
                get() = _right
                set(right) {
                    _right = right
                    _right.parent = this
                }

            init {
                left.parent = this
                right.parent = this
            }

            override fun toString(): String {
                return "[${left.toString()},${right.toString()}]"
            }

            fun reduce(): PairNumber {
                var current = this
                var previousString: String? = null

                while (previousString != current.toString()) {
                    previousString = current.toString()
                    current.explode()
                    if (previousString != current.toString()) {
                        continue
                    }
                    current.split()
                }

                return this
            }

            fun explode() {
                println("exploding ${toString()}")
                val toExplode = findFirstPairAtDepth(4)
                println("first pair at depth 4 is $toExplode")
                if (toExplode == null) {
                    return
                }
                println("to explode $toExplode")
                val explodeParent = toExplode.parent
                if (explodeParent == null) {
                    println("toExplode $toExplode has no parent")
                    return
                }

                val leftTarget = toExplode.findFirstRegularNumberToTheLeft()
                println("explode left ${toExplode.left} to $leftTarget")
                if (leftTarget != null) {
                    leftTarget.value += (toExplode.left as RegularNumber).value
                }

                val rightTarget = toExplode.findFirstRegularNumberToTheRight()
                println("explode right ${toExplode.right} to $rightTarget")
                if (rightTarget != null) {
                    rightTarget.value += (toExplode.right as RegularNumber).value
                }

                if (toExplode == explodeParent.left) {
                    explodeParent.left = regular(0)
                }
                if (toExplode == explodeParent.right) {
                    explodeParent.right = regular(0)
                }
            }

            fun findFirstPairAtDepth(depth: Int): PairNumber? {
                if (depth == 0) {
                    return this
                }
                val left = left
                if (left is PairNumber) {
                    val leftFirstPair = left.findFirstPairAtDepth(depth - 1)
                    if (leftFirstPair != null) {
                        return leftFirstPair
                    }
                }
                val right = right
                if (right is PairNumber) {
                    val rightFirstPair = right.findFirstPairAtDepth(depth - 1)
                    if (rightFirstPair != null) {
                        return rightFirstPair
                    }
                }
                return null
            }

            fun findFirstRegularNumberToTheLeft(): RegularNumber? {
                val parent = parent ?: return null
                if (parent.right == this) {
                    if (parent.left is RegularNumber) {
                        return parent.left as RegularNumber
                    }
                    return (parent.left as PairNumber).rightmostRegularNumber()
                }
                return parent.findFirstRegularNumberToTheLeft()
            }

            fun findFirstRegularNumberToTheRight(): RegularNumber? {
                val parent = parent ?: return null
                if (parent.left == this) {
                    if (parent.right is RegularNumber) {
                        return parent.right as RegularNumber
                    }
                    return (parent.right as PairNumber).leftmostRegularNumber()
                }
                return parent.findFirstRegularNumberToTheRight()
            }

            private fun rightmostRegularNumber(): RegularNumber {
                val right = right
                if (right is RegularNumber) {
                    return right
                }
                return (right as PairNumber).rightmostRegularNumber()
            }

            private fun leftmostRegularNumber(): RegularNumber {
                val left = left
                if (left is RegularNumber) {
                    return left
                }
                return (left as PairNumber).leftmostRegularNumber()
            }

            fun split(): PairNumber {
                var regular = findRegularNumberToSplit() ?: return this

                if (regular == regular.parent!!.left) {
                    regular.parent!!.left = pair(regular.value / 2, (regular.value + 1) / 2)
                }
                if (regular == regular.parent!!.right) {
                    regular.parent!!.right = pair(regular.value / 2, (regular.value + 1) / 2)
                }

                return this
            }

            private fun findRegularNumberToSplit(): RegularNumber? {
                val left = left
                val right = right
                if (left is RegularNumber && left.value > 9) {
                    return left
                }
                if (left is PairNumber) {
                    val leftToSplit = left.findRegularNumberToSplit()
                    if (leftToSplit != null) {
                        return leftToSplit
                    }
                }
                if (right is RegularNumber && right.value > 9) {
                    return right
                }
                if (right is PairNumber) {
                    val rightToSplit = right.findRegularNumberToSplit()
                    if (rightToSplit != null) {
                        return rightToSplit
                    }
                }
                return null
            }
        }

        class RegularNumber(var value: Int) : SnailNumber {
            override var parent: PairNumber? = null
            override fun magnitude(): Int = value

            override fun toString(): String {
                return "$value"
            }
        }
    }
}

