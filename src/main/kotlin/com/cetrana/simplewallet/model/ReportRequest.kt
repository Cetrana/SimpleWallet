package com.cetrana.simplewallet.model

import java.time.Instant
import javax.validation.constraints.NotNull

data class ReportRequest(
    @field:NotNull(message = "startDateTime cannot be null")
    val startDateTime: Instant? = null,
    @field:NotNull(message = "endDateTime cannot be null")
    val endDateTime: Instant? = null
) {


}