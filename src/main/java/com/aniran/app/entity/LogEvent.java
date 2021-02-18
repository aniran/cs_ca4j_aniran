package com.aniran.app.entity;

import javax.persistence.*;

@Entity
@Table(name="log_event")
public class LogEvent {
    private static final int ALERT_THRESHOLD = 4;

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

    public LogEvent(){}

    public LogEvent(String id, EventState eventState, long timestamp, String host, String type){
        this.id = id;
        this.host = host;
        this.type = type;
        this.duration = 0L;
        this.updateTimestamp(eventState, timestamp);
        this.alert = false;
    }

    public String getStringSQLinsertValues(){
        String evalHost = this.host == null ? "NULL" : "'" + this.host + "'" ;
        String evalType = this.type == null ? "NULL" : "'" + this.type + "'" ;

        return "'"+ id +"', " +
                timestampStart.toString() + ", " +
                timestampFinish.toString() + ", " +
                duration.toString() + ", " +
                evalHost + ", " +
                evalType;
    }

    public Boolean isAlert(){
        return this.alert;
    }

    public void updateTimestamp(EventState eventState, long timestamp){
        switch (eventState) {
            case STARTED:
                this.timestampStart = timestamp;
                break;
            case FINISHED:
                this.timestampFinish = timestamp;
                break;
        }
        if (this.timestampStart != null && this.timestampFinish != null){
            this.duration = this.timestampFinish - this.timestampStart;
            this.alert = duration > ALERT_THRESHOLD;
        }
    }

    public LogEvent(String id, EventState eventState, long timestampStart){
        this(id, eventState, timestampStart, null, null);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTimestampStart() {
        return timestampStart;
    }

    public Long getTimestampFinish() {
        return timestampFinish;
    }

    public Long getDuration() {
        return duration;
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
}
