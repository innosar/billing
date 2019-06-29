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


import com.innosar.bean.ItemBean;
import com.innosar.dao.QueryData;
import com.innosar.dao.DatabaseConnection;
import com.innosar.dao.ItemBeanDao;


import com.innosar.utils.JsfUtils;
import org.apache.commons.dbutils.DbUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;


@ManagedBean(name = "itemBeanController")
@ViewScoped
public class ItemBeanController  implements Serializable  {

	/**
	 *
	 */
	private static final long serialVersionUID = 1762563989143654209L;

	private LazyDataModel<ItemBean> lazyModel;

	private ItemBean itemBean = new ItemBean();
	private ItemBean selectedItemBean = new ItemBean();
	private transient ItemBeanDao dao = new ItemBeanDao();
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




	private List<ItemBean> cars5;





		public List<ItemBean> getCars5() {
	        return cars5;
	    }


	@PostConstruct
	void intializeSession() {
		FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}


	/**
	 * @return the itemBean
	 */
	public ItemBean getItemBean() {
		return itemBean;
	}




			 @PostConstruct
			    public void init() {

			   cars5 = findAll();

		   }
	/**
	 * @param itemBean
	 *            the itemBean to set
	 */
	public void setItemBean(ItemBean itemBean) {
		this.itemBean = itemBean;
	}


	/**
	 * @return the selectedItemBean
	 */
	public ItemBean getSelectedItemBean() {
		return selectedItemBean;
	}

	/**
	 * @param selectedItemBean
	 *            the selectedItemBean to set
	 */
	public void setSelectedItemBean(ItemBean selectedItemBean) {
		this.selectedItemBean = selectedItemBean;

	}

	public List<ItemBean> findAll() {
		ItemBean filter = new ItemBean();
		QueryData<ItemBean> data = new QueryData<ItemBean>(0, 1000000000, "name",
				"DESC", filter);
		dao.search(data);
		return data.getData();
	}




	public LazyDataModel<ItemBean> getLazyModel() {
		if (lazyModel == null) {
			lazyModel = new LazyDataModel<ItemBean>() {

				private static final long serialVersionUID = 1L;

				@Override
				public ItemBean getRowData(String rowKey) {
					return fetchRow(rowKey);
				}

				@Override
				public Object getRowKey(ItemBean object) {
					return object;
				}


				public List<ItemBean> load(int first, int pageSize,
						String sortField, SortOrder sortOrder,
						Map<String, Object> filters) {

					String order = (SortOrder.DESCENDING.equals(sortOrder)) ? "DESC"
							: "ASC";

					ItemBean itemBean = new ItemBean();
					itemBean.mask(filters);

					QueryData<ItemBean> data = new QueryData<ItemBean>(first,
							pageSize, sortField, order, itemBean);

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

	public void setLazyModel(LazyDataModel<ItemBean> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public ItemBean fetchRow(final String rowKey) {
		ItemBean filter = new ItemBean();
		filter.setItem(rowKey);
		QueryData<ItemBean> data = new QueryData<ItemBean>(0, 1, "name", "DESC",
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
			if (selectedItemBean.getItem() == null) {
				// Do nothing
			} else {
				dao.updateItemBean(selectedItemBean);
				//JsfUtils.addSuccessMessage("ItemBean details saved successfully");
			}
			selectedItemBean = new ItemBean();
		} catch (Exception e) {
			System.out.println(">>>>> selectedAddress = " + selectedItemBean);
			e.printStackTrace();
			JsfUtils.addErrorMessage("Order details not saved.");
		}
	}







	public void clearListener(ActionEvent event) {
		selectedItemBean = new ItemBean();
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		UIComponent component = viewRoot.findComponent("form2");
		JsfUtils.clearComponentHierarchy(component);
	}

	public void deleteListener(ActionEvent event) {
		try {

			if (selectedItemBean== null) {
				// Do nothing
			} else {
				dao.deleteItemBean(selectedItemBean);
				JsfUtils.addSuccessMessage("ItemBean details deleted successfully");
			}
			selectedItemBean = new ItemBean();
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtils.addErrorMessage("");
		}
	}

	public String deleteListener(String parameterName1) {

		Connection con = null;

		try {

			con = DatabaseConnection.DB.getConnection();
			String sqlString = "DELETE FROM ADD_ITEM WHERE SNO=?";
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
