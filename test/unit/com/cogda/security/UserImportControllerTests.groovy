package com.cogda.security

import com.cogda.domain.security.Role
import com.cogda.multitenant.CustomerAccount
import com.cogda.multitenant.CustomerAccountService
import grails.test.mixin.*
import grails.test.mixin.domain.DomainClassUnitTestMixin
import org.junit.*
import org.springframework.mock.web.MockMultipartFile
import org.springframework.mock.web.MockMultipartHttpServletRequest
import org.springframework.web.multipart.MultipartHttpServletRequest

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(UserImportController)
@TestMixin(DomainClassUnitTestMixin)
class UserImportControllerTests {

    private static final CSV_FILE_NAME = "userImport.csv"

    void tearDown(){
        File file = new File(CSV_FILE_NAME)
        if(file.exists()){
            file.delete()
        }
    }


    void testIndexAction() {
       mockDomain(Role)
       controller.index()

       assert view.equals("/userImport/index")
    }

    void testImportUserFile(){
        // Mock the userImportService
        def mockUserImportService = mockFor(UserImportService, false)
        mockUserImportService.demand.processUserImport(0..1) { InputStream inputStream ->
            def list = []
            list.add([line:1, success:false, message:"a message"])
            return list
        }
        controller.userImportService = mockUserImportService.createMock()

        // controller.metaClass.request = mockFor(MultipartHttpServletRequest).createMock()
        controller.metaClass.request = new MockMultipartHttpServletRequest()
        File file = createCsvFile()
        controller.request.addFile(new MockMultipartFile('file', file.name, "text/plain", file.bytes))

        def model = controller.importUserFile()

        assert model.importMessages == [[line:1, success:false, message:"a message"]]
    }

    void testImportUserFileInvalidExtension(){
        mockDomain(CustomerAccount, [new CustomerAccount(id:1, subDomain:"aValidSubDomain")])

        request.putAt("customerAccount", 1)

        // Mock the userImportService
        def mockUserImportService = mockFor(UserImportService, false)
        mockUserImportService.demand.processUserImport(0..1) { InputStream inputStream ->
            def list = []
            list.add([line:1, success:false, message:"a message"])
            return list
        }
        controller.userImportService = mockUserImportService.createMock()

        // controller.metaClass.request = mockFor(MultipartHttpServletRequest).createMock()
        controller.metaClass.request = new MockMultipartHttpServletRequest()
        File file = createCsvFile()
        controller.request.addFile(new MockMultipartFile('file', "something.zip", "text/plain", file.bytes))

        def model = controller.importUserFile()

        assert flash.error.equals("fileupload.upload.unauthorizedExtension")
    }


    private File createCsvFile(){
        def contents = """"username","firstName","lastName","emailAddress","${CustomerAccountService.ROLE_MARKETER},${CustomerAccountService.ROLE_PRODUCER}"
"mariarais","maria","schiller","maria@rais.com","${CustomerAccountService.ROLE_MARKETER},${CustomerAccountService.ROLE_PRODUCER}"
"""
        File file = new File(CSV_FILE_NAME)
        file.write(contents)
        return file
    }
}
