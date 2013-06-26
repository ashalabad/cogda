package com.cogda.util

import au.com.bytecode.opencsv.CSVReader
import com.amazonaws.services.s3.model.GetObjectRequest
import com.cogda.domain.admin.BusinessType
import com.cogda.domain.admin.LineOfBusiness
import com.cogda.domain.admin.LineOfBusinessCategory
import grails.plugin.awssdk.AmazonWebService
import org.apache.commons.lang.StringUtils

/**
 * DataPopulatorService
 * A service class encapsulates the core business logic of a Grails application
 */
class DataPopulatorService {
    AmazonWebService amazonWebService

    /**
     * Pulls the BusinessTypes from our testingfiles/ s3 bucket and parses the
     * csv files and saves BusinessType objects to the database.
     */
    def createBusinessTypes(){
        InputStream is = amazonWebService.s3.getObject(new GetObjectRequest("cogda-test", "testingfiles/BusinessTypes.csv")).getObjectContent()
        CSVReader reader = new CSVReader(new InputStreamReader(is))
        String[] nextLine;
        Integer count = 0;

        while ((nextLine = reader.readNext()) != null) {

            String businessTypeCode = nextLine[0]
            businessTypeCode = StringUtils.strip(businessTypeCode)

            if(!BusinessType.findByCode(businessTypeCode)){
                BusinessType businessType = new BusinessType()
                businessType.code = businessTypeCode
                businessType.description = businessTypeCode
                businessType.codeFrom = nextLine[1].toInteger()
                businessType.codeTo = nextLine[2].toInteger()
                businessType.intCode = count

                businessType.save(failOnError:true)
            }
            count++
        }


    }



    def createLinesOfBusiness(){
        InputStream is = amazonWebService.s3.getObject(new GetObjectRequest("cogda-test", "testingfiles/LinesOfBusinessData.csv")).getObjectContent()
        CSVReader reader = new CSVReader(new InputStreamReader(is))
        String[] nextLine;
        Integer count = 0;
        Map lobCat = [:]

        while ((nextLine = reader.readNext()) != null) {
            String categoryCode = nextLine[0]
            categoryCode = categoryCode.replaceAll("[^A-Za-z0-9& ]", "")
            categoryCode = StringUtils.strip(categoryCode)

            String lobCode = nextLine[1]
            lobCode = StringUtils.strip(lobCode)
            String lobDescription = nextLine[2]
            lobDescription = StringUtils.strip(lobDescription)
            Map lob = [code:lobCode, description:lobDescription]

            if(lobCat.containsKey(categoryCode)){
                List lobs = lobCat.get(categoryCode)
                lobs.add(lob)
                lobCat.put(categoryCode, lobs)
            }else{
                lobCat.put(categoryCode, [lob])
            }
        }

        lobCat.eachWithIndex { String k, List<Map> v, int i ->
            LineOfBusinessCategory lineOfBusinessCategory = new LineOfBusinessCategory(code:k, description: k, intCode: i)
            lineOfBusinessCategory.save(failOnError: true)

            v.eachWithIndex { Map map, int j ->
                LineOfBusiness lineOfBusiness = new LineOfBusiness()
                lineOfBusiness.code = map.code
                lineOfBusiness.description = map.description
                lineOfBusiness.intCode = j
                lineOfBusiness.lineOfBusinessCategory = lineOfBusinessCategory
                lineOfBusiness.save(failOnError: true)
            }
        }


    }


}
