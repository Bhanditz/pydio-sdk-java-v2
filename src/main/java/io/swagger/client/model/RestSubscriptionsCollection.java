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
import io.swagger.client.model.ActivitySubscription;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * RestSubscriptionsCollection
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-09-25T16:31:30.189Z")
public class RestSubscriptionsCollection {
  @SerializedName("subscriptions")
  private List<ActivitySubscription> subscriptions = null;

  public RestSubscriptionsCollection subscriptions(List<ActivitySubscription> subscriptions) {
    this.subscriptions = subscriptions;
    return this;
  }

  public RestSubscriptionsCollection addSubscriptionsItem(ActivitySubscription subscriptionsItem) {
    if (this.subscriptions == null) {
      this.subscriptions = new ArrayList<ActivitySubscription>();
    }
    this.subscriptions.add(subscriptionsItem);
    return this;
  }

   /**
   * Get subscriptions
   * @return subscriptions
  **/
  @ApiModelProperty(value = "")
  public List<ActivitySubscription> getSubscriptions() {
    return subscriptions;
  }

  public void setSubscriptions(List<ActivitySubscription> subscriptions) {
    this.subscriptions = subscriptions;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RestSubscriptionsCollection restSubscriptionsCollection = (RestSubscriptionsCollection) o;
    return Objects.equals(this.subscriptions, restSubscriptionsCollection.subscriptions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subscriptions);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RestSubscriptionsCollection {\n");
    
    sb.append("    subscriptions: ").append(toIndentedString(subscriptions)).append("\n");
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

