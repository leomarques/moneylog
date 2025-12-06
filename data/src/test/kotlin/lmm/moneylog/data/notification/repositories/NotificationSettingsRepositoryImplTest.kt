package lmm.moneylog.data.notification.repositories

import android.content.SharedPreferences
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Before
import org.junit.Test

class NotificationSettingsRepositoryImplTest {
    private val sharedPreferences = mockk<SharedPreferences>()
    private val editor = mockk<SharedPreferences.Editor>(relaxed = true)
    private lateinit var repository: NotificationSettingsRepositoryImpl

    @Before
    fun setup() {
        every { sharedPreferences.edit() } returns editor
        every { editor.putInt(any(), any()) } returns editor
        every { editor.remove(any()) } returns editor
        every { editor.apply() } returns Unit

        repository = NotificationSettingsRepositoryImpl(sharedPreferences)
    }

    @Test
    fun `should return credit card id when stored`() {
        every {
            sharedPreferences.getInt("notification_default_credit_card_id", -1)
        } returns 5

        val result = repository.getDefaultCreditCardId()

        assertEquals(5, result)
    }

    @Test
    fun `should return null when no credit card id stored`() {
        every {
            sharedPreferences.getInt("notification_default_credit_card_id", -1)
        } returns -1

        val result = repository.getDefaultCreditCardId()

        assertNull(result)
    }

    @Test
    fun `should save credit card id correctly`() {
        repository.saveDefaultCreditCardId(10)

        verify { editor.putInt("notification_default_credit_card_id", 10) }
        verify { editor.apply() }
    }

    @Test
    fun `should remove credit card id correctly`() {
        repository.removeDefaultCreditCardId()

        verify { editor.remove("notification_default_credit_card_id") }
        verify { editor.apply() }
    }

    @Test
    fun `should handle multiple save operations`() {
        repository.saveDefaultCreditCardId(1)
        repository.saveDefaultCreditCardId(2)

        verify { editor.putInt("notification_default_credit_card_id", 1) }
        verify { editor.putInt("notification_default_credit_card_id", 2) }
        verify(exactly = 2) { editor.apply() }
    }

    @Test
    fun `should return category id when stored`() {
        every {
            sharedPreferences.getInt("notification_default_category_id", -1)
        } returns 3

        val result = repository.getDefaultCategoryId()

        assertEquals(3, result)
    }

    @Test
    fun `should return null when no category id stored`() {
        every {
            sharedPreferences.getInt("notification_default_category_id", -1)
        } returns -1

        val result = repository.getDefaultCategoryId()

        assertNull(result)
    }

    @Test
    fun `should save category id correctly`() {
        repository.saveDefaultCategoryId(7)

        verify { editor.putInt("notification_default_category_id", 7) }
        verify { editor.apply() }
    }

    @Test
    fun `should remove category id correctly`() {
        repository.removeDefaultCategoryId()

        verify { editor.remove("notification_default_category_id") }
        verify { editor.apply() }
    }

    @Test
    fun `should handle multiple category save operations`() {
        repository.saveDefaultCategoryId(1)
        repository.saveDefaultCategoryId(2)

        verify { editor.putInt("notification_default_category_id", 1) }
        verify { editor.putInt("notification_default_category_id", 2) }
        verify(exactly = 2) { editor.apply() }
    }
}
