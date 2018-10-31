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

/**
 * RestResetPasswordRequest
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-09-25T16:31:30.189Z")
public class RestResetPasswordRequest {
  @SerializedName("ResetPasswordToken")
  private String resetPasswordToken = null;

  @SerializedName("UserLogin")
  private String userLogin = null;

  @SerializedName("NewPassword")
  private String newPassword = null;

  public RestResetPasswordRequest resetPasswordToken(String resetPasswordToken) {
    this.resetPasswordToken = resetPasswordToken;
    return this;
  }

   /**
   * Get resetPasswordToken
   * @return resetPasswordToken
  **/
  @ApiModelProperty(value = "")
  public String getResetPasswordToken() {
    return resetPasswordToken;
  }

  public void setResetPasswordToken(String resetPasswordToken) {
    this.resetPasswordToken = resetPasswordToken;
  }

  public RestResetPasswordRequest userLogin(String userLogin) {
    this.userLogin = userLogin;
    return this;
  }

   /**
   * Get userLogin
   * @return userLogin
  **/
  @ApiModelProperty(value = "")
  public String getUserLogin() {
    return userLogin;
  }

  public void setUserLogin(String userLogin) {
    this.userLogin = userLogin;
  }

  public RestResetPasswordRequest newPassword(String newPassword) {
    this.newPassword = newPassword;
    return this;
  }

   /**
   * Get newPassword
   * @return newPassword
  **/
  @ApiModelProperty(value = "")
  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RestResetPasswordRequest restResetPasswordRequest = (RestResetPasswordRequest) o;
    return Objects.equals(this.resetPasswordToken, restResetPasswordRequest.resetPasswordToken) &&
        Objects.equals(this.userLogin, restResetPasswordRequest.userLogin) &&
        Objects.equals(this.newPassword, restResetPasswordRequest.newPassword);
  }

  @Override
  public int hashCode() {
    return Objects.hash(resetPasswordToken, userLogin, newPassword);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RestResetPasswordRequest {\n");
    
    sb.append("    resetPasswordToken: ").append(toIndentedString(resetPasswordToken)).append("\n");
    sb.append("    userLogin: ").append(toIndentedString(userLogin)).append("\n");
    sb.append("    newPassword: ").append(toIndentedString(newPassword)).append("\n");
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

