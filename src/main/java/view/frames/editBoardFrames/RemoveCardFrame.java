package view.frames.editBoardFrames;

import annotations.ClassAnnotation;
import controller.exceptions.KanbanObjectNotFoundException;
import controller.exceptions.UnknownKanbanObjectException;
import javafx.util.Pair;
import model.Change;
import model.ChangeLog;
import view.boardComponents.BoardPanel;
import view.boardComponents.KanbanCardButton;
import view.boardComponents.KanbanColumn;
import view.frames.KanbanCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

import static controller.OptionPanes.*;

@ClassAnnotation(
        classAuthors = {"Jeanne", "Petra"},
        creationDate = "29/11/2019",
        lastEdit = "08/12/2019"
)
public class RemoveCardFrame extends RemoveColumnFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel chooseCardLabel;
    private JComboBox<String> chooseCardBox;
    private JButton columnChosenButton;


    public RemoveCardFrame(BoardPanel currentPanel) {

        super(currentPanel);
        submit.addActionListener(this);
        addCardBox();
    }

    private void addCardBox() {

        chooseCardBox = new JComboBox<String>();
        chooseCardLabel = new JLabel("Choose a card:");
        columnChosenButton = new JButton("Verify");
        columnChosenButton.addActionListener(e->createCardList());

        container.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                ("Removing a card from the board")));

        Map<JComponent, Pair<Integer,Integer>> map = Map.ofEntries(
                new AbstractMap.SimpleEntry<JComponent, Pair<Integer,Integer>>(chooseCardLabel, new Pair<>(0,2)),
                new AbstractMap.SimpleEntry<JComponent, Pair<Integer,Integer>>(chooseCardBox, new Pair<>(1,2)),
                new AbstractMap.SimpleEntry<JComponent, Pair<Integer,Integer>>(columnChosenButton, new Pair<>(3,0))
        );

        placeComponents(map, 3);
    }

    private KanbanColumn getSelectedColumn() {

        KanbanColumn toReturn = null;

        for (KanbanColumn col : currentPanel.getColumns()) {
            if (col.getColumnTitle().equals(columnsBox.getSelectedItem())) {
                toReturn = col;
                break;
            }
        }

        return toReturn;
    }



    private void createCardList() {

        if (getSelectedColumn() == null) {
            missingComponentError("Column");
            chooseCardBox.setEnabled(false);
            return;
        }

        ArrayList<KanbanCardButton> cards = getSelectedColumn().getCards();

        for (KanbanCardButton cardButton : cards) {
            chooseCardBox.addItem(cardButton.getCardButtonTitle());
        }

        if (cards.isEmpty()==true) columnsBox.setEnabled(false);

        revalidate();
    }


    /**
     * Method triggering the removal of a card.
     * @param event
     */
    public void actionPerformed(ActionEvent event) {

        if (event.getSource() == submit) {

            if(!columnsBox.isEnabled() || !chooseCardBox.isEnabled() || !submit.isValid()) {
                return;
            }

            KanbanColumn col = getSelectedColumn();
            String cardName = String.valueOf(chooseCardBox.getSelectedItem());

            try {
                KanbanCardButton card = col.getCardByTitle(cardName);
                col.removeCard(card);
                Change change = new Change(Change.ChangeType.REMOVE, cardName, card);
                ChangeLog.getInstance().addChange(change);
            }
            catch (KanbanObjectNotFoundException e) {
                System.out.println("Error: Card not found");
                e.printStackTrace();
                return;
            }
            catch (UnknownKanbanObjectException u){
                System.out.println("Failed to log.");
                u.printStackTrace();
            }

            dispose();
        }

        else {
            commandNotFoundError();
        }

    }

}
