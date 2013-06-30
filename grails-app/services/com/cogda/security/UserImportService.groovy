package com.cogda.security

import au.com.bytecode.opencsv.CSVReader
import com.cogda.command.UserImportCommand
import com.cogda.domain.UserProfileService
import com.cogda.domain.admin.CustomerNotificationService
import com.cogda.domain.security.PendingUser
import com.cogda.domain.security.Role
import com.cogda.domain.security.User
import com.cogda.domain.security.UserRole
import com.cogda.multitenant.CustomerAccountService
import com.cogda.util.ErrorMessageResolverService
import grails.plugins.springsecurity.SpringSecurityService
import org.apache.commons.logging.LogFactory
import org.grails.plugin.platform.events.EventMessage
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.validation.FieldError

/**
 * UserImportService
 * A service class encapsulates the core business logic of a Grails application
 */
class UserImportService {
    private static final log = LogFactory.getLog(this)

    SpringSecurityService springSecurityService
    UserService userService

    /**
     * Validates the User's Import Data
     * @param inputStream
     * @return List
     */
    public List validateUserImportData(InputStream inputStream){
        List list = parseInputStream(inputStream)
        List userImports = []
        list.each { UserImportCommand command ->
            command.validate()
            userImports.add(command)
        }
        return userImports
    }


    /**
     * Takes a passed in list of Command objects and stores an
     * PendingUser in the database.  Returns a list of
     * PendingUser objects.
     * @param userImportCommands
     * @return List
     */
    public List createImportedUsers(String username, List userImportCommands){
        userImportCommands.each { UserImportCommand command ->

            if(!command.hasErrors()){
                PendingUser pendingUser = new PendingUser()
                pendingUser.firstName = command.firstName
                pendingUser.lastName = command.lastName
                pendingUser.emailAddress = command.emailAddress
                pendingUser.securityRoles = command.securityRoles

                pendingUser.loadedByUsername = username
                pendingUser.loadedDate = new Date()

                pendingUser.onboardedSuccessfully = false
                pendingUser.onboardedDate = null

                pendingUser.save()
                if(pendingUser.hasErrors()){
                    pendingUser.errors.allErrors.each { FieldError error ->
                        final String field = error.field
                        final String code = "userImportCommand.$field.$error.code"
                        command.errors.rejectValue(field, code)
                    }
                }
            }
        }
        return userImportCommands
    }

    /**
     * Processes the import of users from a CSV file.
     * @param inputStream
     * @return List of UserImportCommand objects.
     */
    public List processUserImport(InputStream inputStream){
        List userImportCommands = validateUserImportData(inputStream)
        String loadedByUsername = springSecurityService.currentUser.username
        createImportedUsers(loadedByUsername, userImportCommands)
        return userImportCommands
    }

    /**
     * Parses the data file based on the comma separated values into the
     * UserImportCommand object.
     * @param inputStream
     * @param skipFirstLine
     * @return List
     */
    def List parseInputStream(InputStream inputStream, Boolean skipFirstLine = Boolean.FALSE){
        List returnList = []
        CSVReader reader = new CSVReader(new InputStreamReader(inputStream))
        String [] nextLine;
        Integer count = 0;
        while((nextLine = reader.readNext()) != null){
            if(nextLine.size() < 4){

                log.warn ("User attempted to import a line that did not have the proper column length ${nextLine}")

            }else{

                if (count == 0 && skipFirstLine){
                    // gulp
                }
                else {
                    // nextLine[] is an array of values from the line
                    UserImportCommand command = new UserImportCommand()
                    command.firstName = nextLine[0]?.trim()
                    command.lastName = nextLine[1]?.trim()
                    command.emailAddress = nextLine[2]?.trim()
                    command.securityRoles = nextLine[3]?.trim()
                    returnList.add(command)
                }
            }

            count++

        }

        return returnList
    }

    /**
     * Creates a default user based on the passed in username.
     * Generates a temporary password and sets the passwordExpired
     * to true.
     * @param username
     * @return User
     */
    def User createDefaultUser(String username){
        User user = new User(username: username)
        user.password = PasswordGenerator.createTempPassword()
        user.enabled = Boolean.TRUE
        user.accountExpired = Boolean.FALSE
        user.accountLocked = Boolean.FALSE
        user.passwordExpired = Boolean.TRUE
        return user
    }


}
