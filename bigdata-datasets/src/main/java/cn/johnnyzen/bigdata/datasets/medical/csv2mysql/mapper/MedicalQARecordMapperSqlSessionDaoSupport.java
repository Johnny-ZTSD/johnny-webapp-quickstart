package cn.johnnyzen.bigdata.datasets.medical.csv2mysql.mapper;

import cn.johnnyzen.bigdata.datasets.medical.csv2mysql.entity.MedicalQARecord;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

/**
 * @orgnization: 成都四方伟业软件股份有限公司
 * @project: SSMDemo2
 * @author: 千千寰宇
 * @date: 2020/6/6  01:19:27
 * @description: SqlSessionDaoSupport: 对 SqlSessionTemplate 的封装，内部含 getSqlSession()方法
 */

public class MedicalQARecordMapperSqlSessionDaoSupport extends SqlSessionDaoSupport implements MedicalQARecordMapper {
/*    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;*/

    @Override
    public List<MedicalQARecord> findAll() {
        return getSqlSession().getMapper(MedicalQARecordMapper.class).findAll();
    }

    @Override
    public List<MedicalQARecord> findByDepartment(String department) {
        return getSqlSession().getMapper(MedicalQARecordMapper.class).findByDepartment(department);
    }

    @Override
    public int save(MedicalQARecord medicalQARecord) {
        return getSqlSession().getMapper(MedicalQARecordMapper.class).save(medicalQARecord);
    }

    @Override
    public int saveList(List<MedicalQARecord> medicalQARecordList) {
        return getSqlSession().getMapper(MedicalQARecordMapper.class).saveList(medicalQARecordList);
    }
}
