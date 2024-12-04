fun main() {
    fun part1(input: Grid<Char>): Int {
        return input.asSequence
            .filter { it.value == 'X' }
            .flatMap { pos ->
                Grid.Direction.entries.map { input.followDirection(pos, it) }
            }
            .filter {
                it.take(4).joinToString("").startsWith("XMAS")
            }
            .count()
    }

    fun part2(input: Grid<Char>): Int {
        return input.asSequence
            .filter { it.value == 'A' }
            .filter { pos ->
                val topLeft = input.followDirection(pos, Grid.Direction.LeftUp).take(2).last()
                val rightDown = input.followDirection(pos, Grid.Direction.RightDown).take(2).last()
                val string = "${topLeft}A$rightDown"
                if (string == "SAM" || string.reversed() == "SAM") {
                    val topRight = input.followDirection(pos, Grid.Direction.RightUp).take(2).last()
                    val leftDown = input.followDirection(pos, Grid.Direction.LeftDown).take(2).last()
                    val otherString = "${topRight}A$leftDown"
                    otherString == "SAM" || otherString.reversed() == "SAM"
                } else {
                    false
                }
            }
            .count()
    }


    val input = readInput("Day04")
    val grid = Grid(input)
    part1(grid).println()
    part2(grid).println()
}
