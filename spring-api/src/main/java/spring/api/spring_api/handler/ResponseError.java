package spring.api.spring_api.handler;

import java.util.Date;

public class ResponseError {
    private Date timestamp = new Date();
    private String status = "error";
    private Integer statusCode = 409;
    private String error;


    public ResponseError(Integer statusCode, String error) {
        this.statusCode = statusCode;
        this.error = error;
    }
    
    public Date getTimestamp() {
        return timestamp;
    }
    public String getStatus() {
        return status;
    }
    public Integer getStatusCode() {
        return statusCode;
    }
    public String getError() {
        return error;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
    public void setError(String error) {
        this.error = error;
    }

    



    
}
