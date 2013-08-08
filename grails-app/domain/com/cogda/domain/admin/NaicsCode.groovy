package com.cogda.domain.admin

import java.util.regex.Pattern

/**
 * NaicsCode
 * A domain class describes the data object and it's mapping to the database
 */
class NaicsCode {

    /* Default (injected) attributes of GORM */
    Long id
    Long version

    String code
    String description

    Integer level  // calculate the level based upon the parentNaicsCode  ~  level = parentNaicsCode ? parentNaicsCode.level + 1 : 0

    NaicsCode parentNaicsCode

    static hasMany = [childNaicsCodes: NaicsCode]

    Boolean active = Boolean.TRUE  //  True-> Available for selection and reporting  | False-> Available for reporting only

    /* Automatic timestamping of GORM */
    Date dateCreated
    Date lastUpdated

    static transients = ["allChildNaicsCodes"]

    static constraints = {
        code(nullable: false, unique: ['parentNaicsCode'])
        description(nullable: false)
    }

    def beforeValidate() {
        level = parentNaicsCode ? (parentNaicsCode.level + 1) : 0
    }

    def afterInsert() {
        if (parentNaicsCode) {
            parentNaicsCode.addToChildNaicsCodes(this)
            parentNaicsCode.save()
        }
    }

    def beforeDelete() {
        if (parentNaicsCode) {
            parentNaicsCode.removeFromChildNaicsCodes(this)
            parentNaicsCode.save()
        }
    }

    /*
     * Methods of the Domain Class
     */

    @Override    // Override toString for a nicer / more descriptive UI
    public String toString() {
        return "${code} - ${description}";
    }

    def aggregateParentIds() {
        def parents = []
        def naicsToFind = this
        while (naicsToFind.parentNaicsCode != null) {
            parents.add(naicsToFind.parentNaicsCode)
            naicsToFind = naicsToFind.parentNaicsCode
        }
        return parents
    }

    def getAllChildNaicsCodes() {
        def children = []
        children.addAll(childNaicsCodes)
        childNaicsCodes.each {
            children.addAll(it.getAllChildNaicsCodes())
        }
        return children
    }

    def getAllChildNaicsCodes(String filter) {
        def children = []

        children.addAll(childNaicsCodes.findAll { NaicsCode child ->
            def regex = Pattern.compile(Pattern.quote(filter), Pattern.CASE_INSENSITIVE)
            return regex.matcher(child.description).find() || regex.matcher(child.code).find()
        })
        childNaicsCodes.each {
            children.addAll(it.getAllChildNaicsCodes(filter))
        }
        return children
    }
}
