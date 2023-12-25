package edu.hw11.task3;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;
import static net.bytebuddy.jar.asm.Opcodes.F_APPEND;
import static net.bytebuddy.jar.asm.Opcodes.F_SAME;
import static net.bytebuddy.jar.asm.Opcodes.GOTO;
import static net.bytebuddy.jar.asm.Opcodes.ICONST_2;
import static net.bytebuddy.jar.asm.Opcodes.IF_ICMPGT;
import static net.bytebuddy.jar.asm.Opcodes.ILOAD;
import static net.bytebuddy.jar.asm.Opcodes.ISTORE;
import static net.bytebuddy.jar.asm.Opcodes.LADD;
import static net.bytebuddy.jar.asm.Opcodes.LCONST_0;
import static net.bytebuddy.jar.asm.Opcodes.LCONST_1;
import static net.bytebuddy.jar.asm.Opcodes.LLOAD;
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
        mv.visitVarInsn(LSTORE, 4); // long second
        mv.visitInsn(ICONST_2);
        mv.visitVarInsn(ISTORE, 6); // int counter

        Label loop = new Label();
        Label end = new Label();

        mv.visitLabel(loop); // loop:
        mv.visitFrame(F_APPEND, 3, new Object[] {Opcodes.LONG, Opcodes.LONG, Opcodes.INTEGER}, 0, null);
        mv.visitVarInsn(ILOAD, 6);
        mv.visitVarInsn(ILOAD, 1);
        mv.visitJumpInsn(IF_ICMPGT, end);

        mv.visitVarInsn(LLOAD, 2);
        mv.visitVarInsn(LLOAD, 4);
        mv.visitInsn(LADD); // -> first + second

        mv.visitVarInsn(LLOAD, 4);
        mv.visitVarInsn(LSTORE, 2);

        mv.visitVarInsn(LSTORE, 4);
        mv.visitIincInsn(6, 1);
        mv.visitJumpInsn(GOTO, loop);

        mv.visitLabel(end); // end:
        mv.visitFrame(F_SAME, 0, null, 0, null);
        mv.visitVarInsn(LLOAD, 4);
        mv.visitInsn(LRETURN);
        mv.visitEnd();
        return new Size(10, 10);
    }

}
