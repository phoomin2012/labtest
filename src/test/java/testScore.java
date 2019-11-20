import de.saxsys.javafx.test.JfxRunner;
import javafx.scene.control.Label;
import model.Score;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;

@RunWith(JfxRunner.class)
public class testScore {

    @Test
    public void whenSetPoint() {
        Score score = new Score(0,0);
        score.setPoint(10);

        assertEquals("10", score.getPoint());
    }
}
