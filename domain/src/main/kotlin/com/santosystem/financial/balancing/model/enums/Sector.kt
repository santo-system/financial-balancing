package com.santosystem.financial.balancing.model.enums

enum class Sector(val value: String) {
    PETROLEO_GAS_E_BIOCOMBUSTIVEIS("PETRÓLEO, GÁS E BIOCOMBUSTÍVEIS"),
    MATERIAIS_BASICOS("MATERIAIS BÁSICOS"),
    BENS_INDUSTRIAIS("BENS INDUSTRIAIS"),
    CONSUMO_NAO_CICLICO("CONSUMO NÃO CÍCLICO"),
    CONSUMO_CICLICO("CONSUMO CÍCLICO"),
    SAUDE("SAÚDE"),
    TECNOLOGIA_DA_INFORMACAO("TECNOLOGIA DA INFORMAÇÃO"),
    COMUNICACOES("COMUNICAÇÕES"),
    UTILIDADE_PUBLICA("UTILIDADE PÚBLICA"),
    FINANCEIRO("FINANCEIRO"),
    OUTROS("OUTROS"),
    UNDEFINED("NÃO DEFINIDO");

    companion object {
        fun getSector(value: String) = try {
            valueOf(value.uppercase())
        } catch (ex: IllegalArgumentException) {
            UNDEFINED
        }
    }

}
