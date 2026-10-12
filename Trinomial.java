public class Trinomial {

    private double a, b, c;
    private double delta;
    private double x1, x2;

    public Trinomial(String aStr, String bStr, String cStr) throws Exception {
        this.a = Double.valueOf(aStr);
        this.b = Double.valueOf(bStr);
        this.c = Double.valueOf(cStr);


        if (this.a == 0) {
            throw new Exception("a nie moze byc 0, równanie liniowe!!!!");
        }

        this.delta = this.delta();
    }

    private double delta() {
        return this.b * this.b - 4 * this.a * this.c;
    }

    public void calc_roots() throws Exception{
        if (this.delta < 0) {
            throw new Exception("Delta mniejsza od 0!!!!");
        }

        this.x1 = (-this.b - Math.sqrt(this.delta)) / (2 * this.a);
        this.x2 = (-this.b + Math.sqrt(this.delta)) / (2 * this.a);

        if (this.delta == 0) {
            System.out.println("Jeden pierwiastek podwójny x0: " + this.x1);
        } else {
            System.out.println("Pierwiastek x1: " + this.x1 + "; pierwiastek x2: " + this.x2);
        }
    }
}