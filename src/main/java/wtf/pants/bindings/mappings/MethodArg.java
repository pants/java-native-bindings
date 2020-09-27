package wtf.pants.bindings.mappings;

public class MethodArg {
    private final String argumentType;
    private final String argumentName;

    public MethodArg(final String argumentType, final String argumentName) {
        this.argumentType = argumentType;
        this.argumentName = argumentName;
    }

    public String getArgumentType() {
        return argumentType;
    }

    public String getArgumentName() {
        return argumentName;
    }

    @Override
    public String toString() {
        return "MethodArg{" +
                "argumentType='" + argumentType + '\'' +
                ", argumentName='" + argumentName + '\'' +
                '}';
    }
}
