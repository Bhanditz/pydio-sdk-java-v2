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
 * UpdateUpdateResponse
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-11-30T14:51:15.861Z")
public class UpdateUpdateResponse {
    @SerializedName("Channel")
    private String channel = null;

    @SerializedName("AvailableBinaries")
    private List<UpdatePackage> availableBinaries = null;

    public UpdateUpdateResponse channel(String channel) {
        this.channel = channel;
        return this;
    }

    /**
     * Get channel
     *
     * @return channel
     **/
    @ApiModelProperty(value = "")
    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public UpdateUpdateResponse availableBinaries(List<UpdatePackage> availableBinaries) {
        this.availableBinaries = availableBinaries;
        return this;
    }

    public UpdateUpdateResponse addAvailableBinariesItem(UpdatePackage availableBinariesItem) {
        if (this.availableBinaries == null) {
            this.availableBinaries = new ArrayList<UpdatePackage>();
        }
        this.availableBinaries.add(availableBinariesItem);
        return this;
    }

    /**
     * Get availableBinaries
     *
     * @return availableBinaries
     **/
    @ApiModelProperty(value = "")
    public List<UpdatePackage> getAvailableBinaries() {
        return availableBinaries;
    }

    public void setAvailableBinaries(List<UpdatePackage> availableBinaries) {
        this.availableBinaries = availableBinaries;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UpdateUpdateResponse updateUpdateResponse = (UpdateUpdateResponse) o;
        return Objects.equals(this.channel, updateUpdateResponse.channel) &&
                Objects.equals(this.availableBinaries, updateUpdateResponse.availableBinaries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(channel, availableBinaries);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UpdateUpdateResponse {\n");

        sb.append("    channel: ").append(toIndentedString(channel)).append("\n");
        sb.append("    availableBinaries: ").append(toIndentedString(availableBinaries)).append("\n");
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

