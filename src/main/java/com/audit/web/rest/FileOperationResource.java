package com.audit.web.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.audit.security.AuthoritiesConstants;
import com.audit.service.FileOperationService;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class FileOperationResource {

	private Logger logger = LoggerFactory.getLogger(FileOperationResource.class);
	private static final String FILEPATH = "\\src\\main\\resources\\tempfiles\\";

	private FileOperationService fileOperationService;

	public FileOperationResource(FileOperationService fileOperationService) {
		this.fileOperationService = fileOperationService;
	}

	@GetMapping(value = "/file/download/{module}")
	@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
	public ResponseEntity<Object> downloadFile(@PathVariable String module) throws IOException {
		FileWriter filewriter = null;
		try {
			
			File currentDirFile = new File("");

			StringBuilder filecontent = fileOperationService.generateContent(module);
			String filename = currentDirFile.getAbsolutePath()+FILEPATH + "temp.csv";
			 try {
		            Files.createDirectories(Paths.get(currentDirFile.getAbsolutePath() + FILEPATH)
		                    .toAbsolutePath().normalize());
		            Files.createFile(Paths.get(filename)
		                    .toAbsolutePath().normalize());
		        } catch (Exception ex) {
		        	logger.error("File already exists", ex.getMessage());
		        }
			 try {
		            Files.write(Paths.get(filename)
		                    .toAbsolutePath().normalize(), filecontent.toString().getBytes());
		        } catch (IOException e) {
		            e.printStackTrace();
		        }

			File file = new File(Paths.get(filename).toAbsolutePath().toString());

			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");

			ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length())
					.contentType(MediaType.parseMediaType("application/text")).body(resource);
			return responseEntity;
		} catch (Exception e) {
			logger.error("Error while downloading file ::{0} ", e.getMessage());
			return new ResponseEntity<>("error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			if (filewriter != null)
				filewriter.close();
		}
	}

}
