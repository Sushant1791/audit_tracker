package com.audit.web.rest;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.audit.domain.AuditTestingPlanMaster;
import com.audit.domain.DeptMaster;
import com.audit.domain.LocationMaster;
import com.audit.service.AuditTestingPlanService;
import com.audit.service.FileOperationService;
import com.audit.service.dto.AuditPlanTestingSearchDTO;
import com.audit.service.dto.AuditTestingPlanMasterDTO;
import com.audit.specification.AuditTestingPlanSpecification;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class AuditTestingPlanResource {

	private final AuditTestingPlanService auditTestingPlanService;
	private FileOperationService fileOperationService;
	ObjectMapper mapper = new ObjectMapper();
	

	public AuditTestingPlanResource(AuditTestingPlanService auditTestingPlanService,FileOperationService fileOperationService) {
		super();
		this.auditTestingPlanService = auditTestingPlanService;
		this.fileOperationService = fileOperationService;
	}
	
	@PostMapping("/getAllAuditTestingPlan")
    public List<AuditTestingPlanMasterDTO> getAllProcedureMaster(@RequestParam("searchAuditPlan") String _searchAudit,
			@RequestParam("page") String _page) throws URISyntaxException, JsonParseException, JsonMappingException, IOException {
		Integer page = mapper.readValue(_page, Integer.class);
       return auditTestingPlanService.getAllAuditTestingPlan(page);
    }
	
	@PostMapping("/createAuditTestingPlan")
	public ResponseEntity<Void> createAuditTesting(
			@RequestParam(name = "files", required = false) List<MultipartFile> files,
			@RequestParam("auditTesting") String auditdata) throws URISyntaxException {
		try {
			ObjectMapper jsonParserClient = new ObjectMapper();
			AuditTestingPlanMasterDTO dto = jsonParserClient.readValue(auditdata, AuditTestingPlanMasterDTO.class);
			AuditTestingPlanMaster auditTestingPlanMaster = auditTestingPlanService.saveAuditPlanTesting(dto);
			if (!files.isEmpty()) {
				String folderName = "AuditTestingplan" + "/" + auditTestingPlanMaster.getId();
				fileOperationService.fileUpload(files, folderName);
			}
			auditTestingPlanService.sendNotificationReminderToAuditee(auditTestingPlanMaster);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/audittesting/bulkupdate")
	public ResponseEntity<Void> bulkUpdateAuditPlan(@Valid @RequestBody List<AuditTestingPlanMasterDTO> AuditTestingPlanMasterDTOs) {
		try {
			auditTestingPlanService.bulkUpdateAuditTests(AuditTestingPlanMasterDTOs);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
	
	@GetMapping("/deleteAuditTestingPlan/{id}")
	public ResponseEntity<Void> deleteProcedure(@PathVariable("id") Long id) throws URISyntaxException{
		try {
//			auditTestingPlanService.deleteAuditTestingPlan(id);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/get/testing/plan/{id}")
    public AuditTestingPlanMasterDTO getTestingById(@PathVariable("id") Long id) throws URISyntaxException {

       return auditTestingPlanService.getAuditTestingById(id);
    }
	
	@GetMapping("/get/testing/plans/{planfor}")
	public List<AuditTestingPlanMasterDTO> getRoleBasedTestingPlans(@PathVariable("planfor") String planFor) throws URISyntaxException
	{
		return auditTestingPlanService.fetchRoleBasedAuditTestingPlans(planFor);
	}
	
	
	/*
	 * @GetMapping("/auditeeResponse") public List<AuditTestingPlanMaster>
	 * auditeeResponse() throws URISyntaxException { return
	 * auditTestingPlanService.getAuditeeResponse(); }
	 */
	
	@PostMapping("/update/auditee")
	public ResponseEntity<Void> updateAuditeeResponse(
			@RequestParam(name = "files", required = false) List<MultipartFile> files,
			@RequestParam(name="auditeeResponse", required=true) String auditTestingPlan) {
		try {
			ObjectMapper jsonParserClient = new ObjectMapper();
			AuditTestingPlanMasterDTO dto;
			dto = jsonParserClient.readValue(auditTestingPlan, AuditTestingPlanMasterDTO.class);
			auditTestingPlanService.updateAuditeeResponse(dto);
			if (!files.isEmpty()) {
				String folderName = "AuditTestingplan" + File.separator + dto.getId();
				fileOperationService.fileUpload(files, folderName);
			}
			auditTestingPlanService.sendEmailToAuditeeForResponse(auditTestingPlanService.getAuditTestingById(dto.getId()));
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/auditreport/{department}/{month}/{year}/{location}")
	public List<AuditTestingPlanMasterDTO> generateAuditReport(@PathVariable("department") Long deptId, @PathVariable("month") Long month,
			@PathVariable("year") Long year, @PathVariable("location") Long locationId) {
		DeptMaster department = new DeptMaster();
		department.setId(deptId);
		LocationMaster location = new LocationMaster();
		location.setId(locationId);
		return auditTestingPlanService.generateReport(month, year, department,location);
	}
	
	@PostMapping("/lock/audittesting")
	public ResponseEntity<Void> lockAuditTesting(
			@Valid @RequestBody AuditTestingPlanMasterDTO auditTestingPlanMasterDTO) {
		try {
			AuditTestingPlanMasterDTO auditTestingExistingDTO = auditTestingPlanService
					.getAuditTestingById(auditTestingPlanMasterDTO.getId());
			auditTestingExistingDTO.setLocked(auditTestingPlanMasterDTO.getLocked());
			auditTestingExistingDTO.setResponded(true);
			auditTestingPlanService.saveAuditPlanTesting(auditTestingExistingDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping("/testing/auditee/reminder/{id}")
	public void sendReminder(@PathVariable("id") Long id) throws URISyntaxException {
		auditTestingPlanService.sendEmailReminderToAuditee(id);
	}
	
	@PostMapping("/get/allTestingPlan")
	public Map<String,Object> getAllTestingPlans(@RequestParam("searchTestingPlan") String _auditPlanTestingSearchDTO,@RequestParam("page") String page) throws JsonParseException, JsonMappingException, IOException, InterruptedException, ParseException {
		
		System.out.println("_auditPlanTestingSearchDTO : "+_auditPlanTestingSearchDTO);
		AuditPlanTestingSearchDTO auditPlanTestingSearchDTO 	= mapper.readValue(_auditPlanTestingSearchDTO, AuditPlanTestingSearchDTO.class);
		Integer _page	= mapper.readValue(page, Integer.class);
		AuditTestingPlanSpecification specification = auditTestingPlanService.getAuditTestingPlanSpecificationForAuditeeResponse(auditPlanTestingSearchDTO);
		return auditTestingPlanService.getAllTestingPlans(specification, _page);
	}
	    
    @PostMapping("/get/nextTestingPlan")
	public List<AuditTestingPlanMasterDTO> nextTestingPlan(@RequestParam("searchTestingPlan") String _auditPlanTestingSearchDTO,@RequestParam("page") String page)  throws JsonParseException, JsonMappingException, IOException, InterruptedException, ParseException {
    	System.out.println("_auditPlanTestingSearchDTO : "+_auditPlanTestingSearchDTO);
		AuditPlanTestingSearchDTO auditPlanTestingSearchDTO 	= mapper.readValue(_auditPlanTestingSearchDTO, AuditPlanTestingSearchDTO.class);
		Integer _page	= mapper.readValue(page, Integer.class);
		AuditTestingPlanSpecification specification = auditTestingPlanService.getAuditTestingPlanSpecificationForAuditeeResponse(auditPlanTestingSearchDTO);
		return auditTestingPlanService.nextTestingPlans(specification, _page);
	}

    
    @PostMapping("/get/allTestingPlanForTestingScreen")
	public Map<String,Object> getAllTestingPlansForTestingScreen(@RequestParam("searchTestingPlan") String _auditPlanTestingSearchDTO,@RequestParam("page") String page) throws JsonParseException, JsonMappingException, IOException, InterruptedException, ParseException {
		System.out.println("_auditPlanTestingSearchDTO : "+_auditPlanTestingSearchDTO);
		AuditPlanTestingSearchDTO auditPlanTestingSearchDTO 	= mapper.readValue(_auditPlanTestingSearchDTO, AuditPlanTestingSearchDTO.class);
		Integer _page	= mapper.readValue(page, Integer.class);
		AuditTestingPlanSpecification specification = auditTestingPlanService.getAuditTestingPlanSpecificationForAuditTestingScreen(auditPlanTestingSearchDTO);
		return auditTestingPlanService.getAllTestingPlans(specification, _page);
	}
    
    @PostMapping("/get/nextTestingPlanForTestingScreen")
	public List<AuditTestingPlanMasterDTO> nextTestingPlanForTestingScreen(@RequestParam("searchTestingPlan") String _auditPlanTestingSearchDTO,@RequestParam("page") String page)  throws JsonParseException, JsonMappingException, IOException, InterruptedException, ParseException {
    	System.out.println("_auditPlanTestingSearchDTO : "+_auditPlanTestingSearchDTO);
		AuditPlanTestingSearchDTO auditPlanTestingSearchDTO 	= mapper.readValue(_auditPlanTestingSearchDTO, AuditPlanTestingSearchDTO.class);
		Integer _page	= mapper.readValue(page, Integer.class);
		AuditTestingPlanSpecification specification = auditTestingPlanService.getAuditTestingPlanSpecificationForAuditTestingScreen(auditPlanTestingSearchDTO);
		return auditTestingPlanService.nextTestingPlans(specification, _page);
	}
    
    
    @GetMapping("/testing/dashboard/counts")
    public HashMap<String, Long> getDashboardStats(){
    	return auditTestingPlanService.getDashBoardCounts();
    }
}

