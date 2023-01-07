package cn.johnnyzen;

import cn.johnnyzen.bigdata.datasets.medical.csv2mysql.mapper.MedicalQARecordMapperSqlSessionDaoSupport;
import cn.johnnyzen.common.print.Print;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        MedicalQARecordMapperSqlSessionDaoSupport medicalQARecordMapperSqlSessionDaoSupport = new MedicalQARecordMapperSqlSessionDaoSupport();
        Print.print(medicalQARecordMapperSqlSessionDaoSupport.findAll());
    }
}
