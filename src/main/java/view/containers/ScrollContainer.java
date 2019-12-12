package view.containers;

import annotations.ClassAnnotation;

import javax.swing.*;
import java.awt.*;

@ClassAnnotation(
        classAuthors = {"Jeanne"},
        creationDate = "21/11/2019",
        lastEdit = "12/12/2019"
)
public class ScrollContainer extends JScrollPane {

    private JPanel container;


    public ScrollContainer(){
        container = new JPanel();
        initialiseScrollContainer();
    }


    /**
     * Create ScrollContainer and its container
     */
    private void initialiseScrollContainer() {
        setOpaque(false);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        container.setOpaque(false);
        getViewport().setOpaque(false);
        getViewport().add(container,null);


    }

    public void add(JComponent button) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(button);
    }

    public void remove(JComponent button){
        container.remove(button);
    }

    public int getActivityButtons(){
        return container.getComponentCount();
    }
}
