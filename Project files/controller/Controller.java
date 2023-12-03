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

    private void writeTheseProgramStatesToLogFile(List<ProgramState> listOfPrograms)
    {
        listOfPrograms.forEach(programState -> {
            try { //WHY DO I HAVE TO DO THIS?
                repository.writeProgramStateToLogFile(programState);
            } catch (MyException error) {
                System.out.println(error.getMessage());
                System.exit(1);
            }
        });
    }

    private void executeOneStepForEachProgram(List<ProgramState> listOfPrograms) {
        //writeTheseProgramStatesToLogFile(listOfPrograms);

        List<Callable<ProgramState>> callList = listOfPrograms.stream()
                .map((ProgramState program) -> (Callable<ProgramState>) (program::executeOneStep))
                .toList();

        List<ProgramState> newListOfPrograms = new ArrayList<>();
        try {
            newListOfPrograms = executorService.invokeAll(callList).stream().
                    map(future ->
                    {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException error) {
                            System.out.println(error.getMessage());
                            System.exit(1);
                            return null;
                        }
                    }).filter(programState -> programState != null).toList();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        listOfPrograms.addAll(newListOfPrograms);
        repository.setListOfPrograms(listOfPrograms);
        writeTheseProgramStatesToLogFile(listOfPrograms);
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
