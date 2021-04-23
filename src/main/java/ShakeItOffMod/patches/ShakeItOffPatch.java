package ShakeItOffMod.patches;

import ShakeItOffMod.util.TextureLoader;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.ShrugItOff;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;

import static ShakeItOffMod.ShakeItOffMod.makeCardPath;

/*
 * You will ***NEED*** to follow the official SpirePatch documentation here as you read through this patch.
 * https://github.com/kiooeht/ModTheSpire/wiki/SpirePatch
 * https://github.com/kiooeht/ModTheSpire/wiki/Matcher
 * Comments with quotations are taken from the documentation.
 *
 * This is a good time to Ctrl+Click on AbstractDungeon down there and Ctrl+f for returnRandomRelicKey() - that is the method that we will be patching.
 * Have a read through it's code. returnRandomRelicKey() is a method that is passed a rarity (relic tier) and returns the first relic
 * from the appropriate pool of that rarity (which are pre-shuffled), as well as removing it from the relic pool so that you never get it again.
 */

@SpirePatch(    // "Use the @SpirePatch annotation on the patch class."
        clz = ShrugItOff.class, // This is the class where the method we will be patching is. In our case - Abstract Dungeon
        method = SpirePatch.CONSTRUCTOR // This is the name of the method we will be patching.
)
public class ShakeItOffPatch {// Don't worry about the "never used" warning - *You* usually don't use/call them anywhere. Mod The Spire does.
    
    private static final Logger logger = LogManager.getLogger(ShakeItOffPatch.class.getName());

    @SpirePostfixPatch
    //"A patch method must be a public static method."
    public static void thisIsOurActualPatchMethod(
            ShrugItOff __instance) {

        String cardName = "Shake It Off";
        //Texture cardIMG = ImageMaster.loadImage(makeCardPath("TaylorSwift.png"));
        Texture cardIMG = TextureLoader.getTexture(makeCardPath("TaylorSwift.png"));

        __instance.name = cardName;
        __instance.originalName = cardName;

        /*
        try {
            Field f = AbstractCard.class.getDeclaredField("portraitImg");
            f.setAccessible(true);
            //f.set(__instance, cardIMG);
            f.set(__instance, null);
        } catch (NoSuchFieldException | IllegalAccessException | SecurityException e) {
            logger.info("Error changing card art");
        }
        */

        __instance.portrait = new AtlasRegion(cardIMG, 0, 0, cardIMG.getWidth(), cardIMG.getHeight());
        __instance.jokePortrait = __instance.portrait;

        logger.info("Editing card complete");
    }
}