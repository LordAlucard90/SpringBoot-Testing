package springboottesting.advancedjunit5;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ExtensionExample implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        System.out.println("DisplayName: " + context.getDisplayName());
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        System.out.println("Test Method: " + context.getTestMethod().orElse(null));
    }
}
