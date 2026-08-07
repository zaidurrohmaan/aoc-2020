fun main() {
    fun part1(input: List<String>): Int {
        var grid = input.map { it.toCharArray() }.toTypedArray()
        val rows = grid.size
        val cols = grid[0].size

        val directions = arrayOf(
            -1 to -1, -1 to 0, -1 to 1,
            0 to -1,           0 to 1,
            1 to -1,  1 to 0,  1 to 1
        )

        while (true) {
            val nextGrid = Array(rows) { grid[it].clone() }
            var changed = false

            for (r in 0 until rows) {
                for (c in 0 until cols) {
                    if (grid[r][c] == '.') continue

                    var occupied = 0
                    for ((dr, dc) in directions) {
                        val nr = r + dr
                        val nc = c + dc
                        if (nr in 0 until rows && nc in 0 until cols && grid[nr][nc] == '#') {
                            occupied++
                        }
                    }

                    if (grid[r][c] == 'L' && occupied == 0) {
                        nextGrid[r][c] = '#'
                        changed = true
                    } else if (grid[r][c] == '#' && occupied >= 4) {
                        nextGrid[r][c] = 'L'
                        changed = true
                    }
                }
            }

            if (!changed) break
            grid = nextGrid
        }

        return grid.sumOf { row -> row.count { it == '#' } }
    }

    fun part2(input: List<String>): Int {
        var grid = input.map { it.toCharArray() }.toTypedArray()
        val rows = grid.size
        val cols = grid[0].size

        while (true) {
            val nextGrid = Array(rows) { grid[it].clone() }
            var changed = false

            for (r in 0 until rows) {
                for (c in 0 until cols) {
                    if (grid[r][c] == '.') continue

                    var seenOccupied = 0
                    // right
                    for (col in c+1 until cols) {
                        if (grid[r][col] != '.') {
                            if (grid[r][col] == '#') seenOccupied++
                            break
                        }
                    }

                    //left
                    for (col in c-1 downTo 0) {
                        if (grid[r][col] != '.') {
                            if (grid[r][col] == '#') seenOccupied++
                            break
                        }
                    }

                    // down
                    for (row in r+1 until rows) {
                        if (grid[row][c] != '.') {
                            if (grid[row][c] == '#') seenOccupied++
                            break
                        }
                    }

                    // up
                    for (row in r-1 downTo 0) {
                        if (grid[row][c] != '.') {
                            if (grid[row][c] == '#') seenOccupied++
                            break
                        }
                    }

                    // up-left
                    var row = r-1
                    var col = c-1
                    while (row >= 0 && col >= 0) {
                        if (grid[row][col] != '.') {
                            if (grid[row][col] == '#') seenOccupied++
                            break
                        }
                        row--
                        col--
                    }

                    // up-right
                    row = r-1
                    col = c+1
                    while(row >= 0 && col < cols) {
                        if (grid[row][col] != '.') {
                            if (grid[row][col] == '#') seenOccupied++
                            break
                        }
                        row--
                        col++
                    }

                    // down-right
                    row = r+1
                    col = c+1
                    while (row < rows && col < cols) {
                        if (grid[row][col] != '.') {
                            if (grid[row][col] == '#') seenOccupied++
                            break
                        }
                        row++
                        col++
                    }

                    // down-left
                    row = r+1
                    col = c-1
                    while (row < rows && col >= 0) {
                        if (grid[row][col] != '.') {
                            if (grid[row][col] == '#') seenOccupied++
                            break
                        }
                        row++
                        col--
                    }

                    if (grid[r][c] == 'L' && seenOccupied == 0) {
                        nextGrid[r][c] = '#'
                        changed = true
                    } else if (grid[r][c] == '#' && seenOccupied >= 5) {
                        nextGrid[r][c] = 'L'
                        changed = true
                    }
                }
            }

            if (!changed) break
            grid = nextGrid
        }

        return grid.sumOf { row -> row.count { it == '#' } }
    }

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}
