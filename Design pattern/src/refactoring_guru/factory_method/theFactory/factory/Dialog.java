package refactoring_guru.factory_method.theFactory.factory;

import refactoring_guru.factory_method.theFactory.buttons.Button;

/**
 * 基础创建者
 * Base factory class. Note that "factory" is merely a role for the class. It
 * should have some core 【business logic】 which needs different products to be
 * created.
 */

public abstract class Dialog {
    public void renderWindow() {
        // ... other code ...

        Button okButton = createButton();
        okButton.render();
    }

    /**
     * Subclasses will override this method in order to create specific button
     * objects.
     */
    public abstract Button createButton();
}
