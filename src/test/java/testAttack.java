import de.saxsys.javafx.test.JfxRunner;
import model.entity.Enemy;
import model.entity.Player;
import org.junit.Test;
import org.junit.runner.RunWith;
import view.Platform;

import static junit.framework.TestCase.assertEquals;

@RunWith(JfxRunner.class)
public class testAttack {

    @Test
    public void checkPlayerDead() {
        Platform platform = new Platform();
        Player player = new Player(platform, 0,0);

        player.dead();
        assertEquals(true, player.isDead());
    }

    @Test
    public void checkEnemyDead() {
        Enemy enemy = new Enemy(0,0);

        enemy.dead();
        assertEquals(true, enemy.isDead());
    }
}
