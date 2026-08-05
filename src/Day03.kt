fun main() {
    fun part1(input: List<String>): Int {
        var idx = 0
        val len = input[0].length
        var ans = 0
        for (i in 1 until input.size) {
            idx = (idx + 3) % len
            if (input[i][idx] == '#') ans++
        }
        return ans
    }

    fun part2(input: List<String>): Long {
        val slopes = listOf(Pair(1,1), Pair(3,1), Pair(5,1), Pair(7,1), Pair(1,2))
        val len = input[0].length
        var ans = 1L

        for (slope in slopes) {
            var idx = 0
            var cnt = 0
            for (i in slope.second until input.size step slope.second) {
                idx = (idx + slope.first) % len
                if (input[i][idx] == '#') cnt++
            }
            ans *= cnt
        }
        return ans
    }

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
