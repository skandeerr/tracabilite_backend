package com.tracabilite.tracabilite.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.xml.bind.annotation.XmlTransient;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

@MappedSuperclass
public class TimestampEntity implements Serializable, PreInsertEventListener, PreUpdateEventListener {

    private static final long serialVersionUID = 1L;

    @Column(name = "CREATION_DATE", nullable = false)
    @JsonIgnore
    @XmlTransient
    protected Date creationDate = new Date();

    @Column(name = "UPDATE_DATE", nullable = true)
    @JsonIgnore
    @XmlTransient
    protected Date updateDate;


    @Override
    public boolean onPreInsert(PreInsertEvent event) {
        if (event.getEntity() instanceof TimestampEntity) {
            TimestampEntity timestampEntity = (TimestampEntity) event.getEntity();
            timestampEntity.creationDate = Date.from(Instant.now());
            setPropertyState(event.getState(), event.getPersister().getPropertyNames(), "creationDate", Date.from(Instant.now()));

        }
        return false;
    }

    @Override
    public boolean onPreUpdate(PreUpdateEvent event) {
        if (event.getEntity() instanceof TimestampEntity) {
            TimestampEntity timestampEntity = (TimestampEntity) event.getEntity();
            timestampEntity.updateDate = Date.from(Instant.now());
            setPropertyState(event.getState(), event.getPersister().getPropertyNames(), "updateDate", Date.from(Instant.now()));
        }
        return false;
    }

    private void setPropertyState(Object[] propertyStates, String[] propertyNames, String propertyName, Object propertyState) {

        for (int i = 0; i < propertyNames.length; i++) {
            if (propertyName.equals(propertyNames[i])) {
                propertyStates[i] = propertyState;
                return;
            }
        }
    }
}



