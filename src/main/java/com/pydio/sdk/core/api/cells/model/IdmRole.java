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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;

/**
 * IdmRole
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-11-30T14:51:15.861Z")
public class IdmRole {
  @SerializedName("Uuid")
  private String uuid = null;

  @SerializedName("Label")
  private String label = null;

  @SerializedName("IsTeam")
  private Boolean isTeam = null;

  @SerializedName("GroupRole")
  private Boolean groupRole = null;

  @SerializedName("UserRole")
  private Boolean userRole = null;

  @SerializedName("LastUpdated")
  private Integer lastUpdated = null;

  @SerializedName("AutoApplies")
  private List<String> autoApplies = null;

  @SerializedName("Policies")
  private List<ServiceResourcePolicy> policies = null;

  @SerializedName("PoliciesContextEditable")
  private Boolean policiesContextEditable = null;

  public IdmRole uuid(String uuid) {
    this.uuid = uuid;
    return this;
  }

   /**
   * Get uuid
   * @return uuid
  **/
  @ApiModelProperty(value = "")
  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public IdmRole label(String label) {
    this.label = label;
    return this;
  }

   /**
   * Get label
   * @return label
  **/
  @ApiModelProperty(value = "")
  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public IdmRole isTeam(Boolean isTeam) {
    this.isTeam = isTeam;
    return this;
  }

   /**
   * Get isTeam
   * @return isTeam
  **/
  @ApiModelProperty(value = "")
  public Boolean isIsTeam() {
    return isTeam;
  }

  public void setIsTeam(Boolean isTeam) {
    this.isTeam = isTeam;
  }

  public IdmRole groupRole(Boolean groupRole) {
    this.groupRole = groupRole;
    return this;
  }

   /**
   * Get groupRole
   * @return groupRole
  **/
  @ApiModelProperty(value = "")
  public Boolean isGroupRole() {
    return groupRole;
  }

  public void setGroupRole(Boolean groupRole) {
    this.groupRole = groupRole;
  }

  public IdmRole userRole(Boolean userRole) {
    this.userRole = userRole;
    return this;
  }

   /**
   * Get userRole
   * @return userRole
  **/
  @ApiModelProperty(value = "")
  public Boolean isUserRole() {
    return userRole;
  }

  public void setUserRole(Boolean userRole) {
    this.userRole = userRole;
  }

  public IdmRole lastUpdated(Integer lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }

   /**
   * Get lastUpdated
   * @return lastUpdated
  **/
  @ApiModelProperty(value = "")
  public Integer getLastUpdated() {
    return lastUpdated;
  }

  public void setLastUpdated(Integer lastUpdated) {
    this.lastUpdated = lastUpdated;
  }

  public IdmRole autoApplies(List<String> autoApplies) {
    this.autoApplies = autoApplies;
    return this;
  }

  public IdmRole addAutoAppliesItem(String autoAppliesItem) {
    if (this.autoApplies == null) {
      this.autoApplies = new ArrayList<String>();
    }
    this.autoApplies.add(autoAppliesItem);
    return this;
  }

   /**
   * Get autoApplies
   * @return autoApplies
  **/
  @ApiModelProperty(value = "")
  public List<String> getAutoApplies() {
    return autoApplies;
  }

  public void setAutoApplies(List<String> autoApplies) {
    this.autoApplies = autoApplies;
  }

  public IdmRole policies(List<ServiceResourcePolicy> policies) {
    this.policies = policies;
    return this;
  }

  public IdmRole addPoliciesItem(ServiceResourcePolicy policiesItem) {
    if (this.policies == null) {
      this.policies = new ArrayList<ServiceResourcePolicy>();
    }
    this.policies.add(policiesItem);
    return this;
  }

   /**
   * Get policies
   * @return policies
  **/
  @ApiModelProperty(value = "")
  public List<ServiceResourcePolicy> getPolicies() {
    return policies;
  }

  public void setPolicies(List<ServiceResourcePolicy> policies) {
    this.policies = policies;
  }

  public IdmRole policiesContextEditable(Boolean policiesContextEditable) {
    this.policiesContextEditable = policiesContextEditable;
    return this;
  }

   /**
   * Get policiesContextEditable
   * @return policiesContextEditable
  **/
  @ApiModelProperty(value = "")
  public Boolean isPoliciesContextEditable() {
    return policiesContextEditable;
  }

  public void setPoliciesContextEditable(Boolean policiesContextEditable) {
    this.policiesContextEditable = policiesContextEditable;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IdmRole idmRole = (IdmRole) o;
    return Objects.equals(this.uuid, idmRole.uuid) &&
        Objects.equals(this.label, idmRole.label) &&
        Objects.equals(this.isTeam, idmRole.isTeam) &&
        Objects.equals(this.groupRole, idmRole.groupRole) &&
        Objects.equals(this.userRole, idmRole.userRole) &&
        Objects.equals(this.lastUpdated, idmRole.lastUpdated) &&
        Objects.equals(this.autoApplies, idmRole.autoApplies) &&
        Objects.equals(this.policies, idmRole.policies) &&
        Objects.equals(this.policiesContextEditable, idmRole.policiesContextEditable);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid, label, isTeam, groupRole, userRole, lastUpdated, autoApplies, policies, policiesContextEditable);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IdmRole {\n");
    
    sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    isTeam: ").append(toIndentedString(isTeam)).append("\n");
    sb.append("    groupRole: ").append(toIndentedString(groupRole)).append("\n");
    sb.append("    userRole: ").append(toIndentedString(userRole)).append("\n");
    sb.append("    lastUpdated: ").append(toIndentedString(lastUpdated)).append("\n");
    sb.append("    autoApplies: ").append(toIndentedString(autoApplies)).append("\n");
    sb.append("    policies: ").append(toIndentedString(policies)).append("\n");
    sb.append("    policiesContextEditable: ").append(toIndentedString(policiesContextEditable)).append("\n");
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

