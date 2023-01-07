package cn.johnnyzen.bigdata.datasets.medical.csv2mysql.handler;

import cn.johnnyzen.bigdata.datasets.medical.csv2mysql.entity.MedicalQARecord;

import java.util.List;

/**
 * @project: JohnnyWebappQuickstart
 * @author: Johnny
 * @date: 2022/6/20  16:22:27
 * @description: ...
 */

public abstract class AbstractDataHandler {
    /**
     * 来源数据集的抽取
     * @return
     */
    public abstract List<MedicalQARecord> source(Object [] params);

    /**
     * 对来源数据的处理
     * @param sourceDataSet
     * @return
     */
    public abstract List<MedicalQARecord> handle(List<MedicalQARecord> sourceDataSet);

    /**
     * 数据的输出
     * @param sourceDataSet
     */
    public abstract void sink(List<MedicalQARecord> sourceDataSet);
}
