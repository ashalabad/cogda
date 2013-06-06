package com.cogda.domain.admin

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

import name.fraser.neil.plaintext.DiffMatchPatch;
import grails.test.mixin.*
import org.junit.*
import com.cogda.common.MarkupLanguage

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(SystemEmailMessageTemplate)
class SystemEmailMessageTemplateTests {

    String bodyMessage = """Dear {clientName},
Thank you for your interest in {appName}.  We look forward to serving you and your organization {organizationName}.

Please click the following verification code to activate your new {appName} account.

{activationUrl}

Upon successful activation of your account you will be redirected to your organization's new home at {organizationUrl}

Thank you!

{appName} Team"""

    SystemEmailMessageTemplate systemEmailMessageCons

    /**
     * class SystemEmailMessageTemplate {
     * 	...
     *  markupLanguage nullable:false
     *  requiredParameterNames nullable:true
     *  title nullable:false
     *  description nullable:false
     *  subject nullable:false
     *  fromEmail nullable:false
     *  body nullable:false
     *  acceptsParameters nullable:true
     *  ...
     * }
     */
    void testNullable(){
        systemEmailMessageCons = new SystemEmailMessageTemplate()
        assertFalse systemEmailMessageCons.validate()
        assert "nullable" == systemEmailMessageCons.errors["markupLanguage"].code
        assert !systemEmailMessageCons.errors["requiredParameterNames"]
        assert "nullable" == systemEmailMessageCons.errors["title"].code
        assert "nullable" == systemEmailMessageCons.errors["description"].code
        assert "nullable" == systemEmailMessageCons.errors["subject"].code
        assert "nullable" == systemEmailMessageCons.errors["fromEmail"].code
        assert "nullable" == systemEmailMessageCons.errors["body"].code
        assert !systemEmailMessageCons.errors["acceptsParameters"]

        systemEmailMessageCons.markupLanguage = MarkupLanguage.MARKDOWN
        systemEmailMessageCons.validate()
        assert !systemEmailMessageCons.errors["markupLanguage"]

        systemEmailMessageCons.title = "TITLE_TITLE"
        systemEmailMessageCons.validate()
        assert !systemEmailMessageCons.errors["title"]

        systemEmailMessageCons.description = "description"
        systemEmailMessageCons.validate()
        assert !systemEmailMessageCons.errors["description"]

        systemEmailMessageCons.subject = "subject"
        systemEmailMessageCons.validate()
        assert !systemEmailMessageCons.errors["subject"]

        systemEmailMessageCons.fromEmail = "fromEmail@gmail.com"
        systemEmailMessageCons.validate()
        assert !systemEmailMessageCons.errors["fromEmail"]

        systemEmailMessageCons.body = "body"
        systemEmailMessageCons.validate()
        assert !systemEmailMessageCons.errors["body"]

    }

    /**
     * class SystemEmailMessageTemplate {
     * 	markupLanguage(nullable:false, inList: (MarkupLanguage.values() as List))
     * }
     */
    void testInList(){
        systemEmailMessageCons = new SystemEmailMessageTemplate()
        assertFalse systemEmailMessageCons.validate()

        systemEmailMessageCons.markupLanguage = MarkupLanguage.MARKDOWN
        systemEmailMessageCons.validate()
        assert !systemEmailMessageCons.errors["markupLanguage"]

        systemEmailMessageCons.markupLanguage = MarkupLanguage.PLAINTEXT
        systemEmailMessageCons.validate()
        assert !systemEmailMessageCons.errors["markupLanguage"]
    }

    /**
     * markupLanguage(nullable:false, inList: (MarkupLanguage.values() as List))
     *	requiredParameterNames(nullable:true, validator: { val, obj ->
     *		if(obj.acceptsParameters && !val){
     *			return ['parameters.notfound']
     *		}
     *		if(!obj.acceptsParameters && val){
     *			return ['acceptsParameters.isfalse']
     *		}
     *	})
     *	title(nullable:false, size:0..35, unique:true, matches: "[A-Z0-9]_+")
     *	description(nullable:false, size:0..255)
     *	subject(nulllable:false, size:0..255)
     *	fromEmail(nullable:false, email:true)
     *	body(nullable:false)
     *	acceptsParameters(nullable:true, validator: { val, obj ->
     *		if(val){
     *			if(obj.requiredParameterNames.isEmpty()){
     *				return ['paramaters.required', val]
     *			}
     *		}
     *	})
     */
    void testSize(){
        systemEmailMessageCons = new SystemEmailMessageTemplate()
        assertFalse systemEmailMessageCons.validate()

        systemEmailMessageCons.title = "TITLE_TITLE"
        systemEmailMessageCons.validate()
        assert !systemEmailMessageCons.errors["title"]
        systemEmailMessageCons.title = ""
        (0..35).each {
            systemEmailMessageCons.title += "A"
        }
        systemEmailMessageCons.validate()
        assert "size.toobig" == systemEmailMessageCons.errors["title"].code

        systemEmailMessageCons.description = "description"
        systemEmailMessageCons.validate()
        assert !systemEmailMessageCons.errors["description"]
        systemEmailMessageCons.description = ""
        (0..255).each {
            systemEmailMessageCons.description += "A"
        }
        systemEmailMessageCons.validate()
        assert "size.toobig" == systemEmailMessageCons.errors["description"].code

        systemEmailMessageCons.subject = "subject"
        systemEmailMessageCons.validate()
        assert !systemEmailMessageCons.errors["subject"]
        systemEmailMessageCons.subject = ""
        (0..255).each {
            systemEmailMessageCons.subject += "A"
        }
        systemEmailMessageCons.validate()
        assert "size.toobig" == systemEmailMessageCons.errors["subject"].code
    }

    /**
     * markupLanguage(nullable:false, inList: (MarkupLanguage.values() as List))
     *	requiredParameterNames(nullable:true, validator: { val, obj ->
     *		if(obj.acceptsParameters && !val){
     *			return ['parameters.notfound']
     *		}
     *		if(!obj.acceptsParameters && val){
     *			return ['acceptsParameters.isfalse']
     *		}
     *	})
     *	title(nullable:false, size:0..35, unique:true, matches: "[A-Z0-9]_+")
     *	description(nullable:false, size:0..255)
     *	subject(nulllable:false, size:0..255)
     *	fromEmail(nullable:false, email:true)
     *	body(nullable:false)
     *	acceptsParameters(nullable:true, validator: { val, obj ->
     *		if(val){
     *			if(obj.requiredParameterNames.isEmpty()){
     *				return ['paramaters.required', val]
     *			}
     *		}
     *	})
     */
    void testMatches(){
        systemEmailMessageCons = new SystemEmailMessageTemplate()
        assertFalse systemEmailMessageCons.validate()

        def failList = ['title', 'Title', '4Title', '__Ti']
        failList.each { failTitle ->
            systemEmailMessageCons.title = failTitle
            systemEmailMessageCons.validate()
            assert "matches.invalid" == systemEmailMessageCons.errors["title"].code
        }

        def successList = ['TITLE', 'T', 'T_', '1234567890', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ_']
        successList.each { successTitle ->
            systemEmailMessageCons.title = successTitle
            systemEmailMessageCons.validate()
            assert !systemEmailMessageCons.errors["title"]
        }
    }

    /**
     *	title(nullable:false, size:0..35, unique:true, matches: "[A-Z0-9]_+")
     */
    void testUnique(){
        SystemEmailMessageTemplate existingSystemEmailMessageTemplate = new SystemEmailMessageTemplate(title:"EXISTING_TITLE")
        mockForConstraintsTests(SystemEmailMessageTemplate, [existingSystemEmailMessageTemplate])

        def systemEmailMessage = new SystemEmailMessageTemplate(title:'EXISTING_TITLE')
        assertFalse systemEmailMessage.validate()
//		println systemEmailMessage.errors["title"]
        assert "unique" == systemEmailMessage.errors["title"]

        systemEmailMessage = new SystemEmailMessageTemplate(title:'NOT_EXISTING_TITLE')
        assertFalse systemEmailMessage.validate()
        assert !systemEmailMessage.errors["title"]
    }

    /**
     *	fromEmail(nullable:false, email:true)
     */
    void testEmail(){
        systemEmailMessageCons = new SystemEmailMessageTemplate()
        assertFalse systemEmailMessageCons.validate()

        systemEmailMessageCons.fromEmail = "test"
        systemEmailMessageCons.validate()
        assert "email.invalid" == systemEmailMessageCons.errors["fromEmail"].code

        systemEmailMessageCons.fromEmail = "test@gmail.com"
        systemEmailMessageCons.validate()
        assert !systemEmailMessageCons.errors["fromEmail"]
    }

    /**
     * markupLanguage(nullable:false, inList: (MarkupLanguage.values() as List))
     *	requiredParameterNames(nullable:true, validator: { val, obj ->
     *		if(obj.acceptsParameters && !val){
     *			return ['parameters.notfound']
     *		}
     *		if(!obj.acceptsParameters && val){
     *			return ['acceptsParameters.isfalse']
     *		}
     *	})
     *	title(nullable:false, size:0..35, unique:true, matches: "[A-Z0-9]_+")
     *	description(nullable:false, size:0..255)
     *	subject(nulllable:false, size:0..255)
     *	fromEmail(nullable:false, email:true)
     *	body(nullable:false)
     *	acceptsParameters(nullable:true, validator: { val, obj ->
     *		if(val){
     *			if(!obj.requiredParameterNames){
     *				return ['parameters.required', val]
     *			}
     *		}
     *	})
     */
    void testValidator(){
        SystemEmailMessageTemplate existingSystemEmailMessageTemplate = new SystemEmailMessageTemplate(title:"EXISTING_TITLE")
        mockForConstraintsTests(SystemEmailMessageTemplate, [existingSystemEmailMessageTemplate])

        // requiredParameterNames - validator tests
        def systemEmailMessage = new SystemEmailMessageTemplate()
        systemEmailMessage.acceptsParameters = true
        systemEmailMessage.requiredParameterNames = null
        systemEmailMessage.validate()
        assertEquals "parameters.notfound", systemEmailMessage.errors['requiredParameterNames']

        systemEmailMessage.requiredParameterNames = ['paramOne', 'paramTwo']
        systemEmailMessage.validate()
        assert !systemEmailMessage.errors["requiredParameterNames"]

        systemEmailMessage.acceptsParameters = false
        systemEmailMessage.validate()
        assertEquals "acceptsParameters.isfalse", systemEmailMessage.errors['requiredParameterNames']

        systemEmailMessage.requiredParameterNames.clear()
        systemEmailMessage.validate()
        assert !systemEmailMessage.errors["requiredParameterNames"]

        // test invalid requiredParameterNames
        systemEmailMessage.acceptsParameters = true
        systemEmailMessage.requiredParameterNames = ['camelCase', 'NotCamelCase', 'camelCase1', 'camelCaseWithSpace ']
        systemEmailMessage.validate()
        assertEquals "invalid.parameters", systemEmailMessage.errors['requiredParameterNames']

        // acceptsParameters - validator tests
        systemEmailMessage.acceptsParameters = true
        systemEmailMessage.requiredParameterNames.clear()
        systemEmailMessage.validate()
        assertEquals "parameters.required", systemEmailMessage.errors['acceptsParameters']

        systemEmailMessage.acceptsParameters = false
        systemEmailMessage.validate()
        assert !systemEmailMessage.errors["acceptsParameters"]

        systemEmailMessage.requiredParameterNames = ['paramOne', 'paramTwo']
        systemEmailMessage.acceptsParameters = true
        systemEmailMessage.validate()
        assert !systemEmailMessage.errors["acceptsParameters"]
    }

    void testApplyMapArgs() {
        SystemEmailMessageTemplate sem = new SystemEmailMessageTemplate(body:bodyMessage)
        Map paramMap = [clientName:'Christopher Kwiatkowski', appName:'RapidCommittee',
                organizationName:'UniversityOfGeorgia', activationUrl:'https://rapidcommittee.com/confirm/1003010830',
                organizationUrl:'https://universityofgeorgia.rapidcommittee.com']

        String retString = sem.applyMapArgs(paramMap)

        assert retString.contains("Dear Christopher Kwiatkowski")
    }

    void testApplyMapArgsMissingParameter(){
        SystemEmailMessageTemplate sem = new SystemEmailMessageTemplate(body:bodyMessage)
        Map paramMap = [clientName:'Christopher Kwiatkowski', appName:'RapidCommittee',
                organizationName:'UniversityOfGeorgia', activationUrl:'https://rapidcommittee.com/confirm/1003010830']

        String retString = sem.applyMapArgs(paramMap)

        assert retString.contains("Dear Christopher Kwiatkowski")
        assert !retString.contains("https://universityofgeorgia.rapidcommittee.com")
        assert !retString.contains("[organizationUrl]")
    }

    void testHasAllRequiredParameters(){
        SystemEmailMessageTemplate sem = new SystemEmailMessageTemplate(body:bodyMessage)
        sem.acceptsParameters = true
        sem.requiredParameterNames = ["clientName", "appName", "organizationName", "activationUrl", "organizationUrl"]
        assert sem.hasAllRequiredParameters()

        sem.requiredParameterNames << "anotherRequiredParameter"

        assert !sem.hasAllRequiredParameters()

        sem.requiredParameterNames.drop(3)

        assert !sem.hasAllRequiredParameters()

    }

    void testConvertMarkupPlainText(){
        String outputMarkup = "This is a plain text message that should be returned in the same format."
        SystemEmailMessageTemplate sem = new SystemEmailMessageTemplate(body:outputMarkup)
        sem.markupLanguage = MarkupLanguage.PLAINTEXT
        sem.acceptsParameters = false
        String plainText = sem.convertMarkup(outputMarkup)

        DiffMatchPatch dmp = new DiffMatchPatch()
        def d = dmp.diff_main(plainText, outputMarkup)
        dmp.diff_cleanupSemantic(d)
        def prettyDiff = dmp.diff_prettyHtml(d)

        assert plainText.equals(outputMarkup), prettyDiff
    }

    void testConvertMarkup(){
        String outputMarkup = """<p>Dear {clientName},
Thank you for your interest in {appName}.  We look forward to serving you and your organization {organizationName}.</p>

<p>Please click the following verification code to activate your new {appName} account.</p>

<p>{activationUrl}</p>

<p>Upon successful activation of your account you will be redirected to your organization's new home at {organizationUrl}</p>

<p>Thank you!</p>

<p>{appName} Team</p>
""".toString()

        SystemEmailMessageTemplate sem = new SystemEmailMessageTemplate(body:bodyMessage)
        sem.markupLanguage = MarkupLanguage.MARKDOWN
        sem.acceptsParameters = true
        sem.requiredParameterNames = ["clientName", "appName", "organizationName", "activationUrl", "organizationUrl"]
        String html = sem.convertMarkup(bodyMessage)

        DiffMatchPatch dmp = new DiffMatchPatch()
        def d = dmp.diff_main(html, outputMarkup)
        dmp.diff_cleanupSemantic(d)

        assert html.equals(outputMarkup), dmp.diff_prettyHtml(d)
    }

    void testWriteMessageOutput(){
        String outputMarkup = """<p>Dear Christopher Kwiatkowski,
Thank you for your interest in RapidCommittee.  We look forward to serving you and your organization UniversityOfGeorgia.</p>

<p>Please click the following verification code to activate your new RapidCommittee account.</p>

<p>https://rapidcommittee.com/confirm/1003010830</p>

<p>Upon successful activation of your account you will be redirected to your organization's new home at https://universityofgeorgia.rapidcommittee.com</p>

<p>Thank you!</p>

<p>RapidCommittee Team</p>
"""

        SystemEmailMessageTemplate sem = new SystemEmailMessageTemplate(body:bodyMessage)
        sem.markupLanguage = MarkupLanguage.MARKDOWN
        sem.acceptsParameters = true
        Map paramMap = [clientName:'Christopher Kwiatkowski', appName:'RapidCommittee',
                organizationName:'UniversityOfGeorgia', activationUrl:'https://rapidcommittee.com/confirm/1003010830',
                organizationUrl:'https://universityofgeorgia.rapidcommittee.com']
        String messageOutput = sem.writeMessageOutput(paramMap)

        DiffMatchPatch dmp = new DiffMatchPatch()
        def d = dmp.diff_main(messageOutput, outputMarkup)
        dmp.diff_cleanupSemantic(d)

        assert messageOutput.equals(outputMarkup), dmp.diff_prettyHtml(d)
    }

    void testWriteMessageOutputPlainText(){
        String outputMarkup = """Dear Christopher Kwiatkowski,
Thank you for your interest in RapidCommittee.  We look forward to serving you and your organization UniversityOfGeorgia.

Please click the following verification code to activate your new RapidCommittee account.

https://rapidcommittee.com/confirm/1003010830

Upon successful activation of your account you will be redirected to your organization's new home at https://universityofgeorgia.rapidcommittee.com

Thank you!

RapidCommittee Team"""

        SystemEmailMessageTemplate sem = new SystemEmailMessageTemplate(body:bodyMessage)
        sem.markupLanguage = MarkupLanguage.PLAINTEXT
        sem.acceptsParameters = true
        Map paramMap = [clientName:'Christopher Kwiatkowski', appName:'RapidCommittee',
                organizationName:'UniversityOfGeorgia', activationUrl:'https://rapidcommittee.com/confirm/1003010830',
                organizationUrl:'https://universityofgeorgia.rapidcommittee.com']

        String messageOutput = sem.writeMessageOutput(paramMap)

        DiffMatchPatch dmp = new DiffMatchPatch()
        def d = dmp.diff_main(messageOutput, outputMarkup)
        dmp.diff_cleanupSemantic(d)

        assert messageOutput.equals(outputMarkup), dmp.diff_prettyHtml(d)
    }

    void testRetrieveCurrentParameterNames(){
        SystemEmailMessageTemplate sem = new SystemEmailMessageTemplate(body:bodyMessage)
        sem.requiredParameterNames = ["clientName", "appName", "organizationName", "activationUrl", "organizationUrl"]
        List currentParameterNames = sem.retrieveCurrentParameterNames()
        assert currentParameterNames.equals(sem.requiredParameterNames)
    }

    void testRetrieveParamCheck(){
        SystemEmailMessageTemplate sem = new SystemEmailMessageTemplate(body:bodyMessage)
        sem.acceptsParameters = true
        sem.requiredParameterNames = ["clientName", "appName", "organizationName", "activationUrl", "organizationUrl"]

        Map paramCheck = sem.retrieveParamCheck()
//		paramCheck.each { k, v ->
//			println "${k}: $v"
//		}

        assert paramCheck != null
        assert paramCheck.valid.containsAll(sem.requiredParameterNames)
        assert paramCheck.valid.size() == sem.requiredParameterNames.size()
        assert paramCheck.missing.isEmpty()
        assert paramCheck.invalid.isEmpty()

        sem.requiredParameterNames.add("missingParameterName")

        paramCheck = sem.retrieveParamCheck()
//		paramCheck.each { k, v ->
//			println "${k}: $v"
//		}
        assert paramCheck != null
//		println "Valid: " + paramCheck.valid.size()
//		println "requiredParameterNames: " + sem.requiredParameterNames.size()
//		println " paramCheck.valid.containsAll(sem.requiredParameterNames) " + paramCheck.valid.containsAll(sem.requiredParameterNames)
        assert !paramCheck.valid.containsAll(sem.requiredParameterNames)
        assert paramCheck.valid.size() != sem.requiredParameterNames.size()
        assert paramCheck.missing.contains("missingParameterName")
        assert paramCheck.missing.size() == 1
        assert paramCheck.invalid.isEmpty()

        // add an invalid parameter to the body
        sem.body += " {invalidParameter} that is not found in the requiredParameters list for this instance of SystemEmailMessageTemplate"
        paramCheck = sem.retrieveParamCheck()
//		paramCheck.each { k, v ->
//			println "${k}: $v"
//		}
        assert paramCheck != null
        assert !paramCheck.valid.containsAll(sem.requiredParameterNames)
        assert paramCheck.valid.size() != sem.requiredParameterNames.size()
        assert paramCheck.missing.contains("missingParameterName")
        assert paramCheck.missing.size() == 1
        assert paramCheck.invalid.contains("invalidParameter")
        assert paramCheck.invalid.size() == 1
    }
}

