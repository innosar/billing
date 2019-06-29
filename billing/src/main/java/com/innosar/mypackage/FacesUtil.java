/*Developed by Rakesh M D & Abhijith T N
 Copyright 2015*/

package com.innosar.mypackage;

import javax.faces.context.FacesContext;

public class FacesUtil {

    // Getters -----------------------------------------------------------------------------------

    public static String getRequestParameter(String name) {
        return (String) FacesContext.getCurrentInstance().getExternalContext()
            .getRequestParameterMap().get(name);
    }

}
