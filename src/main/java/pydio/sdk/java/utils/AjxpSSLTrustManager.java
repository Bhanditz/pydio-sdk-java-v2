/**
 *  Copyright 2012 Charles du Jeu
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 *  This file is part of the AjaXplorer Java Client
 *  More info on http://ajaxplorer.info/
 */
package pydio.sdk.java.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;


public class AjxpSSLTrustManager implements X509TrustManager {
	
		private String certKey = null;
        private static final X509Certificate[] _AcceptedIssuers = new X509Certificate[] {};

        AjxpSSLTrustManager(String certKey){
        	super();
        	this.certKey = certKey;
        }
        AjxpSSLTrustManager(){
        	super();
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        	if( this.certKey == null ){
        		// This is the Accept All certificates case.
        		return;
        	}

        	// Otherwise, we have a certKey defined. We should now examine the one we got from the server.
        	// They match? All is good. They don't, throw an exception.
        	String our_key = this.certKey.replaceAll("\\s+", "");
        	try {
            	//Assume self-signed root is okay?
            	X509Certificate ss_cert = chain[0];
				String thumbprint = AjxpSSLTrustManager.getThumbPrint(ss_cert);
				if( our_key.equalsIgnoreCase(thumbprint) ){
					return;
				}
				else {
					throw new CertificateException("Certificate key [" + thumbprint + "] doesn't match expected value.");
				}
			} catch (NoSuchAlgorithmException e) {
				throw new CertificateException("Unable to check self-signed cert, unknown algorithm. " + e.toString());
			}
        }

        public boolean isClientTrusted(X509Certificate[] chain) {
                return true;
        }

        public boolean isServerTrusted(X509Certificate[] chain) {
                return true;
        }

        public X509Certificate[] getAcceptedIssuers() {
                return _AcceptedIssuers;
        }

        // Thank you: http://stackoverflow.com/questions/1270703/how-to-retrieve-compute-an-x509-certificates-thumbprint-in-java
        private static String getThumbPrint(X509Certificate cert) throws NoSuchAlgorithmException, CertificateEncodingException {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] der = cert.getEncoded();
            md.update(der);
            byte[] digest = md.digest();
            return hexify(digest);
        }

        private static String hexify (byte bytes[]) {
            char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7',
                            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

            StringBuffer buf = new StringBuffer(bytes.length * 2);

            for (int i = 0; i < bytes.length; ++i) {
                    buf.append(hexDigits[(bytes[i] & 0xf0) >> 4]);
                buf.append(hexDigits[bytes[i] & 0x0f]);
            }

            return buf.toString();
        }
}