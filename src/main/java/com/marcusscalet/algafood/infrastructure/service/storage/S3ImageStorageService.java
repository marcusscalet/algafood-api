package com.marcusscalet.algafood.infrastructure.service.storage;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.marcusscalet.algafood.core.storage.StorageProperties;
import com.marcusscalet.algafood.domain.service.ImageStorageService;

public class S3ImageStorageService implements ImageStorageService {

	@Autowired
	private AmazonS3 amazonS3;

	@Autowired
	private StorageProperties storageProperties;

	@Override
	public void store(NewImage newImage) {
		try {
			String filePath = getFilePath(newImage.getFileName());

			var objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(newImage.getContentType());
			
			var putObjectRequest = new PutObjectRequest(
					storageProperties.getS3().getBucket(),
					filePath,
					newImage.getInputStream(),
					objectMetadata)
					.withCannedAcl(CannedAccessControlList.PublicRead);

			amazonS3.putObject(putObjectRequest);
		
		} catch (Exception e) {
			throw new StorageException("Não foi possível enviar arquivo para Amazon S3.", e);
		}
	}

	@Override
	public void remove(String fileName) {
		try {

			String filePath = getFilePath(fileName);
			
		var delete = new DeleteObjectRequest(
				storageProperties.getS3().getBucket(), filePath);
				
				amazonS3.deleteObject(delete);
				
		} catch (Exception e) {
			throw new StorageException("Não foi possível excluir arquivo na Amazon S3.", e);
		}
	}

	@Override
	public RecoveredImage recover(String fileName) {
		String filePath = getFilePath(fileName);
		
		URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), filePath);
		
		return RecoveredImage.builder()
				.url(url.toString()).build();
	}
	
	private String getFilePath(String fileName) {
		return String.format("%s/%s", storageProperties.getS3().getDirectoryImages(), fileName);
	}


}
