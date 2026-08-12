import kotlin.collections.mutableListOf

fun main() {
    fun part1(input: List<String>): Long {
        val ranges = mutableSetOf<Int>()
        var nearbyTickets = false
        var sum = 0L
        for (line in input) {
            if (nearbyTickets) {
                sum += line.split(",").map { it.toInt() }.filter { !ranges.contains(it) }.sum()
            } else if (line.startsWith("nearby tickets:")) {
                nearbyTickets = true
            } else if (line.contains("or")) {
                val (range1, range2) = line.substringAfter(": ").split(" or ")
                ranges.addAll(range1.split("-").map { it.toInt() }.let { it[0]..it[1] })
                ranges.addAll(range2.split("-").map { it.toInt() }.let { it[0]..it[1] })
            }
        }
        return sum
    }

    fun part2(input: List<String>): Long {
        val fields = mutableMapOf<String, List<IntRange>>()
        val nearbyTickets = mutableListOf<List<Int>>()
        val yourTicket = input[input.indexOfFirst { it.startsWith("your ticket:") } + 1].split(",").map { it.toInt() }

        for (line in input) {
            if (!line.contains("or")) {
                break
            }

            val (field, ranges) = line.split(": ")
            fields[field] = ranges.split(" or ").map { it.split("-").map { bounds -> bounds.toInt() }.let { bound -> bound[0]..bound[1] } }
        }

        fun Int.isValidValue() = fields.values.any { it.any { range -> range.contains(this) } }

        fun String.isValidTicket() = this.split(",").map { it.toInt() }.all { it.isValidValue() }

        input.subList(input.indexOfFirst { it.startsWith("nearby tickets:") } + 1, input.size).forEach { ticket ->
            if (ticket.isValidTicket()) {
                nearbyTickets.add(ticket.split(",").map { it.toInt() })
            }
        }

        val belongsToWhichField = mutableMapOf<Int, MutableList<String>>()
        val bookedFields = mutableSetOf<String>()

        for (position in yourTicket.indices) {
            val valuesOfPosition = mutableListOf<Int>()
            for (ticket in nearbyTickets) {
                valuesOfPosition.add(ticket[position])
            }
            for ((key, value) in fields) {
                if (valuesOfPosition.all { it ->
                    value.any { fieldRange -> it in fieldRange }
                }) {
                    belongsToWhichField.getOrPut(position) { mutableListOf() }.add(key)
                }
            }
        }

        val sortedBelongToWhichField = belongsToWhichField.entries
            .sortedBy { it.value.size }
            .associate { it.key to it.value }
            .toMutableMap()

        for ((position, fields) in sortedBelongToWhichField) {
            for (field in fields) {
                if (!bookedFields.contains(field)) {
                    bookedFields.add(field)
                    sortedBelongToWhichField.getOrPut(position) { mutableListOf() }.removeAll { it != field }
                    break
                }
            }
        }

        var ans = 1L

        sortedBelongToWhichField.entries.forEach { (position, fields) ->
            if (fields.first().startsWith("departure")) {
                ans *= yourTicket[position].toLong()
            }
        }

        return ans
    }

    val input = readInput("Day16")
    part1(input).println()
    part2(input).println()
}
