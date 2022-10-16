package com.santosystem.financial.balancing.exception

class BusinessError {

    companion object {
        @Throws(BusinessException::class)
        fun required(msg: String) {
            throw BusinessException("$msg required")
        }

        @Throws(BusinessException::class)
        fun notExisting(msg: String) {
            throw BusinessException("$msg not existing")
        }
    }

}
