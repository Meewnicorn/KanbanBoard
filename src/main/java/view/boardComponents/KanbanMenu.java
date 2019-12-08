package view.boardComponents;

import annotations.ClassAnnotation;
import controller.Load;
import controller.Save;
import view.KanbanBoard;
import view.frames.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.awt.event.*;

/**
 * This class is responsible for the creation of the menu
 * bar at the top of the board.
 */
@ClassAnnotation(
        classAuthors = {"Jeanne"},
        creationDate = "13/11/2019",
        lastEdit = "22/11/2019"
)
public class KanbanMenu extends JMenuBar {


	private static final long serialVersionUID = 1L;
	KanbanBoard currentBoard;

    public KanbanMenu(KanbanBoard currentBoard) {

        super();
        this.currentBoard = currentBoard;

        // Aesthetics
        setBackground(new java.awt.Color(10, 15, 67));
        setBorder(new EmptyBorder(0,0,0,0));

        // Create all JMenuItems for the Kanban Menu
        add(createFileMenu());
        add(createEditMenu());
        add(createKanbanMenu());
        add(createViewMenu());
        add(createHelpMenu());
    }

    /** Set up menu colour */
    public JMenu makeMenu(String menuName){
        JMenu menu = new JMenu(menuName);
        menu.setForeground(new java.awt.Color(255, 255, 255));
        return menu;
    }

    public JMenuItem makeItem(String menuItemName, ImageIcon icon){
        JMenuItem menu = new JMenuItem(menuItemName, icon);
        menu.setForeground(new java.awt.Color(228, 228, 228));
        menu.setBackground(new java.awt.Color(33, 42, 101));
        return menu;
    }

    /**
     * File menu : New board, Open board, Save board
     */
    private JMenu createFileMenu(){
        // Menu items for JMenu 'file'
        JMenu file = makeMenu("File");

        // Icons
        ImageIcon newIcon = new ImageIcon("src/images/newdocument.jpg");
        ImageIcon openIcon = new ImageIcon("src/images/open-folder-with-document.jpg");
        ImageIcon saveIcon = new ImageIcon("src/images/save.jpg");

        JMenuItem newBoard = new JMenuItem("New", newIcon);
        newBoard.addActionListener(e->new CreateFrame().setVisible(true));
        JMenuItem openBoard = new JMenuItem("Open", openIcon);
        openBoard.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
            	BoardPanel newBoard = Load.loadBoard();

            	ArrayList<KanbanColumn> cols = newBoard.getColumns();
                
                for(int i = 0; i < cols.size(); i++) {
             	   KanbanColumn col = cols.get(i);
             	   KanbanColumn boardCol = new KanbanColumn(col.getColumnTitle(), col.getRole());
             	   ArrayList<KanbanCardButton> cards = cols.get(i).getCards();
             	   if(cards.size() != 0) {
             		  for(int j = 0; j < cards.size(); j++) {
                		   //KanbanCardButton cardButton = cards.get(j);
                		   
                		  KanbanCardButton cardButton = new KanbanCardButton(cards.get(j));

                		   boardCol.addCard(cardButton);
                	   }
             	   }
             	   currentBoard.getBoard().addColumn(boardCol);
                }

            }
        });


        JMenuItem saveBoard = new JMenuItem("Save", saveIcon);
        saveBoard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                BoardPanel board = currentBoard.getBoard();
                Save.saveBoard(board);
            }
        });


        file.add(newBoard);
        file.add(new JSeparator(SwingConstants.HORIZONTAL));
        file.add(openBoard);
        file.add(new JSeparator(SwingConstants.HORIZONTAL));
        file.add(saveBoard);

        return file;
    }

    /**
     * Edit Menu : Add (column, card), Delete (column, card), Clear board
     * @return
     */
    private JMenu createEditMenu() {

        // Menu items for JMenu 'Edit'
        JMenu edit = makeMenu("Edit");

        // Icons
        ImageIcon addIcon = new ImageIcon("src/images/plus-sign.jpg");
        ImageIcon deleteIcon = new ImageIcon("src/images/delete.jpg");
        ImageIcon clearIcon = new ImageIcon("src/images/delete-symbol.jpg");

        // Add component JMenu
        JMenu insertMenu = new JMenu("Add");
        insertMenu.setIcon(addIcon);
            // Add column
        JMenuItem insertColumn = new JMenuItem("Insert new column");
        insertColumn.addActionListener(e->new AddColumnFrame(currentBoard.getBoard()).setVisible(true));
            // Add card
        JMenuItem insertCard = new JMenuItem("Insert new card");
        insertCard.addActionListener(e->new AddCardFrame(currentBoard.getBoard()).setVisible(true));

        // Delete component JMenu
        JMenu deleteMenu = new JMenu("Delete");
        deleteMenu.setIcon(deleteIcon);
            // Delete column
        JMenuItem deleteColumn = new JMenuItem("Remove a column");
        deleteColumn.addActionListener(e->new RemoveColumnFrame(currentBoard.getBoard()).setVisible(true));
            // Delete card
        JMenuItem deleteCard = new JMenuItem("Remove a card");
        deleteCard.addActionListener(e->new RemoveCardFrame(currentBoard.getBoard()).setVisible(true));

        insertMenu.add(insertColumn);
        insertMenu.add(insertCard);
        deleteMenu.add(deleteColumn);
        deleteMenu.add(deleteCard);

        // Clear board
        JMenuItem clearBoard = new JMenuItem("Clear board", clearIcon);
        clearBoard.addActionListener(e->currentBoard.getBoard().clearBoard());

        edit.add(insertMenu);
        edit.add(new JSeparator(SwingConstants.HORIZONTAL));
        edit.add(deleteMenu);
        edit.add(new JSeparator(SwingConstants.HORIZONTAL));
        edit.add(clearBoard);

        return edit;
    }

    /**
     * Kan Menu : Team info, Settings, Exit Kanban
     */
    private JMenu createKanbanMenu() {

        // Menu items for JMenu 'kanban'
        JMenu kanban = makeMenu("Kanban");

        JMenuItem team = new JMenuItem("Team");
        String teamMsg = "Jeanne Beyazian,\nTrey Collier,\nNathan Kuansataporn,\nAli Mohamed,\nand Petra Scutaru.";
        team.addActionListener(e-> new JOptionPane()
                .showMessageDialog(null, teamMsg, "Our Team (Indigo) !",
                JOptionPane.INFORMATION_MESSAGE));

        JMenuItem settings = new JMenuItem("Settings");
        
        JMenuItem exitBoard = new JMenuItem("Exit Kanban");
        exitBoard.addActionListener(e->System.exit(0));

        kanban.add(team);
        kanban.add(settings);
        kanban.add(new JSeparator(SwingConstants.HORIZONTAL));
        kanban.add(exitBoard);

        return kanban;
    }

    /**
     * View Menu : show or hide editor Panel
     */
    private JMenu createViewMenu() {

        // JMenu items for View menu
        JMenu view = makeMenu("View");
        JCheckBoxMenuItem showBar = new JCheckBoxMenuItem("Show Editor Panel");
        showBar.setSelected(true);

        showBar.addItemListener((e)->{
            if (e.getStateChange() == ItemEvent.SELECTED) currentBoard.getEditorPanel().setVisible(true);
            else currentBoard.getEditorPanel().setVisible(false);
        });

        view.add(showBar);

        return view;
    }


    /**
     * Help Menu : displays help frame
     * @return
     */
    private JMenu createHelpMenu() {

        // JMenu help
        JMenu help = makeMenu("Help");
        //help.addActionListener(e->new HelpFrame().setVisible(true));
        ImageIcon helpIcon = new ImageIcon("src/images/help.png");
        help.setIcon(helpIcon);

        return help;
    }


}
