package com.cogda.command

import com.google.gson.JsonPrimitive
import org.codehaus.groovy.grails.web.context.ServletContextHolder
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes

import java.lang.reflect.Type;

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer


class UserImportCommandSerializer implements JsonSerializer<UserImportCommand> {
    def ctx = ServletContextHolder.servletContext.getAttribute(GrailsApplicationAttributes.APPLICATION_CONTEXT)
    def messageSource = ctx.messageSource
    @Override
    public JsonElement serialize(final UserImportCommand userImportCommand, final Type typeOfSrc, final JsonSerializationContext context) {

        final JsonObject json = new JsonObject();
        json.addProperty("firstName", userImportCommand.firstName)
        json.addProperty("lastName", userImportCommand.lastName)
        json.addProperty("emailAddress", userImportCommand.emailAddress)
        json.addProperty("securityRoles", userImportCommand.securityRoles)
        json.addProperty("success", !userImportCommand.hasErrors())
        if(!userImportCommand.resolvedErrorsByField.isEmpty()){

            final JsonArray jsonArray = new JsonArray();
            json.add("errors", jsonArray);
            userImportCommand.resolvedErrorsByField.each { String field, List<String> errors ->

                final JsonArray errorsArray = new JsonArray();
                errors.each { String errorMessage ->
                    JsonElement jsonElement = new JsonPrimitive(errorMessage)
                    errorsArray.add(jsonElement)
                }

                JsonObject jsonObject = new JsonObject()

                jsonObject.add(field, errorsArray)
                jsonArray.add(jsonObject);
            }

        }


        return json;
    }


}
