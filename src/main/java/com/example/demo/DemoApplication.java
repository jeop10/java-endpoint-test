package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.util.Collections;

@SpringBootApplication
public class DemoApplication {

	public static final int TITLE_MSG = 1;
	public static final int INFO_MSG = 2;

	public static void logMessage(int msgType, String message) {
		switch(msgType) {
			case TITLE_MSG:
				System.out.println("--> "+message);
				break;
			case INFO_MSG:
			default:
				System.out.println(message);
				break;
		}
	}

	public static Configclass getConfig() {
		logMessage(TITLE_MSG, "Getting the config:");
		String configFilePath = "config.yaml";
		//Yaml yaml = new Yaml();
		LoaderOptions loaderOptions = new LoaderOptions();
		Yaml yaml = new Yaml(new Constructor(Configclass.class, loaderOptions));

		// Check that the config file exists

		try {
			InputStream inputStream = DemoApplication.class
					.getClassLoader()
					.getResourceAsStream(configFilePath);
			return yaml.load(inputStream);
		} catch (Exception e) {
			System.out.println(e);
			throw new RuntimeException("Missing or invalid config file: config.yaml");
		}

	}

	public static void main(String[] args) {

		Configclass config = getConfig();
		logMessage(INFO_MSG, "Config port: "+config.getRest().getPort());
		logMessage(INFO_MSG, "Config input: "+config.getRest().getInput());
		logMessage(INFO_MSG, "Config output: "+config.getRest().getOutput());



//		SpringApplication.run(DemoApplication.class, args);
		SpringApplication app = new SpringApplication(DemoApplication.class);
		app.setDefaultProperties(Collections
				.singletonMap("server.port", config.getRest().getPort()));
		app.run(args);
	}

}
