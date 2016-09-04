package com.kibou;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class ClassResourceFounder {
	public static void main(String[] args) throws IOException {
		String resourcePath = "META-INF/spring.factories";
		Enumeration<URL> resources = ClassLoader.getSystemResources(resourcePath);
		while(resources.hasMoreElements()){
			System.out.println(resources.nextElement());
		}
		
	}
}
