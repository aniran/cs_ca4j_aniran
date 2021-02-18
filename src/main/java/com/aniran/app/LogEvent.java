package com.aniran.app;

enum State { STARTED, FINISHED }

public class LogEvent {

    private String id;
    private Long timestampStart;
    private Long timestampFinish;
    private Long duration;
    private String host;
    private String type;

    public LogEvent(String id, State state, long timestamp, String host, String type){
        this.id = id;
        this.host = host;
        this.type = type;
        this.duration = 0L;
        this.updateTimestamp(state, timestamp);
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

    public void updateTimestamp(State state, long timestamp){
        switch (state) {
            case STARTED:
                this.setTimestampStart(timestamp); break;
            case FINISHED:
                this.setTimestampFinish(timestamp); break;
        }
    }

    private void setTimestampStart(Long timestamp){
        this.timestampStart = timestamp;

        if (this.timestampStart != null && this.timestampFinish != null){
            this.duration = this.timestampFinish - this.timestampStart;
        }
    }

    private void setTimestampFinish(Long timestamp){
        this.timestampFinish = timestamp;

        if (this.timestampStart != null && this.timestampFinish != null){
            this.duration = this.timestampFinish - this.timestampStart;
        }
    }

    public LogEvent(String id, State state, long timestampStart){
        this(id, state, timestampStart, null, null);
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
