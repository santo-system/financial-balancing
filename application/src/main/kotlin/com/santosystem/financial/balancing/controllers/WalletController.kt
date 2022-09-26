package com.santosystem.financial.balancing.controllers

import com.santosystem.financial.balancing.ports.repositories.WalletRepositoryPort
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/wallets")
class WalletController() {

    private lateinit var walletRepositoryPort: WalletRepositoryPort

    fun WalletController(walletRepositoryPort: WalletRepositoryPort) {
        this.walletRepositoryPort = walletRepositoryPort
    }

//    @PostMapping
//    fun createWallet(
//        @Valid @RequestBody wallet: WalletDTO
//    ): ResponseEntity<WalletDTO> {
//        val walletResponse = walletRepositoryPort.save()
//    }

}
