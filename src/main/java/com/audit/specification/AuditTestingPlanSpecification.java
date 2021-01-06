package com.audit.specification;

import java.time.Instant;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.audit.domain.AuditPlanMaster;
import com.audit.domain.AuditTestingPlanMaster;
import com.audit.repository.SearchCriteria;

public class AuditTestingPlanSpecification implements Specification<AuditTestingPlanMaster> {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = -1424143736060783389L;
	private SearchCriteria criteria;
 
    public AuditTestingPlanSpecification(SearchCriteria searchCriteria) {
    	this.criteria = searchCriteria;
    }

	@Override
    public Predicate toPredicate(Root<AuditTestingPlanMaster> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
  
		if (criteria.getOperation().equalsIgnoreCase(">")) {
			return builder.greaterThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().get(0).toString());
		} else if (criteria.getOperation().equalsIgnoreCase("<")) {
			return builder.lessThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().get(0).toString());
		} else if (criteria.getOperation().equalsIgnoreCase("between")) {
			return builder.between(root.<Instant>get(criteria.getKey()),(Instant)criteria.getValue().get(0),(Instant)criteria.getValue().get(1));
		} else if (criteria.getOperation().equalsIgnoreCase(":")) {
			if(criteria.getKey()!=null && (criteria.getKey().equalsIgnoreCase("auditeeId") || criteria.getKey().equalsIgnoreCase("auditorId") )) {
				return builder.equal(root.get(criteria.getKey()),(Long) criteria.getValue().get(0));
			}else if(criteria.getKey()!=null && criteria.getKey().equalsIgnoreCase("auditPlanId.auditPlanEntity")) {
				Join<AuditTestingPlanMaster, AuditPlanMaster> groupJoin = root.join("auditPlanId");
				return builder.like(builder.lower(groupJoin.<String>get("auditPlanEntity")), "%" + criteria.getValue().get(0).toString().toLowerCase() + "%");
			}else if (root.get(criteria.getKey()).getJavaType() == String.class) {
				return builder.like(root.<String>get(criteria.getKey()), "%" + criteria.getValue().get(0).toString() + "%");
			}else if (root.get(criteria.getKey()).getJavaType() == Boolean.class) {
				return builder.equal(root.get(criteria.getKey()),(Boolean) criteria.getValue().get(0));
			} else {
				return builder.equal(root.get(criteria.getKey()), criteria.getValue().get(0).toString());
			}
		}
        return null;
    }
}