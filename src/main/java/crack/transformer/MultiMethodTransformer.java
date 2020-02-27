package crack.transformer;

import crack.AddConstFunction;
import javassist.bytecode.ClassFile;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.ExceptionTable;
import javassist.bytecode.MethodInfo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.List;

public class MultiMethodTransformer extends CrackTransformer {

    public MultiMethodTransformer(String targetClassName, String targetMethodName, int stack, int locals, byte[] newCode, AddConstFunction function) {
        super(targetClassName, targetMethodName, stack, locals, newCode, function);
    }

    @Override
    protected byte[] modifyMethod(byte[] classfileBuffer, String methodName, int stack, int locals, byte[] newCode) throws Exception {
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(classfileBuffer));
        ClassFile classFile = new ClassFile(inputStream);
        inputStream.close();
        ConstPool constPool = classFile.getConstPool();
        if (this.function != null) {
            this.function.accept(constPool);
        }
        CodeAttribute codeAttribute = new CodeAttribute(constPool, stack, locals, newCode, new ExceptionTable(constPool));
        List<MethodInfo> methodInfoList = classFile.getMethods();
        methodInfoList.forEach(methodInfo -> {
            if (methodInfo.getName().equals(this.targetMethodName)) {
                methodInfo.setCodeAttribute(codeAttribute);
            }
        });
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream outputStream = new DataOutputStream(byteArrayOutputStream);
        classFile.write(outputStream);
        outputStream.close();
        byte[] result = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return result;
    }
}
