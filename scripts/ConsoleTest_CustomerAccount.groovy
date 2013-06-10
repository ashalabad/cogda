import com.cogda.multitenant.CustomerAccount
import com.cogda.domain.security.User
import com.cogda.domain.UserProfile


UserProfile.executeUpdate("delete from UserProfile")
User.executeUpdate("delete from User")
CustomerAccount.executeUpdate("delete from CustomerAccount")

// testing Creation of a new CustomerAccount
CustomerAccount customerAccount = new CustomerAccount(subDomain:"super")
assert customerAccount.save(failOnError:true, flush:true)


customerAccount.withThisTenant {
    User user = new User();
    user.username = "christopherk"
    user.password = "password"
    user.enabled = true
    
    assert user.save(failOnError:true, flush:true)
}



UserProfile userProfile = new UserProfile()
userProfile.firstName = "Christopher"
userProfile.lastName = "Kwiatkowski"
userProfile.user = User.findByUsername("christopherk")
assert userProfile.save(failOnError:true, flush:true)

println userProfile.user.id
println userProfile.user.username
assert userProfile.user

println userProfile



UserProfile.executeUpdate("delete from UserProfile")
User.executeUpdate("delete from User")
CustomerAccount.executeUpdate("delete from CustomerAccount")