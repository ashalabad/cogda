package com.cogda.security

import au.com.bytecode.opencsv.CSVParser
import au.com.bytecode.opencsv.CSVReader
import com.cogda.command.UserImportCommand
import com.cogda.domain.UserProfile
import com.cogda.domain.UserProfileService
import com.cogda.domain.security.Role
import com.cogda.domain.security.User
import com.cogda.domain.security.UserRole
import com.cogda.errors.RoleNotFoundException
import com.cogda.multitenant.CustomerAccount
import com.cogda.util.ErrorMessageResolverService
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.validation.FieldError

/**
 * UserImportService
 * A service class encapsulates the core business logic of a Grails application
 */
class UserImportService {

    UserProfileService userProfileService
    UserService userService
    ErrorMessageResolverService errorMessageResolverService

    /**
     * Loads users based on the passed in userDataFile
     * which must be a csv file with the following characteristics.
     *
     * Username, First Name, Last Name, Email Address, Security Roles (comma separated)
     *
     * @param userDataFile
     */
    public List loadUserData(InputStream inputStream) {
        List list = parseInputStream(inputStream)
        List userImports = []
        list.each { UserImportCommand command ->
            command.validate()

            if(!command.hasErrors()){

                User user = createDefaultUser(command.username)
                user.validate()
                if(user.hasErrors()){
                    if (user.errors.hasErrors())
                    {
                        user.errors.allErrors.each { FieldError error ->
                            final String field = error.field
                            final String code = "userImportCommand.$field.$error.code"
                            command.errors.rejectValue(field, code)
                        }
                    }
                }
                else{

                    try {
                        user.save(insert:true)
                    }catch(DataIntegrityViolationException dive){
                        command.errors.rejectValue("username", "registration.username.taken")
                        user = null
                    }

                    if(!command.hasErrors()){

                        log.debug ("Successfully inserted new user -> ${user.username}")

                        List userRoles = []
                        command.parsedSecurityRoles.each { String securityRole ->
                            if(Role.ADMIN_ASSIGNABLE_AUTHORITIES.contains(securityRole)){
                                userRoles.add(Role.findByAuthority(securityRole))
                            }else{
                                log.warn("User attempted to load a role that was not found in the Role.ADMIN_ASSIGNABLE_AUTHORITIES list -> ${securityRole}")
                            }
                        }

                        if(userRoles){
                            userRoles.each { Role role ->
                                UserRole.create(user, role)
                                log.debug ("Successfully added role -> ${role.authority} to User -> ${user.username}")
                            }
                        }

                        userProfileService.createUserProfile(user, command.firstName, command.lastName, command.emailAddress)
                    }
                }


            }
            userImports.add(command)
        }
        return userImports
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
            if(nextLine.size() < 5){

                log.warn ("User attempted to import a line that did not have the proper column length ${nextLine}")

            }else{

                if (count == 0 && skipFirstLine){
                    // gulp
                }
                else {
                    // nextLine[] is an array of values from the line
                    UserImportCommand command = new UserImportCommand()
                    command.username = nextLine[0]?.trim()
                    command.firstName = nextLine[1]?.trim()
                    command.lastName = nextLine[2]?.trim()
                    command.emailAddress = nextLine[3]?.trim()
                    command.securityRoles = nextLine[4]?.trim()
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
