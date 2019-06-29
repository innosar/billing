/*Developed by Rakesh M D & Abhijith T N
 Copyright 2015*/

package com.innosar.dao;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;

import org.apache.commons.dbutils.handlers.BeanListHandler;

import org.apache.commons.lang3.StringUtils;
import com.innosar.bean.AddressBean;

import com.innosar.utils.JsfUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.innosar.dao.DatabaseConnection.*;


public class AddressBeanDao {


    private Object kall;
    //private Object kdomain;
    private int klevel;


    public void search(QueryData<AddressBean> qd) {

        Connection conn = null;

        int first = qd.getFirst();
        int pagesize = qd.getPageSize();
        AddressBean filter = qd.getFilter();
        String sortOrder = qd.getSortOrder();
        String sortField = qd.getSortField();

        try {

            String countQuery = "SELECT COUNT(*) FROM ADDRESS_TABLE WHERE 1 =1";
            String resultQuery = "SELECT * FROM ADDRESS_TABLE WHERE 1 = 1 ";

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
                if ("address1".equals(sortField)) {
                    sortOrderBuilder.append(" ORDER BY  ADDRESS1 ").append(
                            sortOrder);
                }else  if ("address2".equals(sortField)) {
                    sortOrderBuilder.append(" ORDER BY  ADDRESS2 ").append(
                            sortOrder);
                } else  if ("address3".equals(sortField)) {
                    sortOrderBuilder.append(" ORDER BY  ADDRESS3 ").append(
                            sortOrder);
                } else  if ("address4".equals(sortField)) {
                    sortOrderBuilder.append(" ORDER BY  ADDRESS4 ").append(
                            sortOrder);
                } else  if ("address5".equals(sortField)) {
                    sortOrderBuilder.append(" ORDER BY  ADDRESS5 ").append(
                            sortOrder);
                } else  if ("gst".equals(sortField)) {
                    sortOrderBuilder.append(" ORDER BY  GST ").append(
                            sortOrder);
                } else  if ("web".equals(sortField)) {
                    sortOrderBuilder.append(" ORDER BY  WEB ").append(
                            sortOrder);
                }else  if ("email".equals(sortField)) {
                    sortOrderBuilder.append(" ORDER BY  EMAIL ").append(
                            sortOrder);
                }else  if ("fb".equals(sortField)) {
                    sortOrderBuilder.append(" ORDER BY  FB ").append(
                            sortOrder);
                }else  if ("insta".equals(sortField)) {
                    sortOrderBuilder.append(" ORDER BY  INSTA ").append(
                            sortOrder);
                }else  if ("mob".equals(sortField)) {
                    sortOrderBuilder.append(" ORDER BY  MOB ").append(
                            sortOrder);
                }else  if ("twit".equals(sortField)) {
                    sortOrderBuilder.append(" ORDER BY  TWIT ").append(
                            sortOrder);
                }else {
                    sortOrderBuilder.append(" ORDER BY SNO ").append(
                            sortOrder);
                }

                sortOrderBuilder.append(" LIMIT ").append(first).append(",")
                        .append(pagesize);

                List<AddressBean> resultList = qr
                        .query(conn, resultQuery + whereClause
                                        + sortOrderBuilder.toString(),
                                new BeanListHandler<>(AddressBean.class,
                                        new BasicRowProcessor(
                                                new AddressBeanProcessor())),
                                params);

                qd.setData(resultList);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    private String buildWhereClause(AddressBean addressBean) {
        StringBuilder sb = new StringBuilder(100);



        if (StringUtils.isNotBlank(addressBean.getAddress1())) {
            sb.append(" AND ADDRESS1 like ? ");
        }

        if (StringUtils.isNotBlank(addressBean.getAddress2())) {
            sb.append(" AND ADDRESS2 like ? ");
        }

        if (StringUtils.isNotBlank(addressBean.getAddress3())) {
            sb.append(" AND ADDRESS3 like ? ");
        }

        if (StringUtils.isNotBlank(addressBean.getAddress4())) {
            sb.append(" AND ADDRESS4 like ? ");
        }
        if (StringUtils.isNotBlank(addressBean.getAddress5())) {
            sb.append(" AND ADDRESS5 like ? ");
        }

        if (StringUtils.isNotBlank(addressBean.getGst())) {
            sb.append(" AND GST like ? ");
        }

        if (StringUtils.isNotBlank(addressBean.getWeb())) {
            sb.append(" AND WEB like ? ");
        }
        if (StringUtils.isNotBlank(addressBean.getEmail())) {
            sb.append(" AND EMAIL like ? ");
        }
        if (StringUtils.isNotBlank(addressBean.getMob())) {
            sb.append(" AND MOB like ? ");
        }
        if (StringUtils.isNotBlank(addressBean.getFb())) {
            sb.append(" AND FB like ? ");
        }
        if (StringUtils.isNotBlank(addressBean.getInsta())) {
            sb.append(" AND INSTA like ? ");
        }
        if (StringUtils.isNotBlank(addressBean.getTwit())) {
            sb.append(" AND TWIT like ? ");
        }

        return sb.toString();
    }

    private Object[] getParams(AddressBean addressBean) throws SQLException {

        List<String> params = new ArrayList<>();




        if (StringUtils.isNotBlank(addressBean.getAddress1())) {
            params.add("%" + addressBean.getAddress1() + "%");
        }
        if (StringUtils.isNotBlank(addressBean.getAddress2())) {
            params.add("%" + addressBean.getAddress2() + "%");
        }
        if (StringUtils.isNotBlank(addressBean.getAddress3())) {
            params.add("%" + addressBean.getAddress3() + "%");
        }
        if (StringUtils.isNotBlank(addressBean.getAddress4())) {
            params.add("%" + addressBean.getAddress4() + "%");
        }
        if (StringUtils.isNotBlank(addressBean.getAddress5())) {
            params.add("%" + addressBean.getAddress5() + "%");
        }
        if (StringUtils.isNotBlank(addressBean.getGst())) {
            params.add("%" + addressBean.getGst() + "%");
        }
        if (StringUtils.isNotBlank(addressBean.getWeb())) {
            params.add("%" + addressBean.getWeb() + "%");
        }
        if (StringUtils.isNotBlank(addressBean.getEmail())) {
            params.add("%" + addressBean.getEmail() + "%");
        }
        if (StringUtils.isNotBlank(addressBean.getFb())) {
            params.add("%" + addressBean.getFb() + "%");
        }
        if (StringUtils.isNotBlank(addressBean.getInsta())) {
            params.add("%" + addressBean.getInsta() + "%");
        }
        if (StringUtils.isNotBlank(addressBean.getMob())) {
            params.add("%" + addressBean.getMob() + "%");
        }
        if (StringUtils.isNotBlank(addressBean.getTwit())) {
            params.add("%" + addressBean.getTwit() + "%");
        }



        if (params.isEmpty())
            return null;

        return params.toArray();
    }


    public boolean updateAddressBean(AddressBean addressBean) throws Exception {

        Connection conn = null;

            try {
                conn = DB.getConnection();
                conn.setAutoCommit(false);
                String updateTed = "UPDATE ADDRESS_TABLE SET ADDRESS1=?,ADDRESS2=?,ADDRESS3=?,ADDRESS4=?,ADDRESS5=?,GST=?,WEB=?,EMAIL=?,MOB=?,FB=?,INSTA=?,TWIT=? WHERE SNO = ?";

                getQueryRunner().update(conn, updateTed,addressBean.getAddress1(),addressBean.getAddress2(),addressBean.getAddress3(),addressBean.getAddress4(),addressBean.getAddress5(),addressBean.getGst(),addressBean.getWeb(),addressBean.getEmail(),addressBean.getMob(),addressBean.getFb(),addressBean.getInsta(),addressBean.getTwit(),addressBean.getSno());
                conn.commit();
                JsfUtils.addSuccessMessage("Address Updated successfully");
               // return rowCount == 1;
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                DbUtils.closeQuietly(conn);
            }


        return false;
    }


    public boolean deleteAddressBean(AddressBean addressBean) {

        Connection conn = null;

        try {
            String updateQuery = "DELETE FROM ADDRESS_TABLE WHERE SNO = ?";
            conn = DB.getConnection();
            conn.setAutoCommit(true);
            int rowCount = getQueryRunner().update(conn, updateQuery, addressBean.getSno());
            return rowCount == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn);
        }

        return false;
    }



}
