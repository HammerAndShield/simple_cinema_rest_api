package cinema.controllers

import cinema.exceptions.SeatOutOfBoundsException
import cinema.exceptions.TicketAlreadyPurchasedException
import cinema.exceptions.WrongPasswordException
import cinema.models.*
import com.fasterxml.jackson.annotation.ObjectIdGenerators.UUIDGenerator
import org.apache.el.parser.Token
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@RestController
class SeatsController {

    val cinema : CinemaRoomModel = generateEmptyCinema()
    val purchasedSeats = ConcurrentHashMap<UUID, SeatsModel>()

    private final fun generateEmptyCinema(): CinemaRoomModel {
        val rows = 9
        val columns = 9
        val seats = ConcurrentHashMap<UUID, SeatsModel>()

        for (i in 1..rows){
            for (j in 1..columns){
                val seatUUID = UUID.randomUUID()
                seats[seatUUID] = SeatsModel(row = i, column = j)
            }
        }

        return CinemaRoomModel(
            totalRows = rows,
            totalColumns = columns,
            availableSeats = seats
        )
    }

    @GetMapping(path = ["/seats"])
    fun getSeats(): ResponseEntity<Any> {
        val response = mapOf(
            "total_rows" to cinema.totalRows,
            "total_columns" to cinema.totalColumns,
            "available_seats" to cinema.availableSeats.values.toList().sortedWith(compareBy({ it.row }, { it.column }))
        )
        return ResponseEntity.ok(response)
    }

    @PostMapping(path = ["/purchase"])
    fun purchaseSeat(
        @RequestBody requestedSeat: SeatsModel
    ): ResponseEntity<Any> {

        val seatEntry = cinema.availableSeats.entries.find {
            it.value.row == requestedSeat.row && it.value.column == requestedSeat.column
        }

        val isGoodRequest = requestedSeat.row in 1..cinema.totalRows &&
                            requestedSeat.column in 1..cinema.totalColumns

        if (!isGoodRequest) {
            throw SeatOutOfBoundsException("The number of a row or a column is out of bounds!")
        }

        if (seatEntry != null && !purchasedSeats.containsKey(seatEntry.key)) {
            val token = seatEntry.key
            purchasedSeats[token] = seatEntry.value

            val response = PurchaseResponse(
                token = seatEntry.key,
                ticket = seatEntry.value
            )

            return ResponseEntity.ok(response)
        } else {
            throw TicketAlreadyPurchasedException("The ticket has been already purchased!")
        }
    }

    @PostMapping(path = ["/return"])
    fun returnTicket(
        @RequestBody request: TokenRequest
    ): ResponseEntity<Any> {
        val tokenU = try {
            UUID.fromString(request.token)
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.badRequest().body("Invalid UUID Format")
        }

        val seatIsReturnable = tokenU in purchasedSeats.keys
        val returnedSeat = purchasedSeats[tokenU]

        if (seatIsReturnable && returnedSeat != null) {
            val response = mapOf(
                "returned_ticket" to returnedSeat
            )
            purchasedSeats.remove(tokenU)
            return ResponseEntity.ok(response)
        } else {
            val errorResponse = mapOf("error" to "Wrong token!")
            return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping(path = ["/stats"])
    fun getStats(
        @RequestParam(required = false) password: String?
    ): ResponseEntity<Any> {
        if (password != "super_secret" || password.isNullOrEmpty()) {
            throw WrongPasswordException("The password is wrong!")
        } else {
            val body = processStats()

            return ResponseEntity(body, HttpStatus.OK)
        }
    }

    fun processStats() : StatsReponse {
        var currentIncome = 0

        purchasedSeats.values.forEach {
            currentIncome += it.price
        }

        val numberOfPurchasedSeats = purchasedSeats.values.size
        val numberOfAvailableSeats = cinema.availableSeats.size - numberOfPurchasedSeats

        return StatsReponse(
            availableSeats = numberOfAvailableSeats,
            numberOfPurchasedTickets = numberOfPurchasedSeats,
            currentIncome = currentIncome
        )
    }

}