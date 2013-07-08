package com.cogda.multitenant

import org.apache.commons.lang.builder.HashCodeBuilder
import grails.plugin.multitenant.core.annotation.MultiTenant

/**
 *
 * @see http://multi-tenant.github.com/grails-multi-tenant-single-db/
 */
@MultiTenant
class AccountCompanyOwner implements Serializable {

    Account account
    Company company

    boolean equals(other) {
        if (!(other instanceof AccountCompanyOwner)) {
            return false
        }

        other.account?.id == account?.id &&
                other.company?.id == company?.id

    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if (account) builder.append(account.id)
        if (company) builder.append(company.id)
        builder.toHashCode()
    }

    static AccountCompanyOwner get(long accountId, long companyId) {
        find 'from AccountCompanyOwner where account.id=:accountId and company.id=:companyId',
                [accountId: accountId, companyId: companyId]
    }

    static AccountCompanyOwner create(Account account, Company company, boolean flush = false) {
        new AccountCompanyOwner(account: account, company: company).save(flush: flush, insert: true)
    }

    static boolean remove(Account account, Company company, boolean flush = false) {
        AccountCompanyOwner instance = AccountCompanyOwner.findByAccountAndCompany(account, company)
        if (!instance) {
            return false
        }

        instance.delete(flush: flush)
        true
    }

    static void removeAll(Account account) {
        executeUpdate 'DELETE FROM AccountCompanyOwner WHERE account=:account', [account: account]
    }

    static void removeAll(Company company) {
        executeUpdate 'DELETE FROM AccountCompanyOwner WHERE company=:company', [company: company]
    }

    static mapping = {
        id composite: ['company', 'account']
        version false
    }
}
