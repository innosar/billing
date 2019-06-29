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


import com.innosar.bean.OrderpBean;
import com.innosar.dao.QueryData;
import com.innosar.dao.DatabaseConnection;
import com.innosar.dao.OrderpBeanDao;


import com.innosar.utils.JsfUtils;
import org.apache.commons.dbutils.DbUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;


@ManagedBean(name = "orderpBeanController")
@ViewScoped
public class OrderpBeanController  implements Serializable  {

	/**
	 *
	 */
	private static final long serialVersionUID = 1762563989143654209L;

	private LazyDataModel<OrderpBean> lazyModel;

	private OrderpBean orderpBean = new OrderpBean();
	private OrderpBean selectedOrderpBean = new OrderpBean();
	private transient OrderpBeanDao dao = new OrderpBeanDao();




	private List<OrderpBean> cars5;





		public List<OrderpBean> getCars5() {
	        return cars5;
	    }


	@PostConstruct
	void intializeSession() {
		FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}


	/**
	 * @return the orderpBean
	 */
	public OrderpBean getOrderpBean() {
		return orderpBean;
	}




			 @PostConstruct
			    public void init() {

			   cars5 = findAll();

		   }
	/**
	 * @param orderpBean
	 *            the orderpBean to set
	 */
	public void setOrderpBean(OrderpBean orderpBean) {
		this.orderpBean = orderpBean;
	}


	/**
	 * @return the selectedOrderpBean
	 */
	public OrderpBean getSelectedOrderpBean() {
		return selectedOrderpBean;
	}

	/**
	 * @param selectedOrderpBean
	 *            the selectedOrderpBean to set
	 */
	public void setSelectedOrderpBean(OrderpBean selectedOrderpBean) {
		this.selectedOrderpBean = selectedOrderpBean;

	}

	public List<OrderpBean> findAll() {
		OrderpBean filter = new OrderpBean();
		QueryData<OrderpBean> data = new QueryData<OrderpBean>(0, 1000000000, "name",
				"DESC", filter);
		dao.search(data);
		return data.getData();
	}




	public LazyDataModel<OrderpBean> getLazyModel() {
		if (lazyModel == null) {
			lazyModel = new LazyDataModel<OrderpBean>() {

				private static final long serialVersionUID = 1L;

				@Override
				public OrderpBean getRowData(String rowKey) {
					return fetchRow(rowKey);
				}

				@Override
				public Object getRowKey(OrderpBean object) {
					return object;
				}


				public List<OrderpBean> load(int first, int pageSize,
						String sortField, SortOrder sortOrder,
						Map<String, Object> filters) {

					String orderp = (SortOrder.DESCENDING.equals(sortOrder)) ? "DESC"
							: "ASC";

					OrderpBean orderpBean = new OrderpBean();
					orderpBean.mask(filters);

					QueryData<OrderpBean> data = new QueryData<OrderpBean>(first,
							pageSize, sortField, orderp, orderpBean);

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

	public void setLazyModel(LazyDataModel<OrderpBean> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public OrderpBean fetchRow(final String rowKey) {
		OrderpBean filter = new OrderpBean();
		filter.setTname(rowKey);
		QueryData<OrderpBean> data = new QueryData<OrderpBean>(0, 1, "name", "DESC",
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
			if (selectedOrderpBean.getTname() == null) {
				// Do nothing
			} else {
				dao.updateOrderpBean(selectedOrderpBean);
				//JsfUtils.addSuccessMessage("OrderpBean details saved successfully");
			}
			selectedOrderpBean = new OrderpBean();
		} catch (Exception e) {
			System.out.println(">>>>> selectedAddress = " + selectedOrderpBean);
			e.printStackTrace();
			JsfUtils.addErrorMessage("Orderp details not saved.");
		}
	}







	public void clearListener(ActionEvent event) {
		selectedOrderpBean = new OrderpBean();
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		UIComponent component = viewRoot.findComponent("form2");
		JsfUtils.clearComponentHierarchy(component);
	}

	public void deleteListener(ActionEvent event) {
		try {

			if (selectedOrderpBean== null) {
				// Do nothing
			} else {
				dao.deleteOrderpBean(selectedOrderpBean);
				JsfUtils.addSuccessMessage("OrderpBean details deleted successfully");
			}
			selectedOrderpBean = new OrderpBean();
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtils.addErrorMessage("");
		}
	}

	public String deleteListener(String parameterName1) {

		Connection con = null;

		try {

			con = DatabaseConnection.DB.getConnection();
			String sqlString = "DELETE FROM ORDER_ITEM WHERE SNO=?";
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
