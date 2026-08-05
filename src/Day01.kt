fun main() {
    fun part1(input: List<String>): Int {
        val list = input.map { it.toInt() }.sorted()
        var i = 0
        var j = input.size - 1

        while (i < j) {
            val sum = list[i] + list[j]
            if (sum == 2020) return list[i] * list[j]
            if (sum < 2020) i++
            else j--
        }
        return 0
    }

    fun part2(input: List<String>): Int {
        val list = input.map { it.toInt() }

        for (i in 0 until list.size) {
            for (j in i+1 until list.size) {
                for (k in j+1 until list.size) {
                    val sum = list[i] + list[j] + list[k]
                    if (sum == 2020) return list[i] * list[j] * list[k]
                }
            }
        }

        return 0
    }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
