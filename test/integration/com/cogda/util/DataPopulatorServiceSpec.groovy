package com.cogda.util

import com.cogda.domain.admin.LineOfBusiness
import com.cogda.domain.admin.LineOfBusinessCategory
import grails.plugin.awssdk.AmazonWebService
import grails.plugin.spock.IntegrationSpec

class DataPopulatorServiceSpec extends IntegrationSpec {
    AmazonWebService amazonWebService
    DataPopulatorService dataPopulatorService

	def setup() {

	}

	def cleanup() {
	}

	void "test createLinesOfBusiness data"() {
        when:
        dataPopulatorService.createLinesOfBusiness()

        then:
        assert LineOfBusinessCategory.count() > 1
        assert LineOfBusiness.count() > 1
	}
}