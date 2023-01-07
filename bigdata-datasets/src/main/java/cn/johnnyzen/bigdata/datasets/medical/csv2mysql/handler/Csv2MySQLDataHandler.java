package cn.johnnyzen.bigdata.datasets.medical.csv2mysql.handler;

import cn.johnnyzen.bigdata.datasets.FileUtil;
import cn.johnnyzen.bigdata.datasets.medical.csv2mysql.entity.MedicalQARecord;
import cn.johnnyzen.bigdata.datasets.medical.csv2mysql.mapper.MedicalQARecordMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @project: JohnnyWebappQuickstart
 * @author: Johnny
 * @date: 2022/6/20  16:36:41
 * @description: ...
 */

public class Csv2MySQLDataHandler extends AbstractDataHandler {
   public static void main(String[] args) {
        String csvPathDir = "C:\\Users\\Johnny\\Desktop\\dataset\\Chinese-medical-dialogue-data-master\\Data_数据\\";

        ArrayList<String> fileFullPaths = new ArrayList<>();
        FileUtil.getTxtName(csvPathDir, "csv", fileFullPaths);

        System.out.println("fileFullPaths:" + fileFullPaths.size());
        for(int i=0;i<fileFullPaths.size();i++){
            String csvPath = fileFullPaths.get(i); //"C:\\Users\\Johnny\\Desktop\\dataset\\Chinese-medical-dialogue-data-master\\Data_数据\\Andriatria_男科\\男科5-13000.csv";
            //String [] paths = csvPath.split(FileUtil.FILE_SEPARATOR);
            //System.out.println(paths[paths.length-2].split("_"));

            Csv2MySQLDataHandler handler = new Csv2MySQLDataHandler();
            int columnNum = 4;
            List<MedicalQARecord> sourceDataSet = handler.source(new Object[]{ csvPath, columnNum });
            handler.sink(handler.handle(sourceDataSet));
            System.out.println("file:" + csvPath);
        }
    }

    @Override
    public List<MedicalQARecord> source(Object[] params) {
        String csvPath = (String) params[0]; //"C:\\Users\\Johnny\\Desktop\\dataset\\Chinese-medical-dialogue-data-master\\Data_数据\\Andriatria_男科\\男科5-13000.csv";
        int columnNum = (int) params[1];// 4
        ArrayList<String[]> originDataset = FileUtil.readCsvByCsvReader(csvPath, columnNum, "GB2312");
        List<MedicalQARecord> resultDataSet = new LinkedList<>();
        for (int j = 0, length = originDataset.size(); j < length; j++) {
            MedicalQARecord record = new MedicalQARecord();
            for (int i = 0; i < columnNum; i++) {
                String[] paths = csvPath.split(FileUtil.FILE_SEPARATOR);
                String[] departmentAndCode = paths[paths.length - 2].split("_");
                record.setDepartment(departmentAndCode[1]);// 男科
                record.setDepartmentCode(departmentAndCode[0]);// "Andriatria"
                record.setSubDept(originDataset.get(j)[0]);
                record.setTitle(originDataset.get(j)[1]);
                record.setDescribe(originDataset.get(j)[2]);
                record.setAnswer(originDataset.get(j)[3]);
            }
            resultDataSet.add(record);
        }
        return resultDataSet;
    }

    @Override
    public List<MedicalQARecord> handle(List<MedicalQARecord> sourceDataSet) {
        return sourceDataSet;
    }

    @Override
    public void sink(List<MedicalQARecord> sourceDataSet) {
        InputStream in = null;
        try {
            in = Resources.getResourceAsStream("db/mybatis-config.xml");
        } catch (IOException e) {
            e.printStackTrace();
        } //"cn/johnnyzen/dataset/bigdata/medical/csv2mysql/mapper/MedicalQARecordMapper.xml"

        //创建SqlSessionFactory工厂
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(in);
        //使用工厂生产SqlSession对象
        SqlSession session = factory.openSession();
        //使用SqlSession创建Mapper接口的代理对象
        MedicalQARecordMapper medicalQARecordMapper = session.getMapper(MedicalQARecordMapper.class);
        //使用代理对象执行方法
        //List<MedicalQARecord> list = medicalQARecordMapper.findAll();
        for (MedicalQARecord item : sourceDataSet) {
            //System.out.println(item);
            medicalQARecordMapper.save(item);
            session.commit();
        }
        session.close();
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void findAllTest() throws IOException {
        InputStream in = Resources.getResourceAsStream("db/mybatis-config.xml");
                //Resources.getResourceAsStream("cn/johnnyzen/dataset/bigdata/medical/csv2mysql/mapper/MedicalQARecordMapper.xml");
        //创建SqlSessionFactory工厂
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(in);
        //使用工厂生产SqlSession对象
        SqlSession session = factory.openSession();
        //使用SqlSession创建Mapper接口的代理对象
        MedicalQARecordMapper medicalQARecordMapper = session.getMapper(MedicalQARecordMapper.class);
        //使用代理对象执行方法
        List<MedicalQARecord> list = medicalQARecordMapper.findAll();
        for (MedicalQARecord item : list) {
            System.out.println(item);
        }
        session.close();
        in.close();
    }
}
