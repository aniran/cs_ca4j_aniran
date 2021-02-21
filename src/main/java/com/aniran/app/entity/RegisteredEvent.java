package com.aniran.app.entity;

import javax.persistence.*;

@Entity
@Table(name="log_event")
public class RegisteredEvent {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "timestamp_start")
    private Long timestampStart;

    @Column(name = "timestamp_finish")
    private Long timestampFinish;

    @Column
    private Long duration;

    @Column
    private String host;

    @Column
    private String type;

    @Column
    private Boolean alert;

    public RegisteredEvent(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTimestampStart() {
        return timestampStart;
    }

    public void setTimestampStart(Long timestampStart) {
        this.timestampStart = timestampStart;
    }

    public Long getTimestampFinish() {
        return timestampFinish;
    }

    public void setTimestampFinish(Long timestampFinish) {
        this.timestampFinish = timestampFinish;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getAlert() {
        return alert;
    }

    public void setAlert(Boolean alert) {
        this.alert = alert;
    }
}
