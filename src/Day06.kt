fun main() {
    fun part1(
        map: Grid<MapTile>,
        startPosition: Grid.Position<MapTile> = map.find { it.isStartPosition } ?: error("No start position")
    ): Int {
        var currentPosition = startPosition
        var currentDirection = Grid.Direction.Up
        val visitedPlaces = mutableMapOf<Grid.Position<MapTile>, Set<Grid.Direction>>()
        loop@ while (true) {
            map.followDirectionAndPosition(currentPosition, currentDirection)
                .windowed(2, partialWindows = true)
                .forEach {
                    if (it.size == 1) {
                        val directions = visitedPlaces.getOrDefault(it.first(), emptySet())
                        visitedPlaces[it.first()] = directions + currentDirection
                        break@loop
                    }

                    val (current, next) = it
                    val directions = visitedPlaces.getOrDefault(it.first(), emptySet())
                    if (directions.contains(currentDirection)) {
                        return -1 // we've been already here
                    }
                    visitedPlaces[current] = directions + currentDirection

                    if (next.value.isObstacle) {
                        currentDirection = currentDirection.turn90()
                        currentPosition = current
                        continue@loop
                    }
                }
        }

        return visitedPlaces.count()
    }

    fun part2(input: Grid<MapTile>): Int {
        return input.asSequence.filter { !it.value.isObstacle || it.value.isStartPosition }
            .filter {
                part1(input.update(it.copy(value = it.value.copy(isObstacle = true)))) == -1
            }.count()
    }

    val input = readInput("Day06")
    //val input = SAMPLE.split("\n")

    val grid = Grid(input).map {
        when (it) {
            '.' -> MapTile()
            '#' -> MapTile(isObstacle = true)
            '^' -> MapTile(isStartPosition = true)
            else -> error("Unknown character $it")
        }
    }

    part1(grid).println()
    part2(grid).println()
}

data class MapTile(
    val isObstacle: Boolean = false,
    val isStartPosition: Boolean = false
)
