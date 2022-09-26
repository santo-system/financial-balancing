package com.santosystem.financial.balancing.exceptions

class Error {
    fun required(msg: String) {
        throw BusinessException("$msg é obrigatório.")
    }

    fun notExist(msg: String) {
        throw BusinessException("$msg é inexistente.")
    }
}
