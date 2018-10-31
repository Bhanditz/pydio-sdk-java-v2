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
import java.util.ArrayList;
import java.util.List;

/**
 * RestGetBulkMetaRequest
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-09-25T16:31:30.189Z")
public class RestGetBulkMetaRequest {
  @SerializedName("NodePaths")
  private List<String> nodePaths = null;

  @SerializedName("NodeUuids")
  private List<String> nodeUuids = null;

  @SerializedName("AllMetaProviders")
  private Boolean allMetaProviders = null;

  @SerializedName("Versions")
  private Boolean versions = null;

  @SerializedName("Offset")
  private Integer offset = null;

  @SerializedName("Limit")
  private Integer limit = null;

  public RestGetBulkMetaRequest nodePaths(List<String> nodePaths) {
    this.nodePaths = nodePaths;
    return this;
  }

  public RestGetBulkMetaRequest addNodePathsItem(String nodePathsItem) {
    if (this.nodePaths == null) {
      this.nodePaths = new ArrayList<String>();
    }
    this.nodePaths.add(nodePathsItem);
    return this;
  }

   /**
   * Get nodePaths
   * @return nodePaths
  **/
  @ApiModelProperty(value = "")
  public List<String> getNodePaths() {
    return nodePaths;
  }

  public void setNodePaths(List<String> nodePaths) {
    this.nodePaths = nodePaths;
  }

  public RestGetBulkMetaRequest nodeUuids(List<String> nodeUuids) {
    this.nodeUuids = nodeUuids;
    return this;
  }

  public RestGetBulkMetaRequest addNodeUuidsItem(String nodeUuidsItem) {
    if (this.nodeUuids == null) {
      this.nodeUuids = new ArrayList<String>();
    }
    this.nodeUuids.add(nodeUuidsItem);
    return this;
  }

   /**
   * Get nodeUuids
   * @return nodeUuids
  **/
  @ApiModelProperty(value = "")
  public List<String> getNodeUuids() {
    return nodeUuids;
  }

  public void setNodeUuids(List<String> nodeUuids) {
    this.nodeUuids = nodeUuids;
  }

  public RestGetBulkMetaRequest allMetaProviders(Boolean allMetaProviders) {
    this.allMetaProviders = allMetaProviders;
    return this;
  }

   /**
   * Get allMetaProviders
   * @return allMetaProviders
  **/
  @ApiModelProperty(value = "")
  public Boolean isAllMetaProviders() {
    return allMetaProviders;
  }

  public void setAllMetaProviders(Boolean allMetaProviders) {
    this.allMetaProviders = allMetaProviders;
  }

  public RestGetBulkMetaRequest versions(Boolean versions) {
    this.versions = versions;
    return this;
  }

   /**
   * Get versions
   * @return versions
  **/
  @ApiModelProperty(value = "")
  public Boolean isVersions() {
    return versions;
  }

  public void setVersions(Boolean versions) {
    this.versions = versions;
  }

  public RestGetBulkMetaRequest offset(Integer offset) {
    this.offset = offset;
    return this;
  }

   /**
   * Get offset
   * @return offset
  **/
  @ApiModelProperty(value = "")
  public Integer getOffset() {
    return offset;
  }

  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  public RestGetBulkMetaRequest limit(Integer limit) {
    this.limit = limit;
    return this;
  }

   /**
   * Get limit
   * @return limit
  **/
  @ApiModelProperty(value = "")
  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RestGetBulkMetaRequest restGetBulkMetaRequest = (RestGetBulkMetaRequest) o;
    return Objects.equals(this.nodePaths, restGetBulkMetaRequest.nodePaths) &&
        Objects.equals(this.nodeUuids, restGetBulkMetaRequest.nodeUuids) &&
        Objects.equals(this.allMetaProviders, restGetBulkMetaRequest.allMetaProviders) &&
        Objects.equals(this.versions, restGetBulkMetaRequest.versions) &&
        Objects.equals(this.offset, restGetBulkMetaRequest.offset) &&
        Objects.equals(this.limit, restGetBulkMetaRequest.limit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nodePaths, nodeUuids, allMetaProviders, versions, offset, limit);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RestGetBulkMetaRequest {\n");
    
    sb.append("    nodePaths: ").append(toIndentedString(nodePaths)).append("\n");
    sb.append("    nodeUuids: ").append(toIndentedString(nodeUuids)).append("\n");
    sb.append("    allMetaProviders: ").append(toIndentedString(allMetaProviders)).append("\n");
    sb.append("    versions: ").append(toIndentedString(versions)).append("\n");
    sb.append("    offset: ").append(toIndentedString(offset)).append("\n");
    sb.append("    limit: ").append(toIndentedString(limit)).append("\n");
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

