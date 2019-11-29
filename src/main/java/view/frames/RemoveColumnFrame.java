package view.frames;
import annotations.ClassAnnotation;
import controller.ColumnRole;
import view.boardComponents.BoardPanel;
import view.boardComponents.KanbanCardButton;
import view.boardComponents.KanbanColumn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

@ClassAnnotation(
        classAuthors = {"Jeanne"},
        creationDate = "29/11/2019",
        lastEdit = "29/11/2019"
)

public class RemoveColumnFrame extends EditorFrame implements ActionListener {

    protected JLabel chooseColumnLabel;
    protected  JComboBox<String> columnsBox;
    protected String type = "column";

    public RemoveColumnFrame(BoardPanel currentPanel) {

        super(currentPanel);
        chooseColumnLabel = new JLabel("Choose a column:");
        columnsBox = createColumnsList();
        submit.addActionListener(this);
        this.setUpFrame();
    }

    private void setUpFrame() {
        container.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                ("Removing a column from the board")));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.WEST;

        constraints.gridx = 0;
        constraints.gridy = 0;
        container.add(chooseColumnLabel, constraints);
        constraints.gridx = 1;
        container.add(columnsBox, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        container.add(submit, constraints);
        constraints.anchor = GridBagConstraints.EAST;
        container.add(cancel,constraints);

        pack();
    }


    public void actionPerformed(ActionEvent event) {

        if (event.getSource() == submit) {

            if(!columnsBox.isEnabled()) {
                noColumnSelectedError();
                return;
            }

            String columnName = String.valueOf(columnsBox.getSelectedItem());
            KanbanColumn columnToRemove = null;

            for (KanbanColumn col : currentPanel.getColumns()) {
                if (col.getColumnTitle().equals(columnName)) {
                    columnToRemove = col;
                    break;
                }
            }

            currentPanel.removeColumn(columnToRemove);
            dispose();
        }

        else {
            showError();
        }

    }


}
