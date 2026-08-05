import kotlin.io.path.Path
import kotlin.io.path.readText

fun parseGroups(input: String): List<String> {
    return input.replace("\r\n", "\n")
        .trim()
        .split("\n\n")
}

fun main() {
    fun part1(input: String): Int {
        return parseGroups(input).sumOf { group ->
            group.replace("\n", "").toSet().size
        }
    }

    fun part2(input: String): Int {
        return parseGroups(input).sumOf { group ->
            group.lines()
                .map { it.toSet() }
                .reduce { acc, set -> acc intersect set}
                .size
        }
    }

    val input = Path("src/Day06.txt").readText().trim()
    part1(input).println()
    part2(input).println()
}
