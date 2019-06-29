/*Developed by Rakesh M D & Abhijith T N
 Copyright 2015*/

package com.innosar.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.innosar.bean.Addmodify;
import com.innosar.dao.QueryData;
import com.innosar.dao.AddmodifyDao;
import com.innosar.utils.JsfUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

@ManagedBean(name = "addmodifyController")
@ViewScoped
public class AddmodifyController implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1762563989143654209L;

	private LazyDataModel<Addmodify> lazyModel;

	private Addmodify addmodify = new Addmodify();
	private Addmodify selectedAddmodify = new Addmodify();
	private transient AddmodifyDao dao = new AddmodifyDao();

	@PostConstruct
	void intializeSession() {
		FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}

	/**
	 * @return the addmodify
	 */
	public Addmodify getAddmodify() {
		return addmodify;
	}

	/**
	 * @param addmodify
	 *            the addmodify to set
	 */
	public void setAddmodify(Addmodify addmodify) {
		this.addmodify = addmodify;
	}

	/**
	 * @return the selectedAddmodify
	 */
	public Addmodify getSelectedAddmodify() {
		return selectedAddmodify;
	}

	/**
	 * @param selectedAddmodify
	 *            the selectedAddmodify to set
	 */
	public void setSelectedAddmodify(Addmodify selectedAddmodify) {
		this.selectedAddmodify = selectedAddmodify;
	}

	public List<Addmodify> findAll() {
		Addmodify filter = new Addmodify();
		QueryData<Addmodify> data = new QueryData<Addmodify>(0, 1000000000, "name",
				"DESC", filter);
		dao.search(data);
		return data.getData();
	}

	public LazyDataModel<Addmodify> getLazyModel() {
		if (lazyModel == null) {
			lazyModel = new LazyDataModel<Addmodify>() {

				private static final long serialVersionUID = 1L;

				@Override
				public Addmodify getRowData(String rowKey) {
					return fetchRow(rowKey);
				}

				@Override
				public Object getRowKey(Addmodify object) {
					return object;
				}


				public List<Addmodify> load(int first, int pageSize,
						String sortField, SortOrder sortOrder,
						Map<String, Object> filters) {

					String order = (SortOrder.DESCENDING.equals(sortOrder)) ? "DESC"
							: "ASC";

					Addmodify addmodify = new Addmodify();
					addmodify.mask(filters);

					QueryData<Addmodify> data = new QueryData<Addmodify>(first,
							pageSize, sortField, order, addmodify);

					dao.search(data);

					this.setRowCount(data.getTotalResultCount());

					return data.getData();
				}

				@Override
				public void setRowIndex(int rowIndex) {
					if (rowIndex == -1 || getPageSize() == 0) {
						super.setRowIndex(-1);
					} else {
						super.setRowIndex(rowIndex % getPageSize());
					}
				}

			};
		}
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<Addmodify> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public Addmodify fetchRow(final String rowKey) {
		Addmodify filter = new Addmodify();
		filter.setAuser(rowKey);
		QueryData<Addmodify> data = new QueryData<Addmodify>(0, 1, "name", "DESC",
				filter);
		dao.search(data);
		if (data.getData().size() > 0) {
			return data.getData().get(0);
		}

		return null;
	}

	public void createOrUpdateListener(ActionEvent event) {
		createOrUpdateListener();
	}

	public void createOrUpdateListener() {
		try {
			if (selectedAddmodify.getAuser() == null) {
				// Do nothing
			} else {
				dao.updateAddmodify(selectedAddmodify);
				JsfUtils.addSuccessMessage("Addmodify details saved successfully");
			}
			selectedAddmodify = new Addmodify();
		} catch (Exception e) {
			System.out.println(">>>>> selectedAddress = " + selectedAddmodify);
			e.printStackTrace();
			JsfUtils.addErrorMessage("Addmodify details not saved.");
		}
	}

	public void clearListener(ActionEvent event) {
		selectedAddmodify = new Addmodify();
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		UIComponent component = viewRoot.findComponent("form2");
		JsfUtils.clearComponentHierarchy(component);
	}

	public void deleteListener(ActionEvent event) {
		try {
			if (selectedAddmodify.getAuser() == null) {
				// Do nothing
			} else {
				dao.deleteAddmodify(selectedAddmodify);
				JsfUtils.addSuccessMessage("Addmodify details deleted successfully");
			}
			selectedAddmodify = new Addmodify();
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtils.addErrorMessage("");
		}
	}






}
