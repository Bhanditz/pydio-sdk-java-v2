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

import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;

/**
 * RestListPeerFoldersRequest
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-11-30T14:51:15.861Z")
public class RestListPeerFoldersRequest {
    @SerializedName("PeerAddress")
    private String peerAddress = null;

    @SerializedName("Path")
    private String path = null;

    public RestListPeerFoldersRequest peerAddress(String peerAddress) {
        this.peerAddress = peerAddress;
        return this;
    }

    /**
     * Get peerAddress
     *
     * @return peerAddress
     **/
    @ApiModelProperty(value = "")
    public String getPeerAddress() {
        return peerAddress;
    }

    public void setPeerAddress(String peerAddress) {
        this.peerAddress = peerAddress;
    }

    public RestListPeerFoldersRequest path(String path) {
        this.path = path;
        return this;
    }

    /**
     * Get path
     *
     * @return path
     **/
    @ApiModelProperty(value = "")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RestListPeerFoldersRequest restListPeerFoldersRequest = (RestListPeerFoldersRequest) o;
        return Objects.equals(this.peerAddress, restListPeerFoldersRequest.peerAddress) &&
                Objects.equals(this.path, restListPeerFoldersRequest.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(peerAddress, path);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class RestListPeerFoldersRequest {\n");

        sb.append("    peerAddress: ").append(toIndentedString(peerAddress)).append("\n");
        sb.append("    path: ").append(toIndentedString(path)).append("\n");
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

