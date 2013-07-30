package com.cogda.common.marshallers

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes

/**
 * Created with IntelliJ IDEA.
 * User: Justin
 * Date: 7/30/13
 * Time: 10:41 AM
 * To change this template use File | Settings | File Templates.
 */
class NaicsSicParentChildExclusionStrategy  implements ExclusionStrategy{
    private static final PARENT_NAME = "parent"
    private static final CHILD_NAME = "child"

    @Override
    boolean shouldSkipField(FieldAttributes f) {
        println "fieldName: ${f.name}"
        return (f.name.contains(PARENT_NAME) || f.name.contains(CHILD_NAME))
    }

    @Override
    boolean shouldSkipClass(Class<?> clazz) {
        return false  //To change body of implemented methods use File | Settings | File Templates.
    }
}
