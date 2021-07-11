package com.leom.moneylog.data

import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun save(value: Double)
    fun getTransactions(): Flow<List<Transaction>>
}
