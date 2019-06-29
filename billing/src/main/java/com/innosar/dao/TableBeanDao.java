/*Developed by Rakesh M D & Abhijith T N
 Copyright 2015*/

package com.innosar.dao;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;

import org.apache.commons.dbutils.handlers.BeanListHandler;

import org.apache.commons.lang3.StringUtils;
import com.innosar.bean.TableBean;

import com.innosar.utils.JsfUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.innosar.dao.DatabaseConnection.*;


public class TableBeanDao {


    private Object kall;
    //private Object kdomain;
    private int klevel;


    public void search(QueryData<TableBean> qd) {

        Connection conn = null;

        int first = qd.getFirst();
        int pagesize = qd.getPageSize();
        TableBean filter = qd.getFilter();
        String sortOrder = qd.getSortOrder();
        String sortField = qd.getSortField();

        try {

            String countQuery = "SELECT COUNT(*) FROM ADD_TABLE WHERE 1 =1";
            String resultQuery = "SELECT * FROM ADD_TABLE WHERE 1 = 1 ";

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

                List<TableBean> resultList = qr
                        .query(conn, resultQuery + whereClause
                                        + sortOrderBuilder.toString(),
                                new BeanListHandler<>(TableBean.class,
                                        new BasicRowProcessor(
                                                new TableBeanProcessor())),
                                params);

                qd.setData(resultList);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    private String buildWhereClause(TableBean tableBean) {
        StringBuilder sb = new StringBuilder(100);



        if (StringUtils.isNotBlank(tableBean.getTname())) {
            sb.append(" AND TNAME like ? ");
        }


        if (StringUtils.isNotBlank(tableBean.getSta())) {
            sb.append(" AND STA like ? ");
        }


        return sb.toString();
    }

    private Object[] getParams(TableBean tableBean) throws SQLException {

        List<String> params = new ArrayList<>();




        if (StringUtils.isNotBlank(tableBean.getTname())) {
            params.add("%" + tableBean.getTname() + "%");
        }


        if (StringUtils.isNotBlank(tableBean.getSta())) {
            params.add("%" + tableBean.getSta()+ "%");
        }


        if (params.isEmpty())
            return null;

        return params.toArray();
    }


    public boolean updateTableBean(TableBean tableBean) throws Exception {

        Connection conn = null;

            try {
                conn = DB.getConnection();
                conn.setAutoCommit(false);
                String updateTed = "UPDATE ADD_TABLE SET STA=? WHERE SNO = ?";

                getQueryRunner().update(conn, updateTed,tableBean.getSta(),tableBean.getSno());
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


    public boolean deleteTableBean(TableBean tableBean) {

        Connection conn = null;

        try {
            String updateQuery = "DELETE FROM ADD_TABLE WHERE SNO = ?";
            conn = DB.getConnection();
            conn.setAutoCommit(true);
            int rowCount = getQueryRunner().update(conn, updateQuery, tableBean.getSno());
            return rowCount == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn);
        }

        return false;
    }



}
