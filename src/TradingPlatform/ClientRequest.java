package TradingPlatform;

import java.io.Serializable;

public class ClientRequest implements Serializable {
    private static final long serialVersionUID = -2289891509187701177L;

    private String className;
    private String methodName;
    private String[] arguments;

    public ClientRequest(String className, String methodName) {
        this.className = className;
        this.methodName = methodName;
        this.arguments = null;
    }

    public ClientRequest(String className, String methodName, String[] arguments) {
        this(className, methodName);
        this.arguments = arguments;
    }
}
