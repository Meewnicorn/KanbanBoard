package view.frames;

import annotations.ClassAnnotation;
import controller.exceptions.UnknownKanbanObjectException;
import model.Change;
import model.ChangeLog;
import view.boardComponents.BoardPanel;
import view.boardComponents.EditorPanel;
import view.boardComponents.KanbanCardButton;
import view.boardComponents.KanbanColumn;
import view.boardComponents.KanbanMenu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;


@ClassAnnotation(
        classAuthors = "Jeanne",
        classEditors = "Jeanne, Petra",
        creationDate = "08/11/2019",
        lastEdit = "08/12/2019"
)
/**
 * This class sets up the application frame.
 * It contains the main frame (Board Panel) containing all the columns and cards,
 * a menu bar, and an editor panel to quickly and easily access user commands.
 */
public class KanbanBoard extends JFrame {

	private static final long serialVersionUID = 1L;

	// KanbanBoard frame components
    private static BoardPanel board;
    private EditorPanel editorPanel;

    private static final int WIDTH = 1100;
    private static final int HEIGHT = 800;

    public KanbanBoard(String title) {

        // Set up the JFrame
        setName("KanbanBoardFrame");
        this.setTitle(title);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // track change
        try {
            Change change = new Change(board, Change.ChangeType.ADD, title, this);
            ChangeLog.getInstance().addChange(change);
        } catch (UnknownKanbanObjectException u){
            System.out.println("Failed to log.");
            u.printStackTrace();
        }

        initialseKanbanBoard();
        setVisible(true);

    }


    public KanbanBoard() {

        // Set up the JFrame
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // track change
        try {
            Change change = new Change(board, Change.ChangeType.ADD, "", this);
            ChangeLog.getInstance().addChange(change);
        } catch (UnknownKanbanObjectException u){
            System.out.println("Failed to log.");
            u.printStackTrace();
        }

        initialseKanbanBoard();
        setVisible(true);

    }

    /**
     * Create each components for the frame : Board panel, Editor panel, Kanban Menu bar
     */
    private void initialseKanbanBoard() {

        // Create board panel
        board = new BoardPanel(this);
        add(board);

        //board.setPreferredSize(new Dimension(WIDTH/4*3, HEIGHT));
        JScrollPane boardScroll = new JScrollPane(board);
        boardScroll.setBorder(new EmptyBorder(0,0,0,0));
        add(boardScroll);

        // Create the editor panel
        editorPanel = new EditorPanel(board);
        editorPanel.setPreferredSize(new Dimension(WIDTH/4,HEIGHT));
        add(editorPanel, BorderLayout.EAST);

        // Create empty box on the west border
        JPanel westBox = new JPanel();
        westBox.setBackground(Color.black);
        westBox.setPreferredSize(new Dimension(10, HEIGHT));
        add(westBox, BorderLayout.WEST);

        // Create empty box on the north border
        JPanel northBox = new JPanel();
        northBox.setBackground(Color.black);
        northBox.setPreferredSize(new Dimension(WIDTH, 10));
        add(northBox, BorderLayout.NORTH);

        // Create the menu bar
        createMenuBar(this);

    }

    /** Sets up the Kanban menu */
    public void createMenuBar(KanbanBoard currentBoard) {
        KanbanMenu menu = new KanbanMenu(currentBoard);
        setJMenuBar(menu);
    }

    /** @return child component BoardPanel */
    public BoardPanel getBoard(){
        return board;
    }


    /**
     * Change the associated board to a new one
     * @param newBoard
     */
    public void setBoard(BoardPanel newBoard){
        board = newBoard;
        board.setVisible(true);
        revalidate();
        repaint();
    }

    /** @return child component EditorPanel*/
    public EditorPanel getEditorPanel(){
        return editorPanel;
    }

    /** @return name of the board */
    public String getBoardName(){ return this.getTitle(); }


    /**
     * Change the name of the board (including frame title)
     * @param newName
     */
    public void setBoardName(String newName){
        setTitle(newName);
        revalidate();
    }

    /**
     * Open an existing saved Kanban Board
     * @param newBoard
     */
    public static void openBoard(BoardPanel newBoard) {

        ArrayList<KanbanColumn> cols = newBoard.getColumns();

        for(KanbanColumn col : cols) {
            KanbanColumn newBoardCol = new KanbanColumn(col.getColumnTitle(), col.getRole());
            ArrayList<KanbanCardButton> cards = col.getCards();
            if (cards.size() != 0) {
                for (KanbanCardButton card : cards) {
                    KanbanCardButton newCardButton = new KanbanCardButton(card);
                    newBoardCol.addCard(newCardButton);
                }
            }
     	   board.addColumn(newBoardCol);
        }
    }




}
