/*Developed by Rakesh M D & Abhijith T N
 Copyright 2015*/

package com.innosar.dao;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;

import org.apache.commons.dbutils.handlers.BeanListHandler;

import org.apache.commons.lang3.StringUtils;
import com.innosar.bean.ItemBean;

import com.innosar.utils.JsfUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.innosar.dao.DatabaseConnection.*;


public class ItemBeanDao {


    private Object kall;
    //private Object kdomain;
    private int klevel;


    public void search(QueryData<ItemBean> qd) {

        Connection conn = null;

        int first = qd.getFirst();
        int pagesize = qd.getPageSize();
        ItemBean filter = qd.getFilter();
        String sortOrder = qd.getSortOrder();
        String sortField = qd.getSortField();

        try {

            String countQuery = "SELECT COUNT(*) FROM ADD_ITEM WHERE 1 =1";
            String resultQuery = "SELECT * FROM ADD_ITEM WHERE 1 = 1 ";

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
                if ("item".equals(sortField)) {
                    sortOrderBuilder.append(" ORDER BY  ITEM ").append(
                            sortOrder);
                } else if ("created".equals(sortField)) {
                    sortOrderBuilder.append(" ORDER BY CREATED ").append(
                            sortOrder);
                } else if ("modified".equals(sortField)) {
                    sortOrderBuilder.append(" ORDER BY MODIFIED ").append(
                            sortOrder);
                } else if ("rate".equals(sortField)) {
                    sortOrderBuilder.append(" ORDER BY RATE ").append(
                            sortOrder);
                } else {
                    sortOrderBuilder.append(" ORDER BY SNO ").append(
                            sortOrder);
                }

                sortOrderBuilder.append(" LIMIT ").append(first).append(",")
                        .append(pagesize);

                List<ItemBean> resultList = qr
                        .query(conn, resultQuery + whereClause
                                        + sortOrderBuilder.toString(),
                                new BeanListHandler<>(ItemBean.class,
                                        new BasicRowProcessor(
                                                new ItemBeanProcessor())),
                                params);

                qd.setData(resultList);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    private String buildWhereClause(ItemBean itemBean) {
        StringBuilder sb = new StringBuilder(100);



        if (StringUtils.isNotBlank(itemBean.getItem())) {
            sb.append(" AND ITEM like ? ");
        }




        return sb.toString();
    }

    private Object[] getParams(ItemBean itemBean) throws SQLException {

        List<String> params = new ArrayList<>();




        if (StringUtils.isNotBlank(itemBean.getItem())) {
            params.add("%" + itemBean.getItem() + "%");
        }





        if (params.isEmpty())
            return null;

        return params.toArray();
    }


    public boolean updateItemBean(ItemBean itemBean) throws Exception {

        Connection conn = null;

            try {
                conn = DB.getConnection();
                conn.setAutoCommit(false);
                String updateTed = "UPDATE ADD_ITEM SET ITEM=?,RATE=?,MODIFIED=NOW() WHERE SNO = ?";

                getQueryRunner().update(conn, updateTed,itemBean.getItem(),itemBean.getRate(),itemBean.getSno());
                conn.commit();
                JsfUtils.addSuccessMessage("Item Updated successfully");
               // return rowCount == 1;
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                DbUtils.closeQuietly(conn);
            }


        return false;
    }


    public boolean deleteItemBean(ItemBean itemBean) {

        Connection conn = null;

        try {
            String updateQuery = "DELETE FROM ADD_ITEM WHERE SNO = ?";
            conn = DB.getConnection();
            conn.setAutoCommit(true);
            int rowCount = getQueryRunner().update(conn, updateQuery, itemBean.getSno());
            return rowCount == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn);
        }

        return false;
    }



}
