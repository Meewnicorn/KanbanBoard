package view;
import org.junit.Test;
import com.athaydes.automaton.Swinger;

import javax.swing.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


public class KanbanCardTest {

    @Test
    public void testApp(){
        JFrame frame = new JFrame();
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel pane = new JPanel();
        pane.add(new KanbanCard("Name", "Description", 50));
        frame.getContentPane().add(pane);
    }
}