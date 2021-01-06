package com.audit.specification;

import java.time.Instant;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.audit.domain.AuditPlanMaster;
import com.audit.repository.SearchCriteria;

public class AuditPlanSpecification implements Specification<AuditPlanMaster> {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = -1424143736060783389L;
	private SearchCriteria criteria;
 
    public AuditPlanSpecification(SearchCriteria searchCriteria) {
    	this.criteria = searchCriteria;
    }

	@Override
    public Predicate toPredicate(Root<AuditPlanMaster> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
  
		if (criteria.getOperation().equalsIgnoreCase(">")) {
			if(criteria.getKey()!=null && (criteria.getKey().equalsIgnoreCase("startDate"))) {
				return builder.greaterThanOrEqualTo(root.<Instant>get(criteria.getKey()), (Instant)criteria.getValue().get(0));	
			}
			return builder.greaterThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().get(0).toString());
		} else if (criteria.getOperation().equalsIgnoreCase("<")) {
			if(criteria.getKey()!=null && (criteria.getKey().equalsIgnoreCase("endDate"))) {
				return builder.lessThanOrEqualTo(root.<Instant>get(criteria.getKey()), (Instant)criteria.getValue().get(0));	
			}
			return builder.lessThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().get(0).toString());
		} else if (criteria.getOperation().equalsIgnoreCase("between")) {
			return builder.between(root.<Instant>get(criteria.getKey()),(Instant)criteria.getValue().get(0),(Instant)criteria.getValue().get(1));
		} else if (criteria.getOperation().equalsIgnoreCase(":")) {
			if(criteria.getKey()!=null && (criteria.getKey().equalsIgnoreCase("assignTo"))) {
				return builder.equal(root.get(criteria.getKey()),(Long) criteria.getValue().get(0));
			}else if (root.get(criteria.getKey()).getJavaType() == String.class) {
				return builder.like(builder.lower(root.<String>get(criteria.getKey())), "%" + criteria.getValue().get(0).toString().toLowerCase() + "%");
			}else if (root.get(criteria.getKey()).getJavaType() == Boolean.class) {
				return builder.equal(root.get(criteria.getKey()),(Boolean) criteria.getValue().get(0));
			} else {
				return builder.equal(root.get(criteria.getKey()), criteria.getValue().get(0).toString());
			}
		}
        return null;
    }
}