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
import io.swagger.client.model.ServiceResourcePolicyAction;
import io.swagger.client.model.ServiceResourcePolicyPolicyEffect;
import java.io.IOException;

/**
 * ServiceResourcePolicy
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-09-25T16:31:30.189Z")
public class ServiceResourcePolicy {
  @SerializedName("id")
  private String id = null;

  @SerializedName("Resource")
  private String resource = null;

  @SerializedName("Action")
  private ServiceResourcePolicyAction action = null;

  @SerializedName("Subject")
  private String subject = null;

  @SerializedName("Effect")
  private ServiceResourcePolicyPolicyEffect effect = null;

  @SerializedName("JsonConditions")
  private String jsonConditions = null;

  public ServiceResourcePolicy id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(value = "")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public ServiceResourcePolicy resource(String resource) {
    this.resource = resource;
    return this;
  }

   /**
   * Get resource
   * @return resource
  **/
  @ApiModelProperty(value = "")
  public String getResource() {
    return resource;
  }

  public void setResource(String resource) {
    this.resource = resource;
  }

  public ServiceResourcePolicy action(ServiceResourcePolicyAction action) {
    this.action = action;
    return this;
  }

   /**
   * Get action
   * @return action
  **/
  @ApiModelProperty(value = "")
  public ServiceResourcePolicyAction getAction() {
    return action;
  }

  public void setAction(ServiceResourcePolicyAction action) {
    this.action = action;
  }

  public ServiceResourcePolicy subject(String subject) {
    this.subject = subject;
    return this;
  }

   /**
   * Get subject
   * @return subject
  **/
  @ApiModelProperty(value = "")
  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public ServiceResourcePolicy effect(ServiceResourcePolicyPolicyEffect effect) {
    this.effect = effect;
    return this;
  }

   /**
   * Get effect
   * @return effect
  **/
  @ApiModelProperty(value = "")
  public ServiceResourcePolicyPolicyEffect getEffect() {
    return effect;
  }

  public void setEffect(ServiceResourcePolicyPolicyEffect effect) {
    this.effect = effect;
  }

  public ServiceResourcePolicy jsonConditions(String jsonConditions) {
    this.jsonConditions = jsonConditions;
    return this;
  }

   /**
   * Get jsonConditions
   * @return jsonConditions
  **/
  @ApiModelProperty(value = "")
  public String getJsonConditions() {
    return jsonConditions;
  }

  public void setJsonConditions(String jsonConditions) {
    this.jsonConditions = jsonConditions;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServiceResourcePolicy serviceResourcePolicy = (ServiceResourcePolicy) o;
    return Objects.equals(this.id, serviceResourcePolicy.id) &&
        Objects.equals(this.resource, serviceResourcePolicy.resource) &&
        Objects.equals(this.action, serviceResourcePolicy.action) &&
        Objects.equals(this.subject, serviceResourcePolicy.subject) &&
        Objects.equals(this.effect, serviceResourcePolicy.effect) &&
        Objects.equals(this.jsonConditions, serviceResourcePolicy.jsonConditions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, resource, action, subject, effect, jsonConditions);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServiceResourcePolicy {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    resource: ").append(toIndentedString(resource)).append("\n");
    sb.append("    action: ").append(toIndentedString(action)).append("\n");
    sb.append("    subject: ").append(toIndentedString(subject)).append("\n");
    sb.append("    effect: ").append(toIndentedString(effect)).append("\n");
    sb.append("    jsonConditions: ").append(toIndentedString(jsonConditions)).append("\n");
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

