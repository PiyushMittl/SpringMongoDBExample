package com.ituple.execute.mongoscript.throughjs;

import java.io.BufferedReader;
import java.io.FileReader;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.script.ExecutableMongoScript;
import org.springframework.data.mongodb.core.script.NamedMongoScript;

import com.ituple.execute.mongoscript.config.MongoConfig;

public class ExecuteMongoScriptThrouhJsFile {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(MongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations) context.getBean("mongoTemplate");
		try {
			Resource resource = context.getResource("fetchTaskForCalendarView.js");
			StringBuilder text = new StringBuilder();
			BufferedReader br = new BufferedReader(new FileReader(resource.getFile()));
			/*** pick file other that resource file ***/
			/*
			 * StringBuilder text = new StringBuilder(); BufferedReader br = new
			 * BufferedReader(new FileReader(new File("C:\\piyush\\tun.js")));
			 */
			try {
				while (true) {
					String line = br.readLine();
					if (line == null)
						break;
					text.append(line).append("\n");
				}
			} finally {
				try {
					br.close();
				} catch (Exception ignore) {
					System.out.println(ignore);
				}
			}
			ExecutableMongoScript echoScript = new ExecutableMongoScript(text.toString());
			/*** echo is the name to register our script in mongo db ***/
			mongoOperation.scriptOps().register(new NamedMongoScript("echo", echoScript));
			// testing for loggedin user
			Object ob = mongoOperation.scriptOps().call("echo", "", "", "", "2016-11-01T00:00:00",
					"2016-11-31T23:59:59", "57decd89d8117015ec085f93");
			System.out.println(ob);
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("script executed");
	}

}