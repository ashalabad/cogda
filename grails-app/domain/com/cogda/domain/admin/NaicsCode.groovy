package com.cogda.domain.admin

/**
 * NaicsCode
 * A domain class describes the data object and it's mapping to the database
 */
class NaicsCode {

    /* Default (injected) attributes of GORM */
	Long	id
	Long	version

    Long code
    String description

    Integer level  // calculate the level based upon the parentNaicsCode  ~  level = parentNaicsCode ? parentNaicsCode.level + 1 : 0

    NaicsCode parentNaicsCode

    static hasMany = [childNaicsCodes: NaicsCode]

    Boolean active = Boolean.TRUE  //  True-> Available for selection and reporting  | False-> Available for reporting only

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated


    static constraints = {
        code(nullable:false, unique:['parentNaicsCode'])
        description(nullable:false)
    }

    def beforeValidate() {
        level = parentNaicsCode ? (parentNaicsCode.level + 1): 0
    }

    def afterInsert() {
        if(parentNaicsCode)
        {
            parentNaicsCode.addToChildNaicsCodes(this)
            parentNaicsCode.save()
        }
    }

    def beforeDelete() {
        if(parentNaicsCode)
        {
            parentNaicsCode.removeFromChildNaicsCodes(this)
            parentNaicsCode.save()
        }
    }

    /*
     * Methods of the Domain Class
     */
    @Override	// Override toString for a nicer / more descriptive UI
    public String toString() {
        return "${code} - ${description}";
    }

}
