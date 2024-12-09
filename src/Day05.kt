import kotlin.math.floor

fun main() {
    val input = readInput("Day05").filter { it.isNotBlank() }

    val (rawOrderings, rawJobs) = input.partition { it.contains("|") }
    val orderings = rawOrderings.map { it.split("|").map { it.toInt() } }.map { it[0] to it[1] }
    val jobs = rawJobs.map { it.split(",").map { it.toInt() } }

    fun getComparator(job: List<Int>): java.util.Comparator<Int> {
        val restrictions = orderings.filter { job.contains(it.first) && job.contains(it.second) }
        val comparator = Comparator<Int> { o1, o2 ->
            if (restrictions.any { it.second == o1 && it.first == o2 }) {
                1
            } else if (restrictions.any { it.second == o2 && it.first == o1 }) {
                -1
            } else {
                0
            }
        }
        return comparator
    }

    val (orderedJobs, unorderedJobs) = jobs.partition { job ->
        job == job.sortedWith(getComparator(job))
    }

    fun part1(): Int {
        return orderedJobs.sumOf {
            it[floor(it.size / 2f).toInt()]
        }
    }

    fun part2(): Int {
        return unorderedJobs.map { job ->
            job.sortedWith(getComparator(job))
        }.sumOf {
            it[floor(it.size / 2f).toInt()]
        }
    }

    part1().println()
    part2().println()
}
