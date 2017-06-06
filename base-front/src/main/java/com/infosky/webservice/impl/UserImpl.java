package com.infosky.webservice.impl;

import javax.xml.ws.Endpoint;

import com.infosky.webservice.IUser;

public class UserImpl implements IUser {

    public String getUser(String id) {
        return "1111111111111" + id;
    }

    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8080/base-manager/webservice/user?wsdl", UserImpl.class);
        System.out.println("232323");
    }
}
