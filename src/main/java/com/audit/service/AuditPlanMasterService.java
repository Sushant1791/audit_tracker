package com.audit.service;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.audit.domain.AuditPlanMaster;
import com.audit.domain.AuditPlanObjectiveMap;
import com.audit.domain.AuditTestingPlanMaster;
import com.audit.domain.Authority;
import com.audit.domain.User;
import com.audit.repository.AuditObjectiveRepository;
import com.audit.repository.AuditPlanMasterRepository;
import com.audit.repository.AuditTestingPlanMasterRepository;
import com.audit.repository.CostCentreRepository;
import com.audit.repository.DeptMasterRepository;
import com.audit.repository.LocationRepository;
import com.audit.repository.SearchCriteria;
import com.audit.repository.UserRepository;
import com.audit.security.SecurityUtils;
import com.audit.service.dto.AuditPlanMasterDTO;
import com.audit.service.dto.AuditPlanSearchDTO;
import com.audit.service.dto.UserDTO;
import com.audit.service.util.DateUtils;
import com.audit.specification.AuditPlanSpecification;


@Service
@Transactional
public class AuditPlanMasterService {

	private final Logger log = LoggerFactory.getLogger(AuditPlanMasterService.class);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final AuditPlanMasterRepository auditPlanMasterRepository;
	private final UserRepository userRepository;
	private final CostCentreRepository centreRepository;
	private final DeptMasterRepository deptMasterRepository;
	private final AuditObjectiveRepository auditObjectiveRepository;
    private final SpringTemplateEngine templateEngine;
    private final MailService mailService;
    private final LocationRepository locationRepository;
    
    private final AuditTestingPlanMasterRepository auditTestingPlanMasterRepository;
    


	private final ExampleMatcher search_matcher = ExampleMatcher.matching()
			.withMatcher("auditPlanEntity", contains().ignoreCase())
			.withMatcher("startDate", contains().ignoreCase())
			.withMatcher("endDate", contains().ignoreCase())
			.withMatcher("assignTo", contains().ignoreCase());

	public AuditPlanMasterService(AuditPlanMasterRepository auditPlanMasterRepository, UserRepository userRepository,
			CostCentreRepository centreRepository, DeptMasterRepository deptMasterRepository,
			AuditObjectiveRepository auditObjectiveRepository, SpringTemplateEngine templateEngine, MailService mailService,
			AuditTestingPlanMasterRepository auditTestingPlanMasterRepository,
			LocationRepository locationRepository) {
		this.auditPlanMasterRepository = auditPlanMasterRepository;
		this.userRepository = userRepository;
		this.centreRepository = centreRepository;
		this.deptMasterRepository = deptMasterRepository;
		this.auditObjectiveRepository = auditObjectiveRepository;
		this.templateEngine = templateEngine;
		this.mailService = mailService;
		this.auditTestingPlanMasterRepository = auditTestingPlanMasterRepository;
		this.locationRepository = locationRepository;
	}

	public AuditPlanMaster createAuditPlanMaster(AuditPlanMasterDTO auditPlanDTO) {
		AuditPlanMaster auditPlan = new AuditPlanMaster();
		try {
			Optional<String> loginName = SecurityUtils.getCurrentUserLogin();
			Optional<User> user = loginName.isPresent() ? userRepository.findOneByLogin(loginName.get())
					: Optional.empty();
			auditPlan.setRemarks(auditPlanDTO.getRemarks());
			auditPlan.setId(auditPlanDTO.getId());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			auditPlan.setStartDate(dateFormat.parse(auditPlanDTO.getStartDate()).toInstant());
			auditPlan.setEndDate(dateFormat.parse(auditPlanDTO.getEndDate()).toInstant());
			auditPlan.setCreatedDate(Instant.now());
			auditPlan.setAuditPlanEntity(auditPlanDTO.getAuditPlanEntity());
			auditPlan.setDeptId(deptMasterRepository.getOne(auditPlanDTO.getDepartment()));
			auditPlan.setCostCentreId(centreRepository.getOne(auditPlanDTO.getCostCentreId()));
			auditPlan.setLocation(locationRepository.getOne(auditPlanDTO.getLocationId()));
			auditPlan.setAssignTo(userRepository.getOne(auditPlanDTO.getAssignTo().getId()));
			auditPlan.setCreatedBy(user.get());
			auditPlan.setActive(true);
			if (null != auditPlanDTO.getProcedure().getId()) {
				auditPlan.setProcedureId(auditObjectiveRepository.getOne(auditPlanDTO.getProcedure().getId()));
			}
			if (null != auditPlanDTO.getId()) {
				auditPlan.setLastModifiedBy(user.get());
				auditPlan.setLastModifiedDate(Instant.now());
				auditPlan.setCreatedDate(dateFormat.parse(auditPlanDTO.getCreatedDate()).toInstant());
				auditPlan.setCreatedBy(userRepository.findById(auditPlanDTO.getCreatedBy().getId()).get());
			}
			
			if (null == auditPlanDTO.getId() || auditPlanDTO.getId() == 0) {
				List<AuditPlanObjectiveMap> auditPlanList = new ArrayList<>();
				auditPlanDTO.getAuditPlanObjectiveData().forEach(auditProcedureObjectiveDto -> {
					AuditPlanObjectiveMap auditPlanObjectiveMap = new AuditPlanObjectiveMap();
					auditPlanObjectiveMap.setActive(true);
					auditPlanObjectiveMap.setCreatedBy(user.get());
					auditPlanObjectiveMap.setCreatedDate(Instant.now());
					auditPlanObjectiveMap.setLastModifiedBy(null);
					auditPlanObjectiveMap.setLastModifiedDate(Instant.now());
					auditPlanObjectiveMap.setObjectiveDescription(auditProcedureObjectiveDto.getObjectiveDescription());
					auditPlanObjectiveMap.setObjectiveName(auditProcedureObjectiveDto.getObjectiveName());
					auditPlanObjectiveMap.setPlanId(auditPlan);
					auditPlanList.add(auditPlanObjectiveMap);
				});
				auditPlan.setAuditPlanObjectiveMapCollection(auditPlanList);	
			}
			
			auditPlanMasterRepository.save(auditPlan);
			log.debug("Created Audit Plan: {}", auditPlan);

		} catch (ParseException e) {
			log.error("Error occurred while creating audit plan ::{} ", e.getMessage());
		}
		return auditPlan;
	}

	public Optional<AuditPlanMasterDTO> updateAuditPlan(AuditPlanMasterDTO auditPlanDTO) {
		return Optional.of(auditPlanMasterRepository.findById(auditPlanDTO.getId())).filter(Optional::isPresent)
				.map(Optional::get).map(auditPlan -> {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					try {
						auditPlan.setEndDate(dateFormat.parse(auditPlanDTO.getEndDate()).toInstant());
						auditPlan.setStartDate(dateFormat.parse(auditPlanDTO.getStartDate()).toInstant());
					} catch (ParseException e) {
						log.error("Date parsing error in updateAuditPlan ::");
					}
					auditPlan.setId(auditPlan.getId());
					auditPlan.setAuditPlanEntity(auditPlan.getAuditPlanEntity());
					auditPlan.setActive(auditPlanDTO.isActive());
					auditPlan.setLastModifiedDate(Instant.now());
					auditPlan.setRemarks(auditPlanDTO.getRemarks());
					User assignTo = new User();
					assignTo.setId(auditPlanDTO.getAssignTo().getId());
					auditPlan.setAssignTo(assignTo);
					User modifiedBy = new User();
					modifiedBy.setId(auditPlanDTO.getLastModifiedBy().getId());
					auditPlan.setLastModifiedBy(modifiedBy);
					log.debug("Changed Information for User: {}", auditPlan.toString());
					return auditPlan;
				}).map(AuditPlanMasterDTO::new);
	}

	@Transactional(readOnly = true)
	public Page<AuditPlanMasterDTO> getAllPlans(Pageable pageable) {
		return auditPlanMasterRepository.findAll(pageable).map(AuditPlanMasterDTO::new);
	}
	
	@Transactional(readOnly = true)
	public List<AuditPlanMasterDTO> getAllPlans() {
		return auditPlanMasterRepository.findAll().stream().map(AuditPlanMasterDTO::new).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<AuditPlanMasterDTO> getAllAuditorPlans() {
		Optional<String> loginName = SecurityUtils.getCurrentUserLogin();
		Optional<User> user = loginName.isPresent() ? userRepository.findOneByLogin(loginName.get()) : Optional.empty();
		Authority author = new Authority();
		author.setName("ROLE_ADMIN");
		return user.get().getAuthorities().contains(author)
				? auditPlanMasterRepository.findAll().stream().map(AuditPlanMasterDTO::new).collect(Collectors.toList())
				: auditPlanMasterRepository.findAllByAssignTo(user.get()).stream().map(AuditPlanMasterDTO::new)
						.collect(Collectors.toList());
	}

	public void deleteAuditPlan(Long id) {
		auditPlanMasterRepository.findById(id).ifPresent(auditPlan -> {
			auditPlanMasterRepository.delete(auditPlan);
			log.debug("Deleted Audit Plan :: {}", auditPlan);
		});
	}

	@SuppressWarnings("deprecation")
	public List<AuditPlanMasterDTO> nextAuditPlans(AuditPlanMaster searchAudit, Integer page) {
		Pageable ten = new PageRequest(page, 10, Sort.Direction.DESC, "auditPlanEntity");
		return auditPlanMasterRepository.findAll(Example.of(searchAudit, search_matcher), ten).getContent().stream()
				.map(AuditPlanMasterDTO::new).collect(Collectors.toList());
	}

	@SuppressWarnings("deprecation")
	public Map<String, Object> getAllAuditPlans(AuditPlanMaster searchAudit, Integer page) throws ParseException {
		Map<String, Object> map = new HashMap<>();

		if (searchAudit != null && searchAudit.getAuditPlanEntity() != null
				&& !searchAudit.getAuditPlanEntity().equalsIgnoreCase("")) {
			searchAudit.setAuditPlanEntity(searchAudit.getAuditPlanEntity().split(" ")[0]);
		}
		searchAudit.setActive(true);
		Pageable ten = new PageRequest(page, 10, Sort.Direction.DESC, "auditPlanEntity");
		
		List<AuditPlanMasterDTO> auditPlans = auditPlanMasterRepository.findAll(Example.of(searchAudit, search_matcher), ten).getContent().stream().map(AuditPlanMasterDTO::new).collect(Collectors.toList());
		for(AuditPlanMasterDTO a : auditPlans) {
			if(DateUtils.isDateSmallerThanToday(DateUtils.convertStringToDate("dd-MM-yyyy", a.getEndDate()))) {
				List<AuditTestingPlanMaster> testingPlans = auditTestingPlanMasterRepository.findByAuditPlanId(a.getId());
				if(!testingPlans.isEmpty()) {
					a.setShowReminder(!testingPlans.get(0).getLocked());
				}else {
					a.setShowReminder(true);	
				}
			}else {
				a.setShowReminder(false);
			}
			
		}
		System.out.println(auditPlans);
		map.put("list",auditPlans);

		map.put("count", this.getCount(searchAudit));
		return map;
	}

	public Integer getCount(AuditPlanMaster searchAudit) {
		Integer count = 0;
		List<AuditPlanMaster> all = auditPlanMasterRepository.findAll(Example.of(searchAudit, search_matcher));
		if (!all.isEmpty())
			count = all.size();
		return count;
	}

	public void bulkUpdateAuditPlans(List<AuditPlanMasterDTO> auditPlans) {
		auditPlans.stream().forEach(auditPlan -> createAuditPlanMaster(auditPlan));
	}
	
	public AuditPlanMasterDTO getPlanById(long id) {
		Optional<AuditPlanMaster> auditProcedureMaster= auditPlanMasterRepository.findById(id);
		if(auditProcedureMaster.isPresent()) {
			return new AuditPlanMasterDTO(auditProcedureMaster.get());
		}
		return null;
	}
	
	@Async
	public void sendAuditPlanEmail(AuditPlanMasterDTO auditplan, User user)
    { 
		UserDTO assign = new UserDTO(userRepository.getOne(auditplan.getAssignTo().getId()));
    	log.debug("Sending audit plan created email to '{}'", assign.getEmail());
    	Locale locale = Locale.forLanguageTag(user.getLangKey());
    	Context context = new Context(locale);
    	context.setVariable("assignTo", assign.getFirstName() + ' ' + assign.getLastName());
    	context.setVariable("createdUser", user.getFirstName());
    	context.setVariable("auditPlanEntity", auditplan.getAuditPlanEntity());
    	String content = templateEngine.process("mail/auditPlanAssigned.html", context);
    	String subject = "Audit Plan assigned notification";
    	mailService.sendEmail(assign.getEmail(), subject, content, false, true);
    }
	
	@Async
	public void sendAuditReminderEmail(Long auditPlanId,String loginName)
    { 
		log.info("sendAuditReminderEmail called..!");
		String subject = "Audit Assignment Reminder";
		AuditPlanMaster auditPlan = auditPlanMasterRepository.getOne(auditPlanId);
		User assigneeUser = auditPlan.getAssignTo();
		log.debug("Sending audit plan reminder email to '{}'", assigneeUser.getEmail());
		Locale locale = Locale.forLanguageTag(assigneeUser.getLangKey());
		Context context = new Context(locale);
		context.setVariable("assigneeUser", assigneeUser);
		context.setVariable("auditPlanEntity", auditPlan.getAuditPlanEntity());
		User user = userRepository.findOneByLogin(loginName).get();
		context.setVariable("currentUserName", user.getFirstName().toUpperCase());
		String content = templateEngine.process("mail/auditPlanReminder", context); 
		mailService.sendEmail(assigneeUser.getEmail(), subject, content, false, true);
		
    }
	
	//--------------------
	
	public Integer getCount(AuditPlanSpecification specification) {
		Integer count = 0;
		List<AuditPlanMaster> all = auditPlanMasterRepository.findAll(specification);
		if (!all.isEmpty())
			count = all.size();
		return count;
	}
	
	@SuppressWarnings("deprecation")
	public Map<String, Object> getAllPlans(AuditPlanSpecification specification, Integer page) {
		Map<String, Object> map = new HashMap<>();
		Pageable ten = new PageRequest(page, 10, Sort.Direction.DESC, "auditPlanEntity");
		Page<AuditPlanMaster> fetchRecords = auditPlanMasterRepository.findAll(specification, ten);
		map.put("list",fetchRecords.getContent().stream().map(AuditPlanMasterDTO::new).collect(Collectors.toList()));
		map.put("count", this.getCount(specification));
		return map;
	}
	
	@SuppressWarnings("deprecation")
	public List<AuditPlanMasterDTO> nextPlans(AuditPlanSpecification specification, Integer page) {
		Pageable ten = new PageRequest(page, 10, Sort.Direction.DESC, "auditPlanEntity");
		Page<AuditPlanMaster> fetchRecords = auditPlanMasterRepository.findAll(specification, ten);
		return fetchRecords.getContent().stream().map(AuditPlanMasterDTO::new).collect(Collectors.toList());
	}
	
	public AuditPlanSpecification getAuditPlanSpecification(AuditPlanSearchDTO search) throws ParseException {
		AuditPlanSpecification specification = null;
		SearchCriteria entity = null, remarks=null, dates = null, assignTo = null;
		List<Object> values = new ArrayList<Object>();

		if (Objects.nonNull(search)) {

			if (StringUtils.isNotBlank(search.getAuditPlanEntity())) {
				values = new ArrayList<Object>();
				values.add(search.getAuditPlanEntity());
				entity = new SearchCriteria("auditPlanEntity", ":", values);
				specification = new AuditPlanSpecification(entity);
			}
			
			if (StringUtils.isNotBlank(search.getRemarks())) {
				values = new ArrayList<Object>();
				values.add(search.getRemarks());
				remarks = new SearchCriteria("remarks", ":", values);
				AuditPlanSpecification remarksSpecification = new AuditPlanSpecification(remarks);
				if (specification != null) {
					specification.and(remarksSpecification);
				} else {
					specification = new AuditPlanSpecification(remarks);
				}
			}

			
			if (StringUtils.isNotBlank(search.getAssignTo()) && !search.getAssignTo().equalsIgnoreCase("0")) {
				values = new ArrayList<Object>();
				values.add( Long.parseLong(search.getAssignTo()));
				assignTo = new SearchCriteria("assignTo", ":", values);
				AuditPlanSpecification assignToSpecification = new AuditPlanSpecification(assignTo);
				if (specification != null) {
					specification.and(assignToSpecification);
				} else {
					specification = new AuditPlanSpecification(assignTo);
				}
			}

			if (StringUtils.isNotBlank(search.getStart_date()) && StringUtils.isNotBlank(search.getEnd_date())) {
				values = new ArrayList<Object>();
				Date sdate = (Date) sdf.parse(search.getStart_date()+" 00:00:00");
				Date edate = (Date) sdf.parse(search.getEnd_date()+" 23:59:59");
				Calendar cal = Calendar.getInstance();
				cal.setTime(sdate);
				values.add(cal.toInstant());

				dates = new SearchCriteria("startDate", ">", values);
				AuditPlanSpecification dateSpecification = new AuditPlanSpecification(dates);
				if (specification != null) {
					specification.and(dateSpecification);
				} else {
					specification = new AuditPlanSpecification(dates);
				}
				
				values = new ArrayList<Object>();
				cal.setTime(edate);
				values.add(cal.toInstant());
				dateSpecification = null;
				dates = new SearchCriteria("endDate", "<", values);
				dateSpecification = new AuditPlanSpecification(dates);
				specification.and(dateSpecification);
				
			}



		}
		return specification;
	}
}
