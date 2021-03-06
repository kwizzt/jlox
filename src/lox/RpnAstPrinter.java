package lox;

public class RpnAstPrinter implements Expr.Visitor<String> {

    String print(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public String visitBinaryExpr(Expr.Binary expr) {
        return print(expr.left) + " " + print(expr.right) + " " + expr.operator.lexeme;
    }

    @Override
    public String visitGroupingExpr(Expr.Grouping expr) {
        return "(" + print(expr.expression) + ")";
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr) {
        if (expr.value == null) return "nil";
        return expr.value.toString();
    }

    @Override
    public String visitUnaryExpr(Expr.Unary expr) {
        return expr.operator.lexeme + "(" + print(expr.right) + ")";
    }

    public static void main(String[] args) {
        Expr expression = new Expr.Binary(
                new Expr.Binary(
                        new Expr.Literal(1),
                        new Token(TokenType.PLUS, "+", null, 1),
                        new Expr.Unary(
                                new Token(TokenType.MINUS, "-", null, 1),
                                new Expr.Literal(2))),
                new Token(TokenType.STAR, "*", null, 1),
                new Expr.Binary(
                        new Expr.Literal(4),
                        new Token(TokenType.MINUS, "-", null, 1),
                        new Expr.Grouping(
                                new Expr.Binary(
                                        new Expr.Literal(3),
                                        new Token(TokenType.PLUS, "+", null, 1),
                                        new Expr.Literal(null)))));
        System.out.println(new RpnAstPrinter().print(expression));
    }
}
