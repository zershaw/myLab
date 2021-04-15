package refactoring_guru.factory_method.theFactory.factory;

import refactoring_guru.factory_method.theFactory.buttons.Button;
import refactoring_guru.factory_method.theFactory.buttons.WindowsButton;

/**
 * 具体创建者
 * Windows Dialog will produce Windows buttons.
 */

public class WindowsDialog extends Dialog {

    @Override
    public Button createButton() {
        return new WindowsButton();
    }
}
