import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ExcelUtil {
    /**
     * 读取excel并输出list集合
     *
     * @param filePath 文件路径
     * @param beginNum 读取行数
     * @return
     * @throws Exception
     */
    public static List<List<String>> readExcel(String filePath, int beginNum) throws Exception {

        ArrayList<List<String>> list = new ArrayList<>();
        //1、获取文件输入流
        InputStream inputStream = new FileInputStream(filePath);
        //2、获取Excel工作簿对象
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        try {
            //3、得到Excel工作表对象
            XSSFSheet sheetAt = workbook.getSheetAt(beginNum);
            //4、循环读取表格数据

            for (Row row : sheetAt) {
                //首行（即表头）不读取
                if (row.getRowNum() < beginNum) {
                    continue;
                }
                //读取当前行中单元格数据，索引从0开始
                short lastCellNum = row.getLastCellNum();
                List<String> list1 = new ArrayList<>();
                for (int i = 0; i < lastCellNum; i++) {
                    Cell cell = row.getCell(i);
                    cell.setCellType(CellType.STRING);

                    list1.add(cell.getStringCellValue());
                }
                list.add(list1);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5、关闭流
            workbook.close();

        }
        return list;
    }

    /**
     * 打印excel
     *
     * @param lists
     */
    public static void printExcel(List<List<String>> lists) {
        for (List<String> list : lists
        ) {
            for (String str : list
            ) {
                System.out.print(str);
            }
            System.out.println();
        }
    }

    /**
     * 查找键值重复的数据
     * @param args 列名
     * @param filePath 文件路径
     * @throws Exception
     */

    public static void findRepeatRecord(char[] args, String filePath) throws Exception {
        List<List<String>> lists = readExcel(filePath, 0);
        //找到数据相同的数组
        Set<String> set = new HashSet<>();

        Integer rowNumber = 0;
        for (List<String> list : lists) {
            rowNumber ++;
            String key = "";
            for (char str : args) {
                key += list.get(str - 97);
            }
            if (!set.contains(key)) {
                set.add(key);
            } else {
                System.out.println("第"+rowNumber+"行键已存在,"+"为："+key);
            }

        }
    }

    public static void main(String[] args) throws Exception {
        //String filePath = "C:\\Users\\yangmf\\resource\\document\\ceshi.xlsx";
        //List<List<String>> lists = readExcel("C:\\Users\\yangmf\\resource\\document\\ceshi.xlsx", 0);
        // printExcel(lists);
        //char[] sz = {'a'};
        Scanner sn = new Scanner(System.in);
        System.out.println("请输入文件路径：");
        String file = sn.next();

        System.out.println("请输入判定列名：");
        char[] chars = sn.next().toCharArray();
        findRepeatRecord(chars,file);
    }
}
