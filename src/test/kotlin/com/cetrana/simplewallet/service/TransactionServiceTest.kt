package com.cetrana.simplewallet.service

import com.cetrana.simplewallet.model.Transaction
import com.cetrana.simplewallet.repository.TransactionRepository
import com.cetrana.simplewallet.repository.entity.TransactionEntity
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.ZoneOffset


internal class TransactionServiceTest {

    private val repository: TransactionRepository = mockk()
    private val transactionService: TransactionService = TransactionService(repository)

    @Test
    fun givenEntities_whenFindTransactions_thenReturnTransactionList() {
        every { repository.findAll() } returns listOf(
            TransactionEntity(LocalDateTime.of(2022, 1, 1, 9, 10, 15).toInstant(ZoneOffset.UTC), 125.0),
            TransactionEntity(LocalDateTime.of(2022, 1, 1, 10, 10, 15).toInstant(ZoneOffset.UTC), 50.0),
            TransactionEntity(LocalDateTime.of(2022, 1, 1, 10, 15, 15).toInstant(ZoneOffset.UTC), 15.5),
        )

        val expected = listOf(
            Transaction(LocalDateTime.of(2022, 1, 1, 9, 10, 15).toInstant(ZoneOffset.UTC), 125.0),
            Transaction(LocalDateTime.of(2022, 1, 1, 10, 10, 15).toInstant(ZoneOffset.UTC), 50.0),
            Transaction(LocalDateTime.of(2022, 1, 1, 10, 15, 15).toInstant(ZoneOffset.UTC), 15.5),
        )

        assertEquals(expected, transactionService.findTransactions())
    }

    @Test
    fun givenTransaction_whenAddTransaction_thenSaveEntity() {
        val request = TransactionEntity(LocalDateTime.of(2022, 1, 1, 9, 10, 15).toInstant(ZoneOffset.UTC), 125.0)
        every { repository.save(any()) } returnsArgument (0)

        transactionService.addTransaction(request)
        verify { repository.save(any()) }
    }

    @Test
    fun givenEntities_wheGetHourlyReport_thenReturnTransactionListAsReport() {
        every { repository.findByDateTimeBetween(any(), any()) } returns listOf(
            TransactionEntity(LocalDateTime.of(2022, 1, 1, 9, 10, 15).toInstant(ZoneOffset.UTC), 125.0),
            TransactionEntity(LocalDateTime.of(2022, 1, 1, 9, 12, 15).toInstant(ZoneOffset.UTC), 5.0),
            TransactionEntity(LocalDateTime.of(2022, 1, 1, 10, 10, 15).toInstant(ZoneOffset.UTC), 50.0),
            TransactionEntity(LocalDateTime.of(2022, 1, 1, 10, 15, 15).toInstant(ZoneOffset.UTC), 15.5),
            TransactionEntity(LocalDateTime.of(2022, 1, 1, 10, 18, 15).toInstant(ZoneOffset.UTC), 1.3),
            TransactionEntity(LocalDateTime.of(2022, 1, 1, 12, 18, 15).toInstant(ZoneOffset.UTC), 0.5),
        )

        val expected = listOf(
            Transaction(LocalDateTime.of(2022, 1, 1, 9, 0).toInstant(ZoneOffset.UTC), 130.0),
            Transaction(LocalDateTime.of(2022, 1, 1, 10, 0).toInstant(ZoneOffset.UTC), 66.8),
            Transaction(LocalDateTime.of(2022, 1, 1, 12, 0).toInstant(ZoneOffset.UTC), 0.5),
        )

        assertEquals(
            expected, transactionService.getHourlyReport(
                LocalDateTime.of(2022, 1, 1, 9, 0).toInstant(ZoneOffset.UTC),
                LocalDateTime.of(2022, 1, 1, 12, 0).toInstant(ZoneOffset.UTC),
            )
        )
    }

}