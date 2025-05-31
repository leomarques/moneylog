package lmm.moneylog.data.notification.repositories

import android.content.SharedPreferences
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class NotificationTransactionRepositoryImplTest {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var repository: NotificationTransactionRepositoryImpl

    @Before
    fun setup() {
        sharedPreferences = mockk()
        editor = mockk(relaxed = true)
        repository = NotificationTransactionRepositoryImpl(sharedPreferences)

        every { sharedPreferences.edit() } returns editor
        every { editor.putLong(any(), any()) } returns editor
        every { editor.remove(any()) } returns editor
        every { editor.apply() } returns Unit
    }

    @Test
    fun `storeTransactionId should store transaction ID with correct key`() =
        runTest {
            val notificationId = 12345
            val transactionId = 67890L

            repository.storeTransactionId(notificationId, transactionId)

            verify { editor.putLong("notification_transaction_12345", 67890L) }
            verify { editor.apply() }
        }

    @Test
    fun `getTransactionId should return stored transaction ID`() =
        runTest {
            val notificationId = 12345
            val expectedTransactionId = 67890L

            every {
                sharedPreferences.getLong("notification_transaction_12345", -1L)
            } returns expectedTransactionId

            val result = repository.getTransactionId(notificationId)

            assertEquals(expectedTransactionId, result)
        }

    @Test
    fun `getTransactionId should return null when transaction ID not found`() =
        runTest {
            val notificationId = 12345

            every {
                sharedPreferences.getLong("notification_transaction_12345", -1L)
            } returns -1L

            val result = repository.getTransactionId(notificationId)

            assertNull(result)
        }

    @Test
    fun `removeTransactionId should remove transaction ID with correct key`() =
        runTest {
            val notificationId = 12345

            repository.removeTransactionId(notificationId)

            verify { editor.remove("notification_transaction_12345") }
            verify { editor.apply() }
        }

    @Test
    fun `clearAllTransactionIds should remove all notification transaction keys`() =
        runTest {
            val allKeys =
                mapOf(
                    "notification_transaction_123" to 1L,
                    "notification_transaction_456" to 2L,
                    "other_key" to 3L,
                    "notification_transaction_789" to 4L
                )

            every { sharedPreferences.all } returns allKeys

            repository.clearAllTransactionIds()

            verify { editor.remove("notification_transaction_123") }
            verify { editor.remove("notification_transaction_456") }
            verify { editor.remove("notification_transaction_789") }
            verify(exactly = 0) { editor.remove("other_key") }
            verify { editor.apply() }
        }

    @Test
    fun `clearAllTransactionIds should handle empty preferences`() =
        runTest {
            every { sharedPreferences.all } returns emptyMap()

            repository.clearAllTransactionIds()

            verify(exactly = 0) { editor.remove(any()) }
            verify { editor.apply() }
        }

    @Test
    fun `clearAllTransactionIds should only remove notification transaction keys`() =
        runTest {
            val allKeys =
                mapOf(
                    "some_other_key" to 1L,
                    "app_setting" to 2L,
                    "user_preference" to 3L
                )

            every { sharedPreferences.all } returns allKeys

            repository.clearAllTransactionIds()

            verify(exactly = 0) { editor.remove(any()) }
            verify { editor.apply() }
        }

    @Test
    fun `storeTransactionId should handle large transaction IDs`() =
        runTest {
            val notificationId = 999999
            val transactionId = Long.MAX_VALUE

            repository.storeTransactionId(notificationId, transactionId)

            verify { editor.putLong("notification_transaction_999999", Long.MAX_VALUE) }
            verify { editor.apply() }
        }

    @Test
    fun `getTransactionId should handle large transaction IDs`() =
        runTest {
            val notificationId = 999999
            val expectedTransactionId = Long.MAX_VALUE

            every {
                sharedPreferences.getLong("notification_transaction_999999", -1L)
            } returns expectedTransactionId

            val result = repository.getTransactionId(notificationId)

            assertEquals(expectedTransactionId, result)
        }
}
