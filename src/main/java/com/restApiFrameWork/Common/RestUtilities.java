package com.restApiFrameWork.Common;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.text.AbstractDocument.Content;

import com.restApiFrameWork.Constants.Auth;
import com.restApiFrameWork.Constants.Path;

import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RestUtilities {
    public static String ENDPOINTS;
    public static RequestSpecBuilder REQUEST_BUILDER;
    public static RequestSpecification REQUEST_SPEC;
    public static ResponseSpecBuilder RESPONSE_BUILDER;
    public static ResponseSpecification RESPONSE_SPEC;
    
    public static void setEndPoint(String endpoint){
    	ENDPOINTS=endpoint;
    }
    
    public static RequestSpecification getRequestSpecification(){
    	
    	AuthenticationScheme authSceme=
				RestAssured.oauth(Auth.CONSUMER_KEY, Auth.CONSUMER_SECRET, Auth.TOKEN, Auth.TOKEN_SECRET);
    	REQUEST_BUILDER=new RequestSpecBuilder();
    	REQUEST_BUILDER.setBaseUri(Path.BASE_URI);
    	REQUEST_BUILDER.setAuth(authSceme);
    	REQUEST_SPEC=REQUEST_BUILDER.build();
		return REQUEST_SPEC;
    }
    
    public static ResponseSpecification getResponseSpecification(){
    	RESPONSE_BUILDER=new ResponseSpecBuilder();
    	RESPONSE_BUILDER.expectStatusCode(200);
    	RESPONSE_BUILDER.expectResponseTime(lessThan(10L),TimeUnit.SECONDS);
    	 RESPONSE_SPEC=RESPONSE_BUILDER.build();
    	return RESPONSE_SPEC;
    }
    
    public static RequestSpecification creatQuerryParam(RequestSpecification rspec,
    		String param,String value){
		return rspec.queryParam(param, value);
    }
    
    public static RequestSpecification creatQuerryParam(RequestSpecification rspec,
    		Map<String,String> queryMap){
		return rspec.queryParams(queryMap);
    }
    
    public static RequestSpecification creatPathParam(RequestSpecification rspec,
    		String param,String value){
		return rspec.pathParam(param, value);
    }

    public static Response getResponse(){
    	return given().get(ENDPOINTS);
    }
    
    public static Response getResponse(RequestSpecification rspec,String type){
    	REQUEST_SPEC.spec(rspec);
    	Response response=null;
    	if(type.equalsIgnoreCase("get")){
    		response=given().spec(REQUEST_SPEC).get(ENDPOINTS);
    	}
    	else if(type.equalsIgnoreCase("post")){
    		response=given().spec(REQUEST_SPEC).post(ENDPOINTS);
    	}else if(type.equalsIgnoreCase("put")){
    		response=given().spec(REQUEST_SPEC).put(ENDPOINTS);
    	}else if(type.equalsIgnoreCase("delete")){
    		response=given().spec(REQUEST_SPEC).delete(ENDPOINTS);
    	}
		response.then().log().all();
		response.then().spec(RESPONSE_SPEC);
		return response;
    }
    
    public static JsonPath getjsonPath(Response res){
    	String resBody=res.asString();
    	JsonPath jpath=new JsonPath(resBody);
    	return jpath;
    }
    public static XmlPath getXmlPath(Response res){
    	String resBody=res.asString();
    	XmlPath xpath=new XmlPath(resBody);
    	return xpath;
    }
    
    public static void restBasePath(){
    	RestAssured.basePath=null;
    }
    
    public static void setContetType(ContentType type){
        given().contentType(type);
    }
}
