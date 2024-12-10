package lmm.moneylog.data.invoice.repositories

import lmm.moneylog.data.invoice.model.Invoice

interface GetInvoicesRepository {
    fun getInvoices(): List<Invoice>
}
