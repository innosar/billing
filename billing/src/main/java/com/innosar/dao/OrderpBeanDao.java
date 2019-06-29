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

import com.innosar.bean.OrderpBean;
import com.innosar.bean.TableBill;
import com.innosar.utils.JsfUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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

@ManagedBean(name = "orderpBeanDao")
@ViewScoped
public class OrderpBeanDao {
	private static float totalamt=0;
	private static String taxval;
	private float totalsum=0;

	private static String cgst;
	private static String sgst;

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
	    private float discp;
		private float disca;

	    private String twit;



	    public float getDiscp() {
	    	return discp;
	    }


	    public void setDiscp(float discp) {
	        this.discp = discp;
	    }


public float getDisca() {
	return disca;
}



public void setDisca(float disca) {
	this.disca = disca;
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

	private String tab;
	private static String formattedDate;
	private static Date cdate1;
	private static Date cdate2;



	 public Date getCdate1() {
	        return cdate1;
	    }

	    public void setCdate1(Date cdate1) {
	        OrderpBeanDao.cdate1 = cdate1;
	    }


	    public Date getCdate2() {
	        return cdate2;
	    }

	    public void setCdate2(Date cdate2) {
	        OrderpBeanDao.cdate2 = cdate2;
	    }



	public String getTab() {
		return tab;
	}


	public void setTab(String tab) {
		this.tab = tab;
	}


	public String getFormattedDate() {
		return formattedDate;
	}


	public void setFormattedDate(String formattedDate) {
		this.formattedDate = formattedDate;
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


	private static String inv="all";
	 private List<String> invs = new ArrayList<>();

	 public List<String> getInvs() {

	        return invs;
	    }

	    public String getInv() {
	        return inv;
	    }

	    public void setInv(String invs) {
	        inv = invs;
	    }


	@PostConstruct
	public void action() {
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			con = DB.getConnection();

			    String distinctInst = "SELECT DISTINCT INVOICE FROM ORDER_ITEM WHERE STA='PAID'";
	               invs = getQueryRunner().query(con, distinctInst, new ColumnListHandler<String>(1));


		} catch (SQLException ex) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getMessage()));
			System.out.println(ex.getMessage());
		} finally {
			DbUtils.closeQuietly(con, st, rs);
		}
	}



    public void search(QueryData<OrderpBean> qd) {

        Connection conn = null;

        int first = qd.getFirst();
        int pagesize = qd.getPageSize();
        OrderpBean filter = qd.getFilter();
        String sortOrderp = qd.getSortOrder();
        String sortField = qd.getSortField();

        try {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        	String countQuery;
        	String resultQuery;
        	 String sumQuery ;
        	//System.out.println("hello"+cdate1);
        	//System.out.println("hello"+cdate2);
        	if(!inv.equalsIgnoreCase("all")){
        		countQuery = "SELECT COUNT(*) FROM ORDER_ITEM WHERE 1 =1 AND SUBSTRING(CREATED,1,10) BETWEEN '"+sdf.format(cdate1)+"' AND '"+sdf.format(cdate2)+"' AND INVOICE='"+inv+"' AND STA='PAID'";
                resultQuery = "SELECT * FROM ORDER_ITEM WHERE 1 = 1 AND SUBSTRING(CREATED,1,10) BETWEEN '"+sdf.format(cdate1)+"' AND '"+sdf.format(cdate2)+"' AND INVOICE='"+inv+"' AND STA='PAID'";
                sumQuery = " SELECT ROUND(SUM(QTY),2),ROUND(SUM(RATE),2),ROUND(SUM(AMT),2) FROM ORDER_ITEM WHERE 1=1 AND SUBSTRING(CREATED,1,10) BETWEEN '"+sdf.format(cdate1)+"' AND '"+sdf.format(cdate2)+"' AND INVOICE='"+inv+"' AND STA='PAID'";
       	}
        	else{
        		   countQuery = "SELECT COUNT(*) FROM ORDER_ITEM WHERE 1 =1 AND SUBSTRING(CREATED,1,10) BETWEEN '"+sdf.format(cdate1)+"' AND '"+sdf.format(cdate2)+"' AND STA='PAID' AND INVOICE!='ALL'";
                   resultQuery = "SELECT * FROM ORDER_ITEM WHERE 1 = 1 AND SUBSTRING(CREATED,1,10) BETWEEN '"+sdf.format(cdate1)+"' AND '"+sdf.format(cdate2)+"' AND STA='PAID' AND INVOICE!='ALL'";
                   sumQuery = " SELECT ROUND(SUM(QTY),2),ROUND(SUM(RATE),2),ROUND(SUM(AMT),2) FROM ORDER_ITEM WHERE 1=1 AND SUBSTRING(CREATED,1,10) BETWEEN '"+sdf.format(cdate1)+"' AND '"+sdf.format(cdate2)+"' AND INVOICE!='ALL' AND STA='PAID'";
        	}



            String whereClause = buildWhereClause(filter);
            Object[] params = getParams(filter);

            conn = DB.getConnection();
            QueryRunner qr = new QueryRunner();

            PreparedStatement pstmt = conn.prepareStatement(countQuery
                    + whereClause);

            qr.fillStatement(pstmt, params);

            PreparedStatement pstmt1 = conn.prepareStatement(sumQuery
					+ whereClause);

			qr.fillStatement(pstmt1, params);


            ResultSet rs = pstmt.executeQuery();
            int count = 0;
            while (rs.next()) {
                count = rs.getInt(1);
            }

ResultSet rs1 = pstmt1.executeQuery();

			OrderpBean f=new OrderpBean();
			while (rs1.next()) {
				f.setQtysum(rs1.getString(1));
				f.setRatesum(rs1.getString(2));
				f.setAmtsum(rs1.getString(3));



			}
            DbUtils.closeQuietly(null, pstmt, rs);
            DbUtils.closeQuietly(null, pstmt1, rs1);
            qd.setTotalResultCount(count);

            if (count > 0) {
                StringBuilder sortOrderpBuilder = new StringBuilder();
                if ("tname".equals(sortField)) {
                    sortOrderpBuilder.append(" ORDER BY  TNAME ").append(
                            sortOrderp);
                } else if ("item".equals(sortField)) {
                    sortOrderpBuilder.append(" ORDER BY  ITEM ").append(
                            sortOrderp);
                } else if ("invoice".equals(sortField)) {
                    sortOrderpBuilder.append(" ORDER BY  INVOICE ").append(
                            sortOrderp);
                } else if ("qty".equals(sortField)) {
                    sortOrderpBuilder.append(" ORDER BY QTY ").append(
                            sortOrderp);
                } else if ("rate".equals(sortField)) {
                    sortOrderpBuilder.append(" ORDER BY RATE ").append(
                            sortOrderp);
                } else if ("amt".equals(sortField)) {
                    sortOrderpBuilder.append(" ORDER BY AMT ").append(
                            sortOrderp);
                } else if ("created".equals(sortField)) {
                    sortOrderpBuilder.append(" ORDER BY CREATED ").append(
                            sortOrderp);
                } else if ("modified".equals(sortField)) {
                    sortOrderpBuilder.append(" ORDER BY MODIFIED ").append(
                            sortOrderp);
                } else if ("sta".equals(sortField)) {
                    sortOrderpBuilder.append(" ORDER BY STA ").append(
                            sortOrderp);
                } else {
                    sortOrderpBuilder.append(" ORDER BY SNO ").append(
                            sortOrderp);
                }

                sortOrderpBuilder.append(" LIMIT ").append(first).append(",")
                        .append(pagesize);

                List<OrderpBean> resultList = qr
                        .query(conn, resultQuery + whereClause
                                        + sortOrderpBuilder.toString(),
                                new BeanListHandler<>(OrderpBean.class,
                                        new BasicRowProcessor(
                                                new OrderpBeanProcessor())),
                                params);

                qd.setData(resultList);
            }

        } catch (SQLException ex) {
        	System.out.println("Missing Data!");
        } catch (Exception e) {
          // e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    private String buildWhereClause(OrderpBean orderpBean) {
        StringBuilder sb = new StringBuilder(100);



        if (StringUtils.isNotBlank(orderpBean.getTname())) {
            sb.append(" AND TNAME like ? ");
        }

        if (StringUtils.isNotBlank(orderpBean.getInvoice())) {
            sb.append(" AND INVOICE like ? ");
        }
        if (StringUtils.isNotBlank(orderpBean.getItem())) {
            sb.append(" AND ITEM = ? ");
        }
        if (StringUtils.isNotBlank(orderpBean.getQty())) {
            sb.append(" AND QTY like ? ");
        }
        if (StringUtils.isNotBlank(orderpBean.getRate())) {
            sb.append(" AND RATE like ? ");
        }
        if (StringUtils.isNotBlank(orderpBean.getAmt())) {
            sb.append(" AND AMT like ? ");
        }
        if (StringUtils.isNotBlank(orderpBean.getSta())) {
            sb.append(" AND STA like ? ");
        }


        return sb.toString();
    }

    private Object[] getParams(OrderpBean orderpBean) throws SQLException {

        List<String> params = new ArrayList<>();




        if (StringUtils.isNotBlank(orderpBean.getTname())) {
            params.add("%" + orderpBean.getTname() + "%");
        }

        if (StringUtils.isNotBlank(orderpBean.getInvoice())) {
            params.add("%" + orderpBean.getInvoice() + "%");
        }

        if (StringUtils.isNotBlank(orderpBean.getItem())) {
            params.add("%" + orderpBean.getItem()+ "%");
        }


        if (StringUtils.isNotBlank(orderpBean.getQty())) {
            params.add("%" + orderpBean.getQty()+ "%");
        }
        if (StringUtils.isNotBlank(orderpBean.getRate())) {
            params.add("%" + orderpBean.getRate()+ "%");
        }
        if (StringUtils.isNotBlank(orderpBean.getAmt())) {
            params.add("%" + orderpBean.getAmt()+ "%");
        }
        if (StringUtils.isNotBlank(orderpBean.getSta())) {
            params.add("%" + orderpBean.getSta()+ "%");
        }


        if (params.isEmpty())
            return null;

        return params.toArray();
    }


    public boolean updateOrderpBean(OrderpBean orderpBean) throws Exception {

        Connection conn = null;

            try {
                conn = DB.getConnection();
                conn.setAutoCommit(false);
                String updateTed = "UPDATE ORDER_ITEM SET ITEM=?,QTY=?, WHERE INVOICE = ?";

                getQueryRunner().update(conn, updateTed,orderpBean.getItem(),orderpBean.getQty(),orderpBean.getInvoice());
                conn.commit();
                JsfUtils.addSuccessMessage("User Updated successfully");
               // return rowCount == 1;
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                DbUtils.closeQuietly(conn);
            }


        return false;
    }


    public boolean deleteOrderpBean(OrderpBean orderpBean) {

        Connection conn = null;

        try {
            String updateQuery = "DELETE FROM ORDER_ITEM WHERE SNO = ?";
            conn = DB.getConnection();
            conn.setAutoCommit(true);
            int rowCount = getQueryRunner().update(conn, updateQuery, orderpBean.getSno());
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
			String sqlStringdisc;
			String sqlStringadd;

			con = DatabaseConnection.DB.getConnection();

			if(!inv.equalsIgnoreCase("all")){
		     sqlString = "SELECT SUM(AMT) FROM ORDER_ITEM WHERE STA='PAID' AND INVOICE='"+inv+"' ";
		     sqlStringdisc = "SELECT DISCP FROM BILL_ORDER WHERE FNO='"+inv+"' ";
			}
		     else{
		     sqlString = "SELECT SUM(AMT) FROM ORDER_ITEM WHERE STA='PAID' AND INVOICE!='ALL'";
		     sqlStringdisc = "SELECT DISCP FROM BILL_ORDER WHERE FNO!='ALL'";
		     }
			st = con.prepareStatement(sqlString);


				st = con.prepareStatement(sqlString);


			//st.setString(1, parameterName2);
			rs = st.executeQuery();
			if (rs.next()) {
				totalsum=rs.getFloat(1);


			}
			st = con.prepareStatement(sqlStringdisc);


			rs = st.executeQuery();
			if (rs.next()) {
				discp=rs.getFloat(1);


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

			 String sqlString1 = "SELECT TNAME FROM ORDER_ITEM WHERE INVOICE='"+inv+"' ";
				st = con.prepareStatement(sqlString1);
				rs = st.executeQuery();
				if (rs.next()) {
					tab=rs.getString(1);


				}



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









}
