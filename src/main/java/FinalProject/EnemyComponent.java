package FinalProject;

import com.almasb.fxgl.app.FXGL;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.time.LocalTimer;
import javafx.util.Duration;

class EnemyComponent extends Component {
    private PhysicsComponent physics;
    private LocalTimer jumptimer;
    //prec: user added in enemies
    //post: timer will start for the jumps
    @Override
    public void onAdded(){
        jumptimer = FXGL.newLocalTimer();
        jumptimer.capture();

    }
    //prec: enemy added
    //postc: checks if 2 seconds have passed before attempting another jump
    @Override
    public void onUpdate(double tpf) {
        if (jumptimer.elapsed(Duration.seconds(2))){
            jump();
            jumptimer.capture();
        }
    }
    //prec: 2 seconds have passed
    //post: enemy will jump a certain height(-400)
    public void jump(){
        physics.setVelocityY(-400);
    }

}
