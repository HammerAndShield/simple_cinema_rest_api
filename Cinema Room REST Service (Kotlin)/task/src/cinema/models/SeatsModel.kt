package cinema.models

data class SeatsModel(
    val row: Int,
    val column: Int,
    val price: Int = if (row <= 4) {10} else {8}
)
