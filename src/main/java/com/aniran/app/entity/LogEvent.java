package com.aniran.app.entity;

public class LogEvent {

    private String id;
    private EventState eventState;
    private long timestamp;
    private String host;
    private String type;

    public LogEvent(String id, EventState eventState, long timestamp, String host, String type) {
        this.id = id;
        this.eventState = eventState;
        this.timestamp = timestamp;
        this.host = host;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EventState getEventState() {
        return eventState;
    }

    public void setEventState(EventState eventState) {
        this.eventState = eventState;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
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
