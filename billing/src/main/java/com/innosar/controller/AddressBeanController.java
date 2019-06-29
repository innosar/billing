/*Developed by Rakesh M D & Abhijith T N
 Copyright 2015*/

package com.innosar.controller;


import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;


import com.innosar.bean.AddressBean;
import com.innosar.dao.QueryData;
import com.innosar.dao.DatabaseConnection;
import com.innosar.dao.AddressBeanDao;


import com.innosar.utils.JsfUtils;
import org.apache.commons.dbutils.DbUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;


@ManagedBean(name = "addressBeanController")
@ViewScoped
public class AddressBeanController  implements Serializable  {

	/**
	 *
	 */
	private static final long serialVersionUID = 1762563989143654209L;

	private LazyDataModel<AddressBean> lazyModel;

	private AddressBean addressBean = new AddressBean();
	private AddressBean selectedAddressBean = new AddressBean();
	private transient AddressBeanDao dao = new AddressBeanDao();
	private static float totalamt=0;
	private static String taxval;

	private static String cgst;
	private static String sgst;


	public  String getTaxval() {
		return taxval;
	}


	public  void setTaxval(String taxval) {
		this.taxval = taxval;
	}


	public  String getCgst() {
		return cgst;
	}


	public  void setCgst(String cgst) {
		this.cgst = cgst;
	}


	public  String getSgst() {
		return sgst;
	}


	public  void setSgst(String sgst) {
		this.sgst = sgst;
	}





	DecimalFormat df = new DecimalFormat("0.0#");






	 public float getTotalamt() {
		return totalamt;
	}




	private List<AddressBean> cars5;





		public List<AddressBean> getCars5() {
	        return cars5;
	    }


	@PostConstruct
	void intializeSession() {
		FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}


	/**
	 * @return the addressBean
	 */
	public AddressBean getAddressBean() {
		return addressBean;
	}




			 @PostConstruct
			    public void init() {

			   cars5 = findAll();

		   }
	/**
	 * @param addressBean
	 *            the addressBean to set
	 */
	public void setAddressBean(AddressBean addressBean) {
		this.addressBean = addressBean;
	}


	/**
	 * @return the selectedAddressBean
	 */
	public AddressBean getSelectedAddressBean() {
		return selectedAddressBean;
	}

	/**
	 * @param selectedAddressBean
	 *            the selectedAddressBean to set
	 */
	public void setSelectedAddressBean(AddressBean selectedAddressBean) {
		this.selectedAddressBean = selectedAddressBean;

	}

	public List<AddressBean> findAll() {
		AddressBean filter = new AddressBean();
		QueryData<AddressBean> data = new QueryData<AddressBean>(0, 1000000000, "name",
				"DESC", filter);
		dao.search(data);
		return data.getData();
	}




	public LazyDataModel<AddressBean> getLazyModel() {
		if (lazyModel == null) {
			lazyModel = new LazyDataModel<AddressBean>() {

				private static final long serialVersionUID = 1L;

				public AddressBean getRowData(int rowKey) {
					return fetchRow(rowKey);
				}

				@Override
				public Object getRowKey(AddressBean object) {
					return object;
				}


				public List<AddressBean> load(int first, int pageSize,
						String sortField, SortOrder sortOrder,
						Map<String, Object> filters) {

					String order = (SortOrder.DESCENDING.equals(sortOrder)) ? "DESC"
							: "ASC";

					AddressBean addressBean = new AddressBean();
					addressBean.mask(filters);

					QueryData<AddressBean> data = new QueryData<AddressBean>(first,
							pageSize, sortField, order, addressBean);

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

	public void setLazyModel(LazyDataModel<AddressBean> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public AddressBean fetchRow(final int rowKey) {
		AddressBean filter = new AddressBean();
		filter.setSno(rowKey);
		QueryData<AddressBean> data = new QueryData<AddressBean>(0, 1, "name", "DESC",
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
			if (selectedAddressBean.getSno() == 0) {
				// Do nothing
			} else {
				dao.updateAddressBean(selectedAddressBean);
				//JsfUtils.addSuccessMessage("AddressBean details saved successfully");
			}
			selectedAddressBean = new AddressBean();
		} catch (Exception e) {
			System.out.println(">>>>> selectedAddress = " + selectedAddressBean);
			e.printStackTrace();
			JsfUtils.addErrorMessage("Order details not saved.");
		}
	}







	public void clearListener(ActionEvent event) {
		selectedAddressBean = new AddressBean();
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		UIComponent component = viewRoot.findComponent("form2");
		JsfUtils.clearComponentHierarchy(component);
	}

	public void deleteListener(ActionEvent event) {
		try {

			if (selectedAddressBean== null) {
				// Do nothing
			} else {
				dao.deleteAddressBean(selectedAddressBean);
				JsfUtils.addSuccessMessage("AddressBean details deleted successfully");
			}
			selectedAddressBean = new AddressBean();
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtils.addErrorMessage("");
		}
	}

	public String deleteListener(String parameterName1) {

		Connection con = null;

		try {

			con = DatabaseConnection.DB.getConnection();
			String sqlString = "DELETE FROM ADDRESS_TABLE WHERE SNO=?";
			DatabaseConnection.getQueryRunner().update(con, sqlString, parameterName1);
			JsfUtils.addSuccessMessage("Successful");
		} catch (SQLException ex) {
			JsfUtils.addErrorMessage("Error!" + ex.getMessage());
			System.out.println(ex.getMessage());
		} finally {
			DbUtils.closeQuietly(con);
		}
		return parameterName1;
	}





}
