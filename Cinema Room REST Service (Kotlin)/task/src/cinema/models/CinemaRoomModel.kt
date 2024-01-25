package cinema.models

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

data class CinemaRoomModel(
    @JsonProperty("total_rows")
    var totalRows: Int,

    @JsonProperty("total_columns")
    var totalColumns: Int,

    @JsonProperty("available_seats")
    var availableSeats: ConcurrentHashMap<UUID, SeatsModel>
)
