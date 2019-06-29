/*Developed by Rakesh M D & Abhijith T N
 Copyright 2015*/

package com.innosar.utils;

import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class JsfUtils {

	public static Object getManagedBean(String beanName) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		Object bean = ctx.getApplication().getELResolver()
				.getValue(ctx.getELContext(), null, beanName);
		return bean;
	}

	public static void addErrorMessages(List<String> messages) {
		for (String message : messages) {
			addErrorMessage(message);
		}
	}

	public static void addErrorMessage(String msg) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				msg, msg);
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	}

	public static void addErrorMessage(String title, String msg) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				title, msg);
		FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
	}

	public static void addSuccessMessage(String title, String msg) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				title, msg);
		FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
	}

	public static void addInfoMessage(String title, String msg) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN,
				title, msg);
		FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
	}

	public static void addSuccessMessage(String msg) {
		addSuccessMessage(msg, msg);
	}

	public static void clearComponentHierarchy(UIComponent pComponent) {

		if (pComponent.isRendered()) {

			if (pComponent instanceof EditableValueHolder) {
				EditableValueHolder editableValueHolder = (EditableValueHolder) pComponent;
				editableValueHolder.setSubmittedValue(null);
				editableValueHolder.setValue(null);
				editableValueHolder.setLocalValueSet(false);
				editableValueHolder.setValid(true);
			}

			for (Iterator<UIComponent> iterator = pComponent
					.getFacetsAndChildren(); iterator.hasNext();) {
				clearComponentHierarchy(iterator.next());
			}

		}
	}

	public static String getCurrentUser() {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getRemoteUser();
	}
}
