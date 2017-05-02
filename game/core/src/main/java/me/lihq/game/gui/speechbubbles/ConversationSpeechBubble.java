package me.lihq.game.gui.speechbubbles;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

import me.lihq.game.AssetLoader;
import me.lihq.game.people.AbstractPerson;

/**
 * Specialised speech bubble that displays a given string
 */

public class ConversationSpeechBubble extends SpeechBubble {
    /**
     * Constructs a speech bubble for conversation
     * @param speakingPerson the character that is speaking
     * @param dialogue the dialogue line
     */
    public ConversationSpeechBubble(AbstractPerson speakingPerson, String dialogue, AssetLoader assetLoader) {
        super(speakingPerson, assetLoader);

        nameLabel = new Label(speakingPerson.getName(), getSkin(), "speechName");

        Label dialogueLabel = new Label(dialogue, getSkin(), "speechDialogue");
        dialogueLabel.setWrap(true);
        dialogueLabel.setAlignment(Align.center);
        contentTable.add(dialogueLabel).align(Align.top).width(250);
    }
}
