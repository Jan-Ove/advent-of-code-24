fun main() {
    fun part1(input: String): Int {
        return Regex("mul\\((\\d+),(\\d+)\\)")
            .findAll(input)
            .sumOf {
                it.groupValues[1].toInt() * it.groupValues[2].toInt()
            }

    }

    fun part2(input: String): Int {
        return Regex("(mul\\((\\d+),(\\d+)\\))|(don't\\(\\))|(do\\(\\))")
            .findAll(input)
            .fold(true to 0) { (isActive, sum): Pair<Boolean, Int>, matchResult: MatchResult ->
                if (matchResult.groupValues[4].isNotBlank()) {
                    false to sum
                } else if (matchResult.groupValues[5].isNotBlank()) {
                    true to sum
                } else if (isActive) {
                    true to (sum + (matchResult.groupValues[2].toInt() * matchResult.groupValues[3].toInt()))
                } else {
                    false to sum
                }
            }.second
    }

    val input = readInput("Day03").joinToString(separator = "")
    part1(input).println()
    part2(input).println()
}
