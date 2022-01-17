package com.cetrana.simplewallet.service

import com.cetrana.simplewallet.model.Transaction
import com.cetrana.simplewallet.repository.TransactionRepository
import com.cetrana.simplewallet.repository.entity.TransactionEntity
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit.HOURS

@Service
class TransactionService(private val repository: TransactionRepository) {

    fun findTransactions(): List<Transaction> =
        repository.findAll().map { it -> Transaction(it.dateTime, it.amount) }.toList()

    fun addTransaction(transaction: TransactionEntity) = repository.save(transaction)

    fun getHourlyReport(startTime: Instant, endTime: Instant): List<Transaction> {
        val transactions =
            repository.findByDateTimeBetween(startTime.truncatedTo(HOURS), endTime.truncatedTo(HOURS).plus(1, HOURS))
        val hours = transactions.map { i -> i.dateTime.truncatedTo(HOURS) }.distinct()
        return hours.map {
            Transaction(
                it,
                transactions.filter { i -> i.dateTime.isAfter(it) && i.dateTime.isBefore(it.plus(1, HOURS)) }
                    .sumOf { i -> i.amount })
        }

    }
}