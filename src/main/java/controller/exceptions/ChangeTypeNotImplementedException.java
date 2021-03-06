package controller.exceptions;
import annotations.ClassAnnotation;
import model.Change;

@ClassAnnotation(
        classAuthors = "Petra",
        classEditors = "Jeanne",
        creationDate = "22/11/2019",
        lastEdit = "08/12/2019"
)

/**
 *  Exception to be thrown when trying to print a Kanban Object change in the log,
 *  when the object type doesn't have a 'pretty print' string implemented
 */
public class ChangeTypeNotImplementedException extends Exception {

    //class name of change type enum
    private String changeType;
    private String objectType = null;

    /**
     * Default constructor
     */
    public ChangeTypeNotImplementedException(){
        super();
    }

    /**
     * Constructor taking enum representing type of change
     * @param changeType
     */
    public ChangeTypeNotImplementedException(Change.ChangeType changeType){
        super();
        this.changeType = changeType.name(); //gets the name of the enum
    }

    public ChangeTypeNotImplementedException(Change.ChangeType changeType, Object object){
        super();
        this.changeType = changeType.name(); //gets the name of the enum
        this.objectType = object.getClass().toString();
    }


}
