package com.cogda.common.marshallers

import com.cogda.domain.onboarding.Registration
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import grails.converters.JSON

/**
 * Created with IntelliJ IDEA.
 * User: Justin
 * Date: 6/21/13
 * Time: 12:42 PM
 * To change this template use File | Settings | File Templates.
 */
class RegistrationAdapter extends TypeAdapter<Registration> {
    @Override
    void write(JsonWriter out, Registration value) throws IOException {
        out.value(value.encodeAsJSON().toString())
    }

    @Override
    Registration read(JsonReader jsonReader) {
        JSON.parse(jsonReader.nextString()) as Registration
    }
}
