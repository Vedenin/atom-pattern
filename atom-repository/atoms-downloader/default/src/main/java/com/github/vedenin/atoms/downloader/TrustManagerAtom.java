package com.github.vedenin.atoms.downloader;

import com.github.vedenin.atom.annotations.Atom;
import com.github.vedenin.atom.annotations.AtomException;
import com.github.vedenin.atom.annotations.BoilerPlate;
import com.github.vedenin.atoms.downloader.exceptions.DownloaderAtomException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * Atom that create TrustManager to all Https connection
 *
 * Created by vvedenin on 4/19/2017.
 */
@SuppressWarnings("WeakerAccess")
@Atom({TrustManager.class, HttpsURLConnection.class, X509TrustManager.class, SSLContext.class})
@AtomException(DownloaderAtomException.class)
public class TrustManagerAtom {

    private static final String SSL_CONST = "SSL";

    public void activate() {
        try {
            // Create a new_version trust manager that trust all certificates
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        public void checkClientTrusted(
                                java.security.cert.X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(
                                java.security.cert.X509Certificate[] certs, String authType) {
                        }
                    }
            };

            // Activate the new_version trust manager
            try {
                SSLContext sc = SSLContext.getInstance(SSL_CONST);
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            } catch (NoSuchAlgorithmException | KeyManagementException exp) {
                throw new DownloaderAtomException(exp);
            }
        } catch (Exception exp) {
            throw new DownloaderAtomException(exp);
        }
    }

    @BoilerPlate
    public static TrustManagerAtom create() {
        return new TrustManagerAtom();
    }
}
