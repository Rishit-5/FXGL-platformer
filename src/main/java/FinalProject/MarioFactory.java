package FinalProject;
import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MarioFactory implements EntityFactory {
    //prec: mariofactory is added as an entity factory
    //post: character jumps up and down and looks like a mario goomba
    @Spawns("enemy")
    public Entity newEnemy(SpawnData data){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        return Entities.builder()
                .type(MarioType.ENEMY)
                .from(data)
                .viewFromTextureWithBBox("goomba.png")
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new EnemyComponent())
                .build();
    }
    //prec: mariofactory is added as an entity factory
    //post: platform that a player an collide with is created, but nothing happens with collision
    @Spawns("platform")
    public Entity newPlatform(SpawnData data){
        return Entities.builder()
                .type(MarioType.PLATFORM)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"),data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .build();
    }
    //prec: mariofactory is added as an entity factory
    //post: alien is created that serves as an npc
    @Spawns("alien")
    public Entity newAlien(SpawnData data){
        return Entities.builder()
                .type(MarioType.ALIEN)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"),data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }
    //prec: mariofactory is added as an entity factory
    //post: cat is made that the user can collide with and pick up
    @Spawns("cat")
    public Entity newCat(SpawnData data){
        return Entities.builder()
                .type(MarioType.CAT)
                .from(data)
                .viewFromTextureWithBBox("cat.png")
                .with(new CollidableComponent(true))
                .build();
    }
    //prec: mariofactory is added as an entity factory
    //post: player has certain features by default that they can eventually customize
    @Spawns("player")
    public Entity newPlayer(SpawnData data){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        return Entities.builder()
                .type(MarioType.PLAYER)
                .from(data)
//                .viewFromNodeWithBBox(new Rectangle(30,30,Color.BLUE))
                .viewFromTextureWithBBox("marioblue.png")
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new PlayerComponent())
                .build();
    }
    //prec: mariofactory is added as an entity factory
    //post: coin that disappears when collided with is created with a coin texture
    @Spawns("coin")
    public Entity newCoin(SpawnData data){
        return Entities.builder()
                .type(MarioType.COIN)
                .from(data)
                .viewFromTexture("coin.png")
                .bbox(new HitBox(BoundingShape.circle(20)))
//                .viewFromNodeWithBBox(new Circle(data.<Integer>get("width")/2,Color.GOLD))
                .with(new CollidableComponent(true))
                .build();
    }
    //prec: mariofactory is added as an entity factory
    //post: porta created with portal texture
    @Spawns("portal")
    public Entity newPortal(SpawnData data){
        return Entities.builder()
                .type(MarioType.PORTAL)
                .from(data)
                .viewFromTextureWithBBox("portal.png")
                .with(new CollidableComponent(true))
                .build();
    }
    //prec: mariofactory is added as an entity factory
    //post: lava created that can support a player and has an orange texture
    @Spawns("lava")
    public Entity newLava(SpawnData data){
        return Entities.builder()
                .type(MarioType.LAVA)
                .from(data)
                .viewFromNodeWithBBox(new Rectangle(data.<Integer>get("width"),data.<Integer>get("height"),Color.ORANGERED))
                .with(new CollidableComponent(true))
                .with(new PhysicsComponent())
                .build();
    }
    //prec: mariofactory is added as an entity factory
    //post: lever created that starts off and can support a player
    @Spawns("lever")
    public Entity newLever(SpawnData data){
        return Entities.builder()
                .type(MarioType.LEVER)
                .from(data)
                .viewFromTextureWithBBox("offlever.png")
                .with(new CollidableComponent(true))
                .with(new PhysicsComponent())
                .build();
    }
    //prec: mariofactory is added as an entity factory
    //post: door with a hitbox is created that the user can't travel through
    @Spawns("door")
    public Entity newDoor(SpawnData data){
        return Entities.builder()
                .type(MarioType.DOOR)
                .from(data)
                .viewFromNodeWithBBox(new Rectangle(21,21*4,Color.BROWN))
                .with(new CollidableComponent(true))
                .with(new PhysicsComponent())
                .build();
    }
    //prec: mariofactory is added as an entity factory
    //post: gray door is created similar to the first door, but with different collision properties
    @Spawns("lockeddoor")
    public Entity newLockedDoor(SpawnData data){
        return Entities.builder()
                .type(MarioType.LOCKEDDOOR)
                .from(data)
                .viewFromNodeWithBBox(new Rectangle(21,21*4,Color.GRAY))
                .with(new CollidableComponent(true))
                .with(new PhysicsComponent())
                .build();
    }

}
