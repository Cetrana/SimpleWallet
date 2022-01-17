package com.cetrana.simplewallet.controller

import com.cetrana.simplewallet.model.Transaction
import com.cetrana.simplewallet.service.TransactionService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime
import java.time.ZoneOffset

@WebMvcTest
internal class TransactionControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    lateinit var transactionService: TransactionService

    @Test
    fun whenGetRecords_thenReturnTransactionList() {
        every { transactionService.findTransactions() } returns emptyList()
        mockMvc.perform(get("/"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun givenTransaction_whenAddRecord_returnSuccess() {
        every { transactionService.addTransaction(any()) } returnsArgument (0)
        val request = "{\"dateTime\":\"2022-01-15T09:42:31.869780Z\",\"amount\":125.25}"
        mockMvc.perform(post("/").content(request).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
    }

    @Test
    fun givenInvalidTransaction_whenAddRecord_returnBadRequest() {
        every { transactionService.addTransaction(any()) } returnsArgument (0)
        val request = "{\"dateTime\":\"2022-01-15T09:42:31.869780Z\"}"
        mockMvc.perform(post("/").content(request).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun givenReportRequest_whenGetHourlyReport_thenReturnReport() {
        every { transactionService.getHourlyReport(any(), any()) } returns listOf(
            Transaction(LocalDateTime.of(2022, 1, 1, 9, 0).toInstant(ZoneOffset.UTC), 130.0),
            Transaction(LocalDateTime.of(2022, 1, 1, 10, 0).toInstant(ZoneOffset.UTC), 66.8),
            Transaction(LocalDateTime.of(2022, 1, 1, 12, 0).toInstant(ZoneOffset.UTC), 0.5),
        )
        val request =
            "{\"startDateTime\": \"2022-01-01T09:15:01+00:00\" ,\"endDateTime\": \"2022-01-01T12:16:01+00:00\"}"
        mockMvc.perform(post("/report").content(request).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

    }

    @Test
    fun givenInvalidRequest_whenGetHourlyReport_thenReturnBadRequest() {
        val request =
            "{\"startDateTime\": \"2022-01-01T09:15:01+00:00\" ,\"endDateTime\": \"2022-01-01T12:16:0+00:00\"}"
        mockMvc.perform(post("/report").content(request).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest)
    }
}

