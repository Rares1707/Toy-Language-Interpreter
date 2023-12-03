package repository;

import model.MyException;
import model.ProgramState;
import model.statements.IStatement;

import java.util.List;

public interface IRepository {
    //ProgramState getCurrentProgramState() throws MyException;
    void setIndexOfProgram(int index) throws MyException;

    int size();

    void addProgram(IStatement program);

    void writeProgramStateToLogFile(ProgramState programState) throws MyException;

    List<ProgramState> getListOfPrograms();

    void setListOfPrograms(List<ProgramState> listOfPrograms);
}
