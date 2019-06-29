/*Developed by Rakesh M D & Abhijith T N
 Copyright 2015*/

package com.innosar.controller;

import javax.faces.event.ActionEvent;

public class FacesUtil {





	    // Getters -----------------------------------------------------------------------------------

	    public static String getActionAttribute(ActionEvent event, String name) {
	        return (String) event.getComponent().getAttributes().get(name);
	    }

		public static String getRequestParameter(String string) {
			// TODO Auto-generated method stub
			return null;
		}

	}


