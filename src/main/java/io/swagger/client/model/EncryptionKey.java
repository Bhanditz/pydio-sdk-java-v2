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
import io.swagger.client.model.EncryptionKeyInfo;
import java.io.IOException;

/**
 * EncryptionKey
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-09-25T16:31:30.189Z")
public class EncryptionKey {
  @SerializedName("Owner")
  private String owner = null;

  @SerializedName("ID")
  private String ID = null;

  @SerializedName("Label")
  private String label = null;

  @SerializedName("Content")
  private String content = null;

  @SerializedName("CreationDate")
  private Integer creationDate = null;

  @SerializedName("Info")
  private EncryptionKeyInfo info = null;

  public EncryptionKey owner(String owner) {
    this.owner = owner;
    return this;
  }

   /**
   * Get owner
   * @return owner
  **/
  @ApiModelProperty(value = "")
  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public EncryptionKey ID(String ID) {
    this.ID = ID;
    return this;
  }

   /**
   * Get ID
   * @return ID
  **/
  @ApiModelProperty(value = "")
  public String getID() {
    return ID;
  }

  public void setID(String ID) {
    this.ID = ID;
  }

  public EncryptionKey label(String label) {
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

  public EncryptionKey content(String content) {
    this.content = content;
    return this;
  }

   /**
   * Get content
   * @return content
  **/
  @ApiModelProperty(value = "")
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public EncryptionKey creationDate(Integer creationDate) {
    this.creationDate = creationDate;
    return this;
  }

   /**
   * Get creationDate
   * @return creationDate
  **/
  @ApiModelProperty(value = "")
  public Integer getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Integer creationDate) {
    this.creationDate = creationDate;
  }

  public EncryptionKey info(EncryptionKeyInfo info) {
    this.info = info;
    return this;
  }

   /**
   * Get info
   * @return info
  **/
  @ApiModelProperty(value = "")
  public EncryptionKeyInfo getInfo() {
    return info;
  }

  public void setInfo(EncryptionKeyInfo info) {
    this.info = info;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EncryptionKey encryptionKey = (EncryptionKey) o;
    return Objects.equals(this.owner, encryptionKey.owner) &&
        Objects.equals(this.ID, encryptionKey.ID) &&
        Objects.equals(this.label, encryptionKey.label) &&
        Objects.equals(this.content, encryptionKey.content) &&
        Objects.equals(this.creationDate, encryptionKey.creationDate) &&
        Objects.equals(this.info, encryptionKey.info);
  }

  @Override
  public int hashCode() {
    return Objects.hash(owner, ID, label, content, creationDate, info);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EncryptionKey {\n");
    
    sb.append("    owner: ").append(toIndentedString(owner)).append("\n");
    sb.append("    ID: ").append(toIndentedString(ID)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
    sb.append("    info: ").append(toIndentedString(info)).append("\n");
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

