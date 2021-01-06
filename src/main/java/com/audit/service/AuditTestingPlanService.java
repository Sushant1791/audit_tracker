package com.audit.service;

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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.audit.domain.AuditTestingPlanMaster;
import com.audit.domain.Authority;
import com.audit.domain.DeptMaster;
import com.audit.domain.LocationMaster;
import com.audit.domain.TestingPlanObservationMaster;
import com.audit.domain.User;
import com.audit.repository.AuditPlanMasterRepository;
import com.audit.repository.AuditTestingPlanMasterRepository;
import com.audit.repository.CostCentreRepository;
import com.audit.repository.DeptMasterRepository;
import com.audit.repository.SearchCriteria;
import com.audit.repository.UserRepository;
import com.audit.security.SecurityUtils;
import com.audit.service.dto.AuditPlanTestingSearchDTO;
import com.audit.service.dto.AuditTestingPlanMasterDTO;
import com.audit.service.dto.UserDTO;
import com.audit.service.mapper.AuditTestingPlanMasterMapping;
import com.audit.specification.AuditTestingPlanSpecification;

@Service
@Transactional
public class AuditTestingPlanService {

	private final Logger log = LoggerFactory.getLogger(AuditTestingPlanService.class);

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final AuditTestingPlanMasterRepository auditTestingPlanMasterRepository;
	private final UserRepository userRepository;
	private final CostCentreRepository costCentreRepository;
	private final DeptMasterRepository deptMasterRepository;
	private final AuditPlanMasterRepository auditPlanMasterRepository;
	private final SpringTemplateEngine templateEngine;
	private final MailService mailService;

	public AuditTestingPlanService(AuditTestingPlanMasterRepository auditTestingPlanMasterRepository,
			UserRepository userRepository, CostCentreRepository costCentreRepository,
			DeptMasterRepository deptMasterRepository, AuditPlanMasterRepository auditPlanMasterRepository,
			SpringTemplateEngine templateEngine, MailService mailService) {
		this.auditTestingPlanMasterRepository = auditTestingPlanMasterRepository;
		this.userRepository = userRepository;
		this.costCentreRepository = costCentreRepository;
		this.deptMasterRepository = deptMasterRepository;
		this.auditPlanMasterRepository = auditPlanMasterRepository;
		this.mailService = mailService;
		this.templateEngine = templateEngine;
	}

	public void bulkUpdateAuditTests(List<AuditTestingPlanMasterDTO> auditTestingPlanMasterDTOs) {
		auditTestingPlanMasterDTOs.stream()
				.forEach(auditTestingPlanMasterDTO -> saveAuditPlanTesting(auditTestingPlanMasterDTO));
	}

	public AuditTestingPlanMaster saveAuditPlanTesting(AuditTestingPlanMasterDTO auditTestingPlanMasterDTO) {
		AuditTestingPlanMaster auditTestingPlanMaster = new AuditTestingPlanMaster();
		try {
			auditTestingPlanMaster.setCreatedBy(getUserDetail());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			auditTestingPlanMaster.setExpectedRevertDate(
					dateFormat.parse(auditTestingPlanMasterDTO.getExpectedRevertDate()).toInstant());
			auditTestingPlanMaster.setId(auditTestingPlanMasterDTO.getId());
			auditTestingPlanMaster.setRiskAreaId(auditTestingPlanMasterDTO.getRiskAreaId());
			auditTestingPlanMaster.setAuditorId(getUserDetail());
			auditTestingPlanMaster.setTestDescription(auditTestingPlanMasterDTO.getTestDescription());
			if (Objects.nonNull(auditTestingPlanMasterDTO.getAuditPlanId())) {
				auditTestingPlanMaster
						.setAuditPlanId(auditPlanMasterRepository.getOne(auditTestingPlanMasterDTO.getAuditPlanId()));
			} else {
				auditTestingPlanMaster.setAuditPlanId(null);
			}
			auditTestingPlanMaster.setCreatedDate(Instant.now());
			auditTestingPlanMaster.setDeptId(deptMasterRepository.getOne(auditTestingPlanMasterDTO.getDeptartment()));
			auditTestingPlanMaster
					.setCostCentreId(costCentreRepository.getOne(auditTestingPlanMasterDTO.getCostCentreId()));
			auditTestingPlanMaster.setTestResult(auditTestingPlanMasterDTO.getTestResultId().longValue());
			auditTestingPlanMaster.setRating(auditTestingPlanMasterDTO.getRating());
			auditTestingPlanMaster.setAuditeeId(userRepository.getOne(auditTestingPlanMasterDTO.getAuditeeId()));
			if (null != auditTestingPlanMasterDTO.getId()) {
				auditTestingPlanMaster.setLastModifiedBy(getUserDetail());
				auditTestingPlanMaster.setLastModifiedDate(Instant.now());
			}
			auditTestingPlanMaster.setActionTaken(auditTestingPlanMasterDTO.getActionTakenId());
			List<TestingPlanObservationMaster> observations = new ArrayList<>();
			if (null != auditTestingPlanMasterDTO.getTestingPlanObservationData()) {
				auditTestingPlanMasterDTO.getTestingPlanObservationData().forEach(auditObservation -> {
					TestingPlanObservationMaster observation = new TestingPlanObservationMaster();
					if (null != auditObservation.getId()) {
						observation.setId(auditObservation.getId());
					}
					observation.setInference(auditObservation.getInference());
					observation.setTestingPlanId(auditTestingPlanMaster);
					observation.setObservation(auditObservation.getObservation());
					observation.setMonetaryImpact(auditObservation.getMonetaryImpact());
					observation.setAuditeeResponse(auditObservation.getAuditeeResponse());
					observations.add(observation);
				});
			}
			auditTestingPlanMaster.setTestingPlanObservationMasterList(observations);
			auditTestingPlanMaster.setLocked(auditTestingPlanMasterDTO.getLocked());
			auditTestingPlanMaster.setResponded(auditTestingPlanMasterDTO.isResponded() ? true : false);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return auditTestingPlanMasterRepository.save(auditTestingPlanMaster);
	}

	public void deleteAuditTestingPlan(long id) {
		auditTestingPlanMasterRepository.deleteById(id);
	}

	public AuditTestingPlanMasterDTO getAuditTestingById(Long id) {
		AuditTestingPlanMaster auditTestingPlanMaster = auditTestingPlanMasterRepository.getOne(id);
		return new AuditTestingPlanMasterMapping().getDTOFromDomain(auditTestingPlanMaster);
	}

	@SuppressWarnings("deprecation")
	public List<AuditTestingPlanMasterDTO> getAllAuditTestingPlan(Integer page) {
		Pageable ten = new PageRequest(page, 10, Sort.Direction.DESC, "id");
		List<AuditTestingPlanMaster> auditTestingLists = auditTestingPlanMasterRepository.findAll(ten).getContent();
		return new AuditTestingPlanMasterMapping().testingPlanDomainToTestingPlanDtos(auditTestingLists);
	}

	public List<AuditTestingPlanMasterDTO> getAllAuditTestingPlansByAuditee(User user) {
		List<AuditTestingPlanMaster> auditTestingLists = auditTestingPlanMasterRepository.findAllByAuditeeId(user);
		return new AuditTestingPlanMasterMapping().testingPlanDomainToTestingPlanDtos(auditTestingLists);
	}

	public List<AuditTestingPlanMasterDTO> getAllAuditTestingPlansByAuditor(User user) {
		List<AuditTestingPlanMaster> auditTestingLists = auditTestingPlanMasterRepository.findAllByAuditorId(user);
		return new AuditTestingPlanMasterMapping().testingPlanDomainToTestingPlanDtos(auditTestingLists);
	}

	public List<AuditTestingPlanMasterDTO> fetchRoleBasedAuditTestingPlans(String planFor) {
		User user = getUserDetail();
		Authority authority = new Authority();
		authority.setName("ROLE_ADMIN");
		if (StringUtils.equalsIgnoreCase(planFor, "Auditee")) {
			if (user.getAuthorities().contains(authority)) {
				return getAllAuditTestingPlan(0);
			} else {
				return getAllAuditTestingPlansByAuditee(user);
			}
		} else {
			return getAllAuditTestingPlansByAuditor(user);
		}
	}

	public void updateAuditeeResponse(AuditTestingPlanMasterDTO auditTestingPlan) {
		Optional<AuditTestingPlanMaster> master = auditTestingPlanMasterRepository.findById(auditTestingPlan.getId());
		AuditTestingPlanMaster testingPlanMaster = master.get();
		testingPlanMaster.setLastModifiedBy(getUserDetail());
		testingPlanMaster.setLastModifiedDate(Instant.now());
		testingPlanMaster.setResponded(true);
		List<TestingPlanObservationMaster> observations = new ArrayList<>();
		if (null != auditTestingPlan.getTestingPlanObservationData()) {
			auditTestingPlan.getTestingPlanObservationData().forEach(auditObservation -> {
				TestingPlanObservationMaster observation = new TestingPlanObservationMaster();
				if (null != auditObservation.getId()) {
					observation.setId(auditObservation.getId());
				}
				observation.setInference(auditObservation.getInference());
				observation.setTestingPlanId(testingPlanMaster);
				observation.setObservation(auditObservation.getObservation());
				observation.setMonetaryImpact(auditObservation.getMonetaryImpact());
				observation.setAuditeeResponse(auditObservation.getAuditeeResponse());
				observations.add(observation);
			});
		}
		testingPlanMaster.setTestingPlanObservationMasterList(observations);
		auditTestingPlanMasterRepository.save(testingPlanMaster);
	}

	public List<AuditTestingPlanMasterDTO> generateReport(Long month, Long year, DeptMaster department,
			LocationMaster location) {
		String reportMonth = month < 11 ? "0"+ month.toString() : month.toString();
		List<TestingPlanObservationMaster> master = auditTestingPlanMasterRepository
				.findAllByDepartmentAndMonthYear(department, reportMonth, year.toString(), location);
		return new AuditTestingPlanMasterMapping().testingObservationDetailsDomainToTestingPlanDTOs(master);
	}

	private User getUserDetail() {
		Optional<String> loginName = SecurityUtils.getCurrentUserLogin();
		Optional<User> user = loginName.isPresent() ? userRepository.findOneByLogin(loginName.get()) : Optional.empty();
		return user.get();
	}

	@Async
	public void sendEmailToAuditeeForResponse(AuditTestingPlanMasterDTO master) {
		UserDTO auditee = new UserDTO(userRepository.getOne(master.getAuditeeId()));
		log.debug("Sending auditor notification response email to auditee '{}'", auditee.getEmail());
		Locale locale = Locale.forLanguageTag(getUserDetail().getLangKey());
		Context context = new Context(locale);
		context.setVariable("revertDate", master.getExpectedRevertDate().toString());
		context.setVariable("auditeeName", auditee.getFirstName());
		UserDTO auditor = new UserDTO(userRepository.getOne(master.getAuditorId()));

		context.setVariable("auditorName", auditor.getFirstName());
		context.setVariable("link", "http://ec2-13-127-43-23.ap-south-1.compute.amazonaws.com:8080/");
		String subject = "Auditor Observation";
		String content = templateEngine.process("mail/auditorNotifEmail.html", context);
		mailService.sendEmail(auditee.getEmail(), subject, content, false, true);
	}

	@Async
	public void sendEmailReminderToAuditee(Long id) {
		AuditTestingPlanMaster auditTesting = auditTestingPlanMasterRepository.getOne(id);
		Locale locale = Locale.forLanguageTag("en");
		Context context = new Context(locale);
		context.setVariable("auditee", auditTesting.getAuditeeId().getFirstName());
		context.setVariable("auditorName", auditTesting.getAuditorId().getFirstName());
		context.setVariable("link", "http://ec2-13-127-43-23.ap-south-1.compute.amazonaws.com:8080/");
		String subject = "Auditor Reminder";
		String content = templateEngine.process("mail/auditeeReminder.html", context);
		mailService.sendEmail(auditTesting.getAuditeeId().getEmail(), subject, content, false, true);

	}

	@Async
	public void sendNotificationReminderToAuditee(AuditTestingPlanMaster master) {
		Locale locale = Locale.forLanguageTag("en");
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM, yyyy");
		Context context = new Context(locale);
		User auditee = userRepository.getOne(master.getAuditeeId().getId());
		context.setVariable("auditeeName", auditee.getFirstName());
		context.setVariable("revertDate", formatter.format(Date.from(master.getExpectedRevertDate())));
		context.setVariable("link", "http://ec2-13-127-43-23.ap-south-1.compute.amazonaws.com:8080/");
		context.setVariable("auditorName", userRepository.getOne(master.getAuditorId().getId()).getFirstName());
		String subject = "Auditor Observation";
		String content = templateEngine.process("mail/auditorNotifEmail.html", context);
		mailService.sendEmail(auditee.getEmail(), subject, content, false, true);
	}

	public AuditTestingPlanSpecification getAuditTestingPlanSpecificationForAuditeeResponse(
			AuditPlanTestingSearchDTO search) throws ParseException {
		AuditTestingPlanSpecification specification = null;
		SearchCriteria entity = null, dates = null, respond = null;
		List<Object> values = new ArrayList<Object>();

		User user = getUserDetail();
		Authority authority = new Authority();
		authority.setName("ROLE_ADMIN");
		if (!user.getAuthorities().contains(authority)) {
			values = new ArrayList<Object>();
			values.add(user.getId());
			SearchCriteria normalUser = new SearchCriteria("auditorId", ":", values);
			specification = new AuditTestingPlanSpecification(normalUser);
		}

		if (Objects.nonNull(search)) {

			if (StringUtils.isNotBlank(search.getAuditPlanEntity())) {
				values = new ArrayList<Object>();
				values.add(search.getAuditPlanEntity());
				entity = new SearchCriteria("auditPlanId.auditPlanEntity", ":", values);
				AuditTestingPlanSpecification entitySpecification = new AuditTestingPlanSpecification(entity);
				if (specification != null) {
					specification.and(entitySpecification);
				} else {
					specification = new AuditTestingPlanSpecification(entity);
				}
			}

			if (StringUtils.isNotBlank(search.getStart_date()) && StringUtils.isNotBlank(search.getEnd_date())) {
				values = new ArrayList<Object>();
				Date sdate = (Date) sdf.parse(search.getStart_date()+" 00:00:00");
				Date edate = (Date) sdf.parse(search.getEnd_date()+" 23:59:59");
				Calendar cal = Calendar.getInstance();
				cal.setTime(sdate);
				values.add(cal.toInstant());
				cal.setTime(edate);
				values.add(cal.toInstant());
				dates = new SearchCriteria("expectedRevertDate", "between", values);
				AuditTestingPlanSpecification dateSpecification = new AuditTestingPlanSpecification(dates);
				if (specification != null) {
					specification.and(dateSpecification);
				} else {
					specification = new AuditTestingPlanSpecification(dates);
				}
			}

			if (StringUtils.isNotBlank(search.getRespond())) {
				values = new ArrayList<Object>();
				if (search.getRespond().equalsIgnoreCase("y")) {
					values.add(true);
				} else {
					values.add(false);
				}
				respond = new SearchCriteria("isResponded", ":", values);
				AuditTestingPlanSpecification respondSpecification = new AuditTestingPlanSpecification(respond);
				if (specification != null) {
					specification.and(respondSpecification);
				} else {
					specification = new AuditTestingPlanSpecification(respond);
				}
			}

		}
		return specification;
	}

	public AuditTestingPlanSpecification getAuditTestingPlanSpecificationForAuditTestingScreen(
			AuditPlanTestingSearchDTO search) throws ParseException {
		AuditTestingPlanSpecification specification = null;
		SearchCriteria entity = null, dates = null, auditee = null, auditor = null;
		List<Object> values = new ArrayList<Object>();
		User currentUser = getUserDetail();
		Authority authority = new Authority();
		authority.setName("ROLE_ADMIN");
		String searchAudit = currentUser.getAuthorities().contains(authority) ? "0": currentUser.getId().toString();
		search.setAuditor(searchAudit);
		if (Objects.nonNull(search)) {

			if (StringUtils.isNotBlank(search.getAuditPlanEntity())) {
				values = new ArrayList<Object>();
				values.add(search.getAuditPlanEntity());
				entity = new SearchCriteria("auditPlanId.auditPlanEntity", ":", values);
				specification = new AuditTestingPlanSpecification(entity);
			}
			
			if (StringUtils.isNotBlank(search.getAuditee()) && !search.getAuditee().equalsIgnoreCase("0")) {
				values = new ArrayList<Object>();
				values.add(Long.parseLong(search.getAuditee()));
				auditee = new SearchCriteria("auditeeId", ":", values);
				AuditTestingPlanSpecification auditeeSpecification = new AuditTestingPlanSpecification(auditee);
				if (specification != null) {
					specification.and(auditeeSpecification);
				} else {
					specification = new AuditTestingPlanSpecification(auditee);
				}
			}

			
			if (StringUtils.isNotBlank(search.getAuditor()) && !search.getAuditor().equalsIgnoreCase("0")) {
				values = new ArrayList<Object>();
				values.add( Long.parseLong(search.getAuditor()));
				auditor = new SearchCriteria("auditorId", ":", values);
				AuditTestingPlanSpecification auditorSpecification = new AuditTestingPlanSpecification(auditor);
				if (specification != null) {
					specification.and(auditorSpecification);
				} else {
					specification = new AuditTestingPlanSpecification(auditor);
				}
			}

			if (StringUtils.isNotBlank(search.getStart_date()) && StringUtils.isNotBlank(search.getEnd_date())) {
				values = new ArrayList<Object>();
				Date sdate = (Date) sdf.parse(search.getStart_date()+" 00:00:00");
				Date edate = (Date) sdf.parse(search.getEnd_date()+" 23:59:59");
				Calendar cal = Calendar.getInstance();
				cal.setTime(sdate);
				values.add(cal.toInstant());
				cal.setTime(edate);
				values.add(cal.toInstant());
				dates = new SearchCriteria("expectedRevertDate", "between", values);
				AuditTestingPlanSpecification dateSpecification = new AuditTestingPlanSpecification(dates);
				if (specification != null) {
					specification.and(dateSpecification);
				} else {
					specification = new AuditTestingPlanSpecification(dates);
				}
			}



		}
		return specification;
	}

	// ---- this is for auditee window ------------

	@SuppressWarnings("deprecation")
	public Map<String, Object> getAllTestingPlans(AuditTestingPlanSpecification specification, Integer page) {
		Map<String, Object> map = new HashMap<>();
		Pageable ten = new PageRequest(page, 10, Sort.Direction.DESC, "createdDate");
		Page<AuditTestingPlanMaster> fetchRecords = auditTestingPlanMasterRepository.findAll(specification, ten);
		map.put("list",
				new AuditTestingPlanMasterMapping().testingPlanDomainToTestingPlanDtos(fetchRecords.getContent()));
		map.put("count", this.getCount(specification));
		return map;
	}

	@SuppressWarnings("deprecation")
	public List<AuditTestingPlanMasterDTO> nextTestingPlans(AuditTestingPlanSpecification specification, Integer page) {
		Pageable ten = new PageRequest(page, 10, Sort.Direction.DESC, "createdDate");
		Page<AuditTestingPlanMaster> fetchRecords = auditTestingPlanMasterRepository.findAll(specification, ten);
		return new AuditTestingPlanMasterMapping().testingPlanDomainToTestingPlanDtos(fetchRecords.getContent());
	}

	public Integer getCount(AuditTestingPlanSpecification specification) {
		Integer count = 0;
		List<AuditTestingPlanMaster> all = auditTestingPlanMasterRepository.findAll(specification);
		if (!all.isEmpty())
			count = all.size();
		return count;
	}

	private Long getCriticalAndNonCriticalCount(long rating, User user) {
		Long count;
		if (null == user) {
			count = auditTestingPlanMasterRepository.countByRating(rating);
		} else {
			count = auditTestingPlanMasterRepository.countByRatingAndAuditorId(rating, user);
		}
		return count;
	}

	private long getIsRespondedCount(boolean responded, User user) {
		Long count;
		if (null == user) {
			count = auditTestingPlanMasterRepository.countByResponded(responded);
		} else {
			count = auditTestingPlanMasterRepository.countByRespondedByAuditorId(responded, user.getId());

		}
		return count;
	}

	private long getLockedCount(boolean locked, User user) {
		Long count;
		if (null == user) {
			count = auditTestingPlanMasterRepository.countByLocked(locked);
		} else {
			count = auditTestingPlanMasterRepository.countByLockedByAuditorId(locked, user.getId());

		}
		return count;
	}

	public HashMap<String, Long> getDashBoardCounts() {
		HashMap<String, Long> data = new HashMap<>();
		User loggedUser = getUserDetail();
		Authority authority = new Authority();
		authority.setName("ROLE_ADMIN");
		if (loggedUser.getAuthorities().contains(authority)) {
			data.put("critical", getCriticalAndNonCriticalCount(1, null));
			data.put("noncritical", getCriticalAndNonCriticalCount(2, null));
			data.put("responded", getIsRespondedCount(true, null));
			data.put("notresponded", getIsRespondedCount(false, null));
			data.put("locked", getLockedCount(true, null));
			data.put("unlocked", getLockedCount(false, null));
		} else {
			data.put("critical", getCriticalAndNonCriticalCount(1, loggedUser));
			data.put("noncritical", getCriticalAndNonCriticalCount(2, loggedUser));
			data.put("responded", getIsRespondedCount(true, loggedUser));
			data.put("notresponded", getIsRespondedCount(false, loggedUser));
			data.put("locked", getLockedCount(true, loggedUser));
			data.put("unlocked", getLockedCount(false, loggedUser));
		}
		return data;
	}

}
