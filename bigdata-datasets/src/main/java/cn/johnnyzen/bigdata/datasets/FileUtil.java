package cn.johnnyzen.bigdata.datasets;

import com.csvreader.CsvReader;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * @project: JohnnyWebappQuickstart
 * @author: Johnny
 * @date: 2022/6/20  16:39:11
 * @description:
 *  参考文献:
 *      java读取csv文件的两种方式 - https://blog.csdn.net/iverson_AL/article/details/121371116
 */

public class FileUtil {
    public final static String FILE_SEPARATOR = File.separator + File.separator;

    /**
     * CsvReader 读取
     * @param filePath
     * @return
     * @usage
     *  String csvPath = "C:\\Users\\Johnny\\Desktop\\dataset\\Chinese-medical-dialogue-data-master\\Data_数据\\Andriatria_男科\\男科5-13000.csv";
     *  readCsvByCsvReader(csvPath, 4, "GB2312");
     */
    public static ArrayList<String[]> readCsvByCsvReader(String filePath, int columnsNum, String charset) {
        ArrayList<String[]> strList = null;
        try {
            ArrayList<String[]> arrList = new ArrayList<String[]>();
            strList = new ArrayList<String[]>();
            CsvReader reader = new CsvReader(filePath, ',', Charset.forName(charset==null?"UTF-8":charset));//UTF-8 / GB2312 / ...
            while (reader.readRecord()) {
//                System.out.println(Arrays.asList(reader.getValues()));
                String [] columns = reader.getValues();
                arrList.add(columns); // 按行读取，并把每一行的数据添加到list集合
            }
            reader.close();

            //System.out.println("读取的行数：" + arrList.size());
            // 如果要返回 String[] 类型的 list 集合，则直接返回 arrList
            // 以下步骤是把 String[] 类型的 list 集合转化为 String 类型的 list 集合
            for (int row = 0; row < arrList.size(); row++) {
                // 组装S tring 字符串
                // 如果不知道有多少列，则可再加一个循环

                String[] ele = new String [columnsNum];
                for(int i=0;i<columnsNum;i++){
                    ele[i] = arrList.get(row)[i];
                    //System.out.print("column["+i+"]:"+ ele[i] + " ");
                }
                //System.out.println();
                strList.add(ele);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strList;
    }

    /**
     * BufferedReader 读取
     * @param filePath
     * @return
     */
    public static ArrayList<String> readCsvByBufferedReader(String filePath, String charset) {
        File csv = new File(filePath);
        csv.setReadable(true);
        csv.setWritable(true);
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            isr = new InputStreamReader(new FileInputStream(csv), charset==null?"UTF-8":charset);
            br = new BufferedReader(isr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String line = "";
        ArrayList<String> records = new ArrayList<>();
        try {
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                records.add(line);
            }
            System.out.println("csv表格读取行数：" + records.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

    /**
     * 输出某个文件夹下所有某个格式的文件
     * @param path
     * @param suffix
     * @return ArrayList<String> fileFullPaths
     * @usage
     *  getTxtName("C:\\Users\\Johnny\\Desktop\\dataset\\Chinese-medical-dialogue-data-master\\Data_数据", "csv", fileFullPaths);
     *  //output:
     *  C:\Users\Johnny\Desktop\dataset\Chinese-medical-dialogue-data-master\Data_数据\Andriatria_男科\男科5-13000.csv
     *  C:\Users\Johnny\Desktop\dataset\Chinese-medical-dialogue-data-master\Data_数据\IM_内科\内科5000-33000.csv
     *  C:\Users\Johnny\Desktop\dataset\Chinese-medical-dialogue-data-master\Data_数据\OAGD_妇产科\妇产科6-28000.csv
     *  C:\Users\Johnny\Desktop\dataset\Chinese-medical-dialogue-data-master\Data_数据\Oncology_肿瘤科\肿瘤科5-10000.csv
     *  C:\Users\Johnny\Desktop\dataset\Chinese-medical-dialogue-data-master\Data_数据\Pediatric_儿科\儿科5-14000.csv
     *  C:\Users\Johnny\Desktop\dataset\Chinese-medical-dialogue-data-master\Data_数据\Surgical_外科\外科5-14000.csv
     * @reference-url https://blog.csdn.net/qq_32539825/article/details/80693901
     */
    public static void getTxtName(String path,String suffix, ArrayList<String> fileFullPaths) {
        //判断文件对象是文件还是文件夹
        //构建文件对象
        File f = new File(path);
        //根据文件或者文件夹处理
        if(f.isFile()) {
            if(f.getName().endsWith(suffix)) {
                //System.out.println(f.getAbsolutePath());
                fileFullPaths.add(f.getAbsolutePath());
            }
        }else {
            //遍历文件夹
            File[] files = f.listFiles();
            if(files!=null && files.length>0) {
                //继续递归得到的文件或文件夹
                for (File file : files) {
                    getTxtName(file.getAbsolutePath(),suffix, fileFullPaths);
                }
            }
        }
        //System.out.println("getTxtName.fileFullPaths:" + fileFullPaths.size());
        //return fileFullPaths;
    }
}
