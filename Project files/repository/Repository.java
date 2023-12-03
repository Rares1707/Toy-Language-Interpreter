package repository;

import model.MyException;
import model.ProgramState;
import model.myADTs.*;
import model.statements.IStatement;
import model.values.IValue;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{

    private List<ProgramState> listOfPrograms;

    private String logFilePath;

    private int programIndex;

    public Repository(IStatement program, String logFilePath) throws MyException {
        listOfPrograms = new ArrayList<ProgramState>();
        addProgram(program);
        this.logFilePath = logFilePath;
        programIndex = 0;
        clearFile();
    }

    private void clearFile() throws MyException { //doesn't work
        try {
            RandomAccessFile file = new RandomAccessFile(logFilePath, "rw");
            file.setLength(0);
        }
        catch (IOException exception)
        {
            throw new MyException(exception.getMessage());
        }
    }

//    @Override
//    public ProgramState getCurrentProgramState() {
//        return listOfPrograms.get(programIndex);
//    }

    @Override
    public void setIndexOfProgram(int index) throws MyException {
        if (programIndex < 0 || programIndex >= listOfPrograms.size())
            throw new MyException("index out of bounds");
        programIndex = index;
    }

    @Override
    public int size() {
        return listOfPrograms.size();
    }

    @Override
    public void addProgram(IStatement program) {
        MyIStack<IStatement> executionStack = new MyStack<IStatement>();
        MyIDictionary<String, IValue> symbolTable = new MyDictionary<String, IValue>();
        MyIList<IValue> outputOfProgram = new MyList<IValue>();
        FileTable fileTable = new FileTable();
        MyIHeap heap = new MyHeap();

        ProgramState programState = new ProgramState(executionStack, symbolTable, outputOfProgram,
                program, fileTable, heap);
        listOfPrograms.add(programState);
    }

    @Override
    public void writeProgramStateToLogFile(ProgramState programState) throws MyException {
        String stateToBePrinted = programState.toString() + '\n';
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.println(stateToBePrinted);
            logFile.close();
        }
        catch (IOException error)
        {
            throw new MyException(error.getMessage());
        }
    }

    @Override
    public List<ProgramState> getListOfPrograms() {
        return listOfPrograms;
    }

    @Override
    public void setListOfPrograms(List<ProgramState> listOfPrograms) {
        this.listOfPrograms = listOfPrograms;
    }
}
