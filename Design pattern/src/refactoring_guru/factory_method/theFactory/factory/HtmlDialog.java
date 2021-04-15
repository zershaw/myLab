package refactoring_guru.factory_method.theFactory.factory;

import refactoring_guru.factory_method.theFactory.buttons.Button;
import refactoring_guru.factory_method.theFactory.buttons.HtmlButton;

/**
 * 具体创建者
 * HTML Dialog will produce HTML buttons.
 */

public class HtmlDialog extends Dialog{

    @Override
    public Button createButton() {
        return new HtmlButton();
    }
}
