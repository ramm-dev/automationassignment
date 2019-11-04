package com.testcase;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.util.ResourcesList;
import com.util.CommonWrapper;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import static com.util.CommonConstants.CONFIG_PROP_FILE;


public class CategoryDetailsTestcase {
    String desc, promotionName, jsonResult;
    Properties prop = new Properties();
    Response jsonResponse;
    JsonPath jsp;

    @BeforeClass
    public void getData() throws Exception
    {
        File file = new File(CONFIG_PROP_FILE);
        FileInputStream fis = new FileInputStream(file);
        prop.load(fis);
        RestAssured.baseURI= prop.getProperty("HOST_NAME");

        jsonResponse = RestAssured.given().
                param("catalogue", prop.getProperty("CATALOGUE")).
                when().get(ResourcesList.CategoryResourcesID(6327)).
                then().assertThat().statusCode(200).and().contentType("application/json").
                extract().response();
        jsonResult = jsonResponse.asString();
        jsp = CommonWrapper.rawToJSON(jsonResponse);
        try {
            Assert.assertEquals(200, jsonResponse.getStatusCode());
        } catch (Exception e) {
            throw new Exception("FAILED To get Category Details because the returned status code is "+jsonResponse.getStatusCode());
        }

    }

    @Test(description = "AC1: Verifying the Name")
    public void validateName()
    {
        Assert.assertEquals((jsp.get("Name")),"Carbon credits");
    }

    @Test (description = "AC2: Verifying the list flagging is enabled")
    public void validateListFlag()
    {
        boolean reList = jsp.get("CanRelist");
        Assert.assertEquals(reList,true);
    }

    @Test (description = "AC3: Verifying whether element with Name 'Gallery' has a Description that contains the text '2x larger image'")
    public void validateDescription() throws Exception
    {
        JSONArray jNode = CommonWrapper.jsnObjectToArray(jsonResult,"Promotions");

        for (int i = 0; i < jNode.size(); i++)
        {
            promotionName = jsp.get("Promotions["+i+"].Name");

            if(jsp.get("Promotions["+i+"].Name").equals("Gallery"))
            {
                desc = jsp.get("Promotions["+i+"].Description");

                if(!desc.contains("2x larger image"))
                {
                    throw new Exception("FAILED! Description does not contain the expected text");
                }
                break;
            }


        }

    }

}
