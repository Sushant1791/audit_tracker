package com.audit.web.rest.util;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.audit.service.FileStorageService;
import com.audit.service.dto.UploadFileResponse;

public class FileUploader {
    
	private final FileStorageService fileStorageService;

	public FileUploader(FileStorageService fileStorageService) {
		this.fileStorageService = fileStorageService;
	}
    
    public  UploadFileResponse uploadFile(MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    public  List<UploadFileResponse> uploadMultipleFiles( List<MultipartFile> files) {
        return files
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }
}
