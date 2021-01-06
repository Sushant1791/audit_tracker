package com.audit.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

@Service
@Transactional
public class FileOperationService {
	private final AuditPlanMasterService auditPlanService;
	private final AuditObjectiveService auditObjectiveService;
	private final UserService userService;
	private final AuditTestingPlanService auditTestingPlanService;
	
	public FileOperationService(AuditPlanMasterService auditPlanService, AuditObjectiveService auditObjectiveService,
			UserService userService, AuditTestingPlanService auditTestingPlanService) {
		this.auditPlanService = auditPlanService;
		this.auditObjectiveService = auditObjectiveService;
		this.userService = userService;
		this.auditTestingPlanService = auditTestingPlanService;
	}

	public StringBuilder generateContent(String module) {
		StringBuilder content = null;
		switch (module) {
		case "AuditPlan":
			content = generateAuditPlanContent();
			break;
		case "Procedure":
			content = generateAuditProcedureContent();
			break;
		case "User":
			content = getUserContent();
			break;
		case "Testing":
			content = getTestingPlanContent();
		default:
			break;
		}
		return content;
	}

	public StringBuilder generateAuditPlanContent() {
		StringBuilder filecontent = new StringBuilder("ID, AUDIT ENTITY, ASSIGN TO,START DATE, END DATE,REMARKS\n");
		auditPlanService.getAllPlans().forEach(obj -> {
			filecontent.append(obj.getId()).append(",").append(obj.getAuditPlanEntity()).append(",")
					.append(StringUtils
							.defaultString(obj.getAssignTo() != null ? obj.getAssignTo().getFirstName() : ""))
					.append(",").append(obj.getStartDate()).append(",").append(obj.getEndDate()).append(",")
					.append(obj.getRemarks()).append("\n");
		});
		return filecontent;
	}

	public StringBuilder generateAuditProcedureContent() {
		StringBuilder filecontent = new StringBuilder("ID, PROCEDURE TITLE, DESCRIPTION, DEPARTMENT NAME\n");
		auditObjectiveService.getAllObjective().forEach(obj -> {
			filecontent.append(obj.getId()).append(",").append(obj.getProcedureTitle()).append(",")
					.append(obj.getProcedureDescription()).append(",").append(obj.getDepartmentName()).append("\n");
		});
		return filecontent;
	}

	public StringBuilder getUserContent() {
		StringBuilder filecontent = new StringBuilder("ID, FULLNAME, AUTHORITY\n");
		userService.getUserForDropDown().forEach(obj -> {
			filecontent.append(obj.getId()).append(",").append(obj.getFirstName() + " " + obj.getLastName()).append(",")
					.append(obj.getAuthorities()).append("\n");
		});
		return filecontent;
	}

	public StringBuilder getTestingPlanContent() {
		StringBuilder fileContent = new StringBuilder(
				"ID, RISKAREA, EXPECTED REVERTED DATE, AUDITPLAN ENTITY, AUDITEE NAME\n");
		auditTestingPlanService.getAllAuditTestingPlan(0).forEach(obj -> {
			fileContent.append(obj.getId()).append(",").append(obj.getRiskAreaId()).append(",")
					.append(obj.getExpectedRevertDate()).append(obj.getAuditPlanId()).append(",")
					.append(obj.getAuditeeName()).append("\n");
		});
		return fileContent;
	}

	public void fileUpload(List<MultipartFile> files, String folder) throws IllegalStateException, IOException {
		File currentDirFile = new File("");
		String serverPath = currentDirFile.getAbsolutePath() + File.separator + "upload" + File.separator;
		if (!files.isEmpty()) {
			String saveDirectory = serverPath + folder + File.separator;
			File directory = new File(saveDirectory);
			if (!directory.isDirectory()) {
				directory.mkdirs();
			}
			List<String> fileNames = new ArrayList<String>();
			for (MultipartFile multipartFile : files) {
				String fileName = multipartFile.getOriginalFilename();
				if (!"".equalsIgnoreCase(fileName)) {
					multipartFile.transferTo(new File(saveDirectory + fileName));
					fileNames.add(fileName);
				}
			}
		}
	}
	
	public static List<String> fetchFiles(String folder, Long id) throws IllegalStateException, IOException {
		File currentDirFile = new File("");
		File allFiles = new File(currentDirFile.getAbsolutePath() + "/upload/" + folder + "/" + id);
		if (allFiles.isDirectory()) {
			return Arrays.asList(allFiles.listFiles()).stream().map(f -> f.getName())
					.collect(Collectors.toList());
		} else {
			return new ArrayList<>();
		}
	}
}
