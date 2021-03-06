package com.pydio.sdk.core.model;


import java.io.Serializable;
import java.util.Properties;


public interface Node extends Serializable {
	
	int TYPE_REMOTE_FILE 	= 1;
	int TYPE_WORKSPACE 		= 2;
	int TYPE_SERVER 		= 3;
	int TYPE_LOCAL_FILE 	= 4;
	int TYPE_OTHER 			= 5;
	int TYPE_SEARCH 		= 6;

    int same = 0;
    int content = 1;
    int different = 2;

	int type();

	String id();

	String label();

	String path();

    String getProperty(String key);

    void setProperty(String key, String value);

    void deleteProperty(String key);

    void setProperties(Properties p);

    String getEncoded();

	int compare(Node node);

	String getEncodedHash();
}
