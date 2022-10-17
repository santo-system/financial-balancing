package com.santosystem.financial.balancing.adapter.service

import com.santosystem.financial.balancing.adapter.service.mock.mockWallet
import com.santosystem.financial.balancing.adapter.service.mock.mockWalletList
import com.santosystem.financial.balancing.exception.BusinessException
import com.santosystem.financial.balancing.exception.BusinessNotFoundException
import com.santosystem.financial.balancing.exception.InfraUnexpectedException
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
    private lateinit var walletRepository: WalletRepository

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
        every { walletRepository.findAll() } returns emptyList()

        // when
        val result = walletService.findAll()

        // then
        verify(exactly = 1) { walletRepository.findAll() }
        verify(exactly = 1) { walletService.findAll() }
        assertEquals(emptyList<Wallet>(), result)
    }

    @Test
    fun findAll() {
        // given
        every { walletRepository.findAll() } returns mockWalletList()

        // when
        val result = walletService.findAll()

        // then
        verify(exactly = 1) { walletRepository.findAll() }
        verify(exactly = 1) { walletService.findAll() }
        assertEquals(2, result.size)
    }

    @Test
    fun findByIdBusinessNotFoundException() {
        // given
        every { walletRepository.findById(10L) } returns null

        // when
        val exception = Assertions.assertThrows(BusinessNotFoundException::class.java) {
            walletService.findById(10L)
        }

        // then
        verify(exactly = 1) { walletRepository.findById(10L) }
        verify(exactly = 1) { walletService.findById(10L) }
        assertEquals("Wallet not found", exception.message)
    }

    @Test
    fun findByIdBusinessException() {
        // given
        every { walletRepository.findById(any()) } returns null

        // when
        val exception = Assertions.assertThrows(BusinessNotFoundException::class.java) {
            walletService.findById(0L)
        }

        // then
        verify(exactly = 1) { walletRepository.findById(0L) }
        verify(exactly = 1) { walletService.findById(0L) }
        assertEquals("Wallet not found", exception.message)
    }

    @Test
    fun findById() {
        // given
        every { walletRepository.findById(1L) } returns mockWallet()

        // when
        val result = walletService.findById(1L)

        // then
        verify(exactly = 1) { walletRepository.findById(1L) }
        verify(exactly = 1) { walletService.findById(1L) }
        assertEquals(1L, result.id)
    }

    @Test
    fun save() {
        // given
        val mockWallet = mockWallet()
        every { walletRepository.save(mockWallet) } returns mockWallet

        // when
        val result = walletService.save(mockWallet)

        // then
        verify(exactly = 1) { walletRepository.save(mockWallet) }
        verify(exactly = 1) { walletService.save(mockWallet) }
        assertEquals(1L, result.id)
    }

    @Test
    fun update() {
        // given
        val mockWallet = mockWallet()
        every { walletRepository.save(mockWallet) } returns mockWallet

        // when
        val result = walletService.update(mockWallet)

        // then
        verify(exactly = 1) { walletRepository.save(mockWallet) }
        verify(exactly = 1) { walletService.update(mockWallet) }
        assertEquals(1L, result.id)
    }

    @Test
    fun delete() {
        // given
        every { walletRepository.delete(1L) } returns Unit

        // when
        walletService.delete(1L)

        // then
        verify(exactly = 1) { walletRepository.delete(1L) }
        verify(exactly = 1) { walletService.delete(1L) }
    }

    @Test
    fun deleteOnFailure() {
        // given
        every { walletRepository.delete(1L) } throws InfraUnexpectedException("An unexpected error occurred with the Wallet")

        // when
        val exception = Assertions.assertThrows(InfraUnexpectedException::class.java) {
            walletService.delete(1L)
        }

        // then
        verify(exactly = 1) { walletRepository.delete(1L) }
        verify(exactly = 1) { walletService.delete(1L) }
        assertEquals("An unexpected error occurred with the Wallet", exception.message)
    }

    @Test
    fun verifyIdIsNullBusinessException() {
        // given
        every { walletRepository.existsById(0L) } returns false

        // when
        val exception = Assertions.assertThrows(BusinessException::class.java) {
            walletService.existsById(0L)
        }

        // then
        verify(exactly = 0) { walletRepository.existsById(0L) }
        assertEquals("Wallet ID required", exception.message)
    }
}
