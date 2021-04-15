package example.trycatch;

import java.net.MalformedURLException;

public class PageCatalog {
    public static void main(String[] args) {
        HomePage[] catalog = new HomePage[5];
        try{
            catalog[0] = new HomePage("a","http://www.news.com");
            catalog[1] = new HomePage("b","http://www.bill.com");
            catalog[2] = new HomePage("c","http://www.workbench.com");
            catalog[3] = new HomePage("d","http://www.cole.com");
            catalog[4] = new HomePage("e","www.rafe.org");//因为这里报错了，故下面的输出步骤无法到达并输出，该异常由主函数而非HomePage类来处理，故跳到主函数的catch模块， PageCatalog的剩余部分不再执行
            for (int i =0;i<catalog.length;i++){ // 并没有执行到这一步
                System.out.println(catalog[i].owner+": "+ catalog[i].address);
            }
        }catch (MalformedURLException e){
            System.out.println("Error: " +e.getMessage());
        }
        finally {
            System.out.println("阿猪");
        }
    }
}
