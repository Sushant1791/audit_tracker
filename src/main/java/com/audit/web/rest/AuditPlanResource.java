package com.audit.web.rest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.audit.common.enums.ActionTakenEnum;
import com.audit.common.enums.IntervalEnum;
import com.audit.common.enums.RiskAreaEnum;
import com.audit.common.enums.TestingResultEnum;
import com.audit.domain.AuditPlanMaster;
import com.audit.domain.User;
import com.audit.repository.UserRepository;
import com.audit.security.AuthoritiesConstants;
import com.audit.security.SecurityUtils;
import com.audit.service.AuditPlanMasterService;
import com.audit.service.dto.ActionTakenDTO;
import com.audit.service.dto.AuditPlanMasterDTO;
import com.audit.service.dto.AuditPlanSearchDTO;
import com.audit.service.dto.IntervalDTO;
import com.audit.service.dto.RiskAreaDTO;
import com.audit.service.dto.TestingResultDTO;
import com.audit.specification.AuditPlanSpecification;
import com.audit.web.rest.util.HeaderUtil;
import com.audit.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.jhipster.web.util.ResponseUtil;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AuditPlanResource {

	private Logger logger = LoggerFactory.getLogger(AuditPlanResource.class);

	private AuditPlanMasterService auditPlanService;
	private final UserRepository userRepository;
	ObjectMapper mapper = new ObjectMapper();

	public AuditPlanResource(AuditPlanMasterService auditPlanService, UserRepository userRepository) {
		this.auditPlanService = auditPlanService;
		this.userRepository = userRepository;
	}

	/**
	 * POST /auditplan/createplan
	 * 
	 * This method creates new audit plan.
	 * 
	 * @param auditPlanDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/auditplan/createplan")
	@Timed
	public ResponseEntity<AuditPlanMaster> createAuditPlan(@Valid @RequestBody AuditPlanMasterDTO auditPlanDTO)
			throws Exception {
		AuditPlanMaster auditplan = auditPlanService.createAuditPlanMaster(auditPlanDTO);
		Optional<String> loginName = SecurityUtils.getCurrentUserLogin();
		Optional<User> user = userRepository.findOneByLogin(loginName.get());
		AuditPlanMasterDTO auditPlanMasterDTO = auditPlanService.getPlanById(auditplan.getId());
		auditPlanService.sendAuditPlanEmail(auditPlanMasterDTO, user.get());
		logger.info("Audit Plan Create :: " + auditPlanDTO);
		return ResponseEntity.ok().build();
	}

	/**
	 * POST /auditplan/updateplan
	 * 
	 * This method existing audit plan.
	 * 
	 * @param auditPlanDTO
	 * @return
	 */
	@PostMapping("/auditplan/updateplan")
	@Timed
	public ResponseEntity<AuditPlanMasterDTO> updateAuditPlan(@Valid @RequestBody AuditPlanMasterDTO auditPlanDTO) {
		logger.debug("REST request to update audit plan : {}", auditPlanDTO);

		Optional<AuditPlanMasterDTO> auditPlan = auditPlanService.updateAuditPlan(auditPlanDTO);

		return ResponseUtil.wrapOrNotFound(auditPlan,
				HeaderUtil.createAlert("userManagement.updated", auditPlan.get().getRemarks()));
	}

	/*
	 * GET /auditplan/auditplans
	 * 
	 * This will return all the audit plan list 16991 74898
	 */
	@GetMapping("/auditplan/auditplans")
	@Timed
	public ResponseEntity<List<AuditPlanMasterDTO>> getAllUsers(Pageable pageable) {
		final Page<AuditPlanMasterDTO> page = auditPlanService.getAllPlans(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/auditplan/auditplans");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}
	
	@GetMapping("/auditplan/user/auditplans")
	@Timed
	public List<AuditPlanMasterDTO> getAuditsForUsers() {
		return auditPlanService.getAllAuditorPlans();
	}

	@DeleteMapping("/users/id:")
	@Timed
	@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		logger.debug("REST request to delete User: {}", id);
		auditPlanService.deleteAuditPlan(id);
		return ResponseEntity.ok().headers(HeaderUtil.createAlert("userManagement.deleted", "Id " + String.valueOf(id)))
				.build();

	}

	@PostMapping("auditplan/get/allplans")
	@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
	public Map<String, Object> getAllAuditPlans(@RequestParam("searchAuditPlan") String _searchAudit,
			@RequestParam("page") String _page)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		
		logger.info("_searchAudit : " + _searchAudit);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		JsonNode jsonNode = mapper.readTree(_searchAudit);
		String auditPlanEntity = null != jsonNode.findValue("auditPlanEntity")
				? jsonNode.findValue("auditPlanEntity").textValue()
				: StringUtils.EMPTY;
		String startDate = null != jsonNode.findValue("startDate") ? jsonNode.findValue("startDate").textValue()
				: StringUtils.EMPTY;
		String endDate = null != jsonNode.findValue("endDate") ? jsonNode.findValue("endDate").textValue()
				: StringUtils.EMPTY;
		String assignTo = null != jsonNode.findValue("assignTo") && !jsonNode.findValue("assignTo").textValue().toString().equalsIgnoreCase("0") ? jsonNode.findValue("assignTo").textValue()
				: StringUtils.EMPTY;
		AuditPlanMaster auditPlanMaster = new AuditPlanMaster();
		auditPlanMaster.setAuditPlanEntity(auditPlanEntity);
		if (StringUtils.isNotEmpty(assignTo))
			auditPlanMaster.setAssignTo(userRepository.getOne(Long.valueOf(assignTo)));
		if (StringUtils.isNotEmpty(startDate))
			auditPlanMaster.setStartDate(dateFormat.parse(startDate).toInstant());
		if (StringUtils.isNotEmpty(endDate))
			auditPlanMaster.setEndDate(dateFormat.parse(startDate).toInstant());
//		_searchAudit : {"auditPlanEntity":"created","startDate":"2018-12-25","endDate":"2018-12-24","assignTo":"3"}
//		AuditPlanMaster search_user = mapper.readValue(_searchUser, AuditPlanMaster.class);
		Integer page = mapper.readValue(_page, Integer.class);
		auditPlanMaster = this.setAuditPlanModel(auditPlanMaster);
		return auditPlanService.getAllAuditPlans(auditPlanMaster, page);

	}

	@PostMapping("/auditplan/get/next")
	@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
	public List<AuditPlanMasterDTO> nextJobs(@RequestParam("searchAuditPlan") String _searchAudit,
			@RequestParam("page") String _page)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		logger.info("_searchUser : " + _searchAudit);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		JsonNode jsonNode = mapper.readTree(_searchAudit);
		String auditPlanEntity = null != jsonNode.findValue("auditPlanEntity")
				? jsonNode.findValue("auditPlanEntity").textValue()
				: StringUtils.EMPTY;
		String startDate = null != jsonNode.findValue("startDate") ? jsonNode.findValue("startDate").textValue()
				: StringUtils.EMPTY;
		String endDate = null != jsonNode.findValue("endDate") ? jsonNode.findValue("endDate").textValue()
				: StringUtils.EMPTY;
		String assignTo = null != jsonNode.findValue("assignTo") ? jsonNode.findValue("assignTo").textValue()
				: StringUtils.EMPTY;
		AuditPlanMaster auditPlanMaster = new AuditPlanMaster();
		auditPlanMaster.setAuditPlanEntity(auditPlanEntity);
		if (StringUtils.isNotEmpty(assignTo))
			auditPlanMaster.setAssignTo(userRepository.getOne(Long.valueOf(assignTo)));
		if (StringUtils.isNotEmpty(startDate))
			auditPlanMaster.setStartDate(dateFormat.parse(startDate).toInstant());
		if (StringUtils.isNotEmpty(endDate))
			auditPlanMaster.setEndDate(dateFormat.parse(startDate).toInstant());
//		_searchAudit : {"auditPlanEntity":"created","startDate":"2018-12-25","endDate":"2018-12-24","assignTo":"3"}
//		AuditPlanMaster search_user = mapper.readValue(_searchUser, AuditPlanMaster.class);
		Integer page = mapper.readValue(_page, Integer.class);
		auditPlanMaster = this.setAuditPlanModel(auditPlanMaster);
		return auditPlanService.nextAuditPlans(auditPlanMaster, page);
	}

	@PostMapping("/auditplan/bulkupdate")
	@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
	public ResponseEntity<Void> bulkUpdateAuditPlan(@Valid @RequestBody List<AuditPlanMasterDTO> auditPlans) {
		try {
			auditPlanService.bulkUpdateAuditPlans(auditPlans);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@GetMapping("/get/intervals")
	public List<IntervalDTO> getIntervals() {
		return IntervalEnum.getIntervalEnums().stream().map(IntervalDTO::new).collect(Collectors.toList());
	}

	@GetMapping("/get/actiontakens")
	public List<ActionTakenDTO> getActionTakens() {
		return ActionTakenEnum.getActionTakenEnum().stream().map(ActionTakenDTO::new).collect(Collectors.toList());
	}

	@GetMapping("/get/riskareas")
	public List<RiskAreaDTO> getRiskAreas() {
		return RiskAreaEnum.getRiskAreaEnum().stream().map(RiskAreaDTO::new).collect(Collectors.toList());
	}

	@GetMapping("/get/testingresults")
	public List<TestingResultDTO> getTestingResults() {
		return TestingResultEnum.getTestingResultEnums().stream().map(TestingResultDTO::new)
				.collect(Collectors.toList());
	}

	@GetMapping("/auditplan/getPlanById/{id}")
	public ResponseEntity<AuditPlanMasterDTO> getObjectiveByProcedureId(@PathVariable("id") Long id)
			throws URISyntaxException {
		AuditPlanMasterDTO auditProcedureMasterDto = auditPlanService.getPlanById(id);
		return new ResponseEntity<>(auditProcedureMasterDto, HttpStatus.OK);
	}
	
	@GetMapping("/auditplan/reminder/{id}")
	public void sendReminder(@PathVariable("id") Long id,Principal principal)throws URISyntaxException {
		auditPlanService.sendAuditReminderEmail(id,principal.getName());
	}

	@SuppressWarnings("unlikely-arg-type")
	public AuditPlanMaster setAuditPlanModel(AuditPlanMaster auditPlan) {
		if (auditPlan.getAuditPlanEntity() != null && auditPlan.getAuditPlanEntity().equalsIgnoreCase("")) {
			auditPlan.setAuditPlanEntity(null);
		}
		if (auditPlan.getStartDate() != null && auditPlan.getStartDate().equals("")) {
			auditPlan.setStartDate(null);
		}
		if (auditPlan.getEndDate() != null && auditPlan.getEndDate().equals("")) {
			auditPlan.setEndDate(null);
		}
		if (auditPlan.getAssignTo() != null && auditPlan.getAssignTo().equals("")) {
			auditPlan.setAssignTo(null);
		}

		auditPlan.setRemarks(null);
		auditPlan.setLastModifiedDate(null);
		auditPlan.setCreatedDate(null);
		auditPlan.setCreatedBy(null);
		auditPlan.setLastModifiedBy(null);
		auditPlan.setId(null);
		auditPlan.setProcedureId(null);
		return auditPlan;
	}
	
	
	//--------------------------------
	
	@PostMapping("/get/allPlanNew")
	public Map<String,Object> getAllPlansNew(@RequestParam("searchPlan") String _auditPlanSearchDTO,@RequestParam("page") String page) throws JsonParseException, JsonMappingException, IOException, InterruptedException, ParseException {
		System.out.println("_auditPlanSearchDTO : "+_auditPlanSearchDTO);
		AuditPlanSearchDTO auditPlanSearchDTO 	= mapper.readValue(_auditPlanSearchDTO, AuditPlanSearchDTO.class);
		Integer _page	= mapper.readValue(page, Integer.class);
		AuditPlanSpecification specification = auditPlanService.getAuditPlanSpecification(auditPlanSearchDTO);
		return auditPlanService.getAllPlans(specification, _page);
	}
	
	@PostMapping("/get/nextPlanNew")
	public List<AuditPlanMasterDTO> nextPlanNew(@RequestParam("searchPlan") String _auditPlanSearchDTO,@RequestParam("page") String page)  throws JsonParseException, JsonMappingException, IOException, InterruptedException, ParseException {
    	System.out.println("_auditPlanSearchDTO : "+_auditPlanSearchDTO);
		AuditPlanSearchDTO auditPlanSearchDTO 	= mapper.readValue(_auditPlanSearchDTO, AuditPlanSearchDTO.class);
		Integer _page	= mapper.readValue(page, Integer.class);
		AuditPlanSpecification specification = auditPlanService.getAuditPlanSpecification(auditPlanSearchDTO);
		return auditPlanService.nextPlans(specification, _page);
	}
	
}
