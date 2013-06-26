package com.cogda.command

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;


class UserImportCommandSerializer implements JsonSerializer<UserImportCommand> {

    @Override
    public JsonElement serialize(final UserImportCommand userImportCommand, final Type typeOfSrc, final JsonSerializationContext context) {

        final JsonObject json = new JsonObject();
        json.addProperty("username", userImportCommand.username);
        json.addProperty("firstName", userImportCommand.firstName);
        json.addProperty("lastName", userImportCommand.lastName);
        json.addProperty("emailAddress", userImportCommand.emailAddress)
        json.addProperty("securityRoles", userImportCommand.securityRoles)
        json.addProperty("success", userImportCommand.hasErrors())

        return json;
    }


}
