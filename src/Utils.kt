import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
@Suppress("unused")
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)


data class Grid<T>(val entries: List<List<T>>) {

    init {
        require(entries.all { it.size == entries.size })
    }

    val asSequence = sequence {
        entries.forEachIndexed { y, row ->
            row.forEachIndexed { x, t ->
                yield(Position(x, y, t))
            }
        }
    }

    fun followDirection(position: Position<T>, direction: Direction) = sequence {
        var x = position.x
        var y = position.y
        while (true) {
            if (x !in 0..entries.lastIndex || y !in 0..entries.lastIndex) {
                break
            }
            yield(entries[y][x])
            y += direction.y
            x += direction.x
        }
    }


    enum class Direction(val x: Int, val y: Int) {
        Left(-1, 0),
        LeftDown(-1, 1),
        Down(0, 1),
        RightDown(1, 1),
        Right(1, 0),
        RightUp(1, -1),
        Up(0, -1),
        LeftUp(-1, -1)
    }

    data class Position<T>(val x: Int, val y: Int, val value: T)

    companion object {
        operator fun invoke(entries: List<String>) = Grid(entries.map { it.toList() })
    }
}