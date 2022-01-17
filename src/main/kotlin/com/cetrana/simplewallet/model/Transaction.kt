package com.cetrana.simplewallet.model

import com.cetrana.simplewallet.repository.entity.TransactionEntity
import java.time.Instant
import javax.validation.constraints.NotNull

data class Transaction(
    @field:NotNull
    val dateTime: Instant? = null,
    @field:NotNull
    val amount: Double? = null,
) {
    fun toEntity(): TransactionEntity = TransactionEntity(dateTime!!, amount!!)
}

