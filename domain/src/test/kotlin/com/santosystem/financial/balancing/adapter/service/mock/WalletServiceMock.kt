package com.santosystem.financial.balancing.adapter.service.mock

import com.santosystem.financial.balancing.model.Wallet

fun mockWalletList(): List<Wallet> {
    return listOf(
        Wallet(
            name = "fake_name",
            description = "fake_description",
            main = true
        ),
        Wallet(
            name = "fake_name2",
            description = "fake_description2",
            main = false
        )
    )
}

fun mockWallet(): Wallet {
    return Wallet(
        id = 1L,
        name = "fake_name",
        description = "fake_description",
        main = true
    )
}
