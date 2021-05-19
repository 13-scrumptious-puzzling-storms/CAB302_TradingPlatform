package TradingPlatform.NetworkProtocol;

import java.io.Serializable;

public class ServerRequest implements Serializable {
    private static final long serialVersionUID = -2289891509187701177L;

    private String className;
    private String methodName;
    private String[] arguments;

    public ServerRequest(String className, String methodName) {
        this.className = className;
        this.methodName = methodName;
        this.arguments = null;
    }

    public ServerRequest(String className, String methodName, String[] arguments) {
        this(className, methodName);
        this.arguments = arguments;
    }

    public String getClassName() { return className; }
    public String getMethodName() { return methodName; }
    public String[] getArguments() { return  arguments; }
}
