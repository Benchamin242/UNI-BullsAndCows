package Tests;
import org.junit.*;
import org.junit.Test;
import static org.junit.Assert.*;
import Classes.Game;
import Classes.Player;
import Codes.SecretCode;
import Codes.NumbersCode;
import Codes.LettersCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class GameTests {

    private Player player;
    private SecretCode code;
    private Game game;
    private String filePath;

    //commented out bc @Before was acting funny
//    @Before
//    public void before(){
//        player = new Player("Test Testerson", 0, 0, 0, 0,0); }

/*
    @Test
    public void testConstructorWithCodeType() {
        Game game = new Game(player, "Letters");

        assertNotNull(game);
    }
*/

//    @Test
//    public void testConstructorWithoutCodeType() {
//        Game game = new Game(player,new NumbersCode());
//
//        assertNotNull(game);
//    }

    @Before
    public void initSG1() throws IOException {
        code = new NumbersCode(0,0);

        game = new Game("idiot","numbers");
        filePath = Paths.get("Bulls and Cows/src/testSave.txt").toAbsolutePath().toString();
        File file = new File(filePath);
        if(!file.exists()){
            file.createNewFile();
        }
    }

    //delete player name form the file before running test

    //save Game test, run once to append then again to replace
    @Test
    public void saveGameAppend() throws FileNotFoundException {
        String playername = "dumb";
        String code_compare = null;
        String guess_compare = null;
        String guess = "3214";
        game = new Game(new Player(playername,0,0,0,0,0));
        game.saveGame(Paths.get("Bulls and Cows/src/testSave.txt").toAbsolutePath().toString(),guess);
        int b = -1; int c = -1;

        Scanner sc = new Scanner(new File(filePath));

        String line;
        while(sc.hasNextLine()) {
            line = sc.nextLine();
            Scanner lines = new Scanner(line);
            if(lines.next().equals(playername)) {
                guess_compare = lines.next();
                code_compare = lines.next();
                b = lines.nextInt();
                c = lines.nextInt();
            }
        }

        assertEquals(guess_compare,guess);
        assertEquals(code_compare, game.getCode().decipheredCode);
        assertTrue(b >= 0 && c >= 0);

    }

    @Test
    public void testLoad() { //scenario 1
        String playername = "load";
        filePath = Paths.get("Bulls and Cows/src/testLoad.txt").toAbsolutePath().toString();
        game = new Game(new Player("toreplace",0,0,0,0,0));
        String guess = game.loadGame(filePath, playername);

        assertEquals("1234",guess);
        assertEquals(0, game.getCode().currentNumOfBulls);
        assertEquals(4,game.getCode().currentNumOfCows);

    }

    @Test
    public void testLoad1() { //scenario 2 & 3
        String playername = "notFound";
        filePath = Paths.get("src/testLoad.txt").toAbsolutePath().toString();
        game = new Game(new Player("toreplace",0,0,0,0,0));
        String guess = game.loadGame(filePath, playername);

        assertEquals("____", guess);

        filePath = "";
        game = new Game(new Player("toreplace",0,0,0,0,0));
        guess = game.loadGame(filePath, playername);

        assertEquals("____",guess);
    }



}

