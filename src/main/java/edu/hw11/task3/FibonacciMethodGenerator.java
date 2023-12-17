package edu.hw11.task3;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import static net.bytebuddy.jar.asm.Opcodes.ICONST_2;
import static net.bytebuddy.jar.asm.Opcodes.IF_ICMPGT;
import static net.bytebuddy.jar.asm.Opcodes.ILOAD;
import static net.bytebuddy.jar.asm.Opcodes.ISTORE;
import static net.bytebuddy.jar.asm.Opcodes.LCONST_0;
import static net.bytebuddy.jar.asm.Opcodes.LCONST_1;
import static net.bytebuddy.jar.asm.Opcodes.LRETURN;
import static net.bytebuddy.jar.asm.Opcodes.LSTORE;

public class FibonacciMethodGenerator implements ByteCodeAppender {
    @Override
    @SuppressWarnings("MagicNumber")
    public Size apply(
        MethodVisitor mv,
        Implementation.Context context,
        MethodDescription methodDescription
    ) {
        mv.visitCode();
        mv.visitInsn(LCONST_0);
        mv.visitVarInsn(LSTORE, 2); // long first
        mv.visitInsn(LCONST_1);
        mv.visitVarInsn(LSTORE, 3); // long second
        mv.visitInsn(ICONST_2);
        mv.visitVarInsn(ISTORE, 4); // int counter

        Label cicle = new Label();
        mv.visitVarInsn(ILOAD, 1);
        mv.visitVarInsn(ILOAD, 4);
        mv.visitJumpInsn(IF_ICMPGT, cicle);

        Label end = new Label();
        mv.visitInsn(LRETURN);
        mv.visitEnd();
        return new Size(10, 4);
    }

}
