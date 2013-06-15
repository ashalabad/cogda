package com.cogda.security

import au.com.bytecode.opencsv.CSVReader
import com.cogda.domain.UserProfile
import com.cogda.domain.UserProfileService
import com.cogda.domain.security.Role
import com.cogda.domain.security.User
import com.cogda.domain.security.UserRole
import com.cogda.errors.RoleNotFoundException

/**
 * UserImportService
 * A service class encapsulates the core business logic of a Grails application
 */
class UserImportService {

    UserProfileService userProfileService


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
        List importMessages = []
        list.eachWithIndex { int i, Map mapData ->
            Map importMessage = [line:(i+1), success:false, message:""]
            String rootMessage = "User at line ${i + 1} "
            if(!User.findByUsername(mapData.username)){
                User user = createDefaultUser(mapData.username)
                List securityRoles = parseRoles(mapData.securityRoles)

                if(!user.validate()){
                    importMessage.success = false
                    importMessage.message = rootMessage + "Error validating the user.  ${user.errors}"
                }else{
                    user.save()

                    securityRoles.each { Role role ->
                        UserRole.create(user, role)
                    }

                    userProfileService.createUserProfile(user, mapData.firstName, mapData.lastName, mapData.emailAddress)

                    importMessage.success = true
                    importMessage.message = rootMessage + "Import Successful"
                }
            }else{
                importMessage.success = false
                importMessage.message = rootMessage + "Username ${mapData.username} already exists."
                importMessages.add(importMessage)
            }
        }
        return importMessages
    }

    /**
     * Parses the data file based on the comma separated values
     * @param inputStream
     * @return List
     */
    def List<Map> parseInputStream(InputStream inputStream){
        List returnList = []
        CSVReader reader = new CSVReader(new InputStreamReader(inputStream))
        String [] nextLine;
        Integer count = 0;
        while((nextLine = reader.readNext()) != null){
            // skip the first line
            if (count != 0){
                // nextLine[] is an array of values from the line
                Map map = [:]
                map.username = nextLine[0]?.toLong()
                map.firstName = nextLine[1]?.trim()
                map.lastName = nextLine[2]?.trim()
                map.emailAddress = nextLine[3]?.trim()
                map.securityRoles = nextLine[4]?.trim()
                map.suggestedUsername = nextLine[5]?.substring(0, nextLine[6]?.indexOf('@'))
                returnList.add(map)
            }
            count++
        }

        return returnList
    }

    def List<Role> parseRoles(String securityRoles){
        String[] securityRolesArray = securityRoles.split(",")
        List roles = []
        securityRolesArray.each { String authority ->
            Role role = Role.findByAuthority(authority)
            if(role){
                roles.add(role)
            }
        }
        return roles
    }

    /**
     * Creates a default user based on the passed in
     * @param username
     * @return
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
