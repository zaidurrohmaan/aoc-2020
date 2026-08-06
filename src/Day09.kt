fun main() {
    fun part1(input: List<Long>): Long {
        val count = mutableMapOf<Long, Int>()
        for (i in input.indices) {
            for (j in i + 1 until input.size) {
                val sum = input[i] + input[j]
                count[sum] = count.getOrDefault(sum, 0) + 1
            }
        }

        for (i in 25 until input.size) {
            val current = input[i]
            if (count[current] == null) return current

            val idxToRemove = i - 25
            val toRemove = input[idxToRemove]
            for (j in idxToRemove until i) {
                val sumToRemove = input[j] + toRemove
                count[sumToRemove] = count.getOrDefault(sumToRemove, 0) - 1
                val sumToAdd = input[j] + current
                count[sumToAdd] = count.getOrDefault(sumToAdd, 0) + 1
            }
        }

        return input.last()
    }

    fun part2(input: List<Long>): Long {
        val indexOfPrefixSum = mutableMapOf<Long, Int>()
        indexOfPrefixSum[0L] = -1

        var currentSum = 0L
        val targetSum = 104054607
        for (i in input.indices) {
            currentSum += input[i]
            indexOfPrefixSum[currentSum] = i
            if (currentSum >= targetSum) {
                val toReduce = currentSum - targetSum
                if (indexOfPrefixSum.containsKey(toReduce)) {
                    val start = indexOfPrefixSum[toReduce]!! + 1
                    if (start == i) continue
                    val range = input.subList(start, i + 1)
                    return range.min()!! + range.max()!!
                }
            }
        }

        return 0L
    }

    val input = readInput("Day09").map { it.toLong() }
    part1(input).println()
    part2(input).println()
}
