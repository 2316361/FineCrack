package crack;

import crack.transformer.SingleMethodTransformer;

import java.io.File;
import java.lang.instrument.Instrumentation;

public class FineCrackInjector {

    public static void premain(String agentArgs, Instrumentation inst) {
        String path = FineCrackInjector.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File file = new File(path);
        String finalPath = file.getAbsolutePath();
        byte[] codeTemplate = new byte[]{18, 29, -72, 0, 12, -74, 0, 30, -64, 0, 31, 75, 18, 11, 4, 42, -72, 0, 32, 87, -72, 0, 19, -71, 0, 33, 1, 0, 76, 43, 3, 43, 16, 64, -74, 0, 34, -74, 0, 35, 77, 44, -72, 0, 36, 78, 45, 18, 37, 1, -74, 0, 38, 45, -74, 0, 39, -79};
        inst.addTransformer(new SingleMethodTransformer("com.fr.runtime.FineRuntime", "initEncryptedBridge", 4, 4, codeTemplate, constPool -> {
            int index = constPool.addClassInfo("java.lang.management.ManagementFactory");
            index = constPool.addMethodrefInfo(index, "getRuntimeMXBean", "()Ljava/lang/management/RuntimeMXBean;");
            codeTemplate[22] = (byte) index;
            codeTemplate[21] = (byte) (index >>> 8);
            index = constPool.addClassInfo("java.lang.management.RuntimeMXBean");
            index = constPool.addInterfaceMethodrefInfo(index, "getName", "()Ljava/lang/String;");
            codeTemplate[25] = (byte) index;
            codeTemplate[24] = (byte) (index >>> 8);
            index = constPool.addMethodrefInfo(92, "indexOf", "(I)I");
            codeTemplate[36] = (byte) index;
            codeTemplate[35] = (byte) (index >>> 8);
            index = constPool.addMethodrefInfo(92, "substring", "(II)Ljava/lang/String;");
            codeTemplate[39] = (byte) index;
            codeTemplate[38] = (byte) (index >>> 8);
            int virtualMachineIndex = constPool.addClassInfo("com.sun.tools.attach.VirtualMachine");
            index = constPool.addMethodrefInfo(virtualMachineIndex, "attach", "(Ljava/lang/String;)Lcom/sun/tools/attach/VirtualMachine;");
            codeTemplate[44] = (byte) index;
            codeTemplate[43] = (byte) (index >>> 8);
            index = constPool.addStringInfo(finalPath);
            codeTemplate[48] = (byte) index;
            index = constPool.addMethodrefInfo(virtualMachineIndex, "loadAgent", "(Ljava/lang/String;Ljava/lang/String;)V");
            codeTemplate[52] = (byte) index;
            codeTemplate[51] = (byte) (index >>> 8);
            index = constPool.addMethodrefInfo(virtualMachineIndex, "detach", "()V");
            codeTemplate[56] = (byte) index;
            codeTemplate[55] = (byte) (index >>> 8);
        }));
    }
}
