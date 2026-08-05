import kotlin.io.path.Path
import kotlin.io.path.readText

fun parsePassports(input: String): List<Map<String, String>> {
    return input.replace("\r\n", "\n")
        .trim()
        .split("\n\n")
        .map { block ->
            block.split(Regex("\\s+")).associate { pair ->
                val (key, value) = pair.split(":")
                key to value
            }
        }
}

fun isContainsAllFields(passport: Map<String, String>): Boolean {
    val mandatory = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
    return passport.keys.containsAll(mandatory)
}

fun isFieldValid(key: String, value: String): Boolean {
    return when(key) {
        "byr" -> value.toIntOrNull() in 1920..2002
        "iyr" -> value.toIntOrNull() in 2010..2020
        "eyr" -> value.toIntOrNull() in 2020..2030
        "hgt" -> {
            val num = value.dropLast(2).toIntOrNull()
            when (value.takeLast(2)) {
                "cm" -> num in 150..193
                "in" -> num in 59..76
                else -> false
            }
        }
        "hcl" -> value.matches(Regex("#[0-9a-f]{6}"))
        "ecl" -> value in setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
        "pid" -> value.matches(Regex("\\d{9}"))
        "cid" -> true
        else -> false
    }
}

fun isPassportValid(passport: Map<String, String>): Boolean {
    if (!isContainsAllFields(passport)) return false
    return passport.all { (key, value) -> isFieldValid(key, value) }
}

fun main() {
    fun part1(input: String): Int {
        return parsePassports(input).count (::isContainsAllFields)
    }

    fun part2(input: String): Int {
        return parsePassports(input).count(::isPassportValid)
    }

    val input = Path("src/Day04.txt").readText().trim()
    part1(input).println()
    part2(input).println()
}
