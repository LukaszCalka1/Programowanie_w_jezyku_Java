import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;



public class Simple_game_2 extends Application implements ChangeListener<Number>
 {
  private static final int FRAME_WIDTH  = 640;
  private static final int FRAME_HEIGHT = 480;  

  int x, y;

  GraphicsContext gc;
  Canvas canvas;
  Slider alpha, v;

      
  public static void main(String[] args) 
   {
    launch(args);
   }


    
    @Override
    public void start(Stage primaryStage) 
     {
      AnchorPane root = new AnchorPane();
      primaryStage.setTitle("Volleyball");
	
      canvas = new Canvas(FRAME_WIDTH, FRAME_HEIGHT);
      canvas.setOnMousePressed(this::mouse);
      
      gc = canvas.getGraphicsContext2D();

      x = 10;
      y = 10;
      	
      root.getChildren().add(canvas);	
	
      Button btn = new Button();
      btn.setText("Play");
      btn.setOnAction(this::play);	

      root.getChildren().add(btn);
      AnchorPane.setBottomAnchor( btn, 5.0d );


      Slider alpha, v;

      alpha = new Slider(30, 80, 5);
      alpha.setShowTickMarks(true);
      alpha.setShowTickLabels(true);
      alpha.valueProperty().addListener(new ChangeListener<Number>() 
                             {
                              public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) 
			       {    
			        System.out.println("alpha=" + new_val);  
			       }
			     });
			       
			       
			       
      root.getChildren().add(alpha);      

      AnchorPane.setBottomAnchor( alpha, 2.0d );
      AnchorPane.setLeftAnchor( alpha, 150.0d );      
      
      
      v = new Slider(10, 100, 10);
      v.setShowTickMarks(true);      
      v.setShowTickLabels(true);
      v.valueProperty().addListener(this::changed);
      
      root.getChildren().add(v);
            
      AnchorPane.setBottomAnchor( v, 2.0d );
      AnchorPane.setLeftAnchor( v, 300.0d );            
      
      Scene scene = new Scene(root);
      primaryStage.setTitle("Volleybal");
      primaryStage.setScene( scene );
      primaryStage.setWidth(FRAME_WIDTH + 10);
      primaryStage.setHeight(FRAME_HEIGHT+ 80);
      primaryStage.show();   
    }


    public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) 
     {    
      System.out.println("v=" + new_val);  
     }


    
    private void step()
     {
      System.out.println("step");     
      gc.fillRect(x, y, 100, 100);      
      x+=10;
     }    
   private void mouse(MouseEvent e)
    {
     System.out.println("X=" + e.getX());
     System.out.println("Y=" + e.getY());         
    }   
    
   private void play(ActionEvent e)    
    {
     Timeline timeline;    
     
     timeline = new Timeline(new KeyFrame(Duration.millis(200), e1->step()));

//     timeline.setCycleCount(Timeline.INDEFINITE);
     timeline.setCycleCount(10);
     timeline.play();
     
    }    
    
}	
