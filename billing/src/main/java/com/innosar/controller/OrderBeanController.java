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


import com.innosar.bean.OrderBean;
import com.innosar.dao.QueryData;
import com.innosar.dao.DatabaseConnection;
import com.innosar.dao.OrderBeanDao;


import com.innosar.utils.JsfUtils;
import org.apache.commons.dbutils.DbUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;


@ManagedBean(name = "orderBeanController")
@ViewScoped
public class OrderBeanController  implements Serializable  {

	/**
	 *
	 */
	private static final long serialVersionUID = 1762563989143654209L;

	private LazyDataModel<OrderBean> lazyModel;

	private OrderBean orderBean = new OrderBean();
	private OrderBean selectedOrderBean = new OrderBean();
	private transient OrderBeanDao dao = new OrderBeanDao();




	private List<OrderBean> cars5;





		public List<OrderBean> getCars5() {
	        return cars5;
	    }


	@PostConstruct
	void intializeSession() {
		FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}


	/**
	 * @return the orderBean
	 */
	public OrderBean getOrderBean() {
		return orderBean;
	}




			 @PostConstruct
			    public void init() {

			   cars5 = findAll();

		   }
	/**
	 * @param orderBean
	 *            the orderBean to set
	 */
	public void setOrderBean(OrderBean orderBean) {
		this.orderBean = orderBean;
	}


	/**
	 * @return the selectedOrderBean
	 */
	public OrderBean getSelectedOrderBean() {
		return selectedOrderBean;
	}

	/**
	 * @param selectedOrderBean
	 *            the selectedOrderBean to set
	 */
	public void setSelectedOrderBean(OrderBean selectedOrderBean) {
		this.selectedOrderBean = selectedOrderBean;

	}

	public List<OrderBean> findAll() {
		OrderBean filter = new OrderBean();
		QueryData<OrderBean> data = new QueryData<OrderBean>(0, 1000000000, "name",
				"DESC", filter);
		dao.search(data);
		return data.getData();
	}




	public LazyDataModel<OrderBean> getLazyModel() {
		if (lazyModel == null) {
			lazyModel = new LazyDataModel<OrderBean>() {

				private static final long serialVersionUID = 1L;

				@Override
				public OrderBean getRowData(String rowKey) {
					return fetchRow(rowKey);
				}

				@Override
				public Object getRowKey(OrderBean object) {
					return object;
				}


				public List<OrderBean> load(int first, int pageSize,
						String sortField, SortOrder sortOrder,
						Map<String, Object> filters) {

					String order = (SortOrder.DESCENDING.equals(sortOrder)) ? "DESC"
							: "ASC";

					OrderBean orderBean = new OrderBean();
					orderBean.mask(filters);

					QueryData<OrderBean> data = new QueryData<OrderBean>(first,
							pageSize, sortField, order, orderBean);

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

	public void setLazyModel(LazyDataModel<OrderBean> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public OrderBean fetchRow(final String rowKey) {
		OrderBean filter = new OrderBean();
		filter.setTname(rowKey);
		QueryData<OrderBean> data = new QueryData<OrderBean>(0, 1, "name", "DESC",
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
			if (selectedOrderBean.getTname() == null) {
				// Do nothing
			} else {
				dao.updateOrderBean(selectedOrderBean);
				//JsfUtils.addSuccessMessage("OrderBean details saved successfully");
			}
			selectedOrderBean = new OrderBean();
		} catch (Exception e) {
			System.out.println(">>>>> selectedAddress = " + selectedOrderBean);
			e.printStackTrace();
			JsfUtils.addErrorMessage("Order details not saved.");
		}
	}







	public void clearListener(ActionEvent event) {
		selectedOrderBean = new OrderBean();
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		UIComponent component = viewRoot.findComponent("form2");
		JsfUtils.clearComponentHierarchy(component);
	}

	public void deleteListener(ActionEvent event) {
		try {

			if (selectedOrderBean== null) {
				// Do nothing
			} else {
				dao.deleteOrderBean(selectedOrderBean);
				JsfUtils.addSuccessMessage("OrderBean details deleted successfully");
			}
			selectedOrderBean = new OrderBean();
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
