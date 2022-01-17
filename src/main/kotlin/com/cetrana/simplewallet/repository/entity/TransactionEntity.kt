package com.cetrana.simplewallet.repository.entity

import java.time.Instant
import javax.persistence.*


@Entity
@Table(name = "transactions")
data class TransactionEntity(
    var dateTime: Instant,
    val amount: Double,
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int? = null
)

