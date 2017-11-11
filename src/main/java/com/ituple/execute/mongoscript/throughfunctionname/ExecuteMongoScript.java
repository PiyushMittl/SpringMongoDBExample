package com.ituple.execute.mongoscript.throughfunctionname;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import com.ituple.execute.mongoscript.config.MongoConfig;

public class ExecuteMongoScript {
	public static void main(String[] args) {
		// For XML
		//ApplicationContext ctx = new GenericXmlApplicationContext("SpringConfig.xml");
		// For Annotation
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
		Object ob=mongoOperation.scriptOps().call("filterredTask","57ac5742b8840200cd8f654c","57ac583cb8840200cd8f654f","2016-10-01T00:00:00.480Z","2016-11-31T23:59:59.480Z");
		System.out.println(ob);
	}
}