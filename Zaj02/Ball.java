import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;



class Ball{

    double x, y;
    double vx, vy;
    double radius = 10;

    public Ball(){
        this.x = 0;
        this.y = 0;
    }

    public Ball(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.YELLOW);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        gc.strokeOval(x - radius, y - radius, radius * 2, radius * 2);
    }

}
