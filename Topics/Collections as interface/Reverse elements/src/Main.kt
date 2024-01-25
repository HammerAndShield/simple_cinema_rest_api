









fun main() {
    val originalList = readln().split(" ").map { it }.toMutableList()
    val originalSet = originalList.toMutableSet()

    println(addElements(originalList))
    println(addElements(originalSet))
}

// Write function addElements() here

fun addElements(list: MutableIterable<String>): List<Any>{
    return list.reversed()
}