/*
 * Pydio Cells Rest API
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: 1.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.pydio.sdk.core.api.cells.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;

/**
 * JobsCtrlCommand
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-11-30T14:51:15.861Z")
public class JobsCtrlCommand {
  @SerializedName("Cmd")
  private JobsCommand cmd = null;

  @SerializedName("JobId")
  private String jobId = null;

  @SerializedName("TaskId")
  private String taskId = null;

  @SerializedName("OwnerId")
  private String ownerId = null;

  public JobsCtrlCommand cmd(JobsCommand cmd) {
    this.cmd = cmd;
    return this;
  }

   /**
   * Get cmd
   * @return cmd
  **/
  @ApiModelProperty(value = "")
  public JobsCommand getCmd() {
    return cmd;
  }

  public void setCmd(JobsCommand cmd) {
    this.cmd = cmd;
  }

  public JobsCtrlCommand jobId(String jobId) {
    this.jobId = jobId;
    return this;
  }

   /**
   * Get jobId
   * @return jobId
  **/
  @ApiModelProperty(value = "")
  public String getJobId() {
    return jobId;
  }

  public void setJobId(String jobId) {
    this.jobId = jobId;
  }

  public JobsCtrlCommand taskId(String taskId) {
    this.taskId = taskId;
    return this;
  }

   /**
   * Get taskId
   * @return taskId
  **/
  @ApiModelProperty(value = "")
  public String getTaskId() {
    return taskId;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

  public JobsCtrlCommand ownerId(String ownerId) {
    this.ownerId = ownerId;
    return this;
  }

   /**
   * Get ownerId
   * @return ownerId
  **/
  @ApiModelProperty(value = "")
  public String getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(String ownerId) {
    this.ownerId = ownerId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JobsCtrlCommand jobsCtrlCommand = (JobsCtrlCommand) o;
    return Objects.equals(this.cmd, jobsCtrlCommand.cmd) &&
        Objects.equals(this.jobId, jobsCtrlCommand.jobId) &&
        Objects.equals(this.taskId, jobsCtrlCommand.taskId) &&
        Objects.equals(this.ownerId, jobsCtrlCommand.ownerId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cmd, jobId, taskId, ownerId);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JobsCtrlCommand {\n");
    
    sb.append("    cmd: ").append(toIndentedString(cmd)).append("\n");
    sb.append("    jobId: ").append(toIndentedString(jobId)).append("\n");
    sb.append("    taskId: ").append(toIndentedString(taskId)).append("\n");
    sb.append("    ownerId: ").append(toIndentedString(ownerId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

