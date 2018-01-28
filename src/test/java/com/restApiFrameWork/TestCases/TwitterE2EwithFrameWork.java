package com.restApiFrameWork.TestCases;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import com.restApiFrameWork.Common.RestUtilities;
import com.restApiFrameWork.Constants.EndPoints;
import com.restApiFrameWork.Constants.Path;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import org.testng.annotations.BeforeClass;

public class TwitterE2EwithFrameWork {
	RequestSpecification reqSpec;
	ResponseSpecification respSpec;
	String tweetId;
 
  @BeforeClass
  public void beforeClass() {
	  reqSpec=RestUtilities.getRequestSpecification();
	  reqSpec.basePath(Path.BASE_PATH);
	  respSpec=RestUtilities.getResponseSpecification();
	  
  }
  @Test
  public void postTweet() {
	//  https://api.twitter.com/1.1/statuses/update.json?status="you post name"
	  Response res=
	  given()
	     .log()
	     .ifValidationFails()
	     .spec(RestUtilities.creatQuerryParam(reqSpec, "status", "my first post from frame"))
	 .when()
	    .post(EndPoints.BASEPATH_TWEET_POST)
	 .then()
	 .log()
	 .all()
	  .spec(respSpec)
	  .extract().response();
  JsonPath path=RestUtilities.getjsonPath(res);
	    tweetId=path.get("id_str");
	   System.out.println(tweetId);
	  
  }
  
  @Test(dependsOnMethods="postTweet")
  public void readTweet() {
	//  https://api.twitter.com/1.1/statuses/show.json?id=210462857140252672
given()
	  .log()
	  .ifValidationFails()
	  .spec(RestUtilities.creatQuerryParam(reqSpec, "id", tweetId))
.when()
	.get(EndPoints.BASEPATH_TWEET_READ)
.then()
	.log()
	.all()
	.spec(respSpec);
	
	  
	  
  }
  @Test(dependsOnMethods="readTweet")
  public void deleteTweet() {
	  //  https://api.twitter.com/1.1/statuses/destroy/240854986559455234.json
	  given()
	  .log()
	  .ifValidationFails()
	  .spec(RestUtilities.creatPathParam(reqSpec, "id", tweetId))
.when()
	.post(EndPoints.BASEPATH_TWEET_DESTROY)
.then()
	.log()
	.all()
	.spec(respSpec);
  }
}
