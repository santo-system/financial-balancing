package com.santosystem.financial.balancing.adapter.service

import com.santosystem.financial.balancing.adapter.service.mock.mockWallet
import com.santosystem.financial.balancing.adapter.service.mock.mockWalletList
import com.santosystem.financial.balancing.exception.BusinessException
import com.santosystem.financial.balancing.exception.BusinessNotFoundException
import com.santosystem.financial.balancing.model.Wallet
import com.santosystem.financial.balancing.port.repository.WalletRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class WalletServiceImplTest {

    @MockK
    private lateinit var repository: WalletRepository

    @InjectMockKs
    private lateinit var walletService: WalletServiceImpl

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun findAllEmptyList() {
        // given
        every { repository.findAll() } returns emptyList()

        // when
        val result = walletService.findAll()

        // then
        verify(exactly = 1) { repository.findAll() }
        verify(exactly = 1) { walletService.findAll() }
        assertEquals(emptyList<Wallet>(), result)
    }

    @Test
    fun findAll() {
        // given
        every { repository.findAll() } returns mockWalletList()

        // when
        val result = walletService.findAll()

        // then
        verify(exactly = 1) { repository.findAll() }
        verify(exactly = 1) { walletService.findAll() }
        assertEquals(2, result.size)
    }

    @Test
    fun findByIdBusinessNotFoundException() {
        // given
        every { repository.findById(10L) } returns null

        // when
        val exception = Assertions.assertThrows(BusinessNotFoundException::class.java) {
            walletService.findById(10L)
        }

        // then
        verify(exactly = 1) { repository.findById(10L) }
        verify(exactly = 1) { walletService.findById(10L) }
        assertEquals("Wallet not found.", exception.message)
    }

    @Test
    fun findByIdBusinessException() {
        // given
        every { repository.findById(any()) } returns null

        // when
        val exception = Assertions.assertThrows(BusinessException::class.java) {
            walletService.findById(null)
        }

        // then
        verify(exactly = 0) { repository.findById(any()) }
        assertEquals("Wallet ID required.", exception.message)
    }

    @Test
    fun findById() {
        // given
        every { repository.findById(1L) } returns mockWallet()

        // when
        val result = walletService.findById(1L)

        // then
        verify(exactly = 1) { repository.findById(1L) }
        verify(exactly = 1) { walletService.findById(1L) }
        assertEquals(1L, result.id)
    }

    @Test
    fun save() {
        // given
        val mockWallet = mockWallet()
        every { repository.save(mockWallet) } returns mockWallet

        // when
        val result = walletService.save(mockWallet)

        // then
        verify(exactly = 1) { repository.save(mockWallet) }
        verify(exactly = 1) { walletService.save(mockWallet) }
        assertEquals(1L, result.id)
    }

    @Test
    fun update() {
    }

    @Test
    fun delete() {
    }
}
