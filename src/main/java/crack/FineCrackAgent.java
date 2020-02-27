package crack;

import crack.transformer.MultiMethodTransformer;
import crack.transformer.SingleMethodTransformer;

import java.lang.instrument.Instrumentation;

public class FineCrackAgent {
    public static void agentmain(String args, Instrumentation inst) throws Exception {
        Class<?>[] classes = inst.getAllLoadedClasses();
        for (Class<?> clazz : classes) {
            String name = clazz.getName();
            if (name.equals("com.fr.license.selector.EncryptedLicenseSelector")) {
                inst.addTransformer(new SingleMethodTransformer(name, "decrypt", 1, 2, new byte[]{43, -80}, null), true);
                inst.retransformClasses(clazz);
                System.out.println(name + " 替换完成！");
            }
            if (name.equals("com.fr.license.security.LicFileRegistry")) {
                inst.addTransformer(new SingleMethodTransformer(name, "check", 1, 2, new byte[]{4, -84}, null), true);
                inst.retransformClasses(clazz);
                System.out.println(name + " 替换完成！");
            }
            if (name.equals("com.fr.license.entity.FineLicense")) {
                inst.addTransformer(new MultiMethodTransformer(name, "support", 1, 2, new byte[]{4, -84}, null), true);
                inst.retransformClasses(clazz);
                System.out.println(name + " 替换完成！");
            }
        }
    }
}
