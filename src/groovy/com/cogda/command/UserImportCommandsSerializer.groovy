package com.cogda.command

import java.lang.reflect.Type;
import java.util.Collection;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

class UserImportCommandsSerializer implements JsonSerializer <Collection<UserImportCommand>>{

    @Override
    public JsonElement serialize(final Collection<UserImportCommand> userImportCommands, final Type typeOfSrc,
                                 final JsonSerializationContext context) {

        final JsonObject json = new JsonObject();
        json.addProperty("size", userImportCommands?.size());

        final JsonArray jsonArray = new JsonArray();
        json.add("userImportCommands", jsonArray);
        for (final UserImportCommand userImportCommand : userImportCommands) {
            final JsonElement element = context.serialize(userImportCommand);
            jsonArray.add(element);
        }
        return json;
    }

}
