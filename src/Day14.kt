import kotlin.collections.forEach

fun Long.setBit(bit: Int) = this or (1L shl bit)

fun Long.isBitOn(bit: Int) = (this and (1L shl bit) != 0L)

fun Long.maskWith(mask: String): Long {
    var result = 0L
    for (idx in 35 downTo 0) {
        val bit = 35 - idx
        when (mask[idx]) {
            '1' -> result = result.setBit(bit)
            'X' -> {
                if (this.isBitOn(bit)) {
                    result = result.setBit(bit)
                }
            }
        }
    }
    return result
}

fun Long.maskWithFloating(mask: String): String {
    val result = MutableList(36) {'0'}
    for (idx in 35 downTo 0) {
        val bit = 35 - idx
        if (mask[idx] != '0') {
            result[idx] = mask[idx]
        } else if (this.isBitOn(bit)) {
            result[idx] = '1'
        }
    }

    return result.joinToString("")
}

fun String.addresses(): List<Long> {
    val xIndices = indices.filter { this[it] == 'X' }
    val max = 1L shl xIndices.size
    val addresses = mutableListOf<Long>()
    for (num in 0 until max) {
        val numStr = num.toString(2).padStart(xIndices.size, '0')
        var j = xIndices.size - 1
        var address = 0L
        for (i in this.length - 1 downTo 0) {
            val bit = 35 - i
            when(this[i]) {
                'X' -> {
                    if (numStr[j--] == '1') {
                        address = address.setBit(bit)
                    }
                }
                '1' -> address = address.setBit(bit)
            }
        }
        addresses.add(address)
    }
    return addresses
}

fun main() {
    fun part1(input: List<String>): Long {
        val mem = mutableMapOf<Int, Long>()
        var mask = ""
        for (line in input) {
            if (line[1] == 'e') { // mem
                val address = line.substringAfter("[").substringBefore("]").toInt()
                val value = line.substringAfter("=").trim().toLong()
                mem[address] = value.maskWith(mask)
            } else { // mask
                mask = line.substringAfter(" = ")
            }
        }
        return mem.values.sum()
    }

    fun part2(input: List<String>): Long {
        val mem = mutableMapOf<Long, Long>()
        var mask = ""
        for (line in input) {
            if (line[1] == 'e') { // mem
                val address = line.substringAfter("[").substringBefore("]").toLong()
                val value = line.substringAfter("=").trim().toLong()
                address.maskWithFloating(mask).addresses().forEach { mem[it] = value }
            } else { // mask
                mask = line.substringAfter(" = ")
            }
        }
        return mem.values.sum()
    }

    val input = readInput("Day14")
    part1(input).println()
    part2(input).println()
}
