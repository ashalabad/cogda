package com.cogda.multitenant

import org.apache.commons.lang.builder.HashCodeBuilder

/**
 * AccountContactLink
 * A domain class describes the data object and it's mapping to the database
 */
class AccountContactLink implements Serializable {

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

    Account account
    AccountContact accountContact
    /**
     * Primary contact
     */
    Boolean primaryContact

    boolean equals(other) {
        if (!(other instanceof AccountContactLink)) {
            return false
        }

        other.account?.id == account?.id &&
                other.accountContact?.id == accountContact?.id

    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if (account) builder.append(account.id)
        if (accountContact) builder.append(accountContact.id)
        builder.toHashCode()
    }

    static AccountContactLink get(long accountId, long accountContactId) {
        find 'from AccountContactLink where account.id=:accountId and accountContact.id=:accountContactId',
                [accountId: accountId, accountContactId: accountContactId]
    }

    static AccountContactLink create(Account account, AccountContact accountContact, Boolean primaryContact, boolean flush = false) {
        new AccountContactLink(account: account, accountContact: accountContact,primaryContact: primaryContact).save(flush: flush, insert: true)
    }

    static boolean remove(Account account, AccountContact accountContact, boolean flush = false) {
        AccountContactLink instance = AccountContactLink.findByAccountAndAccountContact(account, accountContact)
        if (!instance) {
            return false
        }

        instance.delete(flush: flush)
        true
    }

    static void removeAll(Account account) {
        executeUpdate 'DELETE FROM AccountContactLink WHERE account=:account', [account: account]
    }

    static void removeAll(AccountContact accountContact) {
        executeUpdate 'DELETE FROM AccountContactLink WHERE accountContact=:accountContact', [accountContact: accountContact]
    }

    static mapping = {
        id composite: ['accountContact', 'account']
        version false
    }
}
