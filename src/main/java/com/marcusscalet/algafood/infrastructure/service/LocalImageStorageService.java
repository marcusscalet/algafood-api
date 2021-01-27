package com.marcusscalet.algafood.infrastructure.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.marcusscalet.algafood.domain.exception.StorageException;
import com.marcusscalet.algafood.domain.service.ImageStorageService;

@Service
public class LocalImageStorageService implements ImageStorageService {

	@Value("${algafood.storage.local.image-directory}")
	private Path imagesDirectory;
	
	@Override
	public void store(NewImage newImage) {

		try {
			Path filePath = getFilePath(newImage.getFileName());

			FileCopyUtils.copy(newImage.getInputStream(), Files.newOutputStream(filePath));
		} catch (IOException e) {
			throw new StorageException("Não foi possível armazenar arquivo.", e);
		}
	}
	
	private Path getFilePath(String fileName) {
		return imagesDirectory.resolve(Path.of(fileName));
	}

}
