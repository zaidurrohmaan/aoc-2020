
fun main() {
    fun accValue(input: List<String>): Pair<Int, Int> {
        var idx = 0
        var acc = 0

        val visited = MutableList(input.size) { false }

        while(idx in 0 until input.size && !visited[idx]) {
            visited[idx] = true
            val (op, value) = input[idx].split(" ")

            when (op) {
                "acc" -> {
                    acc += value.toInt()
                    idx++
                }
                "jmp" -> {
                    idx += value.toInt()
                }
                "nop" -> {
                    idx++
                }
            }
        }

        return Pair(acc, idx)
    }

    fun part1(input: List<String>): Int {
        return accValue(input).first
    }

    fun part2(input: List<String>): Int {
        val nop = mutableListOf<Int>()
        val jmp = mutableListOf<Int>()

        val commands = input.toMutableList()

        input.forEachIndexed { i, line ->
            when (line.substringBefore(" ")) {
                "nop" -> { nop.add(i) }
                "jmp" -> { jmp.add(i) }
                else -> {}
            }
        }

        for (idx in nop) {
            commands[idx] = commands[idx].replace("nop", "jmp")
            val (acc, id) = accValue(commands)
            if (id == input.size) return acc
            commands[idx] = commands[idx].replace("jmp", "nop")
        }

        for (idx in jmp) {
            commands[idx] = commands[idx].replace("jmp", "nop")
            val (acc, id) = accValue(commands)
            if (id == input.size) return acc
            commands[idx] = commands[idx].replace("nop", "jmp")
        }

        return 0
    }

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
