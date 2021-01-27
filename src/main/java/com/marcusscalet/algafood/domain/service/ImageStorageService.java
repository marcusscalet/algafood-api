package com.marcusscalet.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;


public interface ImageStorageService {

	void store(NewImage newImage);

	default String generateFileName(String originalName) {
		return UUID.randomUUID().toString() + " " + originalName;
	}
	
	@Builder
	@Getter
	class NewImage{
		private String fileName;
		private InputStream inputStream;
	}
}
