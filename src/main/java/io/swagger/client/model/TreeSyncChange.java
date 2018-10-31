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
import io.swagger.client.model.TreeSyncChangeNode;
import io.swagger.client.model.TreeSyncChangeType;
import java.io.IOException;

/**
 * TreeSyncChange
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-09-25T16:31:30.189Z")
public class TreeSyncChange {
  @SerializedName("seq")
  private String seq = null;

  @SerializedName("nodeId")
  private String nodeId = null;

  @SerializedName("type")
  private TreeSyncChangeType type = null;

  @SerializedName("source")
  private String source = null;

  @SerializedName("target")
  private String target = null;

  @SerializedName("node")
  private TreeSyncChangeNode node = null;

  public TreeSyncChange seq(String seq) {
    this.seq = seq;
    return this;
  }

   /**
   * Get seq
   * @return seq
  **/
  @ApiModelProperty(value = "")
  public String getSeq() {
    return seq;
  }

  public void setSeq(String seq) {
    this.seq = seq;
  }

  public TreeSyncChange nodeId(String nodeId) {
    this.nodeId = nodeId;
    return this;
  }

   /**
   * Get nodeId
   * @return nodeId
  **/
  @ApiModelProperty(value = "")
  public String getNodeId() {
    return nodeId;
  }

  public void setNodeId(String nodeId) {
    this.nodeId = nodeId;
  }

  public TreeSyncChange type(TreeSyncChangeType type) {
    this.type = type;
    return this;
  }

   /**
   * Get type
   * @return type
  **/
  @ApiModelProperty(value = "")
  public TreeSyncChangeType getType() {
    return type;
  }

  public void setType(TreeSyncChangeType type) {
    this.type = type;
  }

  public TreeSyncChange source(String source) {
    this.source = source;
    return this;
  }

   /**
   * Get source
   * @return source
  **/
  @ApiModelProperty(value = "")
  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public TreeSyncChange target(String target) {
    this.target = target;
    return this;
  }

   /**
   * Get target
   * @return target
  **/
  @ApiModelProperty(value = "")
  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  public TreeSyncChange node(TreeSyncChangeNode node) {
    this.node = node;
    return this;
  }

   /**
   * Get node
   * @return node
  **/
  @ApiModelProperty(value = "")
  public TreeSyncChangeNode getNode() {
    return node;
  }

  public void setNode(TreeSyncChangeNode node) {
    this.node = node;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TreeSyncChange treeSyncChange = (TreeSyncChange) o;
    return Objects.equals(this.seq, treeSyncChange.seq) &&
        Objects.equals(this.nodeId, treeSyncChange.nodeId) &&
        Objects.equals(this.type, treeSyncChange.type) &&
        Objects.equals(this.source, treeSyncChange.source) &&
        Objects.equals(this.target, treeSyncChange.target) &&
        Objects.equals(this.node, treeSyncChange.node);
  }

  @Override
  public int hashCode() {
    return Objects.hash(seq, nodeId, type, source, target, node);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TreeSyncChange {\n");
    
    sb.append("    seq: ").append(toIndentedString(seq)).append("\n");
    sb.append("    nodeId: ").append(toIndentedString(nodeId)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    source: ").append(toIndentedString(source)).append("\n");
    sb.append("    target: ").append(toIndentedString(target)).append("\n");
    sb.append("    node: ").append(toIndentedString(node)).append("\n");
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

