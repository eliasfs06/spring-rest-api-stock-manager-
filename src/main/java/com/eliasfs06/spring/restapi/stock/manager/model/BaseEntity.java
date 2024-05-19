package com.eliasfs06.spring.restapi.stock.manager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {

    @Column(nullable = true, updatable = false)
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date edtionDate;

    @Column
    private Boolean active = true;

    public BaseEntity() {
        super();
    }

    public abstract Long getId();

    public abstract void setId(Long id);

    @JsonIgnore
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getEdtionDate() {
        return edtionDate;
    }

    public void setEdtionDate(Date edtionDate) {
        this.edtionDate = edtionDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity that)) return false;
        return getCreationDate().equals(that.getCreationDate()) && Objects.equals(getEdtionDate(), that.getEdtionDate()) && getActive().equals(that.getActive());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCreationDate(), getEdtionDate(), getActive());
    }

    @PreUpdate
    public void preUpdate() {
        this.edtionDate = new Date();
    }

    @JsonIgnore
    public Boolean getActive() {
        return active;
    }

    @JsonIgnore
    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}

