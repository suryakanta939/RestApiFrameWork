package com.restApiFrameWork.TestCases;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.restApiFrameWork.Common.RestUtilities;
import com.restApiFrameWork.Constants.EndPoints;
import com.restApiFrameWork.Constants.Path;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class UserTimeLineTest {
	RequestSpecification requestSpec;
	ResponseSpecification resopnseSpec;
	Map<String,String> querymap;
	
	@BeforeClass
	public void settingUp(){
		requestSpec=RestUtilities.getRequestSpecification();
		requestSpec.basePath(Path.BASE_PATH);
//		requestSpec.queryParam("screen_name", "hi surya");
//		requestSpec.queryParam("count", "1");
		 querymap=new HashMap<String, String>();
		querymap.put("screen_name", "hi surya");
		querymap.put("count", "2");
		resopnseSpec=RestUtilities.getResponseSpecification();
//		resopnseSpec.body("user.screen_name",hasItem("East09Surya"));
//		resopnseSpec.body("entities.hashtags[0].text",hasItem("testing2"));
	}
	@Test
	public void readTweet() {
		given()
		  .log()
		  .ifValidationFails()
		//  .spec(requestSpec)
		  .spec(RestUtilities.creatQuerryParam(requestSpec, querymap))
	.when()
		 .get(EndPoints.BASEPATH_USER_TIMELINE)
	.then()
	   .log()
	   .all()
	   .spec(resopnseSpec)
	   .body("user.screen_name",hasItem("East09Surya"))
	   .body("entities.hashtags[0].text",hasItem("testing2"));
	}
}
