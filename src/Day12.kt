import kotlin.math.abs

fun main() {
    fun newDirection(currentDirection: Char, rotate: Char, degree: Int): Char {
        val shift = (degree/90) * if (rotate == 'R') 1 else -1
        val dirs = listOf('N', 'E', 'S', 'W')
        return dirs[(dirs.indexOf(currentDirection) + shift + 4) % 4]
    }

    fun part1(input: List<String>): Int {
        var currentDirection = 'E'
        var x = 0
        var y = 0

        fun move(direction: Char, distance: Int) {
            when (direction) {
                'N' -> y += distance
                'E' -> x += distance
                'S' -> y -= distance
                'W' -> x -= distance
            }
        }

        for (line in input) {
            when (line.first()) {
                in listOf('N', 'E', 'S', 'W') -> move(line.first(), line.drop(1).toInt())
                'F' -> move(currentDirection, line.drop(1).toInt())
                'L', 'R' -> currentDirection = newDirection(currentDirection, line.first(), line.drop(1).toInt())
            }
        }

        return abs(x) + abs(y)
    }

    fun part2(input: List<String>): Int {
        var shipX = 0
        var shipY = 0
        var waypointX = 10
        var waypointY = 1

        fun moveWaypoint(direction: Char, distance: Int) {
            when (direction) {
                'N' -> waypointY += distance
                'E' -> waypointX += distance
                'S' -> waypointY -= distance
                'W' -> waypointX -= distance
            }
        }

        fun forward(multiplier: Int) {
            shipX += waypointX * multiplier
            shipY += waypointY * multiplier
        }

        fun rotateMoveWaypoint(direction: Char, distance: Int) {
            repeat(distance/90) {
                when (direction) {
                    'L' -> waypointX = (waypointY * -1) .also { waypointY = waypointX }
                    'R' -> waypointY = (waypointX * -1).also { waypointX = waypointY }
                }
            }
        }

        for (line in input) {
            when (line.first()) {
                in listOf('N', 'E', 'S', 'W') -> moveWaypoint(line.first(), line.drop(1).toInt())
                'F' -> forward(line.drop(1).toInt())
                'L', 'R' -> rotateMoveWaypoint(line.first(), line.drop(1).toInt())
            }
        }

        return abs(shipX) + abs(shipY)
    }

    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}
