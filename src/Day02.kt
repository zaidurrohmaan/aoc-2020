fun main() {
    fun part1(input: List<String>): Int {
        val regex = """(\d+)-(\d+) (.): (.*)""".toRegex()
        return input.count { line ->
            val (min, max, ch, pw) = regex.matchEntire(line)!!.destructured
            pw.count { it == ch.first() } in min.toInt()..max.toInt()
        }
    }

    fun part2(input: List<String>): Int {
        val regex = """(\d+)-(\d+) (.): (.*)""".toRegex()
        return input.count { line ->
            val (min, max, ch, pw) = regex.matchEntire(line)!!.destructured
            (pw[min.toInt()-1]==ch.first()) xor (pw[max.toInt()-1]==ch.first())
        }
    }

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
