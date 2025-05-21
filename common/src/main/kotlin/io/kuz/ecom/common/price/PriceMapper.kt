package io.kuz.ecom.common.price

import java.math.BigDecimal

object PriceMapper {

    fun toDecimal(cents: Long): BigDecimal {
        return BigDecimal.valueOf(cents, 2)
    }

    fun toCents(dollars: BigDecimal): Long {
        return dollars.multiply(BigDecimal(100)).longValueExact()
    }
}