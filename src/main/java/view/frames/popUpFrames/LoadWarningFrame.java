package view.frames.popUpFrames;

import annotations.ClassAnnotation;
import view.containers.OpenFileChooser;

import javax.swing.*;
import java.awt.*;

@ClassAnnotation(
        classAuthors = { "Jeanne" },
        creationDate = "09/12/2019",
        lastEdit = "10/12/2019"
)
public class LoadWarningFrame extends PopUpFrames {

    /**
     * Constructor - help frame set up.
     */
    public LoadWarningFrame() {

        super("Loading Board Warning");
        buttonPanel.add(createSubmitButton());

    }

    /**
     * Help text with instructions on how to use the Kanban board app in a
     * JTextArea. (Non editable)
     */
    @Override
    protected JTextArea createText() {

        JTextArea warningText = new JTextArea();
        String text = "WARNING.\n\nAre you sure you want to load an existing board?\n\n"
                + "If you haven't saved your current progress, the board you're currently working on"
                + " will be erased.\nThis action cannot be undone, and the data cannot be retrieved.\n\n"
                + "Do you want to proceed with this action?";

        warningText.setText(text);
        warningText.setMargin(new Insets(10, 10, 10, 10));
        warningText.setEditable(false);
        return warningText;
    }

    private JButton createSubmitButton() {

        JButton proceed = new JButton("Proceed");
        proceed.addActionListener(e->{
            new OpenFileChooser();
            if(Frame.getFrames().length>2)dispose();
        });
        proceed.setBorderPainted(false);

        return proceed;
    }

}
