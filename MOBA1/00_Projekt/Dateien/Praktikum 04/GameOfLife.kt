import java.util.Arrays
import java.util.Random

/**
 * You can edit, run, and share this code. 
 * play.kotlinlang.org 
 */

fun main() {
    var x : Int = 12
    var y : Int = 12

     
    var board = generatePlayableBoard(x, y)
    print("OLD:\n")
    printBoard(board)
    
    
    for (i in 1..5){
        board = gameOfLife(board, x, y)
        print("\nNEW:\n")
		printBoard(board)
    }
    
}

fun gameOfLife(arr: Array<Array<Int>>, x: Int, y: Int) : Array<Array<Int>>{
	
    var new = Array(x) {Array(y) {0} }
    
    var i : Int = 1
	while (i < x-1) {
		var j : Int = 1
		while (j < y-1) {
			var counter: Int = livingNeighbours(arr, i, j)
            
            if(arr[i][j] == 0){
                if(counter == 3){
                    new[i][j] = 1
                }
            } else {
                if(counter == 2 || counter == 3){
                    new[i][j] = 1
                }
            }
            
            j++
		}
        i++
	}
    
    
    return new
}

fun generatePlayableBoard(x: Int, y: Int) : Array<Array<Int>>{
    var random = Random()
    
    var arr = Array(x) {Array(y) {random.nextInt(2)} }
    
    
    var i : Int = 0
	while (i < x) {
		var j : Int = 0
		while (j < y) {
			if(i == 0 || j == 0 || j == x - 1 || i == y - 1){
                arr[i][j] = 0
            }
            j++
		}
        i++
	}
    
    return arr
}

fun livingNeighbours(arr: Array<Array<Int>>, x: Int, y: Int): Int{
    var count: Int = 0
    var i : Int = x-1
	while (i <= x+1) {
		var j : Int = y-1
		while (j <= y+1) {
			if(!(i == x && j == y)){
                count += arr[i][j]
            }
            j++
		}
        i++
	}
    return count
}

fun printBoard(toPrint: Array<Array<Int>>){
    for (i in toPrint) {
        for(j in i){
            print(j.toString() + " ")
        }
        println()
    }
}