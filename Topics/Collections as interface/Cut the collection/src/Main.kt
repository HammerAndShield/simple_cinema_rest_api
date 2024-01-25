




fun main() {
    val originalList = readln().split(" ")
    val originalSet = originalList.toSet()
    val word = readln()

    println(dropElements(originalList, word))
    println(dropElements(originalSet, word))
}

// Write function dropElements() here

fun dropElements(list1: Iterable<String>, word: String): List<String>{
    return list1.filter { it != word }
}