fun main() {
    fun part1(input: List<String>): Int {
        val lastOccurrence = mutableMapOf<Int, Int>()
        var lastSpoken = 0
        var cnt = 0

        input[0].split(",").subList(0, input[0].split(",").size-1).forEachIndexed { index, string ->
            lastOccurrence[string.toInt()] = index
        }

        lastSpoken = input[0].split(",").last().toInt()
        cnt = lastOccurrence.size

        while(cnt < 2020 - 1) {
            val nextSpoken = lastOccurrence[lastSpoken]?.let { cnt - it } ?: 0
            lastOccurrence[lastSpoken] = cnt
            lastSpoken = nextSpoken
            cnt++
        }

        return lastSpoken
    }

    fun part2(input: List<String>): Int {
        val lastOccurrence = IntArray(30000005) { -1 }
        var lastSpoken = 0
        var cnt = 0

        input[0].split(",").subList(0, input[0].split(",").size-1).forEachIndexed { index, string ->
            lastOccurrence[string.toInt()] = index
        }

        lastSpoken = input[0].split(",").last().toInt()
        cnt = input[0].split(",").size - 1

        while(cnt < 30000000 - 1) {
            val nextSpoken = if (lastOccurrence[lastSpoken] != -1) cnt - lastOccurrence[lastSpoken] else 0
            lastOccurrence[lastSpoken] = cnt
            lastSpoken = nextSpoken
            cnt++
        }

        return lastSpoken
    }

    val input = readInput("Day15")
    part1(input).println()
    part2(input).println()
}
