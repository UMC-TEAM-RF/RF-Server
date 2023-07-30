package org.rf.rfserver;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

@SpringBootApplication
public class RfserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(RfserverApplication.class, args);
	}

	//파일 업로드 처리를 위한 설정
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize(DataSize.ofBytes(5242880)); //maxFileSize 5MB로 제한
		factory.setMaxRequestSize(DataSize.ofBytes(5242880)); //maxRequestSize 5MB로 제한
		return factory.createMultipartConfig();
	}

	@Bean
	public MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}
}
