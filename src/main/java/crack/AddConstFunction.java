package crack;

import javassist.bytecode.ConstPool;

@FunctionalInterface
public interface AddConstFunction {
    void accept(ConstPool constPool);
}
