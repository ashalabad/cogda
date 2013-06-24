package com.cogda.common.marshallers

import com.cogda.domain.admin.CompanyType
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

/**
 * Created with IntelliJ IDEA.
 * User: chewy
 * Date: 6/20/13
 * Time: 11:19 PM
 * To change this template use File | Settings | File Templates.
 */
class CompanyTypeAdapter extends TypeAdapter<CompanyType> {

    @Override
    void write(JsonWriter out, CompanyType value) throws IOException {
        Gson gson = new Gson()
        out.value(gson.toJson(value))
    }

    @Override
    CompanyType read(JsonReader jsonReader) {
        Gson gson = new Gson()
//        JSON.parse(jsonReader.nextString) as CompanyType
        gson.fromJson(jsonReader.nextString(), CompanyType.class)
    }
}
