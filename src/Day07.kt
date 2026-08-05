
fun main() {
    fun part1(input: List<String>): Int {
        val containedBy = mutableMapOf<String, MutableList<String>>()

        for (line in input) {
            val (outer, contents) = line.split(" bags contain ")
            if (contents == "no other bags.") continue

            contents.split(", ").forEach { bag ->
                val inner = bag.substringAfter(" ").substringBefore(" bag")
                containedBy.getOrPut(inner) { mutableListOf() }.add(outer)
            }
        }

        val queue = ArrayDeque(listOf("shiny gold"))
        val visited = mutableSetOf<String>()

        while(!queue.isEmpty()) {
            val current = queue.removeFirst()
            containedBy[current]?.forEach { parent ->
                if (visited.add(parent)) {
                    queue.add(parent)
                }
            }
        }

        return visited.size
    }

    fun part2(input: List<String>): Int {
        val contentsOf = mutableMapOf<String, MutableList<Pair<String, Int>>>()

        for (line in input) {
            val (outer, contents) = line.split(" bags contain ")
            if (contents == "no other bags.") {
                contentsOf.put(outer, mutableListOf())
                continue
            }

            contents.split(", ").forEach {bag ->
                val inner = bag.substringAfter(" ").substringBefore(" bag")
                val amount = bag.replace(" " + inner + bag.substringAfter(inner), "").trim().toInt()
                contentsOf.getOrPut(outer) { mutableListOf() }.add(Pair(inner, amount))
            }
        }

        fun numOfChild(bag: String): Int {
            if (contentsOf[bag]!!.isEmpty()) return 0

            var num = 0
            for (child in contentsOf[bag]!!) {
                num += child.second
                num += child.second * numOfChild(child.first)
            }

            return num
        }

        return numOfChild("shiny gold")
    }

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
