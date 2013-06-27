package com.cogda.common.marshallers

import com.cogda.domain.Contact
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes

/**
 * Created with IntelliJ IDEA.
 * User: christopher
 * Date: 6/27/13
 * Time: 8:33 AM
 * Created this ExclusionStrategy to limit the data passed back to the client in the response.
 * Nothing that implements the java.util.Set interface - e.g. all of our collections of children on domain classes
 * that are not marked explicitly with a List type.
 * @see java.util.Set
 */
class JavaUtilSetExclusionStrategy implements ExclusionStrategy{

    @Override
    boolean shouldSkipField(FieldAttributes f) {
        return false
    }

    @Override
    boolean shouldSkipClass(Class<?> clazz) {
        return (clazz == Set.class)  //To change body of implemented methods use File | Settings | File Templates.
    }
}
