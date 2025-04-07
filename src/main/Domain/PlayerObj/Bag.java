package main.Domain.PlayerObj;

import main.Domain.Enchantment.CloakOfProtection;
import main.Domain.Enchantment.Enchantment;
import main.Domain.Enchantment.LuringGem;
import main.Domain.Enchantment.Reveal;

import java.util.ArrayList;

public class Bag {
    public int numLuringGem = 0;
    public int numReveal = 0;
    public int numCloakOfProtection = 0;
    public Player player;
    public ArrayList<Enchantment> enchantments = new ArrayList<>();
    public Bag(Player player){
        this.player = player;
    }

    public int getLuringGem() {
        return numLuringGem;
    }

    public int getRevealCount() {
        return numReveal;
    }
    public int getCloakOfProtection() {
        return numCloakOfProtection;
    }

    public void addLuringGem(LuringGem luringGem){
        enchantments.add(luringGem);
        numLuringGem +=1;
    }

    public void addReveal(Reveal reveal){
        enchantments.add(reveal);
        numReveal +=1;
    }

    public void addCloakOfProtection(CloakOfProtection cloakOfProtection){
        enchantments.add(cloakOfProtection);
        numCloakOfProtection +=1;
    }

    public void luringGemUsed(){
        for (Enchantment enchantment : enchantments){
            if (enchantment instanceof LuringGem){
                enchantments.remove(enchantment);
                player.game.notifyEnchantmentRemoved(enchantment.toString());
                break;
            }
        }
        numLuringGem -=1;
    }

    public void revealUsed(){
        for (Enchantment enchantment : enchantments){
            if (enchantment instanceof Reveal){
                enchantments.remove(enchantment);
                player.game.notifyEnchantmentRemoved(enchantment.toString());
                break;
            }
        }
        numReveal -=1;
    }

    public void cloakOfProtectionUsed(){
        for (Enchantment enchantment : enchantments){
            if (enchantment instanceof CloakOfProtection){
                enchantments.remove(enchantment);
                player.game.notifyEnchantmentRemoved(enchantment.toString());
                break;
            }
        }
        numCloakOfProtection -=1;
    }

    public void setLuringGemCount(int numLuringGem) {
        this.numLuringGem = numLuringGem;
    }

    public void setRevealCount(int numReveal) {
        this.numReveal = numReveal;
    }

    public void setCloakOfProtectionCount(int numCloakOfProtection) {
        this.numCloakOfProtection = numCloakOfProtection;
    }

    public Reveal getReveal(){
        for (Enchantment enchantment : enchantments){
            if (enchantment instanceof Reveal){
                return (Reveal) enchantment;
            }
        }
        return null;
    }
    public LuringGem getLuring(){
        for (Enchantment enchantment : enchantments){
            if (enchantment instanceof LuringGem){
                return (LuringGem) enchantment;
            }
        }
        return null;
    }

    public void removeEnchantment(Enchantment enchantment){
        enchantments.remove(enchantment);
    }
}
