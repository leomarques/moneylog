package lmm.moneylog.data.invoice

interface GetInvoicesRepository {
    fun getInvoices(): List<Invoice>
}
