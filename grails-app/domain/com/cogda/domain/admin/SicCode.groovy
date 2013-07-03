package com.cogda.domain.admin

/**
 * SicCode
 * A domain class describes the data object and it's mapping to the database
 */
class SicCode {

    /* Default (injected) attributes of GORM */
	Long id
	Long version

    String code
    String description

    Integer level  // calculate the level based upon the parentSicCode  ~  level = parentSicCode ? parentSicCode.level + 1 : 0

    SicCode parentSicCode

    static hasMany = [childSicCodes: SicCode]

    Boolean active = Boolean.TRUE  //  True-> Available for selection and reporting  | False-> Available for reporting only

    /* Automatic timestamping of GORM */
    Date	dateCreated
    Date	lastUpdated

    static constraints = {
        code(nullable:false,unique:['parentSicCode'])
        description(nullable:false)
    }

    def beforeValidate() {
        level = parentSicCode ? (parentSicCode.level + 1): 0
    }

    def afterInsert() {
        if(parentSicCode)
        {
            parentSicCode.addToChildSicCodes(this)
            parentSicCode.save()
        }
    }

    def beforeDelete() {
        if(parentSicCode)
        {
            parentSicCode.removeFromChildSicCodes(this)
            parentSicCode.save()
        }
    }

    /*
     * Methods of the Domain Class
     */
	@Override	// Override toString for a nicer / more descriptive UI
	public String toString() {
		return "${code} - ${description}";
	}

     def aggregateParentIds() {
        def parents = []
        def sicToFind = this
        while (sicToFind.parentSicCode != null) {
            parents.add(sicToFind.parentSicCode)
            sicToFind = sicToFind.parentSicCode
            println sicToFind
        }
        return parents
    }
}
