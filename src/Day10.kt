fun main() {
    fun part1(input: List<Int>): Int {
        var currentDeviceJolts = 0
        val joltDifferences = mutableMapOf<Int, Int>()
        input.sorted().forEach { jolt ->
            val difference = jolt - currentDeviceJolts
            joltDifferences[difference] = joltDifferences.getOrDefault(difference, 0) + 1
            currentDeviceJolts = jolt
        }
        return joltDifferences[1]!! * (joltDifferences[3]!! + 1)
    }

    fun part2(input: List<Int>): Long {
        val ways = mutableMapOf<Int, Long>()
        ways[input.max()] = 1L

        fun countWays(jolt: Int): Long {
            return ways[jolt] ?: run {
                var sum = 0L
                for (i in 1..3) {
                    val neighbor = jolt + i
                    if (input.contains(neighbor)) {
                        sum += countWays(neighbor)
                    }
                }

                ways[jolt] = sum
                return ways[jolt]!!
            }
        }

        return countWays(0)
    }

    val input = readInput("Day10").map { it.toInt() }
    part1(input).println()
    part2(input).println()
}
