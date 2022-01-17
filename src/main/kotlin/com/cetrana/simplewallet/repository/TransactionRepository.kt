package com.cetrana.simplewallet.repository

import com.cetrana.simplewallet.repository.entity.TransactionEntity
import org.springframework.data.repository.CrudRepository
import java.time.Instant

interface TransactionRepository : CrudRepository<TransactionEntity, Int> {


    fun findByDateTimeBetween(dateTimeStart: Instant, dateTimeEnd: Instant): List<TransactionEntity>

}