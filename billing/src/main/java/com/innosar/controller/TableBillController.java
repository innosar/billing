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


import com.innosar.bean.TableBill;
import com.innosar.dao.QueryData;
import com.innosar.dao.DatabaseConnection;
import com.innosar.dao.TableBillDao;


import com.innosar.utils.JsfUtils;
import org.apache.commons.dbutils.DbUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;


@ManagedBean(name = "tableBillController")
@ViewScoped
public class TableBillController  implements Serializable  {

	/**
	 *
	 */
	private static final long serialVersionUID = 1762563989143654209L;

	private LazyDataModel<TableBill> lazyModel;

	private TableBill tableBill = new TableBill();
	private TableBill selectedTableBill = new TableBill();
	private transient TableBillDao dao = new TableBillDao();








	private List<TableBill> cars5;





		public List<TableBill> getCars5() {
	        return cars5;
	    }


	@PostConstruct
	void intializeSession() {
		FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}


	/**
	 * @return the tableBill
	 */
	public TableBill getTableBill() {
		return tableBill;
	}




			 @PostConstruct
			    public void init() {

			   cars5 = findAll();

		   }
	/**
	 * @param tableBill
	 *            the tableBill to set
	 */
	public void setTableBill(TableBill tableBill) {
		this.tableBill = tableBill;
	}


	/**
	 * @return the selectedTableBill
	 */
	public TableBill getSelectedTableBill() {
		return selectedTableBill;
	}

	/**
	 * @param selectedTableBill
	 *            the selectedTableBill to set
	 */
	public void setSelectedTableBill(TableBill selectedTableBill) {
		this.selectedTableBill = selectedTableBill;

	}

	public List<TableBill> findAll() {
		TableBill filter = new TableBill();
		QueryData<TableBill> data = new QueryData<TableBill>(0, 1000000000, "name",
				"DESC", filter);
		dao.search(data);
		return data.getData();
	}




	public LazyDataModel<TableBill> getLazyModel() {
		if (lazyModel == null) {
			lazyModel = new LazyDataModel<TableBill>() {

				private static final long serialVersionUID = 1L;

				@Override
				public TableBill getRowData(String rowKey) {
					return fetchRow(rowKey);
				}

				@Override
				public Object getRowKey(TableBill object) {
					return object;
				}


				public List<TableBill> load(int first, int pageSize,
						String sortField, SortOrder sortOrder,
						Map<String, Object> filters) {

					String order = (SortOrder.DESCENDING.equals(sortOrder)) ? "DESC"
							: "ASC";

					TableBill tableBill = new TableBill();
					tableBill.mask(filters);

					QueryData<TableBill> data = new QueryData<TableBill>(first,
							pageSize, sortField, order, tableBill);

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

	public void setLazyModel(LazyDataModel<TableBill> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public TableBill fetchRow(final String rowKey) {
		TableBill filter = new TableBill();
		filter.setTname(rowKey);
		QueryData<TableBill> data = new QueryData<TableBill>(0, 1, "name", "DESC",
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
			if (selectedTableBill.getTname() == null) {
				// Do nothing
			} else {
				dao.updateTableBill(selectedTableBill);
				//JsfUtils.addSuccessMessage("TableBill details saved successfully");
			}
			selectedTableBill = new TableBill();
		} catch (Exception e) {
			System.out.println(">>>>> selectedAddress = " + selectedTableBill);
			e.printStackTrace();
			JsfUtils.addErrorMessage("Order details not saved.");
		}
	}







	public void clearListener(ActionEvent event) {
		selectedTableBill = new TableBill();
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		UIComponent component = viewRoot.findComponent("form2");
		JsfUtils.clearComponentHierarchy(component);
	}

	public void deleteListener(ActionEvent event) {
		try {

			if (selectedTableBill== null) {
				// Do nothing
			} else {
				dao.deleteTableBill(selectedTableBill);
				JsfUtils.addSuccessMessage("TableBill details deleted successfully");
			}
			selectedTableBill = new TableBill();
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtils.addErrorMessage("");
		}
	}

	public String deleteListener(String parameterName1) {

		Connection con = null;

		try {

			con = DatabaseConnection.DB.getConnection();
			String sqlString = "DELETE FROM BILL_ORDER WHERE FNO=?";
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
