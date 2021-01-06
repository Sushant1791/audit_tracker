package com.audit.web.rest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.audit.domain.AuditProcedureMaster;
import com.audit.service.AuditObjectiveService;
import com.audit.service.dto.AuditProcedureMasterDto;
import com.audit.service.dto.AuditProcedureObjectiveDto;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class AuditObjectiveResource {

	private final Logger log = LoggerFactory.getLogger(AuditObjectiveResource.class);

	private final AuditObjectiveService auditObjectiveService;

	ObjectMapper mapper = new ObjectMapper();

	public AuditObjectiveResource(AuditObjectiveService auditObjectiveService) {
		this.auditObjectiveService = auditObjectiveService;
	}

	@GetMapping("/getAllProcedure")
	public List<AuditProcedureMasterDto> getAllProcedureMaster() throws URISyntaxException {
		log.debug("REST request to get All Objective : {}");
		return auditObjectiveService.getAllObjective();
	}

	@PostMapping("/createProcedure")
	public ResponseEntity<Void> createProcedure(@Valid @RequestBody AuditProcedureMasterDto auditProcedureMaster)
			throws URISyntaxException {
		auditObjectiveService.saveObjective(auditProcedureMaster);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/auditObjective/update")
	public ResponseEntity<Void> updateObjective(
			@Valid @RequestBody AuditProcedureObjectiveDto auditProcedureObjectiveDto) {
		auditObjectiveService.updateObjective(auditProcedureObjectiveDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/updateStatus/{id}")
	public ResponseEntity<Void> setActiveOrInActiveObjective(@PathVariable("id") Long id,
			@RequestParam("status") boolean status) throws URISyntaxException {
		auditObjectiveService.setActiveDeactiveProcedure(id, status);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/updateObjectiveStatus/{id}")
	public ResponseEntity<Void> updateObjectiveStatus(@PathVariable("id") Long id,
			@RequestParam("status") boolean status) throws URISyntaxException {
		auditObjectiveService.setActiveDeactiveObjective(id, status);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/deleteProcedure/{id}")
	public ResponseEntity<Void> deleteProcedure(@PathVariable("id") Long id) throws URISyntaxException {
		auditObjectiveService.deleteProcedure(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/deleteObjective/{id}")
	public ResponseEntity<Void> deleteObjective(@PathVariable("id") Long id) throws URISyntaxException {
		auditObjectiveService.deleteObjectie(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/getObjectiveById/{id}")
	public ResponseEntity<AuditProcedureMasterDto> getObjectiveByProcedureId(@PathVariable("id") Long id)
			throws URISyntaxException {
		AuditProcedureMasterDto auditProcedureMasterDto = auditObjectiveService.getProcedureById(id);
		return new ResponseEntity<>(auditProcedureMasterDto, HttpStatus.OK);
	}

	@PostMapping("/get/allProcedure")
	public List<AuditProcedureMasterDto> getAllUsers(@RequestParam("searchUser") String _searchUser)
			throws JsonParseException, JsonMappingException, IOException {
		JsonNode jsonNode = mapper.readTree(_searchUser);
		String title = jsonNode.findValue("title").textValue();
		String description = jsonNode.findValue("description").textValue();
		if(StringUtils.isBlank(title) && StringUtils.isBlank(title))
		return auditObjectiveService.getAllObjective();
		return auditObjectiveService.findByProcedureTitleOrProcedureDescription(title, description);
	}

	@PostMapping("/auditobjective/bulkupdate")
	public ResponseEntity<Void> bulkUpdateAuditPlan(@Valid @RequestBody List<AuditProcedureMasterDto> auditEntities) {
		try {
			auditObjectiveService.bulkUpdateAuditEntities(auditEntities);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping("/entity/get/all")
	public Map<String,Object> getAllUsers(@RequestParam("searchProcedure") String _searchProcedure,@RequestParam("page") String page) throws JsonParseException, JsonMappingException, IOException {
		System.out.println("_searchProcedure : "+_searchProcedure);
		AuditProcedureMaster search_procedure 	= mapper.readValue(_searchProcedure, AuditProcedureMaster.class);
		Integer _page	= mapper.readValue(page, Integer.class);
		search_procedure = this.setProcedureModel(search_procedure);
		return auditObjectiveService.getIntialObjectives(search_procedure, _page);
	}

    @PostMapping("/entity/get/next")
	public List<AuditProcedureMasterDto> nextJobs(@RequestParam("searchProcedure") String _searchProcedure,@RequestParam("page") String _page) throws JsonParseException, JsonMappingException, IOException {
		System.out.println("_searchProcedure : "+_searchProcedure);
		AuditProcedureMaster search_procedure 	= mapper.readValue(_searchProcedure, AuditProcedureMaster.class);
		Integer page	= mapper.readValue(_page, Integer.class);
		search_procedure = this.setProcedureModel(search_procedure);
		return auditObjectiveService.nextObjectives(search_procedure, page);
	}
    
    public AuditProcedureMaster setProcedureModel(AuditProcedureMaster auditProcedureMaster) {
    	
    	if(auditProcedureMaster.getProcedureTitle()!=null && auditProcedureMaster.getProcedureTitle().equalsIgnoreCase("")) {
    		auditProcedureMaster.setProcedureTitle(null);
    	}
    	if(auditProcedureMaster.getProcedureDescription()!=null && auditProcedureMaster.getProcedureDescription().equalsIgnoreCase("")) {
    		auditProcedureMaster.setProcedureDescription(null);
    	}
    	auditProcedureMaster.setId(null);
    	auditProcedureMaster.setActive(null);
    	auditProcedureMaster.setAuditProcObjectiveMaster(null);
    	auditProcedureMaster.setAuditProcObjectiveMasterCollection(null);
    	auditProcedureMaster.setCostCentreId(null);
    	auditProcedureMaster.setCreatedBy(null);
    	auditProcedureMaster.setDeptId(null);    	
    	auditProcedureMaster.setLastModifiedDate(null);
    	auditProcedureMaster.setLastModifiedBy(null);
    	auditProcedureMaster.setCreatedDate(null);
    	auditProcedureMaster.setCreatedDate(null);
    	
		return auditProcedureMaster;
	}
	
}
