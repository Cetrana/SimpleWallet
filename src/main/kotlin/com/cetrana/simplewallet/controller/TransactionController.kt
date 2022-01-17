package com.cetrana.simplewallet.controller

import com.cetrana.simplewallet.model.ReportRequest
import com.cetrana.simplewallet.model.Transaction
import com.cetrana.simplewallet.service.TransactionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class TransactionController(val service: TransactionService) {

    @GetMapping
    fun getRecords(): ResponseEntity<List<Transaction>> = ResponseEntity.ok(service.findTransactions())

    @PostMapping
    fun addRecord(@Valid @RequestBody transaction: Transaction) =
        ResponseEntity.ok(service.addTransaction(transaction.toEntity()))

    @PostMapping("report")
    fun getHourlyReport(@Valid @RequestBody request: ReportRequest) =
        ResponseEntity.ok(service.getHourlyReport(request.startDateTime!!, request.endDateTime!!))

}