package com.cogda.errors

/**
 * Created with IntelliJ IDEA.
 * User: christopher
 * Date: 6/12/13
 * Time: 2:44 PM
 * To change this template use File | Settings | File Templates.
 */
class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(String message){
        this.message = message
    }
}
