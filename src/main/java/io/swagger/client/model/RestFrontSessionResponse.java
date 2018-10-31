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


package io.swagger.client.model;

import java.util.Objects;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RestFrontSessionResponse
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-09-25T16:31:30.189Z")
public class RestFrontSessionResponse {
  @SerializedName("JWT")
  private String JWT = null;

  @SerializedName("ExpireTime")
  private Integer expireTime = null;

  @SerializedName("Trigger")
  private String trigger = null;

  @SerializedName("TriggerInfo")
  private Map<String, String> triggerInfo = null;

  public RestFrontSessionResponse JWT(String JWT) {
    this.JWT = JWT;
    return this;
  }

   /**
   * Get JWT
   * @return JWT
  **/
  @ApiModelProperty(value = "")
  public String getJWT() {
    return JWT;
  }

  public void setJWT(String JWT) {
    this.JWT = JWT;
  }

  public RestFrontSessionResponse expireTime(Integer expireTime) {
    this.expireTime = expireTime;
    return this;
  }

   /**
   * Get expireTime
   * @return expireTime
  **/
  @ApiModelProperty(value = "")
  public Integer getExpireTime() {
    return expireTime;
  }

  public void setExpireTime(Integer expireTime) {
    this.expireTime = expireTime;
  }

  public RestFrontSessionResponse trigger(String trigger) {
    this.trigger = trigger;
    return this;
  }

   /**
   * Get trigger
   * @return trigger
  **/
  @ApiModelProperty(value = "")
  public String getTrigger() {
    return trigger;
  }

  public void setTrigger(String trigger) {
    this.trigger = trigger;
  }

  public RestFrontSessionResponse triggerInfo(Map<String, String> triggerInfo) {
    this.triggerInfo = triggerInfo;
    return this;
  }

  public RestFrontSessionResponse putTriggerInfoItem(String key, String triggerInfoItem) {
    if (this.triggerInfo == null) {
      this.triggerInfo = new HashMap<String, String>();
    }
    this.triggerInfo.put(key, triggerInfoItem);
    return this;
  }

   /**
   * Get triggerInfo
   * @return triggerInfo
  **/
  @ApiModelProperty(value = "")
  public Map<String, String> getTriggerInfo() {
    return triggerInfo;
  }

  public void setTriggerInfo(Map<String, String> triggerInfo) {
    this.triggerInfo = triggerInfo;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RestFrontSessionResponse restFrontSessionResponse = (RestFrontSessionResponse) o;
    return Objects.equals(this.JWT, restFrontSessionResponse.JWT) &&
        Objects.equals(this.expireTime, restFrontSessionResponse.expireTime) &&
        Objects.equals(this.trigger, restFrontSessionResponse.trigger) &&
        Objects.equals(this.triggerInfo, restFrontSessionResponse.triggerInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(JWT, expireTime, trigger, triggerInfo);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RestFrontSessionResponse {\n");
    
    sb.append("    JWT: ").append(toIndentedString(JWT)).append("\n");
    sb.append("    expireTime: ").append(toIndentedString(expireTime)).append("\n");
    sb.append("    trigger: ").append(toIndentedString(trigger)).append("\n");
    sb.append("    triggerInfo: ").append(toIndentedString(triggerInfo)).append("\n");
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

