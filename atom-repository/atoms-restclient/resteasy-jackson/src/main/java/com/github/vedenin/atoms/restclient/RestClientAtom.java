package com.github.vedenin.atoms.restclient;

import com.github.vedenin.atoms.restclient.exceptions.RestClientAtomException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

/**
 * Simple Rest client
 *
 * Created by slava on 04.03.18.
 */
@SuppressWarnings("WeakerAccess")
public class RestClientAtom {

    public <T> T getEntity(String url, Class<T> entityClass) {
        Response response = null;
        try {
            Client client = ClientBuilder.newBuilder().build();
            response = client.target(url).request().get();
            return response.readEntity(entityClass);
        } catch (Exception exp) {
            throw new RestClientAtomException("RestClientAtom.getEntity: ", exp);
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    public static RestClientAtom create() {
        return new RestClientAtom();
    }

}
