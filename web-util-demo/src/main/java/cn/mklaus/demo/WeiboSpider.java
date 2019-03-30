package cn.mklaus.demo;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.nutz.lang.Strings;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author klaus
 * @date 2019/1/23 9:05 PM
 */
public class WeiboSpider {

    private static File file = new File("/Users/klaus/Documents/data.xlsx");

    private static List<String> extract(int sheetNum, int col) throws IOException, InvalidFormatException {
        File file = new File("/Users/klaus/Documents/data.xlsx");
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(sheetNum);
        int numberOfRow = sheet.getLastRowNum() + 1;

        List<String> list = new ArrayList<>();
        for (int i = 0; i < numberOfRow; i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(col);
            if (cell != null) {
                String text = cell.toString();
                if (Strings.isNotBlank(text.trim())) {
                    try {
                        DecimalFormat df = new DecimalFormat("0");
                        text = df.format(cell.getNumericCellValue());
                    } catch (Exception e) {}
                    list.add(text.trim());
                }
            }
        }

        return list;
    }

    public static void main(String[] args) throws IOException, InvalidFormatException {
        List<String> extract = extract(2, 3);
        System.out.println("extract.size() = " + extract.size());
        List<String> urls = extract.stream()
//                .filter(s -> s.startsWith("http"))
//                .map(s-> {
//                    s = s.replace("/video", "");
//                    int start = s.lastIndexOf("/") + 1;
//                    return s.substring(start);
//                })
//                .filter(s -> !(s.equals("抖音ID") || s.equals("博主链接")))
                .filter(s-> !s.equals("博主昵称"))
                .collect(Collectors.toList());
        System.out.println("urls.size() = " + urls.size());
        urls = urls.subList(200, 300);
        String array = urls.stream().map(s -> "'" + s.trim() + "'").collect(Collectors.joining(",", "[", "]"));
        System.out.println("array = " + array);
        System.out.println("urls = " + urls.size());
//        System.out.println(extract);
    }


    static class OidSpider implements PageProcessor {
        @Override
        public Site getSite() {
            return Site.me().setRetryTimes(5).setSleepTime(3000);
        }

        @Override
        public void process(Page page) {
            System.out.println("page.getHtml().get() = " + page.getHtml().get());
        }

        public static void main(String[] args) {
            Spider.create(new OidSpider()).addUrl("http://weibo.com/superlisa").thread(5).run();
        }
    }

}
