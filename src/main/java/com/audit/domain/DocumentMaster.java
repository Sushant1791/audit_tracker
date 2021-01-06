/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.audit.domain;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "document_master")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DocumentMaster implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
    private Long id;
    @Size(max = 255)
    @Column(name = "document_name", length = 255)
    private String documentName;
    @OneToMany(mappedBy = "documentId")
    private Collection<TestingPlanObservationMaster> testingPlanObservationMasterCollection;

    public DocumentMaster() {
    }

    public DocumentMaster(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public Collection<TestingPlanObservationMaster> getTestingPlanObservationMasterCollection() {
        return testingPlanObservationMasterCollection;
    }

    public void setTestingPlanObservationMasterCollection(Collection<TestingPlanObservationMaster> testingPlanObservationMasterCollection) {
        this.testingPlanObservationMasterCollection = testingPlanObservationMasterCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocumentMaster)) {
            return false;
        }
        DocumentMaster other = (DocumentMaster) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.audit.domain.DocumentMaster[ id=" + id + " ]";
    }
    
}
