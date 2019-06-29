/*Developed by Rakesh M D & Abhijith T N
 Copyright 2015*/

package com.innosar.dao;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;

import org.apache.commons.dbutils.handlers.BeanListHandler;

import org.apache.commons.lang3.StringUtils;
import com.innosar.bean.TableBill;

import com.innosar.utils.JsfUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.innosar.dao.DatabaseConnection.*;


public class TableBillDao {


    private Object kall;
    //private Object kdomain;
    private int klevel;


    public void search(QueryData<TableBill> qd) {

        Connection conn = null;

        int first = qd.getFirst();
        int pagesize = qd.getPageSize();
        TableBill filter = qd.getFilter();
        String sortOrder = qd.getSortOrder();
        String sortField = qd.getSortField();

        try {

            String countQuery = "SELECT COUNT(*) FROM BILL_ORDER WHERE 1 =1";
            String resultQuery = "SELECT * FROM BILL_ORDER WHERE 1 = 1 ";


            String sumQuery = " SELECT ROUND(SUM(TAXVAL),2),ROUND(SUM(CGST),2),ROUND(SUM(SGST),2),ROUND(SUM(DISCA),2),ROUND(SUM(TOTALAMT),2) FROM BILL_ORDER WHERE 1=1";

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

			TableBill f=new TableBill();
			while (rs1.next()) {
				f.setTaxvalsum(rs1.getString(1));
				f.setCgstsum(rs1.getString(2));
				f.setSgstsum(rs1.getString(3));
				f.setDiscasum(rs1.getString(4));
				f.setTotalamtsum(rs1.getString(5));


			}

            DbUtils.closeQuietly(null, pstmt, rs);
            DbUtils.closeQuietly(null, pstmt1, rs1);
            qd.setTotalResultCount(count);

            if (count > 0) {
                StringBuilder sortOrderBuilder = new StringBuilder();
                if ("tname".equals(sortField)) {
                    sortOrderBuilder.append(" ORDER BY  TNAME ").append(
                            sortOrder);
                } else if ("taxval".equals(sortField)) {
                    sortOrderBuilder.append(" ORDER BY TAXVAL ").append(
                            sortOrder);
                }else if ("cgst".equals(sortField)) {
                    sortOrderBuilder.append(" ORDER BY CGST ").append(
                            sortOrder);
                }else if ("sgst".equals(sortField)) {
                    sortOrderBuilder.append(" ORDER BY SGST ").append(
                            sortOrder);
                }else if ("discp".equals(sortField)) {
                    sortOrderBuilder.append(" ORDER BY DISCP ").append(
                            sortOrder);
                }else if ("disca".equals(sortField)) {
                    sortOrderBuilder.append(" ORDER BY DISCA ").append(
                            sortOrder);
                } else if ("modep".equals(sortField)) {
                    sortOrderBuilder.append(" ORDER BY MDOEP ").append(
                            sortOrder);
                }else if ("totalamt".equals(sortField)) {
                    sortOrderBuilder.append(" ORDER BY TOTALAMT ").append(
                            sortOrder);
                }   else if ("created".equals(sortField)) {
                    sortOrderBuilder.append(" ORDER BY CREATED ").append(
                            sortOrder);
                } else if ("modified".equals(sortField)) {
                    sortOrderBuilder.append(" ORDER BY MODIFIED ").append(
                            sortOrder);
                }  else {
                    sortOrderBuilder.append(" ORDER BY FNO ").append(
                            sortOrder);
                }

                sortOrderBuilder.append(" LIMIT ").append(first).append(",")
                        .append(pagesize);

                List<TableBill> resultList = qr
                        .query(conn, resultQuery + whereClause
                                        + sortOrderBuilder.toString(),
                                new BeanListHandler<>(TableBill.class,
                                        new BasicRowProcessor(
                                                new TableBillProcessor())),
                                params);

                qd.setData(resultList);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    private String buildWhereClause(TableBill tableBill) {
        StringBuilder sb = new StringBuilder(100);



        if (StringUtils.isNotBlank(tableBill.getTname())) {
            sb.append(" AND TNAME like ? ");
        }

        if (StringUtils.isNotBlank(tableBill.getTaxval())) {
            sb.append(" AND TAXVAL like ? ");
        }

        if (StringUtils.isNotBlank(tableBill.getCgst())) {
            sb.append(" AND CGST like ? ");
        }

        if (StringUtils.isNotBlank(tableBill.getSgst())) {
            sb.append(" AND SGST like ? ");
        }

        if (StringUtils.isNotBlank(tableBill.getDiscp())) {
            sb.append(" AND DISCP like ? ");
        }

        if (StringUtils.isNotBlank(tableBill.getDisca())) {
            sb.append(" AND DISCA like ? ");
        }

        if (StringUtils.isNotBlank(tableBill.getModep())) {
            sb.append(" AND MODEP like ? ");
        }

        if (StringUtils.isNotBlank(tableBill.getTotalamt())) {
            sb.append(" AND TOTALAMT like ? ");
        }

        if (StringUtils.isNotBlank(tableBill.getCreated())) {
            sb.append(" AND CREATED like ? ");
        }

        if (StringUtils.isNotBlank(tableBill.getModified())) {
            sb.append(" AND MODIFIED like ? ");
        }




        return sb.toString();
    }

    private Object[] getParams(TableBill tableBill) throws SQLException {

        List<String> params = new ArrayList<>();




        if (StringUtils.isNotBlank(tableBill.getTname())) {
            params.add("%" + tableBill.getTname() + "%");
        }

        if (StringUtils.isNotBlank(tableBill.getTaxval())) {
            params.add("%" + tableBill.getTaxval() + "%");
        }

        if (StringUtils.isNotBlank(tableBill.getCgst())) {
            params.add("%" + tableBill.getCgst() + "%");
        }

        if (StringUtils.isNotBlank(tableBill.getSgst())) {
            params.add("%" + tableBill.getSgst() + "%");
        }

        if (StringUtils.isNotBlank(tableBill.getDiscp())) {
            params.add("%" + tableBill.getDiscp() + "%");
        }

        if (StringUtils.isNotBlank(tableBill.getDisca())) {
            params.add("%" + tableBill.getDisca() + "%");
        }

        if (StringUtils.isNotBlank(tableBill.getModep())) {
            params.add("%" + tableBill.getModep() + "%");
        }

        if (StringUtils.isNotBlank(tableBill.getTotalamt())) {
            params.add("%" + tableBill.getTotalamt() + "%");
        }

        if (StringUtils.isNotBlank(tableBill.getCreated())) {
            params.add("%" + tableBill.getCreated() + "%");
        }

        if (StringUtils.isNotBlank(tableBill.getModified())) {
            params.add("%" + tableBill.getModified() + "%");
        }

        if (params.isEmpty())
            return null;

        return params.toArray();
    }


    public boolean updateTableBill(TableBill tableBill) throws Exception {

        Connection conn = null;

            try {
                conn = DB.getConnection();
                conn.setAutoCommit(false);
               // String updateTed = "UPDATE BILL_ORDER SET STA=? WHERE SNO = ?";

              //  getQueryRunner().update(conn, updateTed,tableBill.getSta(),tableBill.getSno());
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


    public boolean deleteTableBill(TableBill tableBill) {

        Connection conn = null;

        try {
            String updateQuery = "DELETE FROM BILL_ORDER WHERE FNO = ?";
            conn = DB.getConnection();
            conn.setAutoCommit(true);
            int rowCount = getQueryRunner().update(conn, updateQuery, tableBill.getFno());
            return rowCount == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn);
        }

        return false;
    }



}
