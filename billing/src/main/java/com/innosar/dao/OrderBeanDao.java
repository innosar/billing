/*Developed by Rakesh M D & Abhijith T N
 Copyright 2015*/

package com.innosar.dao;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;
import com.innosar.bean.OrderBean;

import com.innosar.utils.JsfUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import static com.innosar.dao.DatabaseConnection.getQueryRunner;
import static com.innosar.dao.DatabaseConnection.*;

@ManagedBean(name = "orderBeanDao")
@ViewScoped
public class OrderBeanDao {
	private static float totalamt=0;
	private float totalsum=0;
	private static String taxval;

	private float discp;
	private float disca;
	private static String cgst;
	private static String sgst;
	private static int fno=0;
	private  int fnoinc;
	private static String formattedDate;


	private List<String> modeps = Arrays.asList("CASH","CARD");
	  private String selectedModep;

private  String address1;
private  String address2;
private  String address3;
private  String address4;
private String address5;
private String gst;
private String email;
private String mob;
private String web;
private String fb;
private String insta;
private String twit;


private int sno;

private String item;
private float qty;
private float rate;
private float amt;
private String created;
private String modified;
private boolean value2;
private String summary="unchecked";

 private List<String> items = new ArrayList<>();

 public boolean isValue2() {
     return value2;
 }

 public void setValue2(boolean value2) {
     this.value2 = value2;
 }

 public void addMessage() {
      summary = value2 ? "Checked" : "Unchecked";
     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
 }



public String getSelectedModep() {
     return selectedModep;
 }

 public void setSelectedModep(String selectedModep) {
     this.selectedModep = selectedModep;
 }

 public List<String> getModeps() {
     return modeps;
 }


public Float getQty() {
	return qty;
}



public void setQty(Float qty) {
	this.qty = qty;
}



public Float getAmt() {
	return amt;
}



public void setAmt(Float amt) {
	this.amt = amt;
}


public int getSno() {
	return sno;
}



public void setSno(int sno) {
	this.sno = sno;
}



 public List<String> getItems() {

        return items;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String items) {
        item = items;
    }



public Float getRate() {
	return rate;
}



public void setRate(Float rate) {
	this.rate = rate;
}



public String getCreated() {
	return created;
}



public void setCreated(String created) {
	this.created = created;
}



public String getModified() {
	return modified;
}



public void setModified(String modified) {
	this.modified = modified;
}




public float getTotalsum() {
	return totalsum;
}



public void setTotalsum(float totalsum) {
	this.totalsum = totalsum;
}



public float getDiscp() {
	return discp;
}


public void setDiscp(float discp) {
    this.discp = discp;
}

public void handleKeyEvent() {
    this.discp = discp;
}




public float getDisca() {
	return disca;
}



public void setDisca(float disca) {
	this.disca = disca;
}



public void setTotalamt(float totalamt) {
	this.totalamt = totalamt;
}



public String getTwit() {
	return twit;
}



public void setTwit(String twit) {
	this.twit = twit;
}



public String getAddress5() {
	return address5;
}



public void setAddress5(String address5) {
	this.address5 = address5;
}



public String getEmail() {
	return email;
}



public void setEmail(String email) {
	this.email = email;
}



public String getMob() {
	return mob;
}



public void setMob(String mob) {
	this.mob = mob;
}



public String getWeb() {
	return web;
}



public void setWeb(String web) {
	this.web = web;
}



public String getFb() {
	return fb;
}



public void setFb(String fb) {
	this.fb = fb;
}



public String getInsta() {
	return insta;
}



public void setInsta(String insta) {
	this.insta = insta;
}

	public String getAddress2() {
	return address2;
}

public void setAddress2(String address2) {
	this.address2 = address2;
}

public String getAddress3() {
	return address3;
}

public void setAddress3(String address3) {
	this.address3 = address3;
}

public String getAddress4() {
	return address4;
}

public void setAddress4(String address4) {
	this.address4 = address4;
}

public String getGst() {
	return gst;
}

public void setGst(String gst) {
	this.gst = gst;
}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}


	public String getFormattedDate() {
		return formattedDate;
	}


	public void setFormattedDate(String formattedDate) {
		this.formattedDate = formattedDate;
	}


	public  int getFnoinc() {
		return fnoinc;
	}


	public  void setFnoinc(int fnoinc) {
		this.fnoinc = fnoinc;
	}


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


	private static String tname="all";
	 private List<String> tnames = new ArrayList<>();

	 public List<String> getTnames() {

	        return tnames;
	    }

	    public String getTname() {
	        return tname;
	    }

	    public void setTname(String tnames) {
	        tname = tnames;
	    }


	@PostConstruct
	public void init() {



		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			tnames.clear();
			con = DB.getConnection();

			    //String distinctInst = "SELECT DISTINCT TNAME FROM ADD_TABLE";
	              // tnames = getQueryRunner().query(con, distinctInst, new ColumnListHandler<String>(1));

			  String distinctTable = "SELECT DISTINCT TNAME FROM ADD_TABLE WHERE STA='ACTIVE' AND TNAME!='all'";
              tnames = getQueryRunner().query(con, distinctTable, new ColumnListHandler<String>(1));



              String distinctItem = "SELECT ITEM FROM ADD_ITEM WHERE ITEM!='all'";
              items = getQueryRunner().query(con, distinctItem, new ColumnListHandler<String>(1));


              String sqlString = "SELECT RATE FROM ADD_ITEM WHERE ITEM = ?";
  			st = con.prepareStatement(sqlString);
  			st.setString(1, item);
  			rs = st.executeQuery();
  			if (rs.next()) {
				rate = rs.getFloat(1);
  			}




		} catch (SQLException ex) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getMessage()));
			System.out.println(ex.getMessage());
		} finally {
			DbUtils.closeQuietly(con, st, rs);
		}
	}

	public void action() {

		Connection con = null;
		String sqlString1;


		try {
			con = DatabaseConnection.DB.getConnection();

			sqlString1 = "INSERT INTO ORDER_ITEM(TNAME,ITEM,QTY,RATE,AMT,STA,INVOICE,CREATED, MODIFIED) VALUES (?,?,?,?,?,'UNPAID','val',NOW(), NOW())";

			if(summary.equalsIgnoreCase("unchecked"))
			DatabaseConnection.getQueryRunner().update(con, sqlString1, tname,item,qty,rate,amt=rate*qty);
			else
			DatabaseConnection.getQueryRunner().update(con, sqlString1, tname,item,qty,rate,amt=Math.round(qty*rate/1000));
			//DatabaseConnection.getQueryRunner().update(con, sqlString2);
			//DatabaseConnection.getQueryRunner().update(con, sqlString3);
			JsfUtils.addSuccessMessage("Successful");
		} catch (SQLException ex) {
			JsfUtils.addErrorMessage("Error!" + ex.getMessage());
			System.out.println(ex.getMessage());
		} finally {
			DbUtils.closeQuietly(con);
		}
	}


    public void search(QueryData<OrderBean> qd) {

        Connection conn = null;

        int first = qd.getFirst();
        int pagesize = qd.getPageSize();
        OrderBean filter = qd.getFilter();
        String sortOrder = qd.getSortOrder();
        String sortField = qd.getSortField();

        try {
        	String countQuery;
        	String resultQuery;
        	//System.out.println("hello"+tname);
        	if(!tname.equalsIgnoreCase("all")){
        		countQuery = "SELECT COUNT(*) FROM ORDER_ITEM WHERE 1 =1 AND TNAME='"+tname+"' AND STA='UNPAID'";
                resultQuery = "SELECT * FROM ORDER_ITEM WHERE 1 = 1 AND TNAME='"+tname+"' AND STA='UNPAID'";
       	}
        	else{
        		   countQuery = "SELECT COUNT(*) FROM ORDER_ITEM WHERE 1 =1 AND STA='UNPAID'";
                   resultQuery = "SELECT * FROM ORDER_ITEM WHERE 1 = 1 AND STA='UNPAID'";
        	}

            String whereClause = buildWhereClause(filter);
            Object[] params = getParams(filter);

            conn = DB.getConnection();
            QueryRunner qr = new QueryRunner();

            PreparedStatement pstmt = conn.prepareStatement(countQuery
                    + whereClause);

            qr.fillStatement(pstmt, params);

            ResultSet rs = pstmt.executeQuery();
            int count = 0;
            while (rs.next()) {
                count = rs.getInt(1);
            }

            DbUtils.closeQuietly(null, pstmt, rs);

            qd.setTotalResultCount(count);

            if (count > 0) {
                StringBuilder sortOrderBuilder = new StringBuilder();
                if ("tname".equals(sortField)) {
                    sortOrderBuilder.append(" ORDER BY  TNAME ").append(
                            sortOrder);
                } else if ("item".equals(sortField)) {
                    sortOrderBuilder.append(" ORDER BY  ITEM ").append(
                            sortOrder);
                }  else if ("qty".equals(sortField)) {
                    sortOrderBuilder.append(" ORDER BY QTY ").append(
                            sortOrder);
                } else if ("rate".equals(sortField)) {
                    sortOrderBuilder.append(" ORDER BY RATE ").append(
                            sortOrder);
                } else if ("amt".equals(sortField)) {
                    sortOrderBuilder.append(" ORDER BY AMT ").append(
                            sortOrder);
                } else if ("created".equals(sortField)) {
                    sortOrderBuilder.append(" ORDER BY CREATED ").append(
                            sortOrder);
                } else if ("modified".equals(sortField)) {
                    sortOrderBuilder.append(" ORDER BY MODIFIED ").append(
                            sortOrder);
                } else if ("sta".equals(sortField)) {
                    sortOrderBuilder.append(" ORDER BY STA ").append(
                            sortOrder);
                } else {
                    sortOrderBuilder.append(" ORDER BY SNO ").append(
                            sortOrder);
                }

                sortOrderBuilder.append(" LIMIT ").append(first).append(",")
                        .append(pagesize);

                List<OrderBean> resultList = qr
                        .query(conn, resultQuery + whereClause
                                        + sortOrderBuilder.toString(),
                                new BeanListHandler<>(OrderBean.class,
                                        new BasicRowProcessor(
                                                new OrderBeanProcessor())),
                                params);

                qd.setData(resultList);
            }

        } catch (SQLException ex) {
        	System.out.println("Missing Data!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    private String buildWhereClause(OrderBean orderBean) {
        StringBuilder sb = new StringBuilder(100);



        if (StringUtils.isNotBlank(orderBean.getTname())) {
            sb.append(" AND TNAME like ? ");
        }

        if (StringUtils.isNotBlank(orderBean.getItem())) {
            sb.append(" AND ITEM = ? ");
        }
        if (StringUtils.isNotBlank(orderBean.getQty())) {
            sb.append(" AND QTY like ? ");
        }
        if (StringUtils.isNotBlank(orderBean.getRate())) {
            sb.append(" AND RATE like ? ");
        }
        if (StringUtils.isNotBlank(orderBean.getAmt())) {
            sb.append(" AND AMT like ? ");
        }
        if (StringUtils.isNotBlank(orderBean.getSta())) {
            sb.append(" AND STA like ? ");
        }


        return sb.toString();
    }

    private Object[] getParams(OrderBean orderBean) throws SQLException {

        List<String> params = new ArrayList<>();




        if (StringUtils.isNotBlank(orderBean.getTname())) {
            params.add("%" + orderBean.getTname() + "%");
        }

        if (StringUtils.isNotBlank(orderBean.getItem())) {
            params.add("%" + orderBean.getItem()+ "%");
        }


        if (StringUtils.isNotBlank(orderBean.getQty())) {
            params.add("%" + orderBean.getQty()+ "%");
        }
        if (StringUtils.isNotBlank(orderBean.getRate())) {
            params.add("%" + orderBean.getRate()+ "%");
        }
        if (StringUtils.isNotBlank(orderBean.getAmt())) {
            params.add("%" + orderBean.getAmt()+ "%");
        }
        if (StringUtils.isNotBlank(orderBean.getSta())) {
            params.add("%" + orderBean.getSta()+ "%");
        }


        if (params.isEmpty())
            return null;

        return params.toArray();
    }


    public boolean updateOrderBean(OrderBean orderBean) throws Exception {

        Connection conn = null;

            try {
                conn = DB.getConnection();
                conn.setAutoCommit(false);
                String updateTed = "UPDATE ORDER_ITEM SET ITEM=?,QTY=?, WHERE TNAME = ?";

                getQueryRunner().update(conn, updateTed,orderBean.getItem(),orderBean.getQty(),orderBean.getTname());
                conn.commit();
                JsfUtils.addSuccessMessage("Order Updated successfully");
               // return rowCount == 1;
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                DbUtils.closeQuietly(conn);
            }


        return false;
    }


    public boolean deleteOrderBean(OrderBean orderBean) {

        Connection conn = null;

        try {
            String updateQuery = "DELETE FROM ORDER_ITEM WHERE SNO = ?";
            conn = DB.getConnection();
            conn.setAutoCommit(true);
            int rowCount = getQueryRunner().update(conn, updateQuery, orderBean.getSno());
            return rowCount == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn);
        }

        return false;
    }



public void exportListener() {



		Connection con = null;
		ResultSet rs = null;
		PreparedStatement st = null;

		try {
			totalsum=0;

			String sqlString;
			String sqlStringadd;

			con = DatabaseConnection.DB.getConnection();

			if(!tname.equalsIgnoreCase("all"))
		     sqlString = "SELECT SUM(AMT) FROM ORDER_ITEM WHERE STA='UNPAID' AND TNAME='"+tname+"' ";
		     else
		     sqlString = "SELECT SUM(AMT) FROM ORDER_ITEM WHERE STA='UNPAID' ";
			st = con.prepareStatement(sqlString);

				//st.setString(1, parameterName2);
			rs = st.executeQuery();
			if (rs.next()) {
				totalsum=rs.getFloat(1);


			}

			 sqlStringadd = "SELECT ADDRESS1,ADDRESS2,ADDRESS3,ADDRESS4,ADDRESS5,GST,WEB,EMAIL,MOB,FB,INSTA,TWIT FROM ADDRESS_TABLE ";
				st = con.prepareStatement(sqlStringadd);

				rs = st.executeQuery();
				if (rs.next()) {
					address1=rs.getString(1);
					address2=rs.getString(2);
					address3=rs.getString(3);
					address4=rs.getString(4);
					address5=rs.getString(5);
					gst=rs.getString(6);
					web=rs.getString(7);
					email=rs.getString(8);
					mob=rs.getString(9);
					fb=rs.getString(10);
					insta=rs.getString(11);
					twit=rs.getString(12);


				}



			String selectMaxFNo = "SELECT MAX(FNO) FROM BILL_ORDER";
			Integer result = getQueryRunner().query(con, selectMaxFNo, new ScalarHandler<Integer>(1));
			fno = result == null ? 0 : result;
			fnoinc = fno + 1;

			LocalDateTime myObj = LocalDateTime.now();
			DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		    formattedDate = myObj.format(myFormatObj);
			disca=totalsum*discp/100;
		    totalamt=totalsum-disca;
			taxval=df.format(totalamt/1.05);
			cgst=df.format((totalamt-(totalamt/1.05))/2);
			sgst=df.format((totalamt-(totalamt/1.05))/2);

			JsfUtils.addSuccessMessage("Successful");



		} catch (SQLException ex) {
			JsfUtils.addErrorMessage("Error!" + ex.getMessage());
			System.out.println(ex.getMessage());
		} finally {
			DbUtils.closeQuietly(con);
		}

	}


public void billListener() {



	Connection con = null;
	ResultSet rs = null;
	PreparedStatement st = null;

	try {
		//System.out.println("mode is "+selectedModep);


		String sqlString;

		con = DatabaseConnection.DB.getConnection();

		if(totalamt!=0){
		String insertToBill = "INSERT IGNORE INTO BILL_ORDER (FNO,TNAME, TAXVAL, CGST,SGST,DISCP,DISCA,MODEP,TOTALAMT, CREATED, MODIFIED) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
		getQueryRunner().update(con, insertToBill, fnoinc, tname,taxval,cgst,sgst,discp,disca,selectedModep, totalamt);

		String sqlString1 = "UPDATE ORDER_ITEM SET INVOICE=? WHERE STA='UNPAID' AND TNAME='"+tname+"'";
		getQueryRunner().update(con, sqlString1, fnoinc);

		if(!tname.equalsIgnoreCase("all"))
		 sqlString = "UPDATE ORDER_ITEM SET STA='PAID' WHERE STA='UNPAID' AND TNAME='"+tname+"' ";
		else
		 sqlString = "UPDATE ORDER_ITEM SET STA='PAID' WHERE STA='UNPAID' ";
		st = con.prepareStatement(sqlString);
		st.executeUpdate();
		JsfUtils.addSuccessMessage("Successful");

	}
		else
			JsfUtils.addErrorMessage("Error");


	} catch (SQLException ex) {
		JsfUtils.addErrorMessage("Error!" + ex.getMessage());
		System.out.println(ex.getMessage());
	} finally {
		DbUtils.closeQuietly(con);
	}

}








}
