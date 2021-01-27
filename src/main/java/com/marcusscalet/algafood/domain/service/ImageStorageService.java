package com.marcusscalet.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface ImageStorageService {

	void store(NewImage newImage);

	void remove(String fileName);

	default void substitute(String oldFileName, NewImage newImage) {
		this.store(newImage);

		if (oldFileName != null) {
			this.remove(oldFileName);
		}
	}

	default String generateFileName(String originalName) {
		return UUID.randomUUID().toString() + " " + originalName;
	}

	@Builder
	@Getter
	class NewImage {
		private String fileName;
		private InputStream inputStream;
	}
}
