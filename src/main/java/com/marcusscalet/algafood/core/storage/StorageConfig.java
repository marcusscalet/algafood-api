package com.marcusscalet.algafood.core.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.marcusscalet.algafood.core.storage.StorageProperties.StorageType;
import com.marcusscalet.algafood.domain.service.ImageStorageService;
import com.marcusscalet.algafood.infrastructure.service.storage.LocalImageStorageService;
import com.marcusscalet.algafood.infrastructure.service.storage.S3ImageStorageService;

@Configuration
public class StorageConfig {

	@Autowired
	private StorageProperties storageProperties;
	
	@Bean
	@ConditionalOnProperty(name = "algafood.storage.storage-type", havingValue ="s3")
	public AmazonS3 amazonS3() {
		var credentials = new BasicAWSCredentials(
				storageProperties.getS3().getAccessKeyId(),
				storageProperties.getS3().getSecretAccessKey());
		
		return AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(storageProperties.getS3().getRegion())
				.build();
	}
	
	@Bean
	public ImageStorageService imageStorageService() {

		if(StorageType.S3.equals(storageProperties.getStorageType())) {
			return new S3ImageStorageService();
		} else {
			return new LocalImageStorageService();
		}
	}
}
