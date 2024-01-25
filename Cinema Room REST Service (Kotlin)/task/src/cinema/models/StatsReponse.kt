package cinema.models

import com.fasterxml.jackson.annotation.JsonProperty

data class StatsReponse(
    @JsonProperty("current_income")
    val currentIncome: Int,

    @JsonProperty("number_of_available_seats")
    val availableSeats: Int,

    @JsonProperty("number_of_purchased_tickets")
    val numberOfPurchasedTickets: Int,

)
