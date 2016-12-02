package slpl.ast;

public class If extends AST {

    private AST condition, then, _else;

    public If(AST condition, AST then, AST _else) {
        this.condition = condition;
        this.then = then;
        this._else = _else;
    }

    public If(AST condition, AST then) {
        this(condition, then, null); // TODO: pass some kind of empty node instead of null. VOID? define type of If else statement to be VOID
    }

    @Override
    public AST evaluate() {
        Boolean b = (Boolean) condition.evaluate();
        if(b.getValue()) {
            then.evaluate();
        } else if (_else != null){
            _else.evaluate();
        }
        return this;
    }

    @Override
    public String toString() {
        return String.format("(If %s Then %s Else %s)", condition, then, _else);
    }
}
