package com.cogda.domain.admin

import org.apache.commons.lang.builder.HashCodeBuilder

/**
 * SicNaicsCodeCrosswalk
 * A domain class describes the data object and it's mapping to the database
 */
class SicNaicsCodeCrosswalk  implements Serializable {

    SicCode sicCode
    NaicsCode naicsCode

    boolean equals(other) {
        if (!(other instanceof SicNaicsCodeCrosswalk)) {
            return false
        }

        other.sicCode?.id == sicCode?.id &&
                other.naicsCode?.id == naicsCode?.id

    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if (sicCode) builder.append(sicCode.id)
        if (naicsCode) builder.append(naicsCode.id)
        builder.toHashCode()
    }

    static SicNaicsCodeCrosswalk get(long sicCodeId, long naicsCodeId) {
        find 'from SicNaicsCodeCrosswalk where sicCode.id=:sicCodeId and naicsCode.id=:naicsCodeId',
                [sicCodeId: sicCodeId, naicsCodeId: naicsCodeId]
    }

    static SicNaicsCodeCrosswalk create(SicCode sicCode, NaicsCode naicsCode, boolean flush = false) {
        new SicNaicsCodeCrosswalk(sicCode: sicCode, naicsCode: naicsCode).save(flush: flush, insert: true)
    }

    static boolean remove(SicCode sicCode, NaicsCode naicsCode, boolean flush = false) {
        SicNaicsCodeCrosswalk instance = SicNaicsCodeCrosswalk.findBySicCodeAndNaicsCode(sicCode, naicsCode)
        if (!instance) {
            return false
        }

        instance.delete(flush: flush)
        true
    }

    static void removeAll(SicCode sicCode) {
        executeUpdate 'DELETE FROM SicNaicsCodeCrosswalk WHERE sicCode=:sicCode', [sicCode: sicCode]
    }

    static void removeAll(NaicsCode naicsCode) {
        executeUpdate 'DELETE FROM SicNaicsCodeCrosswalk WHERE naicsCode=:naicsCode', [naicsCode: naicsCode]
    }

    static mapping = {
        id composite: ['naicsCode', 'sicCode']
        version false
    }


}
