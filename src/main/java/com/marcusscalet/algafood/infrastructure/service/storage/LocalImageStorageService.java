package com.marcusscalet.algafood.infrastructure.service.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import com.marcusscalet.algafood.core.storage.StorageProperties;
import com.marcusscalet.algafood.domain.service.ImageStorageService;

public class LocalImageStorageService implements ImageStorageService {

	@Autowired
	private StorageProperties storageProperties;
	
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
		return storageProperties.getLocal()
				.getImageDirectory()
				.resolve(Path.of(fileName));
	}

	@Override
	public void remove(String fileName) {
		
		try {
			Path filePath = getFilePath(fileName);
			
			Files.deleteIfExists(filePath);
		} catch (IOException e) {
			throw new StorageException("Não foi possível excluir arquivo.", e);
		}
	}

	@Override
	public RecoveredImage recover(String fileName) {
		
		try {
	        Path arquivoPath = getFilePath(fileName);

	        RecoveredImage recoveredImage = RecoveredImage.builder()
	        		.inputStream(Files.newInputStream(arquivoPath)).build();
	        
	        return recoveredImage;
	        
	    } catch (Exception e) {
	        throw new StorageException("Não foi possível recuperar arquivo.", e);
	    }
	}

}
