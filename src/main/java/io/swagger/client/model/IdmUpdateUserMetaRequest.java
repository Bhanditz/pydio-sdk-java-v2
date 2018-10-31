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
import io.swagger.client.model.IdmUserMeta;
import io.swagger.client.model.UpdateUserMetaRequestUserMetaOp;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * IdmUpdateUserMetaRequest
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-09-25T16:31:30.189Z")
public class IdmUpdateUserMetaRequest {
  @SerializedName("Operation")
  private UpdateUserMetaRequestUserMetaOp operation = null;

  @SerializedName("MetaDatas")
  private List<IdmUserMeta> metaDatas = null;

  public IdmUpdateUserMetaRequest operation(UpdateUserMetaRequestUserMetaOp operation) {
    this.operation = operation;
    return this;
  }

   /**
   * Get operation
   * @return operation
  **/
  @ApiModelProperty(value = "")
  public UpdateUserMetaRequestUserMetaOp getOperation() {
    return operation;
  }

  public void setOperation(UpdateUserMetaRequestUserMetaOp operation) {
    this.operation = operation;
  }

  public IdmUpdateUserMetaRequest metaDatas(List<IdmUserMeta> metaDatas) {
    this.metaDatas = metaDatas;
    return this;
  }

  public IdmUpdateUserMetaRequest addMetaDatasItem(IdmUserMeta metaDatasItem) {
    if (this.metaDatas == null) {
      this.metaDatas = new ArrayList<IdmUserMeta>();
    }
    this.metaDatas.add(metaDatasItem);
    return this;
  }

   /**
   * Get metaDatas
   * @return metaDatas
  **/
  @ApiModelProperty(value = "")
  public List<IdmUserMeta> getMetaDatas() {
    return metaDatas;
  }

  public void setMetaDatas(List<IdmUserMeta> metaDatas) {
    this.metaDatas = metaDatas;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IdmUpdateUserMetaRequest idmUpdateUserMetaRequest = (IdmUpdateUserMetaRequest) o;
    return Objects.equals(this.operation, idmUpdateUserMetaRequest.operation) &&
        Objects.equals(this.metaDatas, idmUpdateUserMetaRequest.metaDatas);
  }

  @Override
  public int hashCode() {
    return Objects.hash(operation, metaDatas);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IdmUpdateUserMetaRequest {\n");
    
    sb.append("    operation: ").append(toIndentedString(operation)).append("\n");
    sb.append("    metaDatas: ").append(toIndentedString(metaDatas)).append("\n");
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

