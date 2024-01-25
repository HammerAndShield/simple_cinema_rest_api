package cinema.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class SeatsControllerExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(SeatOutOfBoundsException::class)
    fun handleSeatOutOfBounds(
        e: SeatOutOfBoundsException,
        request: WebRequest
    ): ResponseEntity<Map<String, String>> {

        val body = mapOf("error" to "The number of a row or a column is out of bounds!")

        return ResponseEntity<Map<String, String>>(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(TicketAlreadyPurchasedException::class)
    fun handleTicketAlreadyPurchased(
        e: TicketAlreadyPurchasedException,
        request: WebRequest
    ): ResponseEntity<Map<String, String>> {

        val body = mapOf("error" to "The ticket has been already purchased!")

        return ResponseEntity<Map<String, String>>(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(WrongPasswordException::class)
    fun handleWrongPassword(
        e: WrongPasswordException,
        request: WebRequest
    ): ResponseEntity<Map<String, String>> {

        val body = mapOf("error" to "The password is wrong!")

        return ResponseEntity(body, HttpStatus.UNAUTHORIZED)
    }

}