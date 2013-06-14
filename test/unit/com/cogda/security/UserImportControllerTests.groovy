package com.cogda.security



import grails.test.mixin.*
import org.junit.*
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(UserImportController)
class UserImportControllerTests {

    private static final CSV_FILE_NAME = "userImport.csv"

    void tearDown(){
        super.tearDown()
        File file = new File(CSV_FILE_NAME)
        if(file.exists()){
            file.delete()
        }
    }


    void testIndex() {
        controller.index()
       assert view.equals("userImport/index")
    }

    void testImportUserFile(){
        // Mock the userImportService
        def mockUserImportService = mockFor(UserImportService, false)
        mockUserImportService.demand.loadUserData(0..1) { File file ->
            [line:1, success:false, message:"a message"]
        }
        controller.userImportService = mockUserImportService.createMock()

        // controller.metaClass.request = mockFor(MultipartHttpServletRequest).createMock()
        controller.metaClass.request = mockFor(MultipartHttpServletRequest).createMock()
        File file = createCsvFile()
        controller.request.addFile(new MockMultipartFile('file', file.name, "text/plain", file.bytes))

        controller.importUserFile()

        assert model.importMessages.equals([line:1, success:false, message:"a message"])

    }


    private File createCsvFile(){
        def contents = """"username","firstName","lastName","emailAddress","${SecurityService.ROLE_MARKETER},${SecurityService.ROLE_PRODUCER}"
"mariarais","maria","schiller","maria@rais.com","${SecurityService.ROLE_MARKETER},${SecurityService.ROLE_PRODUCER}"
"""
        File file = new File(CSV_FILE_NAME)
        file.write(contents)
        return file
    }
}
