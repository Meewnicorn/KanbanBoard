package view;

import controller.ColumnRole;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * This class creates the panel that will contain the columns
 * The user should be able to add columns.
 * The columns will welcome the Kanban cards.
 */

public class BoardPanel extends JPanel {

    // TODO (J) : figure out how to remove the west border layout empty space

    public BoardPanel() {
        super();
        initialiseBoard();
    }

    public void initialiseBoard() {

        // TESTING PURPOSE : test columns
        KanbanColumn first = new KanbanColumn("First", ColumnRole.BACKLOG);
        KanbanColumn second = new KanbanColumn("Second", ColumnRole.COMPLETED);
        KanbanColumn third = new KanbanColumn("Third", ColumnRole.IN_PROGRESS);

        // Create vertical separation with JSeparator
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(Box.createHorizontalGlue());

        addColumn(first);
        addColumn(second);
        addColumn(third);

    }


    // TODO : Implementation of the following methods

    public void addColumn(KanbanColumn column) {
        add(column);
        add(Box.createRigidArea(new Dimension(10, 0)));

        //add(new JSeparator(SwingConstants.VERTICAL));
    }

    public void removeColumn() {}

    public void reset() {}
}