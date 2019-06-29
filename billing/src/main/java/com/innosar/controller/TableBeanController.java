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
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;


import com.innosar.bean.TableBean;
import com.innosar.dao.QueryData;
import com.innosar.dao.DatabaseConnection;
import com.innosar.dao.TableBeanDao;


import com.innosar.utils.JsfUtils;
import org.apache.commons.dbutils.DbUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;


@ManagedBean(name = "tableBeanController")
@ViewScoped
@SessionScoped

public class TableBeanController  implements Serializable  {

	/**
	 *
	 */
	private static final long serialVersionUID = 1762563989143654209L;

	private LazyDataModel<TableBean> lazyModel;

	private TableBean tableBean = new TableBean();
	private TableBean selectedTableBean = new TableBean();
	private transient TableBeanDao dao = new TableBeanDao();
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




	private List<TableBean> cars5;





		public List<TableBean> getCars5() {
	        return cars5;
	    }



	void intializeSession() {
		FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}


	/**
	 * @return the tableBean
	 */
	public TableBean getTableBean() {
		return tableBean;
	}




			 @PostConstruct
			    public void init() {

			   cars5 = findAll();

		   }
	/**
	 * @param tableBean
	 *            the tableBean to set
	 */
	public void setTableBean(TableBean tableBean) {
		this.tableBean = tableBean;
	}


	/**
	 * @return the selectedTableBean
	 */
	public TableBean getSelectedTableBean() {
		return selectedTableBean;
	}

	/**
	 * @param selectedTableBean
	 *            the selectedTableBean to set
	 */
	public void setSelectedTableBean(TableBean selectedTableBean) {
		this.selectedTableBean = selectedTableBean;

	}

	public List<TableBean> findAll() {
		TableBean filter = new TableBean();
		QueryData<TableBean> data = new QueryData<TableBean>(0, 1000000000, "name",
				"DESC", filter);
		dao.search(data);
		return data.getData();
	}




	public LazyDataModel<TableBean> getLazyModel() {
		if (lazyModel == null) {
			lazyModel = new LazyDataModel<TableBean>() {

				private static final long serialVersionUID = 1L;

				@Override
				public TableBean getRowData(String rowKey) {
					return fetchRow(rowKey);
				}

				@Override
				public Object getRowKey(TableBean object) {
					return object;
				}


				public List<TableBean> load(int first, int pageSize,
						String sortField, SortOrder sortOrder,
						Map<String, Object> filters) {

					String order = (SortOrder.DESCENDING.equals(sortOrder)) ? "DESC"
							: "ASC";

					TableBean tableBean = new TableBean();
					tableBean.mask(filters);

					QueryData<TableBean> data = new QueryData<TableBean>(first,
							pageSize, sortField, order, tableBean);

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

	public void setLazyModel(LazyDataModel<TableBean> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public TableBean fetchRow(final String rowKey) {
		TableBean filter = new TableBean();
		filter.setTname(rowKey);
		QueryData<TableBean> data = new QueryData<TableBean>(0, 1, "name", "DESC",
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
			if (selectedTableBean.getTname() == null) {
				// Do nothing
			} else {
				dao.updateTableBean(selectedTableBean);
				//JsfUtils.addSuccessMessage("TableBean details saved successfully");
			}
			selectedTableBean = new TableBean();
		} catch (Exception e) {
			System.out.println(">>>>> selectedAddress = " + selectedTableBean);
			e.printStackTrace();
			JsfUtils.addErrorMessage("Order details not saved.");
		}
	}







	public void clearListener(ActionEvent event) {
		selectedTableBean = new TableBean();
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		UIComponent component = viewRoot.findComponent("form2");
		JsfUtils.clearComponentHierarchy(component);
	}

	public void deleteListener(ActionEvent event) {
		try {

			if (selectedTableBean== null) {
				// Do nothing
			} else {
				dao.deleteTableBean(selectedTableBean);
				JsfUtils.addSuccessMessage("TableBean details deleted successfully");
			}
			selectedTableBean = new TableBean();
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtils.addErrorMessage("");
		}
	}

	public String deleteListener(String parameterName1) {

		Connection con = null;

		try {

			con = DatabaseConnection.DB.getConnection();
			String sqlString = "DELETE FROM ADD_TABLE WHERE SNO=?";
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
