package edu.hw2.task1;

public sealed interface Expr {
    double evaluate();

    record Constant(double number) implements Expr {
        @Override
        public double evaluate() {
            return number;
        }
    }

    record Negate(Expr obj) implements Expr {
        @Override
        public double evaluate() {
            return -obj.evaluate();
        }
    }

    record Exponent(Expr base, Expr exponent) implements Expr {
        public Exponent(Expr base, double exponent) {
            this(base, new Constant(exponent));
        }

        @Override
        public double evaluate() {
            return Math.pow(base.evaluate(), exponent.evaluate());
        }
    }

    record Addition(Expr lhs, Expr rhs) implements Expr {
        @Override
        public double evaluate() {
            return lhs.evaluate() + rhs.evaluate();
        }
    }

    record Multiplication(Expr lhs, Expr rhs) implements Expr {
        @Override
        public double evaluate() {
            return lhs.evaluate() * rhs.evaluate();
        }
    }
}
