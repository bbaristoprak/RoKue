package main.Domain.Data;

import main.Domain.BuildHall;
import main.Domain.Enchantment.CloakOfProtection;
import main.Domain.Enchantment.Enchantment;
import main.Domain.Enchantment.LuringGem;
import main.Domain.Enchantment.Reveal;
import main.Domain.Game;
import main.Domain.HallObject.SuperObject;
import main.Domain.KeyHandler;
import main.Domain.Monster.Archer;
import main.Domain.Monster.Fighter;
import main.Domain.Monster.Wizard.Wizard;
import main.Domain.PlayerObj.Player;
import main.Domain.Tile.TileHandler;

import java.io.*;
import java.util.ArrayList;

public class SaveLoad {
    public static void saveGame(Game game, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("res/saveFiles/" + fileName + ".dat"))) {
            DataStorage ds = new DataStorage();
            //game data
            ds.hallCount = game.hallCount;
            ds.monsterTimer = game.monsterTimer;
            ds.enchantmentTimer = game.enchantmentTimer;
            ds.totalTime = game.totalTime;
            ds.remainingTime = game.remainingTime;
            ds.remainingTimePercentageGroup = game.remainingTimePercentageGroup;
            ds.runeLocation = new int[]{game.objectWithRune.getX(), game.objectWithRune.getY()};
            //tile handler data
            ds.mapTileNum0 = BuildHall.tileHandlers[0].mapTileNum;
            ds.mapTileNum1 = BuildHall.tileHandlers[1].mapTileNum;
            ds.mapTileNum2 = BuildHall.tileHandlers[2].mapTileNum;
            ds.mapTileNum3 = BuildHall.tileHandlers[3].mapTileNum;
            //player data
            ds.lives = game.player.lives;
            ds.X = game.player.X;
            ds.Y = game.player.Y;
            ds.invincibleTimer = game.player.invincibleTimer;
            ds.direction = game.player.direction;
            ds.numLuringGem = game.player.bag.getLuringGem();
            ds.numReveal = game.player.bag.getRevealCount();
            ds.numCloakOfProtection = game.player.bag.getCloakOfProtection();
            ds.bag = new ArrayList<>();
            for (Enchantment item: game.player.bag.enchantments) {
                ds.bag.add(item.toString());
            }
            //monster data
            ds.monsterIDs = new ArrayList<>();
            ds.monsterXs = new ArrayList<>();
            ds.monsterYs = new ArrayList<>();
            ds.monsterDrawDirections = new ArrayList<>();
            ds.actionLockCountersForFighters = new ArrayList<>();
            for (int i = 0; i < game.monsterList.size(); i++) {
                ds.monsterIDs.add(game.monsterList.get(i).getId());
                ds.monsterXs.add(game.monsterList.get(i).X);
                ds.monsterYs.add(game.monsterList.get(i).Y);
                ds.monsterDrawDirections.add(game.monsterList.get(i).drawDirection);
                if (game.monsterList.get(i).id == 1) {
                    ds.actionLockCountersForFighters.add(((Fighter) game.monsterList.get(i)).actionLockCounter);
                } else {
                    ds.actionLockCountersForFighters.add(-1);
                }
            }

            oos.writeObject(ds);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Game loadGame(String fileName) {
        Game loadedGame = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("res/saveFiles/" + fileName))) {
            DataStorage ds = (DataStorage) ois.readObject();
            loadedGame = new Game(ds.hallCount, true);

            //game data
            loadedGame.monsterTimer = ds.monsterTimer;
            loadedGame.enchantmentTimer = ds.enchantmentTimer;
            loadedGame.setTotalTime(ds.totalTime);
            loadedGame.setRemainingTime(ds.remainingTime);
            loadedGame.setRemainingTimePercentageGroup(ds.remainingTimePercentageGroup);
            //tile handler data
            BuildHall.tileHandlers[0] = new TileHandler();
            BuildHall.tileHandlers[1] = new TileHandler();
            BuildHall.tileHandlers[2] = new TileHandler();
            BuildHall.tileHandlers[3] = new TileHandler();
            BuildHall.tileHandlers[0].mapTileNum = ds.mapTileNum0;
            BuildHall.tileHandlers[1].mapTileNum = ds.mapTileNum1;
            BuildHall.tileHandlers[2].mapTileNum = ds.mapTileNum2;
            BuildHall.tileHandlers[3].mapTileNum = ds.mapTileNum3;
            loadedGame.tileHandler = BuildHall.tileHandlers[loadedGame.hallCount];
            loadedGame.setObjects(loadedGame.tileHandler.loadFromMapTile());
            for (SuperObject obj: loadedGame.objects) {
                if (obj.getX() == ds.runeLocation[0] && obj.getY() == ds.runeLocation[1]) {
                    obj.setHasRune(true);
                    loadedGame.objectWithRune = obj;
                }
            }
            //player data
            KeyHandler keyHandler = new KeyHandler(loadedGame);
            Player player = new Player(loadedGame, keyHandler, true);
            player.lives = ds.lives;
            player.X = ds.X;
            player.Y = ds.Y;
            player.invincibleTimer = ds.invincibleTimer;
            player.direction = ds.direction;
            loadedGame.player = player;
            player.bag.setLuringGemCount(ds.numLuringGem);
            player.bag.setRevealCount(ds.numReveal);
            player.bag.setCloakOfProtectionCount(ds.numCloakOfProtection);
            for (String item: ds.bag) {
                switch (item) {
                    case "Luring Gem":
                        player.bag.addLuringGem(new LuringGem(loadedGame, new int[]{0, 0}));
                        break;
                    case "Reveal Gem":
                        player.bag.addReveal(new Reveal(loadedGame, new int[]{0, 0}));
                        break;
                    case "Cloak of Protection":
                        player.bag.addCloakOfProtection(new CloakOfProtection(loadedGame, new int[]{0, 0}));
                        break;
                }
            }
            //monster data
            for (int i = 0; i < ds.monsterIDs.size();i++) {
                switch (ds.monsterIDs.get(i)) {
                    case 1:
                        int[] coordinates1 = new int[]{ds.monsterXs.get(i), ds.monsterYs.get(i)};
                        Fighter fighter = new Fighter(loadedGame, coordinates1);
                        fighter.drawDirection = ds.monsterDrawDirections.get(i);
                        fighter.actionLockCounter = ds.actionLockCountersForFighters.get(i);
                        loadedGame.monsterList.add(fighter);
                        break;
                    case 2:
                        int[] coordinates2 = new int[]{ds.monsterXs.get(i), ds.monsterYs.get(i)};
                        Archer archer = new Archer(loadedGame, coordinates2);
                        loadedGame.monsterList.add(archer);
                        break;
                    case 3:
                        int[] coordinates3 = new int[]{ds.monsterXs.get(i), ds.monsterYs.get(i)};
                        Wizard wizard = new Wizard(loadedGame, coordinates3);
                        loadedGame.monsterList.add(wizard);
                        break;
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return loadedGame;
    }
}
