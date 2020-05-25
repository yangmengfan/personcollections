import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * @Auther: ymfa
 * @Date: 2020/2/14 14:50
 * @Description:
 */
public class FileUtil {

    public static void saveFileWriter(String filePath,String content) {
        //String filePath = "F:\\work\\water\\123.html";
        File file = new File(filePath);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileWriter fwriter = null;
        try {
            // true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
            fwriter = new FileWriter(filePath, true);
            fwriter.write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fwriter.flush();
                fwriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static Map<String,List<String>> map = new HashMap<>();
    public static Set<String> repeatName = new HashSet<>();
    /**
     * 读取某个文件夹下的所有文件(支持多级文件夹)
     */
    public static boolean readfile(String filepath) throws FileNotFoundException, IOException {
        try {

            File file = new File(filepath);
            if (!file.isDirectory()) {

                if (map.containsKey(file.getName())){
                    repeatName.add(file.getName());
                    map.get(file.getName()).add(file.getAbsolutePath());
                }else{
                    List<String> list = new ArrayList<>();
                    list.add(file.getAbsolutePath());
                    map.put(file.getName(),list);
                }

            } else if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "\\" + filelist[i]);
                    if (!readfile.isDirectory()) {
                        readfile(filepath + "\\" + filelist[i]);

                    } else if (readfile.isDirectory()) {
                        readfile(filepath + "\\" + filelist[i]);
                    }
                }

            }



        } catch (FileNotFoundException e) {
            System.out.println("readfile()   Exception:" + e.getMessage());
        }
        return true;
    }

    public static void main(String[] args)throws Exception {
        String ss = "F:\\BaiduNetdiskDownload\\work";
        System.out.println("请输入文件夹路径：");
        //Scanner in = new Scanner(System.in);
        //String s = in.nextLine();
        readfile(ss);
        //打印重复文件位置
        if (!repeatName.isEmpty()){
            for (String str : repeatName) {
                System.out.println();

                System.out.println("重复的文件名为:  "+str);
                List<String> list = map.get(str);
                System.out.println("重复的文件路径：");
                for (String sf: list){
                    System.out.print(sf+"  ");
                }
                System.out.println();
            }
        }
    }
}
