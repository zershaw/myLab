package refactoring_guru.factory_method.theFactory;

import refactoring_guru.factory_method.theFactory.factory.Dialog;
import refactoring_guru.factory_method.theFactory.factory.HtmlDialog;
import refactoring_guru.factory_method.theFactory.factory.WindowsDialog;

//客户端代码

public class Demo {
    private static Dialog dialog;

    public static void main(String[] args) {
        configure();
        runBusinessLogic();
    }

    /**
     * The concrete factory is usually chosen depending on configuration or
     * environment options.选择要创建的具体产品
     */
    static void configure() {
        if (System.getProperty("os.name").equals("Windows 10")) {
            dialog = new WindowsDialog(); //WindowsDialog()是Dialog的子类
        } else {
            dialog = new HtmlDialog();
        }
    }

    /**
     * All of the client code should work with factories and products through
     * abstract interfaces. This way it does not care which factory it works
     * with and what kind of product it returns.
     */
    static void runBusinessLogic() {
        dialog.renderWindow();
    }
}
