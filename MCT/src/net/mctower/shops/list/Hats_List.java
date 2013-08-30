package net.mctower.shops.list;

import net.mctower.interfaces.ShopInterface;

import org.bukkit.ChatColor;

public class Hats_List {

	
	public static void load(){
		//1: PlayerName CaseSentive
		//2: Title of item
		//3: Lore Message
		//4: Price
		//Template:   ShopInterface.HatShop.addItem(Hats.gethat("", "", Hats.getLore(""), ));
		
		ShopInterface.HatShop.addItem(Hats.gethat("Notch", "Notch", Hats.getLore("&6The one and only NOTCH head!"), 12000));
		ShopInterface.HatShop.addItem(Hats.gethat("Jeb_", "Jeb", Hats.getLore("&6Jeb leader of mojang why not try his head on!"), 11600));
		ShopInterface.HatShop.addItem(Hats.gethat("captainsparklez", "CaptainSparklez", Hats.getLore("&5Play with the life of a Captain's Head"), 10000));
		ShopInterface.HatShop.addItem(Hats.gethat("ChoclateMuffin", "A Choclate Muffin", Hats.getLore("&3A Choclate Muffin that fits on your head."), 6000));
		
		ShopInterface.HatShop.addItem(Hats.gethat("Comcastt", "Squid Head", Hats.getLore("&7Make the squid come a live again on land."), 3100));
		ShopInterface.HatShop.addItem(Hats.gethat("SquareHD", "Dice Head", Hats.getLore("&7Use your head to roll the dice."), 6550));
		ShopInterface.HatShop.addItem(Hats.gethat("semmieeeeee", "Cow Head", Hats.getLore("&7Mooooooooo."), 4800));
		ShopInterface.HatShop.addItem(Hats.gethat("Cheemz", "SpiderMan", Hats.getLore("&7Does what ever a spiderman does."), 5600));
		ShopInterface.HatShop.addItem(Hats.gethat("citro30", "The Joker", Hats.getLore("&aDo you want to know why I use a knife?."), 5900));
	
	}
	
	
	
	
}
