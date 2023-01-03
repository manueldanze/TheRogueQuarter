package mainpackage.world;

import javafx.application.Platform;
import javafx.scene.media.AudioClip;
import mainpackage.itemSystem.Item;
import mainpackage.Settings;
import mainpackage.characterSystem.CharacterModel;
import mainpackage.characterSystem.CPUCharacter;

import static mainpackage.world.GameWorld.getWorld;

public enum CollisionType {

	TILE,
	HAZARD {
		@Override public void collide(CharacterModel subject, Sprite object) {
			subject.takeDamage(Settings.hazardDmg);
		}
	},

	PLAYER {
		@Override public void collide(CharacterModel subject, Sprite object) {
			for (CPUCharacter npc : getWorld().aiPlayers) {
				if (npc.getSprite() == object) {
					if (npc.isHostile()) {
						System.out.println(npc.getLevel());//for debugging
						subject.fight(npc);
						break;
					}
					Sound.talk.play();
					Platform.runLater(() -> NpcShopWindow.createShopWindow(npc));
					break;
				}
			}
		}
	},

	ITEM {
		@Override public void collide(CharacterModel subject, Sprite object) {
			if (subject != getWorld().playerInstance()) return;

			for (Item item : getWorld().items) {
				if (item.getSprite() == object) {
					getWorld().items.remove(item);
					getWorld().removeSprite(item.getSprite());
					getWorld().playerInstance().addToInventory(item);
					Sound.pickup.play();
					Platform.runLater(HUD::fillInventory);
					System.out.println("Picked up " + item.getName());
					HUD.switchText("Picked up " + item.getName());
					break;
				}
			}
		}
	},

	CHEST {
		@Override public void collide(CharacterModel subject, Sprite object) {
			if (subject != getWorld().playerInstance()) return;

			for (Sprite item : getWorld().allSprites) {
				if (item == object) {
					System.out.println("Touched chest");
				}
			}
		}
	},

	COIN {
		@Override public void collide(CharacterModel subject, Sprite object) {
			if (subject != getWorld().playerInstance()) return;

			for (Sprite item : getWorld().allSprites) {
				if (item == object) {
					Sound.coin.play();
					getWorld().removeSprite(item);
					getWorld().playerInstance().addCoin();
					HUD.updateStats();
				}
			}
		}
	},

	COIN_BAG {
		@Override public void collide(CharacterModel subject, Sprite object) {
			if (subject != getWorld().playerInstance()) return;

			for (Sprite item : getWorld().allSprites) {
				if (item == object) {
					Sound.coin.play();
					getWorld().removeSprite(item);
					getWorld().playerInstance().addCoin(5);
					HUD.updateStats();
				}
			}
		}
	},

	MISSION_COLLECT {
		@Override public void collide(CharacterModel subject, Sprite object) {
			if (subject != getWorld().playerInstance()) return;

			getWorld().removeSprite(object);
			getWorld().playerInstance().pickupSkull();
			if (getWorld().playerInstance().getCollectedSkulls() == MapGenerator.skullsToCollect()) {
				new AudioClip("file:src/main/resources/sounds/items/r_item2.wav").play();
				HUD.switchText("You collected all skulls, now find the exit!");
			}
			else Sound.collectible.play();
			HUD.updateStats();
		}
	},

	LADDER {
		@Override
		public void collide(CharacterModel subject, Sprite object) {
			if (subject != getWorld().playerInstance()) return;

			for (Sprite item : getWorld().allSprites) {
				if (item == object) {
					if (getWorld().playerInstance().getCollectedSkulls() != MapGenerator.skullsToCollect()) {
						HUD.switchText("Collect "+MapGenerator.skullsToCollect()+" Skulls of The Rogue-Guild-Members buried in this Dungeon\n to proof you are strong enough for the next level!");
					}else {
						Platform.runLater(() -> getWorld().changeLevel());
					}
				}
			}
		}
	},

	NONE;

	public void collide(CharacterModel subject, Sprite object) {}
}
