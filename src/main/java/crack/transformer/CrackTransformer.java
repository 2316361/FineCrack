package crack.transformer;

import crack.AddConstFunction;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public abstract class CrackTransformer implements ClassFileTransformer {

    protected String targetClassName;
    protected String targetMethodName;
    protected int stack;
    protected int locals;
    protected byte[] newCode;
    protected AddConstFunction function;

    protected CrackTransformer(String targetClassName, String targetMethodName, int stack, int locals, byte[] newCode, AddConstFunction function) {
        this.targetClassName = targetClassName.replace('.', '/');
        this.targetMethodName = targetMethodName;
        this.stack = stack;
        this.locals = locals;
        this.newCode = newCode;
        this.function = function;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        if (className.equals(this.targetClassName)) {
            try {
                return this.modifyMethod(classfileBuffer, this.targetMethodName, this.stack, this.locals, this.newCode);
            } catch (Exception e) {
                e.printStackTrace();
                return classfileBuffer;
            }
        } else {
            return classfileBuffer;
        }
    }

    protected abstract byte[] modifyMethod(byte[] classfileBuffer, String methodName, int stack, int locals, byte[] newCode) throws Exception;

}
