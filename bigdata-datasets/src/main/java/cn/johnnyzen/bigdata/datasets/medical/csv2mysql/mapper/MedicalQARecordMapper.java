package cn.johnnyzen.bigdata.datasets.medical.csv2mysql.mapper;

import cn.johnnyzen.bigdata.datasets.medical.csv2mysql.entity.MedicalQARecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @orgnization: 成都四方伟业软件股份有限公司
 * @project: SSMDemo2
 * @author: 千千寰宇
 * @date: 2020/6/6  01:13:25
 * @description: ...
 */
@Repository("medicalQARecordMapper")
public interface MedicalQARecordMapper {
    List<MedicalQARecord> findAll();

    List<MedicalQARecord> findByDepartment(@Param("department") String department);

    int save(MedicalQARecord medicalQARecord);

    int saveList(List<MedicalQARecord> medicalQARecordList);
}
