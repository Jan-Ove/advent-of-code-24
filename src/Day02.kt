import kotlin.math.absoluteValue

fun main() {
    fun isReportCorrect(report: List<Int>): Pair<Boolean, List<Int>> {
        val incrementing = report.first() < report[1]
        return report
            .windowed(2) {
                if (incrementing && it.first() >= it[1]) {
                    0
                } else if (!incrementing && it.first() <= it[1]) {
                    0
                } else {
                    it.first() - it[1]
                }
            }
            .all {
                it.absoluteValue in 1..3
            } to report
    }

    fun getCorrectLevels(input: List<List<Int>>, transform: (List<Int>) -> List<Int> = { it }) = input
        .map(transform)
        .map { report ->
            isReportCorrect(report)
        }

    fun part1(input: List<List<Int>>): Int {
        return getCorrectLevels(input).filter { it.first }.size
    }


    fun part2(input: List<List<Int>>): Int {
        val levels = getCorrectLevels(input)
        return levels.filter { (isValid, report) ->
            if (isValid) {
                true
            } else if (report.indices.any { isReportCorrect(report.filterIndexed { index, _ -> it != index }).first }) {
                true
            } else {
                false
            }
        }.size
    }

    val input = readInput("Day02")
        .map { it.split(" ").map { it.trim().toInt() } }
    part1(input).println()
    part2(input).println()
}
