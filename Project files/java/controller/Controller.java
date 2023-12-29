package controller;

import model.MyException;
import model.ProgramState;
import model.myADTs.MyIHeap;
import model.values.IValue;
import model.values.ReferenceValue;
import repository.IRepository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


public class Controller {
    IRepository repository;
    boolean displayExecution;
    ExecutorService executorService;

    public Controller(IRepository repository) {
        this.repository = repository;
        displayExecution = true;
    }

    public void wrapperForExecuteOneStepForEachProgram() throws MyException {
        executorService = Executors.newFixedThreadPool(2);
        List<ProgramState> listOfPrograms = removeProgramsWhichHaveFinishedExecution(repository.getListOfPrograms());

        ProgramState state = listOfPrograms.get(0);
        state.getHeap().setContent( //the program states share the same heap, so this will affect all of them
                garbageCollector(
                        getAllAddresses(
                                listOfPrograms.stream().map(programState -> programState.getSymbolTable().getValues()).toList(),
                                state.getHeap().getContent()
                        ),
                        state.getHeap().getContent()
                )
        );
        executeOneStepForEachProgram(listOfPrograms);
        listOfPrograms = removeProgramsWhichHaveFinishedExecution(repository.getListOfPrograms());
        executorService.shutdownNow();
        repository.setListOfPrograms(listOfPrograms);
    }

    public List<ProgramState> getListOfPrograms()
    {
        return repository.getListOfPrograms();
    }
    public void completeExecutionOfTheProgram() throws MyException {
//        repository.setIndexOfProgram(programIndex);
//        ProgramState currentStateOfProgram = repository.getCurrentProgramState();
//        currentStateOfProgram.resetProgramState();
//        MyIStack<IStatement> stack = currentStateOfProgram.getStack();
//
//        while (!stack.isEmpty()) {
//            if (displayExecution) {
//                repository.writeProgramStateToLogFile();
//            }
//            executeOneStep(currentStateOfProgram);
//            currentStateOfProgram.getHeap().setContent(garbageCollector(
//                    getAllAddresses(currentStateOfProgram.getSymbolTable().getValues(),
//                            currentStateOfProgram.getHeap().getContent()),
//                    currentStateOfProgram.getHeap().getContent())
//            );
//        }
//        repository.writeProgramStateToLogFile();

        executorService = Executors.newFixedThreadPool(2);
        List<ProgramState> listOfPrograms = removeProgramsWhichHaveFinishedExecution(repository.getListOfPrograms());
        writeTheseProgramStatesToLogFile(listOfPrograms);
        while (!listOfPrograms.isEmpty()) {
            List<Collection<IValue>> allSymbolTables = new ArrayList<>();
            for (ProgramState program : listOfPrograms) {
                allSymbolTables.add(program.getSymbolTable().getValues());
            }

            MyIHeap heap = listOfPrograms.get(0).getHeap();
            heap.setContent(garbageCollector(
                    getAllAddresses(allSymbolTables, heap.getContent()),
                    heap.getContent())
            );

            executeOneStepForEachProgram(listOfPrograms);
            listOfPrograms = removeProgramsWhichHaveFinishedExecution(repository.getListOfPrograms());
        }
        executorService.shutdownNow();
        repository.setListOfPrograms(listOfPrograms);
    }

    private void writeTheseProgramStatesToLogFile(List<ProgramState> listOfPrograms) {
        listOfPrograms.forEach(programState -> {
            try {
                repository.writeProgramStateToLogFile(programState);
            } catch (MyException error) {
                System.out.println(error.getMessage());
                System.exit(1);
            }
        });
    }

    private void executeOneStepForEachProgram(List<ProgramState> listOfPrograms) throws MyException {
        //writeTheseProgramStatesToLogFile(listOfPrograms);

        List<Callable<ProgramState>> callList = listOfPrograms.stream()
                .map((ProgramState program) -> (Callable<ProgramState>) (program::executeOneStep))
                .toList();
        List<MyException> exceptions = new ArrayList<>();
        List<ProgramState> newListOfPrograms = new ArrayList<>();
        try {
            newListOfPrograms = executorService.invokeAll(callList).stream().
                    map(future ->
                    {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException error) {
                            MyException myException = new MyException(error.getMessage());
                            exceptions.add(myException);
                            return null;
                        }
                    }).filter(programState -> programState != null).toList();
        } catch (InterruptedException error) {
            MyException myException = new MyException(error.getMessage());
            exceptions.add(myException);
        }

        listOfPrograms.addAll(newListOfPrograms);
        repository.setListOfPrograms(listOfPrograms);
        writeTheseProgramStatesToLogFile(listOfPrograms);
        for (MyException error : exceptions)
            throw error;
    }

    Map<Integer, IValue> garbageCollector(List<Integer> addressesFromSymbolTables, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(entry -> addressesFromSymbolTables.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAllAddresses(List<Collection<IValue>> allSymbolTables, Map<Integer, IValue> heap) {
        List<Integer> listOfAddresses = new LinkedList<Integer>();
        allSymbolTables.forEach(symbolTable -> symbolTable.stream().
                filter(value -> value instanceof ReferenceValue).
                forEach(value ->
                {
                    while (value instanceof ReferenceValue) {
                        listOfAddresses.add(((ReferenceValue) value).getAddress());
                        value = heap.get(((ReferenceValue) value).getAddress());
                    }
                }));
        return listOfAddresses;
    }

    public int repositorySize() {
        return repository.size();
    }

    List<ProgramState> removeProgramsWhichHaveFinishedExecution(List<ProgramState> listOfPrograms) {
        return listOfPrograms.stream().
                filter(p -> !p.hasFinishedExecution()).
                collect(Collectors.toList());
    }

}
