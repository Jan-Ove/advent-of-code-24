import kotlin.math.abs

fun main() {
    fun part1(input: List<Pair<Int, Int>>): Int {
        val firstList = input.map { it.first }.sorted()
        val secondList = input.map { it.second }.sorted()

        return firstList.zip(secondList)
            .sumOf { abs(it.first - it.second) }
    }

    fun part2(input: List<Pair<Int, Int>>): Int {
        val firstList = input.map { it.first }
        val secondList = input.map { it.second }.groupingBy { it }.eachCount()

        return firstList.sumOf { it * (secondList[it] ?: 0) }
    }

    val input = readInput("Day01")
        .map { it.split("   ").map { it.trim().toInt() } }
        .map { Pair(it[0], it[1]) }
    part1(input).println()
    part2(input).println()
}
