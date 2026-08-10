fun main() {
    fun part1(input: List<String>): Int {
        val earliestTimestamp = input[0].toInt()
        val busIDs = input[1].split(",")
            .filter { it != "x" }
            .map {Pair(it.toInt() * ((earliestTimestamp-1) / it.toInt() + 1), it.toInt())}
            .sortedBy { it.first }
        return (busIDs[0].first - earliestTimestamp) * busIDs[0].second
    }

    fun part2(input: List<String>): Long {
        val buses = input[1].split(",")
            .withIndex()
            .filter { it.value != "x" }
            .map { it.index to it.value.toLong() }

        var time = 0L
        var step = 1L

        for ((offset, busId) in buses) {
            while ((time + offset) % busId != 0L) {
                time += step
            }
            step *= busId
        }

        return time
    }

    val input = readInput("Day13")
    part1(input).println()
    part2(input).println()
}
