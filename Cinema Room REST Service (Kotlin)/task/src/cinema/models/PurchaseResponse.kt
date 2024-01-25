package cinema.models

import java.util.*

data class PurchaseResponse(
    val token: UUID,
    val ticket: SeatsModel
)
