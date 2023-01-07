package cn.johnnyzen.bigdata.datasets.medical.csv2mysql.handler;

import cn.johnnyzen.bigdata.datasets.medical.csv2mysql.entity.MedicalQARecord;
import cn.johnnyzen.bigdata.datasets.medical.csv2mysql.mapper.MedicalQARecordMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @project: JohnnyWebappQuickstart
 * @author: Johnny
 * @date: 2022/6/20  19:05:16
 * @description: ...
 */

public class MySQL2KafkaDataHandler extends AbstractDataHandler {

    @Override
    public List<MedicalQARecord> source(Object[] params) {
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
        List<MedicalQARecord> list = medicalQARecordMapper.findAll();
/*        for (MedicalQARecord item : list) {
            System.out.println(item);
            medicalQARecordMapper.save(item);
            session.commit();
        }*/
        session.close();
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<MedicalQARecord> handle(List<MedicalQARecord> sourceDataSet) {
        return sourceDataSet;
    }

    @Override
    public void sink(List<MedicalQARecord> sourceDataSet) {

    }
}
