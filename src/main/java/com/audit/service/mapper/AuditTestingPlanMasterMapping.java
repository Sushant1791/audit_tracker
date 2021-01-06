package com.audit.service.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.audit.common.enums.RiskAreaEnum;
import com.audit.common.enums.TestingResultEnum;
import com.audit.domain.AuditPlanObjectiveMap;
import com.audit.domain.AuditProcObjectiveMaster;
import com.audit.domain.AuditTestingPlanMaster;
import com.audit.domain.TestingPlanObservationMaster;
import com.audit.service.dto.AuditPlanObjectionMapDTO;
import com.audit.service.dto.AuditTestingPlanMasterDTO;
import com.audit.service.dto.TestingPlanObservationMasterDTO;

public class AuditTestingPlanMasterMapping {

	public AuditTestingPlanMasterDTO getDTOFromDomain(AuditTestingPlanMaster auditTestingPlanMaster) {
		return new AuditTestingPlanMasterDTO(auditTestingPlanMaster);
	}

	public List<AuditTestingPlanMasterDTO> testingPlanDomainToTestingPlanDtos(
			List<AuditTestingPlanMaster> auditTestingPlanMasterList) {
		return auditTestingPlanMasterList.stream().filter(Objects::nonNull).map(this::getDTOFromDomain)
				.collect(Collectors.toList());
	}
	
	public List<AuditTestingPlanMasterDTO> testingObservationDetailsDomainToTestingPlanDTOs(List<TestingPlanObservationMaster> testingObservations)
	{
		return testingObservations.stream().filter(Objects::nonNull).map(this::getDTOfromDomain).collect(Collectors.toList());
	}
	
	private AuditTestingPlanMasterDTO getDTOfromDomain(TestingPlanObservationMaster master) {
		AuditTestingPlanMasterDTO testingPlanMasterDTO = new AuditTestingPlanMasterDTO();
		testingPlanMasterDTO.setAuditPlanEntity(master.getTestingPlanId().getAuditPlanId().getAuditPlanEntity());
		testingPlanMasterDTO.setDepartmentName(master.getTestingPlanId().getDeptId().getDeptName());
		testingPlanMasterDTO.setCostCentreName(master.getTestingPlanId().getCostCentreId().getCostCentreName());
		testingPlanMasterDTO.setObservation(master.getObservation());
		testingPlanMasterDTO.setMonetaryImpact(master.getMonetaryImpact());
		testingPlanMasterDTO.setAuditeeResponse(master.getAuditeeResponse());
		testingPlanMasterDTO.setInference(master.getInference());
		testingPlanMasterDTO.setAuditorName(master.getTestingPlanId().getAuditorId().getFirstName());
		testingPlanMasterDTO.setRiskArea(RiskAreaEnum.getRiskAreaById(master.getTestingPlanId().getRiskAreaId().intValue()));
		testingPlanMasterDTO.setTestResult(
				TestingResultEnum.getEnumById(master.getTestingPlanId().getTestResult().intValue()).getTestResult());
		testingPlanMasterDTO.setRating(master.getTestingPlanId().getRating());
		testingPlanMasterDTO.setExpectedRevertDate(master.getTestingPlanId().getExpectedRevertDate().toString());
		testingPlanMasterDTO.setResponded(master.getTestingPlanId().isResponded());
		return testingPlanMasterDTO;
	}
	public static List<AuditPlanObjectionMapDTO> getObjectiveDetailsFromAuditPlanAndProcedureMapping(
			List<AuditPlanObjectiveMap> objectives, List<AuditProcObjectiveMaster> procedureObjectives) {
		List<AuditPlanObjectionMapDTO> auditPlanObjectives = new ArrayList<>();
		objectives.stream().filter(obj -> obj.getIsActive()).forEach(objective -> {
			AuditPlanObjectionMapDTO auditPlanObjective = new AuditPlanObjectionMapDTO();
			auditPlanObjective.setId(objective.getId());
			auditPlanObjective.setObjectiveDescription(objective.getObjectiveDescription());
			auditPlanObjective.setObjectiveName(objective.getObjectiveName());
			auditPlanObjectives.add(auditPlanObjective);
		});
		procedureObjectives.stream().filter(obj -> obj.getIsActive() == true).forEach(obj -> {
			AuditPlanObjectionMapDTO auditProcObjective = new AuditPlanObjectionMapDTO();
			auditProcObjective.setId(obj.getId());
			auditProcObjective.setObjectiveDescription(obj.getObjectiveName());
			auditProcObjective.setObjectiveDescription(obj.getObjectiveDescription());
			auditPlanObjectives.add(auditProcObjective);
		});
		return auditPlanObjectives;
	}

	public static List<TestingPlanObservationMasterDTO> extractTestingPlanObservations(
			AuditTestingPlanMaster auditTestingPlanMaster) {
		List<TestingPlanObservationMasterDTO> testingPlanObservationData = new ArrayList<>();
		auditTestingPlanMaster.getTestingPlanObservationMasterList().stream().forEach(map -> {
			TestingPlanObservationMasterDTO testingPlanObservation = new TestingPlanObservationMasterDTO();
			testingPlanObservation.setId(map.getId());
			testingPlanObservation.setInference(map.getInference());
			testingPlanObservation.setObservation(map.getObservation());
			testingPlanObservation.setMonetaryImpact(map.getMonetaryImpact());
			testingPlanObservation.setAuditeeResponse(map.getAuditeeResponse());
			testingPlanObservationData.add(testingPlanObservation);
		});
		return testingPlanObservationData;
	}
}
