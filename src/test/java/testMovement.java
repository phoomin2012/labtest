import controller.GameLoop;
import de.saxsys.javafx.test.JfxRunner;
import de.saxsys.javafx.test.TestInJfxThread;
import javafx.scene.input.KeyCode;
import model.entity.BaseEntity;
import model.entity.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import view.Platform;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

@RunWith(JfxRunner.class)
public class testMovement {

    private Player floatingPlayer;

    private Platform platformUnderTest;
    private GameLoop gameLoopUnderTest;
    private Method updateMethod;
    private Method updateDirectionMethod;
    private Method paintMethod;

    private KeyCode leftKey = KeyCode.A;
    private KeyCode rightKey = KeyCode.D;
    private KeyCode upKey = KeyCode.W;
    private KeyCode downKey = KeyCode.S;
    private KeyCode fightKey = KeyCode.SPACE;

    @Before
    public void setUp() throws NoSuchMethodException {
        platformUnderTest = new Platform();
        gameLoopUnderTest = new GameLoop(platformUnderTest);

        floatingPlayer = new Player(platformUnderTest, 30, 30);

        updateDirectionMethod = GameLoop.class.getDeclaredMethod("updateDirection", Player.class);
        updateDirectionMethod.setAccessible(true);

        updateMethod = GameLoop.class.getDeclaredMethod("update", ArrayList.class);
        updateMethod.setAccessible(true);
    }

    @Test
    public void testMoveDirectionW() throws InvocationTargetException, IllegalAccessException {
        platformUnderTest.getKeys().add(leftKey);
        ArrayList<BaseEntity> c = new ArrayList<>();
        c.add(floatingPlayer);

        updateDirectionMethod.invoke(gameLoopUnderTest, floatingPlayer);
        updateMethod.invoke(gameLoopUnderTest, c);
        assertEquals(true, platformUnderTest.getKeys().isPressed(leftKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(rightKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(upKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(downKey));

        assertEquals(true, floatingPlayer.isMoveLeft());
        assertEquals(false, floatingPlayer.isMoveRight());
        assertEquals(false, floatingPlayer.isMoveUp());
        assertEquals(false, floatingPlayer.isMoveDown());
    }

    @Test
    public void testMoveDirectionNW() throws InvocationTargetException, IllegalAccessException {
        platformUnderTest.getKeys().add(leftKey);
        platformUnderTest.getKeys().add(upKey);
        ArrayList<BaseEntity> c = new ArrayList<>();
        c.add(floatingPlayer);

        updateDirectionMethod.invoke(gameLoopUnderTest, floatingPlayer);
        updateMethod.invoke(gameLoopUnderTest, c);
        assertEquals(true, platformUnderTest.getKeys().isPressed(leftKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(rightKey));
        assertEquals(true, platformUnderTest.getKeys().isPressed(upKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(downKey));

        assertEquals(true, floatingPlayer.isMoveLeft());
        assertEquals(false, floatingPlayer.isMoveRight());
        assertEquals(true, floatingPlayer.isMoveUp());
        assertEquals(false, floatingPlayer.isMoveDown());
    }

    @Test
    public void testMoveDirectionN() throws InvocationTargetException, IllegalAccessException {
        platformUnderTest.getKeys().add(upKey);
        ArrayList<BaseEntity> c = new ArrayList<>();
        c.add(floatingPlayer);

        updateDirectionMethod.invoke(gameLoopUnderTest, floatingPlayer);
        updateMethod.invoke(gameLoopUnderTest, c);
        assertEquals(false, platformUnderTest.getKeys().isPressed(leftKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(rightKey));
        assertEquals(true, platformUnderTest.getKeys().isPressed(upKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(downKey));

        assertEquals(false, floatingPlayer.isMoveLeft());
        assertEquals(false, floatingPlayer.isMoveRight());
        assertEquals(true, floatingPlayer.isMoveUp());
        assertEquals(false, floatingPlayer.isMoveDown());
    }

    @Test
    public void testMoveDirectionNE() throws InvocationTargetException, IllegalAccessException {
        platformUnderTest.getKeys().add(rightKey);
        platformUnderTest.getKeys().add(upKey);
        ArrayList<BaseEntity> c = new ArrayList<>();
        c.add(floatingPlayer);

        updateDirectionMethod.invoke(gameLoopUnderTest, floatingPlayer);
        updateMethod.invoke(gameLoopUnderTest, c);
        assertEquals(false, platformUnderTest.getKeys().isPressed(leftKey));
        assertEquals(true, platformUnderTest.getKeys().isPressed(rightKey));
        assertEquals(true, platformUnderTest.getKeys().isPressed(upKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(downKey));

        assertEquals(false, floatingPlayer.isMoveLeft());
        assertEquals(true, floatingPlayer.isMoveRight());
        assertEquals(true, floatingPlayer.isMoveUp());
        assertEquals(false, floatingPlayer.isMoveDown());
    }

    @Test
    public void testMoveDirectionE() throws InvocationTargetException, IllegalAccessException {
        platformUnderTest.getKeys().add(rightKey);
        ArrayList<BaseEntity> c = new ArrayList<>();
        c.add(floatingPlayer);

        updateDirectionMethod.invoke(gameLoopUnderTest, floatingPlayer);
        updateMethod.invoke(gameLoopUnderTest, c);
        assertEquals(false, platformUnderTest.getKeys().isPressed(leftKey));
        assertEquals(true, platformUnderTest.getKeys().isPressed(rightKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(upKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(downKey));

        assertEquals(false, floatingPlayer.isMoveLeft());
        assertEquals(true, floatingPlayer.isMoveRight());
        assertEquals(false, floatingPlayer.isMoveUp());
        assertEquals(false, floatingPlayer.isMoveDown());
    }

    @Test
    public void testMoveDirectionSE() throws InvocationTargetException, IllegalAccessException {
        platformUnderTest.getKeys().add(rightKey);
        platformUnderTest.getKeys().add(downKey);
        ArrayList<BaseEntity> c = new ArrayList<>();
        c.add(floatingPlayer);

        updateDirectionMethod.invoke(gameLoopUnderTest, floatingPlayer);
        updateMethod.invoke(gameLoopUnderTest, c);
        assertEquals(false, platformUnderTest.getKeys().isPressed(leftKey));
        assertEquals(true, platformUnderTest.getKeys().isPressed(rightKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(upKey));
        assertEquals(true, platformUnderTest.getKeys().isPressed(downKey));

        assertEquals(false, floatingPlayer.isMoveLeft());
        assertEquals(true, floatingPlayer.isMoveRight());
        assertEquals(false, floatingPlayer.isMoveUp());
        assertEquals(true, floatingPlayer.isMoveDown());
    }

    @Test
    public void testMoveDirectionS() throws InvocationTargetException, IllegalAccessException {
        platformUnderTest.getKeys().add(downKey);
        ArrayList<BaseEntity> c = new ArrayList<>();
        c.add(floatingPlayer);

        updateDirectionMethod.invoke(gameLoopUnderTest, floatingPlayer);
        updateMethod.invoke(gameLoopUnderTest, c);
        assertEquals(false, platformUnderTest.getKeys().isPressed(leftKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(rightKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(upKey));
        assertEquals(true, platformUnderTest.getKeys().isPressed(downKey));

        assertEquals(false, floatingPlayer.isMoveLeft());
        assertEquals(false, floatingPlayer.isMoveRight());
        assertEquals(false, floatingPlayer.isMoveUp());
        assertEquals(true, floatingPlayer.isMoveDown());
    }

    @Test
    public void testMoveDirectionSW() throws InvocationTargetException, IllegalAccessException {
        platformUnderTest.getKeys().add(leftKey);
        platformUnderTest.getKeys().add(downKey);
        ArrayList<BaseEntity> c = new ArrayList<>();
        c.add(floatingPlayer);

        updateDirectionMethod.invoke(gameLoopUnderTest, floatingPlayer);
        updateMethod.invoke(gameLoopUnderTest, c);
        assertEquals(true, platformUnderTest.getKeys().isPressed(leftKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(rightKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(upKey));
        assertEquals(true, platformUnderTest.getKeys().isPressed(downKey));

        assertEquals(true, floatingPlayer.isMoveLeft());
        assertEquals(false, floatingPlayer.isMoveRight());
        assertEquals(false, floatingPlayer.isMoveUp());
        assertEquals(true, floatingPlayer.isMoveDown());
    }
}
