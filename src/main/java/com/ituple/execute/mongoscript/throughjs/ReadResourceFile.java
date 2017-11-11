package com.ituple.execute.mongoscript.throughjs;

import java.io.BufferedReader;
import java.io.FileReader;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoOperations;

import com.ituple.execute.mongoscript.config.MongoConfig;

public class ReadResourceFile {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(MongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations) context.getBean("mongoTemplate");
		try {
			Resource resource = context.getResource("tun.js");
			StringBuilder text = new StringBuilder();
			BufferedReader br = new BufferedReader(new FileReader(resource.getFile()));

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

					System.out.println(text);
				}
			}
			System.out.println(text);
		} catch (Exception e) {
			System.out.println(e);
		}

	}
}
