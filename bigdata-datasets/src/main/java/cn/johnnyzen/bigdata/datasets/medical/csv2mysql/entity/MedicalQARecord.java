package cn.johnnyzen.bigdata.datasets.medical.csv2mysql.entity;

/**
 * @project: JohnnyWebappQuickstart
 * @author: Johnny
 * @date: 2022/6/20  16:24:03
 * @description: 医疗问答记录
 *  ods_medical_mqa_tb_medical_qa_record
    create table ods_medical_mqa_tb_medical_qa_record(
        id int(11) comment 'ID/主键' NOT NULL PRIMARY KEY AUTO_INCREMENT,
        department varchar(255) comment '科室',
        department_code varchar(255) comment '科室代码',
        sub_dept varchar(255) comment '子科室名称',
        title varchar(255) comment '问题标题',
        `describe` varchar(1024) comment '问题描述',
        answer varchar(4096) comment '问题解答'
    ) ENGINE=InnoDB AUTO_INCREMENT=704677 DEFAULT CHARSET=utf8 comment '医疗问答记录';
 */

public class MedicalQARecord {

    /* id/自增主键 */
    private Integer id;

    /**
     * @property 科室
     * @sample 男科 / 妇科 / 内科 / 外科 / ...
     */
    private String department;

    /**
     * @property 科室代码
     * @sample
     *  Andriatria(男科)
     *  IM(内科, Internal medicine)
     *  OAGD(妇产科, The department of obstetrics and gynecology)
     *  Oncology(肿瘤科)
     *  Pediatric(儿科)
     *  Surgical(外科)
     */
    private String departmentCode;

    /**
     * @property 子科室名称
     * @sample 早泄 / 阳痿 / ...
     */
    private String subDept;

    /**
     * @property 问题的标题
     * @sample 男孩早泄会是什么病因引起的?
     */
    private String title;

    /**
     * @property 问题描述
     */
    private String describe;

    /**
     * @property 问题回答
     */
    private String answer;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getSubDept() {
        return subDept;
    }

    public void setSubDept(String subDept) {
        this.subDept = subDept;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MedicalQARecord{" +
                "id=" + id +
                ", department='" + department + '\'' +
                ", departmentCode='" + departmentCode + '\'' +
                ", subDept='" + subDept + '\'' +
                ", title='" + title + '\'' +
                ", describe='" + describe + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
