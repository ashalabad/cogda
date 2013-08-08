package com.cogda.common

/**
 * Created with IntelliJ IDEA.
 * User: christopher
 * Date: 6/7/13
 * Time: 11:50 AM
 * To change this template use File | Settings | File Templates.
 */
enum RegistrationStatus {
    AWAITING_USER_EMAIL_CONFIRMATION(0),
    AWAITING_ADMIN_APPROVAL(1),
    APPROVED(2),
    AWAITING_COMPANY_APPROVAL(3), // used for registrations that get directed to existing companies
    REJECTED(4)


    RegistrationStatus(int value) {
        this.value = value
    }

    private final int value

    public int value() { return value }

}
