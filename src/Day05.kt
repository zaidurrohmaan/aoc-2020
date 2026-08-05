fun seatId(code: String): Int {
    return code
        .replace('F', '0')
        .replace('B', '1')
        .replace('L', '0')
        .replace('R', '1')
        .toInt(2)
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.maxOf(::seatId)
    }

    fun part2(input: List<String>): Int {
        return input.map(::seatId)
            .sorted()
            .zipWithNext()
            .first { (current, next) -> next - current == 2}
            .first + 1
    }

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
