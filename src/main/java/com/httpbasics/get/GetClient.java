package com.httpbasics.get;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.httpbasics.model.Student;

public class GetClient {

	final static CloseableHttpClient httpClient = HttpClients.createDefault();
	
	static void sendGetReq() throws Exception{
		HttpEntity respEntity = null;
		HttpGet getReq = new HttpGet("http://localhost:6070/students");
		
		try (CloseableHttpResponse response =  httpClient.execute(getReq)) {
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				System.out.println("Received non 200 status code : " + response.getStatusLine().getStatusCode());
				return;
			}
			respEntity = response.getEntity();
			List<Student> studs = new ObjectMapper().readValue(EntityUtils.toString(respEntity), new TypeReference<List<Student>>() {});
			studs.forEach(System.out::println);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			EntityUtils.consume(respEntity);
		}
		
	}

	static void sendParameterizedGet(Integer studId) throws Exception{
		System.out.println("** Parameterized get **");
		HttpEntity respEntity = null;
		String url = String.format("http://localhost:6070/students/%d", studId);
		HttpGet getReq = new HttpGet(url);
		
		try (CloseableHttpResponse response =  httpClient.execute(getReq)) {
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				System.out.println("Received non 200 status code : " + response.getStatusLine().getStatusCode());
				return;
			}
			respEntity = response.getEntity();
			Student studs = new ObjectMapper().readValue(EntityUtils.toString(respEntity), Student.class);
			System.out.println(studs);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			EntityUtils.consume(respEntity);
		}
		
	}
	
	public static void sendPost() throws Exception {
		CloseableHttpResponse resp = null;
		HttpPost post = new HttpPost("http://localhost:6070/students");
		
		try {
			Student s = new Student(null, "new one", "awsomo");
			
			StringEntity entity = new StringEntity(new ObjectMapper().writeValueAsString(s), ContentType.APPLICATION_JSON);
			
			post.setEntity(entity);
			resp = httpClient.execute(post);

			if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				System.out.println("Error while creating student with status code " + resp.getStatusLine().getStatusCode());
			} else {
				System.out.println("Student created");
			}
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(resp != null) {
				EntityUtils.consume(resp.getEntity());
				resp.close();
			}
		}
	}
	
	static void deleteSomething(Integer id) throws Exception {
		HttpDelete delete = new HttpDelete(String.format("http://localhost:6070/students/%d", id));
		try (CloseableHttpResponse resp = httpClient.execute(delete)) {
			if(resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				System.out.println("Error in deleting the student " + resp.getStatusLine().getStatusCode());
			}
			EntityUtils.consumeQuietly(resp.getEntity());
		}
	}

	static void update(Student s) throws Exception {
		HttpPut put = new HttpPut("http://localhost:6070/students/");
		CloseableHttpResponse resp = null;
		try {
			put.setEntity(new StringEntity(new ObjectMapper().writeValueAsString(s), ContentType.APPLICATION_JSON));

			resp = httpClient.execute(put);

			if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				System.out.println("Error while updating student with status code " + resp.getStatusLine().getStatusCode());
			} else {
				System.out.println("Student updated");
			}

		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(resp != null) {
				EntityUtils.consume(resp.getEntity());
				resp.close();
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		update(new Student(901, "sravya", "nedur"));
		//deleteSomething(155);
		
		//sendPost();
		//sendGetReq();
		//sendParameterizedGet(14);
	}

}
