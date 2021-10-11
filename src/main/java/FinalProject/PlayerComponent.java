package FinalProject;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;

public class PlayerComponent extends Component {

    private PhysicsComponent physics;

    public void onUpdate(Entity entity, double tpf){

    }
    //prec: player clicked a left button
    //post: player goes left
    public void left(int speed){
            physics.setVelocityX(-speed);

    }
    //prec: player clicked a right button
    //post: player goes right
    public void right(int speed){
            physics.setVelocityX(speed);

    }
    //prec: player clicked an up button
    //post: player goes up, but gravity will pull them down
    public void jump(int jump){
        //prevents the player from spamming W to go flying(unless they have infinite jump)
            physics.setVelocityY(-jump);
    }
    public void jump2(int jump){
        //prevents the player from spamming W to go flying
        physics.setVelocityY(-jump);
    }
    //prec: controller asks for current y velocity
    //post: returns the y velocity
    public double getVelocityY(){
        return physics.getVelocityY();
    }
    //prec: nothing has been clicked
    //postc: prevents player from sliding
    public void cometoStop(){
        physics.setVelocityX(0);
    }

}
