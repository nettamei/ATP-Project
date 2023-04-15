package algorithms.mazeGenerators;
import java.util.*;
import java.util.Random;

/**
 * Created by Gil & Netta on 08/04/2023.
 * MyMazeGenerator, extends AMazeGenerator
 * based on Prim's Randomly maze generator algorithm
 * 1. create maze full of walls
 * 2. pick random start position on the border
 * 3. add to list all its unvisited(walls) neighbours
 * 4. while list isnt empty randomly pick from the list a position
 * 4.1 if position has only one passage neighbour
 * 4.1.1 add to list all its unvisited neighbours
 * 4.1.2 turn the picked position to a passage
 * 4.2 remove picked position from list
 */

public class MyMazeGenerator extends AMazeGenerator{

    public Maze generate (int rows, int columns){

        if (rows == 0 || columns == 0)
            return null;

        ArrayList<Position> neighborList = new ArrayList<>();
        int [][] mazeMatrix = new int[rows][columns];

        for(int i=0;i<rows;i++) {
            Arrays.fill(mazeMatrix[i], 1);
        }
        mazeMatrix[0][0] = 0;
        Position startPosition = new Position(0,0);
        Position endPosition = new Position(rows, columns);

        //add to the neighbors list, the neighbors of the start point

        if (mazeMatrix.length > 1)
            neighborList.add(new Position(1,0));

        if (mazeMatrix[0].length > 1)
             neighborList.add(new Position(0,1));


        //while the neighbor list is not empty, choose a random position and check if only one of it's neighbors is a passage
        while(!neighborList.isEmpty()){
            Random rand = new Random();
            int randomIndex = rand.nextInt(0,neighborList.size());
            Position new_pos = neighborList.get(randomIndex);
            //.out.println("Chosen neighbor row index: " + new_pos.getRowIndex() + " column index " + new_pos.getColumnIndex());
            //count how many neighbors are passages
            int counterPassages = 0;

            //same column, row below
            try{
//                System.out.println("mazeMatrix[new_pos.getRowIndex()-1][new_pos.getColumnIndex()]" +
//                        mazeMatrix[new_pos.getRowIndex()-1][new_pos.getColumnIndex()]);

                if(mazeMatrix[new_pos.getRowIndex()-1][new_pos.getColumnIndex()] == 0){
                    counterPassages++;
                }
            }
            catch (Exception e){
                System.out.print("");
            }

            //same column, row above

            try{
//                System.out.println("mazeMatrix[new_pos.getRowIndex()+1][new_pos.getColumnIndex()]" +
//                        mazeMatrix[new_pos.getRowIndex()+1][new_pos.getColumnIndex()]);

                if(mazeMatrix[new_pos.getRowIndex()+1][new_pos.getColumnIndex()] == 0){
                    counterPassages++;
                }
            }
            catch (Exception e){
                System.out.print("");
            }

            //same row, column above
            try{
                if(mazeMatrix[new_pos.getRowIndex()][new_pos.getColumnIndex() + 1] == 0){
                    counterPassages++;
                }
            }
            catch (Exception e){
                System.out.print("");
            }

            //same row, column below
            try{
                if(mazeMatrix[new_pos.getRowIndex()][new_pos.getColumnIndex()-1] == 0){
                    counterPassages++;
                }
            }
            catch (Exception e){
                System.out.print("");
            }
            if(counterPassages==1){
                mazeMatrix[new_pos.getRowIndex()][new_pos.getColumnIndex()] = 0;
                //enter all the walls neighbors of new_pos to the neighbors list if they don't exist there
                try{
                    if(mazeMatrix[new_pos.getRowIndex()-1][new_pos.getColumnIndex()] == 1){
                        neighborList = addNeighborsToList(neighborList, new_pos.getRowIndex()-1, new_pos.getColumnIndex());
                    }
                }
                catch (Exception e){
                    System.out.print("");
                }

                //same column, row above

                try{
                    if(mazeMatrix[new_pos.getRowIndex()+1][new_pos.getColumnIndex()] == 1){
                        neighborList = addNeighborsToList(neighborList, new_pos.getRowIndex()+1, new_pos.getColumnIndex());
                    }
                }
                catch (Exception e){
                    System.out.print("");
                }

                //same row, column above
                try{
                    if(mazeMatrix[new_pos.getRowIndex()][new_pos.getColumnIndex() + 1] == 1){
                        neighborList = addNeighborsToList(neighborList, new_pos.getRowIndex(), new_pos.getColumnIndex()+1);
                    }
                }
                catch (Exception e){
                    System.out.print("");
                }

                //same row, column below
                try{
                    if(mazeMatrix[new_pos.getRowIndex()][new_pos.getColumnIndex()-1] == 1){
                        neighborList = addNeighborsToList(neighborList,  new_pos.getRowIndex(), new_pos.getColumnIndex()-1);
                    }
                }
                catch (Exception e){
                    System.out.print("");
                }
            }
            neighborList.remove(randomIndex);
        }
        mazeMatrix[rows-1][columns-1]=0;
        try{
            mazeMatrix[rows-2][columns-1] = 0;
        }
        catch (Exception e){
            System.out.print("");
        }
        try{
            mazeMatrix[rows-1][columns-2] = 0;
        }
        catch (Exception e){
            System.out.print("");
        }

        return new Maze(mazeMatrix, startPosition,endPosition);
    }
    //function to check if a neighbor (a position) already exists in the neighbor list
    public ArrayList<Position> addNeighborsToList(ArrayList<Position> neighborList, int x, int y){
        if(neighborList.isEmpty()){
            neighborList.add(new Position(x, y));
        }
        else{
            int flag = 0;
            for(int i=0;i<neighborList.size();i++){
                if ((neighborList.get(i).getRowIndex() == x && neighborList.get(i).getColumnIndex() == y)) {
                    flag = 1;
                    break;
                }
            }
            if(flag == 0){
                neighborList.add(new Position(x, y));
            }
        }
        return neighborList;
    }
}
