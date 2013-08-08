package com.cogda.storage.exceptions

/**
 * Created with IntelliJ IDEA.
 * User: igor_pol
 * Date: 8/3/13
 * Time: 9:38 PM
 * To change this template use File | Settings | File Templates.
 */
class InvalidCompanyRequestException extends Exception {

    String requestName
    String savedCompanyName

    InvalidCompanyRequestException(String rqName,String svName) {
        super()
        requestName=rqName
        savedCompanyName=svName
    }

    @Override
    String getMessage() {
        return "A storage request was issued by $requestName but the item was created by $savedCompanyName"
    }
}
